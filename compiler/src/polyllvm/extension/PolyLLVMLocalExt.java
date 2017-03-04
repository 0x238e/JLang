package polyllvm.extension;

import polyglot.ast.Local;
import polyglot.ast.Node;
import polyglot.util.SerialVersionUID;
import polyllvm.ast.PolyLLVMExt;
import polyllvm.visit.LLVMTranslator;

import static org.bytedeco.javacpp.LLVM.LLVMBuildLoad;
import static org.bytedeco.javacpp.LLVM.LLVMValueRef;

public class PolyLLVMLocalExt extends PolyLLVMExt {
    private static final long serialVersionUID = SerialVersionUID.generate();

    @Override
    public Node translatePseudoLLVM(LLVMTranslator v) {
        Local n = (Local) node();
        v.debugInfo.emitLocation(n);

        LLVMValueRef val = LLVMBuildLoad(v.builder, v.getLocalVariable(n.name()), "load_" + n.name());
        v.addTranslation(n, val);
        return super.translatePseudoLLVM(v);
    }
}
