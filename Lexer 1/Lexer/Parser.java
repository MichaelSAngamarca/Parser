import java.util.LinkedList;
import java.util.Optional;

public class Parser {
    private LinkedList<Token> parserTokens;
    private TokenManager tokenManager;

    public Parser(){
        this.parserTokens = new LinkedList<>();
        this.tokenManager = new TokenManager();
    }

    public boolean AcceptSeperators(){
        boolean foundSeperator = false;
        while(tokenManager.Peek(0).isPresent() && tokenManager.Peek(0).get().returnTokenType() == Token.TokenType.ENDOFLINE){
            tokenManager.MatchAndRemove(Token.TokenType.ENDOFLINE);
            foundSeperator = true;
        }
        return foundSeperator;
    }

    public Node parse(){
        return expression();
    }

    private Node expression(){
        Node left = factor();

        while (tokenManager.MoreTokens() &&
                (tokenManager.Peek(0).orElseThrow().returnTokenType() == Token.TokenType.PLUS ||
                        tokenManager.Peek(0).orElseThrow().returnTokenType() == Token.TokenType.MINUS)) {

            Token operator = tokenManager.MatchAndRemove(Token.TokenType.PLUS)
                    .or(() -> tokenManager.MatchAndRemove(Token.TokenType.MINUS))
                    .orElseThrow(); // We must have an operator

            Node right = term();
            left = new MathOpNode(operator.returnTokenType() == Token.TokenType.PLUS ? MathOpNode.TypeOfMathToken.ADD : MathOpNode.TypeOfMathToken.SUBTRACT, left, right);
        }
        return left;
    }

    private Node term(){
    }

    private Node factor(){

    }



}
