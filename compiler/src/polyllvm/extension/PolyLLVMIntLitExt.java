package polyllvm.extension;

import polyglot.ast.IntLit;
import polyglot.ast.Node;
import polyglot.util.SerialVersionUID;
import polyllvm.ast.PolyLLVMExt;
import polyllvm.visit.LLVMTranslator;

import static org.bytedeco.javacpp.LLVM.LLVMConstInt;
import static org.bytedeco.javacpp.LLVM.LLVMValueRef;

public class PolyLLVMIntLitExt extends PolyLLVMExt {
    private static final long serialVersionUID = SerialVersionUID.generate();

    @Override
    public Node translatePseudoLLVM(LLVMTranslator v) {
        IntLit n = (IntLit) node();
        assert n.type().isLongOrLess();

        v.debugInfo.emitLocation(n);

        LLVMValueRef res = LLVMConstInt(v.utils.typeRef(n.type()),
                                        n.value(), /* sign-extend */ 0);
        v.addTranslation(n, res);
        return super.translatePseudoLLVM(v);
    }
}
