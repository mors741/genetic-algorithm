package main;

import problem.Problem;
  
  
public class GA {  
  
    public static final int POPULATION_SIZE = 300;
    public static final int GENERATION_SPAN = 350;
    public static final double CROSSOVER_RATE = 0.8;  
    public static final double MUTATION_RATE = 0.1;
    
    public static final double INIT_RAND_RATE = 0.9;
    public static final double EUCLIDEAN_RADIUS = 2.5;
    
    public void go() {
    	Problem.Init("resources/C101.csv", 100);
    	
    	Population population = new Population();    	
    	population.initialize();
    	for (int i = 0; i < GENERATION_SPAN; i++) {
    		population.evaluateRoutes();
    		population.determineParetoRanks();
    		
    		System.out.println(i+" ------------------------------------------");
    		population.showOptimal();
        	
    		population.mate();
    		population.mutation();
    		population.backToChromosome();
    	}

    }
  
    public static void main(String[] args) {  
    	
    	new GA().go();  
   }  
  
}  