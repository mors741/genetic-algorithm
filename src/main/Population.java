package main;

import individual.Individual;
import individual.IndividualFactory;

import java.util.ArrayList;
import java.util.Random;

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
	
	public void mate(){

		Population parents = new Population(this);
		this.clear();
		this.add(parents.get(0));
		this.add(parents.get(1));

		for (int i = 2; i < GA.POPULATION_SIZE; i+=2){
			crossover(parents.get(tournamentWiner()), parents.get(tournamentWiner()));
		}
					
	}
	
	private int tournamentWiner(){
		// Population is sorted by this stage, 
		// So tournament winer is just Individual with smallest index
		Random rand = new Random();
		int winner = GA.POPULATION_SIZE;
		int rndIndex;
		int[] tournamentSet = new int[4];
		for (int i = 0; i < 4; i++){
			rndIndex = rand.nextInt(GA.POPULATION_SIZE);
			tournamentSet[i] = rndIndex;
			if (rndIndex < winner) {
				winner = rndIndex;
			}
		}
		if (rand.nextDouble() < GA.CROSSOVER_RATE) {
			return winner;
		} else {
			return tournamentSet[rand.nextInt(4)];
		}
	}
	
	/*
	public int tournamentOptimized(){ // Got to be wrong
		Random rand = new Random();
		if (rand.nextDouble() < GA.CROSSOVER_RATE) {
			int winner = GA.POPULATION_SIZE;
			int rndIndex;
			for (int i = 0; i < 4; i++){
				rndIndex = rand.nextInt(GA.POPULATION_SIZE);
				if (rndIndex < winner) {
					winner = rndIndex;
				}
				//System.out.println("w: "+winner+" rnd: "+rndIndex);
			}
			//System.out.println("winer = "+winner);
			return winner;
		} else {
			return rand.nextInt(GA.POPULATION_SIZE);
		}
	}
	*/
	
	private void crossover(Individual parent1, Individual parent2){
		System.out.println(parent1.getParetoRank()+ " + "+ parent2.getParetoRank());
		add(parent1);	// TODO Crossover
		add(parent2);	// TODO Links!! doesn't work well
	}
	
	public void backToChromosome(){
		for (Individual indiv : this) {
			indiv.backToChromosome();
		}
	}

	public void show() {
    	for (int i = 0; i < this.size(); i++) { // TODO: i < GA.POPULATION_SIZE
    		System.out.println(i+": "+get(i));
    	}
    }
	
	public void showInverse() {
    	for (int i = this.size() - 1; i >= 0; i--) { // TODO: int i = GA.POPULATION_SIZE - 1;
    		System.out.println(i+": "+get(i));
    	}
    }
	    
}

