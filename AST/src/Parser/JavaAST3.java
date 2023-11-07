package Parser;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class JavaAST3 extends ASTVisitor {
    private List<String> tokens = new ArrayList<>();

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

            @Override
            public boolean visit(NumberLiteral node) {
                tokens.add(node.getToken());
                return false;
            }
            
            @Override
            public boolean visit(InfixExpression node) {
                tokens.add(node.getOperator().toString());
                return false;
            }

            // Add more cases for other types of expressions if needed

        });

        // Handle the "then" block
        Statement thenStatement = node.getThenStatement();
        if (thenStatement instanceof Block) {
            tokens.add("block");
        } else {
            tokens.add("statement");
        }

        return false;
    }

    public List<String> getTokens() {
        return tokens;
    }
}
