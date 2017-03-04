package polyllvm.ast;

import org.bytedeco.javacpp.LLVM;
import polyglot.ast.Ext;
import polyglot.ast.Ext_c;
import polyglot.ast.Node;
import polyglot.util.InternalCompilerError;
import polyglot.util.SerialVersionUID;
import polyllvm.visit.LLVMTranslator;

import static org.bytedeco.javacpp.LLVM.LLVMBuildCondBr;

public class PolyLLVMExt extends Ext_c implements PolyLLVMOps {
    private static final long serialVersionUID = SerialVersionUID.generate();

    public static PolyLLVMExt ext(Node n) {
        Ext e = n.ext();
        while (e != null && !(e instanceof PolyLLVMExt)) {
            e = e.ext();
        }
        if (e == null) {
            throw new InternalCompilerError("No PolyLLVM extension object for node "
                                            + n + " (" + n.getClass() + ")", n.position());
        }
        return (PolyLLVMExt) e;
    }

    @Override
    public final PolyLLVMLang lang() {
        return PolyLLVMLang_c.instance;
    }

    @Override
    public LLVMTranslator enterTranslatePseudoLLVM(LLVMTranslator v) {
        return v;
    }

    @Override
    public Node translatePseudoLLVM(LLVMTranslator v) {
        return node();
    }

    @Override
    public Node overrideTranslatePseudoLLVM(LLVMTranslator v) {
        return null;
    }

    @Override
    public void translateLLVMConditional(LLVMTranslator v,
                                         LLVM.LLVMBasicBlockRef trueBlock,
                                         LLVM.LLVMBasicBlockRef falseBlock) {
        Node n = v.visitEdge(null, node());
        v.debugInfo.emitLocation(node());
        LLVMBuildCondBr(v.builder, v.getTranslation(n), trueBlock, falseBlock);
    }
}
