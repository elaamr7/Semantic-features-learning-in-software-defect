package Parser;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import Parser.JavaAST3.TokenNode;

class Token {
	
	public static void main(String[] args) {
        String sourceCode = "public class Example { int x = 10; void foo() { System.out.println(x); } }";
        ASTParser parser = ASTParser.newParser(AST.JLS15);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(sourceCode.toCharArray());

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        JavaAST3 tokenCollector = new JavaAST3();
        cu.accept(tokenCollector);

        List<TokenNode> tokenNodes = tokenCollector.getTokenNodes();
        for (TokenNode tokenNode : tokenNodes) {
            System.out.println("Token: " + tokenNode.getToken() +
                    " Start: " + tokenNode.getStart() +
                    " End: " + tokenNode.getEnd());
        }
    }
    
}

