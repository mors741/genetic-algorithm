package main;

import problem.Matrix;
import problem.Problem;
  
  
public class GA {  
  
    public static final int POPULATION_SIZE = 10;
    public static final int GENERATION_SPAN = 10;
    public static final double CROSSOVER_RATE = 0.8;  
    public static final double MUTATION_RATE = 0.1;
    
    public static final double INIT_RAND_RATE = 0.9;
    public static final double EUCLIDEAN_RADIUS = 80;
    
    public void go() {
    	//Problem.Init("resources/C101.csv", 100);
    	Problem.Init(new Matrix());
    	
    	Population population = new Population();    	
    	population.initialize();
    	
    	//long time = System.currentTimeMillis();
    	//long routesTime = 0;
    	//long paretoTime = 0;
    	//long mateTime = 0;
    	//long mutationTime = 0;
    	//long backTime = 0;
    	
    	for (int i = 0; i < GENERATION_SPAN; i++) {
    		population.evaluateRoutes();
    		//routesTime += System.currentTimeMillis() - time;
    		//time = System.currentTimeMillis();
    		
    		population.determineParetoRanks();
    		//paretoTime += System.currentTimeMillis() - time;
    		//time = System.currentTimeMillis();
    		
    		System.out.println(" -------------------------------------" +i+" -------------------------------------");
    		//population.showOptimal();
    		population.showInverse();
        	
    		population.mate();
    		//mateTime += System.currentTimeMillis() - time;
    		//time = System.currentTimeMillis();
    		
    		population.mutation();
    		//mutationTime += System.currentTimeMillis() - time;
    		//time = System.currentTimeMillis();
    		
    		population.backToChromosome();
    		//backTime += System.currentTimeMillis() - time;
    		//time = System.currentTimeMillis();
    		
    	}
    	
    	//System.out.println("routesTime: " + routesTime);
    	//System.out.println("paretoTime: " + paretoTime);
    	//System.out.println("mateTime: " + mateTime);
    	//System.out.println("mutationTime: " + mutationTime);
    	//System.out.println("backTime: " + backTime);
    	//System.out.println("total: " + (routesTime+paretoTime+mateTime+mutationTime+backTime));
    	//System.out.println();
    	//System.out.println("testTime1: " + population.testTime1);
    	//System.out.println("testTime2: " + population.testTime2);
    	

    }
  
    public static void main(String[] args) {     	
    	new GA().go();  
   }  
}  