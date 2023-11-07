package Parser;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MethodCall {

    public static void generateMethodCallSequence(byte[] bytecode) {

    	try {
            ClassReader cr = new ClassReader(bytecode);
            MethodCallSequenceVisitor visitor = new MethodCallSequenceVisitor(Opcodes.ASM4);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cr.accept(visitor, ClassReader.EXPAND_FRAMES);
        } catch (Exception e) {
            // Handle other exceptions (e.g., IllegalArgumentException)
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
 
        try {
            Path filePath = Path.of("src/Parser/Token.java");
            byte[] originalBytecode = Files.readAllBytes(filePath);

            // Generate and print method-call sequence
            generateMethodCallSequence(originalBytecode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
