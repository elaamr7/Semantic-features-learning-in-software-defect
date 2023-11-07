package Parser;

import java.util.List;

import org.eclipse.jdt.core.dom.*;
public class JavaAST {
    public static void main(String[] args) {
        String sourceCode = "if (x > 0) { System.out.println(\"x is positive\"); }";

        ASTParser parser = ASTParser.newParser(AST.JLS14);
        parser.setSource(sourceCode.toCharArray());
        parser.setKind(ASTParser.K_STATEMENTS);
        Block block = (Block) parser.createAST(null);

        JavaAST3 visitor = new JavaAST3();
        block.accept(visitor);

        List<String> tokens = visitor.getTokens();
        System.out.println(tokens);
    }
}
