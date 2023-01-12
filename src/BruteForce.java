import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BruteForce {
    public static void main(String[] args) {
        ArrayList<Item> items = new ArrayList<>();
        File file = new File("src\\Items.txt");
        try{
           Scanner input = new Scanner(file);
           while(input.hasNextLine()){
               String[] itemData = input.nextLine().split(", ");
               items.add(new Item(itemData[0], Double.parseDouble(itemData[1]), Integer.parseInt(itemData[2])));
           }
           input.close();
        }catch (FileNotFoundException fnfe){
            System.out.println("File not found");
            
        }
        ArrayList<Item> combinations = new ArrayList<>();
        ArrayList<Item> copy = new ArrayList<>();
        for(int i = 0; i< items.size(); i++){
            combinations.add(new Item(items.get(i)));
        }
        for(int i = 0; i< items.size(); i++){
            copy.add(new Item(items.get(i)));
        }
        int bestFitness = getFitness(combinations);
        try{
        ArrayList<Item> bestCombination = getOptimalSet(items, combinations, bestFitness, copy);
        for (Item item : bestCombination) {
            System.out.println(item);
        }
        System.out.println(getFitness(bestCombination));
        }catch (InvalidArgumentException iae){
            System.out.println(iae.getStackTrace());
        }             
    }
   
    public static ArrayList<Item> getOptimalSet(ArrayList<Item> items, ArrayList<Item> bestCombination, int bestFitness, ArrayList<Item> copy) throws InvalidArgumentException{
        if(items.size()>=10){
            throw new InvalidArgumentException();
        }
        updateBestCombination(copy, bestCombination);
        if(copy.size()<=1){
            return copy;
        }
        
        for(int i =0 ; i < copy.size(); i++){
            Item item = new Item(copy.remove(i));
            getOptimalSet(items,bestCombination, bestFitness, copy);
            copy.add(item);
            //remove ith item and recursively call getOptimalSet on the remaining item
        }
        return bestCombination;        
    }
    public static void updateBestCombination(ArrayList<Item> items, ArrayList<Item> bestCombination){
        //change the contents of "bestCombination" if a combination with better fitness is found
        if(getFitness(items) > getFitness(bestCombination)){
            bestCombination.clear();
            for(int i = 0; i< items.size(); i++){
                bestCombination.add(new Item(items.get(i)));
            }
        }
        return;
    }
    public static int getFitness(ArrayList<Item> items){
        int sumValue =0;
        double sumWeight = 0;
        for(int i =0; i < items.size(); i++){
            sumWeight += items.get(i).getWeight();
            sumValue += items.get(i).getValue();
        }
        if(sumWeight> 10){
            return 0;
        }
        return sumValue;
    }
}
