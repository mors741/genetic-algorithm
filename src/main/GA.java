package main;

import individual.*;

import java.util.ArrayList;
import java.util.List;

import problem.Problem;
  
  
public class GA {  
  
    public static final int POPULATION_SIZE = 100;
    private static final int GENERATION_SPAN = 1000; 
    public static final double CROSSOVER_RATE = 0.5;  
    public static final double MUTATION_RATE = 0.2;
    
    public static final double INIT_RAND_RATE = 0.9;
    public static final double EUCLIDEAN_RADIUS = 2.5;
      
  
    void initializePopulation(List<Individual> population) {
    	int i;
    	IndividualFactory chromosomeFactory = new IndividualFactory();
    	for (i = 0; i < POPULATION_SIZE * INIT_RAND_RATE; i++) {
    		population.add(chromosomeFactory.generateRandomChromosome());
    	}
    	while (i < POPULATION_SIZE){		
    		population.add(chromosomeFactory.generateGreedyChromosome());
    		i++;
    	}
    }
    
    void showPopulation(List<Individual> population) {
    	for (int i = 0; i<POPULATION_SIZE; i++) {
    		System.out.println(i+": "+population.get(i));
    		if (i == 89) {
    			System.out.println("-----------------");
    		}
    	}
    }
    
    public void go() {
    	double[][] testData = {
    		    {40.00, 50.00, 0.00,  0.00,	  1236.00, 0.00 }, // depot
    		    {45.00, 68.00, 10.00, 912.00, 967.00,  90.00}, // 0
    		    {45.00, 70.00, 30.00, 825.00, 870.00,  90.00}, // 1
    		    {42.00, 66.00, 10.00, 65.00,  146.00,  90.00},
    		    {42.00, 68.00, 10.00, 727.00, 782.00,  90.00},
    		    {42.00, 65.00, 10.00, 15.00,  67.00,   90.00},
    		    {40.00, 69.00, 20.00, 621.00, 702.00,  90.00},
    		    {40.00, 66.00, 20.00, 170.00, 225.00,  90.00},
    		    {38.00, 68.00, 20.00, 255.00, 324.00,  90.00},
    		    {38.00, 70.00, 10.00, 534.00, 605.00,  90.00}, // 8
    	};
    	Problem.Init(testData, 200);
    	//System.out.println(Problem.getCustomer(8).getDueDate());
    	//System.out.println(Problem.getDepot().getY());
    	
    	List<Individual> population = new ArrayList<Individual>(POPULATION_SIZE); 
    	initializePopulation(population);
    	//showPopulation(population);
    	int i = 0;
    	for (Individual indiv : population) {
        	//System.out.print(i++ + ": " + indiv + "-> ");
        	indiv.evaluateRoutes();
        	System.out.print(i++ + ": " + indiv + "\n");
    	}
    	System.out.println("--mutation--");
    	
    	population.get(99).mutate();
    	System.out.println("99: " + population.get(99));
    	
    	//for (Chromosome chr : population){
    	//	System.out.println(chr.toString());
    	//}
    	//Point p = Problem.getCustomer(8);
    	//System.out.println("Nearest: "+ p.getNearestCustomerId());
    	
    	//Chromosome chr = new Chromosome();
    	//chr.generateGreedyChromosome();

    	
    }
  
    public static void main(String[] args) {  
    	
    	new GA().go();  
   }  
  
}  