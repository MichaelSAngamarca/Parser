public class FloatNode extends Node{
    private double floatValue;

    public FloatNode(double floatValue){
        this.floatValue = floatValue;
    }

    public double getFloatValue(){
        return floatValue;
    }

    @Override
    public String toString() {
        return String.valueOf(floatValue);
    }
}
