package polyllvm.ast.PseudoLLVM;

import java.util.List;

public interface LLVMFunction extends LLVMNode {
    /**
     * Return a list of all blocks in this function
     */
    List<LLVMBlock> blocks();

    /**
     * Return a new function with blocks {@code bs}
     */
    LLVMFunction blocks(List<LLVMBlock> bs);
}