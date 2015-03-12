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
    	
    	Population population = new Population(); 
    	
    	population.initialize();
    	for (int i = 0; i < GENERATION_SPAN; i++) {
    		population.evaluateRoutes();
    		population.determineParetoRanks();
    		
    		population.showInverse();
        	System.out.println("------------------------------------------");
    		
    		population.mate();
    		population.mutation();
    		population.backToChromosome();
    	}

    }
  
    public static void main(String[] args) {  
    	
    	new GA().go();  
   }  
  
}  