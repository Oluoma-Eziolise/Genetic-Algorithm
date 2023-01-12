import java.util.ArrayList;
import java.util.Collections;

public class GeneticThread extends Thread{
    private Chromosome fittestChromosome;
    private int start;
    private int end;
    private ArrayList<Chromosome> initialPop;
    private double mutationRate;
    private int numOfEpochs;
    private int numOfThreads;
    public GeneticThread(){

    }
    public GeneticThread(ArrayList<Chromosome> initialPop, int start, int end, double mutationRate, int numOfEpochs, int numOfThreads){
        this.initialPop =initialPop;
        this.start= start;
        this.end = end;
        this.mutationRate = mutationRate;
        this.numOfEpochs = numOfEpochs;
        this.numOfThreads = numOfThreads;
    }
    public void run(){
        ArrayList<Chromosome> firstGen = new ArrayList<>();
        for(int i = start; i< end; i++){
            firstGen.add(new Chromosome(initialPop.get(i)));
        }//create firtGen by copying over the chromosomes this thread is responsible for
        
        for(int i =0 ; i < numOfEpochs/numOfThreads; i++){
            ArrayList<Chromosome> nextGen = new ArrayList<>();
            for(int j = 0; j< firstGen.size();j++){
                nextGen.add(firstGen.get(j));
                //adding the initial population to the next generation
            }

            Collections.shuffle(nextGen);
            int nextGenSize =nextGen.size();
            for(int j=0; j<nextGenSize;j+=2){
                if(firstGen.size()>1){
                    Chromosome child = nextGen.get(j).crossover(nextGen.get(j+1));
                    nextGen.add(child);
                }
            }//creates and adds children to "nextGen"

            Collections.shuffle(nextGen);
            for(int j =0; j < nextGen.size()*mutationRate; j++){
                nextGen.get(j).mutate();
            }
            //gets 10% of the population and exposes them to mutation
            Collections.sort(nextGen);// sort individuals according to fitness

            firstGen.clear();
            int nextGenSize2 = nextGen.size();
            for(int j=nextGenSize2-1; j>9; j--){
                nextGen.remove(j);
            }//clear current population and adds top ten of "nextGen" back into the population
            Collections.sort(nextGen);
            for(int j=0; j< nextGen.size(); j++){
                firstGen.add(nextGen.get(j));
            }
        }
        Collections.sort(firstGen);
        fittestChromosome = firstGen.get(0);
        
    }
    public Chromosome getFittestChromosome(){
        return fittestChromosome;
    }
    public ArrayList<Chromosome> getInitialPop(){
        return initialPop;
    }
    public int getStart(){
        return start;
    }
    public int getEnd(){
        return end;
    }
}
