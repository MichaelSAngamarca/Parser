public class MathOpNode extends Node{
    public enum TypeOfMathToken{
        ADD,SUBTRACT,MULTIPLY,DIVIDE
    }

    private TypeOfMathToken mathToken;
    private Node left;
    private Node right;

    public MathOpNode(TypeOfMathToken mathToken, Node left, Node right){
        this.left = left;
        this.right = right;
        this.mathToken = mathToken;

    }

    public Node getLeft(){
        return left;
    }
    public Node getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operationToString() + " " + right.toString() + ")";
    }

    private String operationToString(){
        switch (mathToken) {
            case ADD:
                return "+";
            case SUBTRACT:
                return "-";
            case MULTIPLY:
                return "*";
            case DIVIDE:
                return "/";
            default:
                throw new IllegalArgumentException("Unknown operation: " + mathToken);
        }
    }
}

