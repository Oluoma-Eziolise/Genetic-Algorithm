public class Item {
    private final String name;
    private final int value;
    private final double weight;
    private boolean included;
    public Item(String name, double weight, int value){
        this.name = name;
        this.value = value;
        this.weight = weight;
        this.included = false;
    }
    public Item(Item other){
        this.name = other.name;
        this.value = other.value;
        this.weight = other.weight;
        this.included = other.included;
    }
    public double getWeight(){
        return weight;
    } 
    public int getValue(){
        return value;
    }
    public boolean isIncluded(){
        return included;
    }
    public void setIncluded(boolean included){
        this.included = included;
    }
    public String toString(){
        String itemToString = "<" + name+ "> (<" + weight+ "> lbs, $<" + value+">)";
        return itemToString + " included? " + isIncluded() + "\n";
    }
}
