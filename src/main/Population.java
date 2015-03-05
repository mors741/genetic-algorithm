package main;

import individual.Individual;
import individual.IndividualFactory;

import java.util.ArrayList;

public class Population extends ArrayList<Individual> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7697698702244322610L;
	
	public Population(){
		super(GA.POPULATION_SIZE);
	}
	
	public Population(Population population){
		super(population);
	}
	
	public void initialize() {
		int i;
    	IndividualFactory chromosomeFactory = new IndividualFactory();
    	for (i = 0; i < GA.POPULATION_SIZE * GA.INIT_RAND_RATE; i++) {
    		add(chromosomeFactory.generateRandomChromosome());
    	}
    	while (i < GA.POPULATION_SIZE){		
    		add(chromosomeFactory.generateGreedyChromosome());
    		i++;
    	}	
	}
	
	public void evaluateRoutes(){
		for (Individual indiv : this) {
	    	indiv.evaluateRoutes();
		}
	}
	
    private boolean isNotDominated(Individual indiv) {
		for (Individual i : this) {
			if (i.dominates(indiv)){
				return false;
			}
		}
		return true;
	}
	
	public void determineParetoRanks(){
		
		Population tempPop = new Population(this);
		this.clear();
		
		int currentRank = 1;
		int n = GA.POPULATION_SIZE;
		Individual indiv;
		
		while (n != 0) {
			
			for (int i = 0; i < n;i++){
				indiv = tempPop.get(i);
				if (tempPop.isNotDominated(indiv)){
					indiv.setParetoRank(currentRank);
				}
			}
			
			for (int i = 0; i < n;i++){
				indiv = tempPop.get(i);
				if (indiv.getParetoRank() == currentRank){
					this.add(indiv);
					tempPop.remove(i);
					i--;
					n--;
				}
			}
			currentRank++;
		}
	}

	public void show() {
    	for (int i = 0; i<GA.POPULATION_SIZE; i++) {
    		System.out.println(i+": "+get(i));
    	}
    }
	
	public void showInverse() {
    	for (int i = GA.POPULATION_SIZE-1; i>=0; i--) {
    		System.out.println(i+": "+get(i));
    	}
    }
	    
}

