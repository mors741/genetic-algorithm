package main;

import java.util.ArrayList;
import java.util.List;
import chromosome.*;
import problem.Problem;
  
  
public class GA {  
  
    public static final int POPULATION_SIZE = 100;
    private static final int GENERATION_SPAN = 1000; 
    public static final double CROSSOVER_RATE = 0.5;  
    public static final double MUTATION_RATE = 0.2;
    
    public static final double INIT_RAND_RATE = 0.9;
    public static final double EUCLIDEAN_RADIUS = 2.5;
      
  
    void initializePopulation(List<Chromosome> population) {
    	int i;
    	for (i = 0; i<POPULATION_SIZE * INIT_RAND_RATE; i++) {
    		Chromosome chr = new Chromosome();
    		chr.generateRandomChromosome();
    		
    		population.add(chr);
    		
    		//System.out.println(i+":  "+ chr);
    	}
    	//System.out.println("------");
    	while (i < POPULATION_SIZE){
    		Chromosome chr = new Chromosome();
    		chr.generateGreedyChromosome();
    		
    		population.add(chr);
    		
    		//System.out.println(i+":  "+ chr);
    		i++;
    	}
  
    }
    
    public void go() {
    	double[][] testData = {
    		    {40.00, 50.00, 0.00,  0.00,   236.00, 0.00 }, // depot
    		    {45.00, 68.00, 10.00, 912.00, 967.00, 90.00}, // 0
    		    {45.00, 70.00, 30.00, 825.00, 870.00, 90.00}, // 1
    		    {42.00, 66.00, 10.00, 65.00,  146.00, 90.00},
    		    {42.00, 68.00, 10.00, 727.00, 782.00, 90.00},
    		    {42.00, 65.00, 10.00, 15.00,  67.00,  90.00},
    		    {40.00, 69.00, 20.00, 621.00, 702.00, 90.00},
    		    {40.00, 66.00, 20.00, 170.00, 225.00, 90.00},
    		    {38.00, 68.00, 20.00, 255.00, 324.00, 90.00},
    		    {38.00, 70.00, 10.00, 534.00, 605.00, 90.00}, // 8
    	};
    	Problem.Init(testData, 200);
    	//System.out.println(Problem.getCustomer(8).getDueDate());
    	//System.out.println(Problem.getDepot().getY());
    	
    	List<Chromosome> population = new ArrayList<Chromosome>(POPULATION_SIZE); 
    	initializePopulation(population);
    	
    	System.out.println(population.get(99));
    	population.get(99).evaluateRoutes();
    	System.out.println(population.get(99).toRouteString());   	
    	
    	//for (Chromosome chr : population){
    	//	System.out.println(chr.toString());
    	//}
    	//Point p = Problem.getCustomer(8);
    	//System.out.println("Neares: "+ p.getNearestCustomerId());
    	
    	//Chromosome chr = new Chromosome();
    	//chr.generateGreedyChromosome();

    	
    }
  
    public static void main(String[] args) {  
    	
    	new GA().go();  
   }  
  
}  