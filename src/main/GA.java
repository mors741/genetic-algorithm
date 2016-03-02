package main;

import java.util.Arrays;

import problem.Problem;
  
  
public class GA {  
  
    public static final int POPULATION_SIZE = 300;
    public static final int GENERATION_SPAN = 350;
    public static final double CROSSOVER_RATE = 0.8;  
    public static final double MUTATION_RATE = 0.1;
    
    public static final double INIT_RAND_RATE = 0.9;
    public static final double EUCLIDEAN_RADIUS = 2.5;
    
    public void run(String resource, int customers) {
    	System.out.print(resource + ": " + customers + " - ");
    	Problem.Init(resource, customers);
    	
    	Population population = new Population();    	
    	population.initialize();
    	
    	long time = System.currentTimeMillis();
    	
    	for (int i = 0; i < GENERATION_SPAN; i++) {
    		population.evaluateRoutes();		
    		population.determineParetoRanks();
    		
//    		System.out.println(" -------------------------------------" +i+" -------------------------------------");
//    		population.showOptimal();
        	
    		population.mate();		
    		population.mutation();
    		
    		population.backToChromosome();
    		
    	}
    	System.out.println(System.currentTimeMillis() - time);  	

    }
  
    public static void main(String[] args) {
    	GA algorithm = new GA();
    	for (String test : Arrays.asList("resources/C201.csv", "resources/R201.csv","resources/RC201.csv")) {
    		for (int i = 1; i <= 10; i++) {
        		algorithm.run(test, 10*i);   		
        	}
    	}   	
   }  
}  