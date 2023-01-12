import java.util.ArrayList;
import java.util.Random;
public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome>{
    private static Random rng = new Random();
    public static long dummy =0;
    public Chromosome(){
    }
    public Chromosome(ArrayList<Item> items){
        for(int i =0; i< items.size(); i++){
            Item item = new Item(items.get(i));
            this.add(item);
            int rand = 1+rng.nextInt(10);
            if(rand <=5){
                this.get(i).setIncluded(false);
            }else{
                this.get(i).setIncluded(true);
            }
        }
    }
    public Chromosome crossover(Chromosome other){
        Chromosome child = new Chromosome();
        for(int i =0; i< other.size(); i++){
            int rand = 1+rng.nextInt(10);
            if(rand<=5){
                child.add(new Item(this.get(i)));
                //adds the corresponding item to the child chromosome
            }else{
                child.add(new Item(other.get(i)));
            }
        }
        return child;
    }
    public void mutate(){
        for(int i =0; i< this.size(); i++){
            int rand = 1+rng.nextInt(10);
            if(rand == 1){
                this.get(i).setIncluded(!this.get(i).isIncluded());
                //if item is included, it is no longer included and vice versa
            }
        }
    }
    public int getFitness(){
        dummy = 0; 
        for (int i=0; i<this.size()*1000; i++) {
            dummy += i;
        }
        int sumValue =0;
        double sumWeight = 0;
        for(int i =0; i < this.size(); i++){
            if(this.get(i).isIncluded()){
                sumWeight += this.get(i).getWeight();
                sumValue += this.get(i).getValue();
            }
        }
        if(sumWeight> 10){
            return 0;
        }
        return sumValue;
    }
    public int compareTo(Chromosome other){
        if(this.getFitness()>other.getFitness()){
            return -1;
        }else if(this.getFitness()<other.getFitness()){
            return 1;
        }
        return 0;
    }
    public String toString(){
        String chromosomeString = "";
        for(int i =0; i< this.size(); i++){
            if(this.get(i).isIncluded()){
                chromosomeString += this.get(i).toString() + " ";
            }
        }
        return chromosomeString;
    }
}
