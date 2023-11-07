package Parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.*;

public class JavaAST2 {
	public static String getFileContent(String filePath) throws FileNotFoundException,IOException{
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while(line !=null){
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		return sb.toString();
	}
    public static List<String> tokenizeMethods(String sourceCode) throws FileNotFoundException,IOException  {
        ASTParser parser = ASTParser.newParser(AST.JLS15);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(sourceCode.toCharArray());
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        List<String> tokens = new ArrayList<>();

        cu.accept(new ASTVisitor() {
        	
        	
        	@Override
            public boolean visit(IfStatement node) {
                tokens.add("if");

                Expression expression = node.getExpression();
                // Visit the expression and collect its tokens
                expression.accept(new ASTVisitor() {
                    @Override
                    public boolean visit(SimpleName name) {
                        tokens.add(name.getIdentifier());
                        return false;
                    }

                });

                // Handle the "then" block
                Statement thenStatement = node.getThenStatement();
                if (thenStatement instanceof Block) {
                    tokens.add("block");
                } else {
                    tokens.add("statement");
                }

                return true;
            }
        	@Override
            public boolean visit(MethodInvocation node) {
                Expression expression = node.getExpression();
                if (expression != null && expression instanceof ThisExpression) {
                    tokens.add("this");
                }

                SimpleName methodName = node.getName();
                tokens.add(methodName.getIdentifier());

                return true;
            }

        	
        	@Override
            public boolean visit(SimpleName node) {
                tokens.add(node.getIdentifier());
                return true;
            }
        	@Override
            public boolean visit(ReturnStatement node) {
                tokens.add("return");

                // Visit the expression inside the return statement and collect its tokens
                Expression expression = node.getExpression();
                if (expression != null) {
                    expression.accept(new ASTVisitor() {
                        @Override
                        public boolean visit(SimpleName name) {
                            tokens.add(name.getIdentifier());
                            return false;
                        }


                        // Add more cases for other types of expressions if needed

                    });
                }

                return true;
            }
        	
        	
        });

        return tokens;
    }
    public static void main(String[] args) throws FileNotFoundException,IOException{
    	String filepath = "src/Parser/Point.java";
        String sourceCode = getFileContent(filepath);
        List<String> tokens = tokenizeMethods(sourceCode);
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(tokens.get(i));
        }
    }
}
