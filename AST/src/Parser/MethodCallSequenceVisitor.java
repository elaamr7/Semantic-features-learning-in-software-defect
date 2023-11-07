package Parser;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodCallSequenceVisitor extends ClassVisitor {

    public MethodCallSequenceVisitor(int api) {
        super(api);
    }

    public MethodCallSequenceVisitor(int api, ClassVisitor cv) {
        super(api, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

        // Create a MethodCallVisitor for the current method
        MethodCallVisitor methodCallVisitor = new MethodCallVisitor(api, mv, name);
        return methodCallVisitor;
    }

    static class MethodCallVisitor extends MethodVisitor {

        private final String methodName;

        public MethodCallVisitor(int api, MethodVisitor mv, String methodName) {
            super(api, mv);
            this.methodName = methodName;
        }

        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            // Output method-call sequence
            System.out.println(methodName + " calls " + owner + "." + name);
            super.visitMethodInsn(opcode, owner, name, descriptor);
        }
    }
}
