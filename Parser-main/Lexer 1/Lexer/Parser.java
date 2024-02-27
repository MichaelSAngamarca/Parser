import java.util.LinkedList;
import java.util.Optional;

public class Parser {
    LinkedList<Token> parserTokens;
    private TokenManager tokenManager;

    public Parser(LinkedList<Token> parserTokens){
        this.parserTokens = new LinkedList<>();
        this.tokenManager = new TokenManager();
        this.parserTokens = parserTokens;
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

    public  Node expression(){
        var left = term();
        Optional<Token> plus;
        Optional<Token> minus;

        while(true){
            plus = tokenManager.MatchAndRemove(Token.TokenType.PLUS);
            minus = tokenManager.MatchAndRemove(Token.TokenType.MINUS);


            if(plus.isPresent()){
                Node right = term();
                left = new MathOpNode(MathOpNode.TypeOfMathToken.ADD,left,right);
                
            } else if (minus.isPresent()) {
                Node right = term();
                left = new MathOpNode(MathOpNode.TypeOfMathToken.SUBTRACT,left,right);
                
            }
            else{
                break;
            }
        }
        return left;
    }

    public Node term(){
        var left = factor();
        Optional<Token> multiply;
        Optional<Token> divide;

        while(true){
            multiply = tokenManager.MatchAndRemove(Token.TokenType.TIMES);
            divide = tokenManager.MatchAndRemove(Token.TokenType.DIVIED);

            if(multiply.isPresent()){
                Node right = factor();
                left = new MathOpNode(MathOpNode.TypeOfMathToken.MULTIPLY,left,right);

            } else if (divide.isPresent()) {
                Node right = factor();
                left = new MathOpNode(MathOpNode.TypeOfMathToken.DIVIDE,left,right);

            }
            else{
                break;
            }
        }
        return left;

    }


    public Node factor(){
        if(tokenManager.Peek(0).isPresent()){
            Token currentToken = tokenManager.Peek(0).get();
            if(currentToken.returnTokenType() == Token.TokenType.NUMBER){
                Token numberToken = tokenManager.MatchAndRemove(Token.TokenType.NUMBER).orElseThrow(() -> new RuntimeException("Expected A Number"));
                String numberValue = String.valueOf(numberToken.returnTokenType());
                if(numberValue.contains(".")){
                    double floatValue = Double.parseDouble(numberValue);
                    return new FloatNode(floatValue);
                }
                else{
                    int intValue = Integer.parseInt(numberValue);
                    return new IntegerNode(intValue);
                }
            } else if (currentToken.returnTokenType() == Token.TokenType.LPAREN) {
                tokenManager.MatchAndRemove(Token.TokenType.LPAREN);
                Node expressionNode = expression();
                tokenManager.MatchAndRemove(Token.TokenType.RPAREN);
                return expressionNode;
                
            }
        }
        throw new RuntimeException("Expected A Number or '(' ");
    }


    private MathOpNode.TypeOfMathToken convertTokenTypeToMathType(Token.TokenType tokenType) {
        switch (tokenType) {
            case PLUS:
                return MathOpNode.TypeOfMathToken.ADD;
            case MINUS:
                return MathOpNode.TypeOfMathToken.SUBTRACT;
            case TIMES:
                return MathOpNode.TypeOfMathToken.MULTIPLY;
            case DIVIED:
                return MathOpNode.TypeOfMathToken.DIVIDE;
            default:
                throw new IllegalArgumentException("Unknown operation: " + tokenType);
        }
    }



}
