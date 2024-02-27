public class IntegerNode extends Node {

    private int integerValue;

    public IntegerNode(int value){
        this.integerValue = value;
    }

    public int getValue(){
        return integerValue;
    }

    @Override
    public String toString() {
        return String.valueOf(integerValue);
    }
}
