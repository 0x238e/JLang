package polyllvm.types;

import java.util.*;

import polyglot.ext.jl5.types.*;
import polyglot.ext.jl7.types.JL7TypeSystem_c;
import polyglot.types.*;

public class PolyLLVMTypeSystem_c extends JL7TypeSystem_c
		implements PolyLLVMTypeSystem {

	@Override
	public SubstMethodInstance substMethodInstance(JL5MethodInstance postSubst,
			JL5MethodInstance preSubst, JL5Subst subst) {
		return new SubstMethodInstance_c(postSubst, preSubst, subst);
	}

	/**
	 * This overriding refines the return type to {@link SubstMethodInstance}.
	 * 
	 * @see JL5TypeSystem_c#methodCallValid(JL5MethodInstance, String, List,
	 *      List, Type)
	 */
	@Override
	public SubstMethodInstance methodCallValid(JL5MethodInstance mi,
			String name, List<? extends Type> argTypes,
			List<? extends ReferenceType> actualTypeArgs,
			Type expectedReturnType) {
		if (actualTypeArgs == null) {
			actualTypeArgs = Collections.emptyList();
		}

		// First check that the number of arguments is reasonable
		if (argTypes.size() != mi.formalTypes().size()) {
			// the actual args don't match the number of the formal args.
			if (!(mi.isVariableArity()
					&& argTypes.size() >= mi.formalTypes().size() - 1)) {
				// the last (variable) argument can consume 0 or more of the
				// actual arguments.
				return null;
			}

		}
		JL5Subst subst = null;
		if (!mi.typeParams().isEmpty()) {
			if (actualTypeArgs.isEmpty()) {
				// need to perform type inference
				subst = inferTypeArgs(mi, argTypes, expectedReturnType);
			} else {
				// type arguments are provided by programmer
				Map<TypeVariable, ReferenceType> m = new HashMap<>();
				Iterator<? extends ReferenceType> iter = actualTypeArgs
						.iterator();
				for (TypeVariable tv : mi.typeParams()) {
					m.put(tv, iter.next());
				}
				subst = (JL5Subst) this.subst(m);
			}
		} else if (mi.typeParams().isEmpty()) {
			if (actualTypeArgs.isEmpty()) {
				subst = (JL5Subst) subst(Collections.emptyMap());
			} else {
				// no type parameter, but type args are given
				return null;
			}
		}
		if (subst != null) {
			// check that the substitution satisfies the bounds
			for (TypeVariable tv : subst.substitutions().keySet()) {
				Type a = subst.substitutions().get(tv);
				Type substUpperBound = subst.substType(tv.upperBound());
				if (!isSubtype(a, substUpperBound)) {
					return null;
				}
			}
			JL5MethodInstance postSubst = subst.substMethod(mi);
			if (name.equals(mi.name()) && super.callValid(postSubst, argTypes))
				return substMethodInstance(postSubst, mi, subst);
			else
				return null;
		} else { // type inference failed
			return null;
		}
	}

}