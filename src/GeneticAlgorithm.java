/*Oluomachukwu Eziolise
CS 1181 07
Project 4 

Performance Data using 100,000 epochs
1 thread- 271 seconds, fitness 7600
10 threads- 56 seconds, fitness 7600
20 threads- 45 seconds, fitness 7500
30 threads- 46 seconds, fitness 7600
40 threads- 43 seconds, fitness 7600
50 threads- 41 seconds, fitness 7600
Average Fitness - 7583
*/
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class GeneticAlgorithm {
    public static final int POP_SIZE = 100; 
    public static final int NUM_EPOCHS = 50000;
    public static final int NUM_THREADS = 15;
    public static ArrayList<Item> readData(String filename) throws FileNotFoundException{
        ArrayList<Item> items = new ArrayList<>();
        java.io.File file = new java.io.File(filename);
        Scanner reader = new Scanner(file);
        while(reader.hasNextLine()){
            String line = reader.nextLine();
            String[] itemSplit = line.split(", ");
            double itemWeight = Double.parseDouble(itemSplit[1]);
            int itemValue = Integer.parseInt(itemSplit[2]);
            Item item = new Item(itemSplit[0], itemWeight, itemValue);
            items.add(item);
        }

        reader.close();
        return items;
    }
    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int populationSize){
        ArrayList<Chromosome> chromosomes = new ArrayList<>();
            for(int i =0;i < populationSize;i++){
                Chromosome chromosome = new Chromosome(items);
                chromosomes.add(chromosome);
            }
        return chromosomes;
    }
    public static void main(String[] args) throws FileNotFoundException{
        long startTime = System.currentTimeMillis();
        int increment = POP_SIZE/NUM_THREADS;
        double mutationRate = 0.1;
        
        ArrayList<Item> items = new ArrayList<>();
        try{
        items = readData("src\\Items.txt");//read data from Items.txt
        }catch(FileNotFoundException fnfe){
            System.out.println("File was not found!");
            return;
        }

        ArrayList<GeneticThread> threads = new ArrayList<>();
        ArrayList<Chromosome> firstGen = initializePopulation(items, POP_SIZE);
        int start= 0;
        int end = increment;
        for( int i = 0; i < NUM_THREADS; i++){
            ArrayList<Chromosome> copyOfInitialPop = new ArrayList<>();
            for(int j =0; j < firstGen.size(); j++){
                copyOfInitialPop.add(new Chromosome(firstGen.get(j)));
            }//pass each thread a copy of the initial population
            GeneticThread geneticThread = new GeneticThread(copyOfInitialPop, start, end, mutationRate, NUM_EPOCHS, NUM_THREADS);
            start += increment;
            end +=increment;
            threads.add(geneticThread);
            geneticThread.start();
        }
        for(int i =0; i < NUM_THREADS; i++){
            try{
                threads.get(i).join();
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
        Chromosome fittestChromosome = new Chromosome();
        int fitness = 0;
        for(int i =0; i< NUM_THREADS; i++){
            Chromosome chromosome = threads.get(i).getFittestChromosome();
            if(chromosome.getFitness()>=fitness){
                fitness = chromosome.getFitness();
                fittestChromosome= chromosome;
            }
        }
        System.out.println(fittestChromosome.toString());
        System.out.println("The fitness is " + fitness);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime -startTime);
    }
}
