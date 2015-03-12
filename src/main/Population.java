package main;

import individual.Individual;
import individual.IndividualFactory;
import individual.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	public void mutation(){
		Random rand = new Random();
		for (Individual indiv : this) {
			if (rand.nextDouble() < GA.MUTATION_RATE) {
				//System.out.println(indiv);
				indiv.mutate();
				//System.out.println(indiv);
			}
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
		//System.out.println(parent1+ " + "+ parent2);
		
		Individual child1 = parent1.clone();
		Individual child2 = parent2.clone();
		
		Random rand = new Random();
		
		Route rRoute1 = child1.getRoute(rand.nextInt(child1.getRoutesNumber())).clone();		
		Route rRoute2 = child2.getRoute(rand.nextInt(child2.getRoutesNumber())).clone();
		
		//System.out.println(rRoute1+ " + "+ rRoute2);
		
		Collections.shuffle(rRoute1);
		Collections.shuffle(rRoute2);
		
		//System.out.println("shuffle " + rRoute1+ " + "+ rRoute2);
		for (int customer : rRoute1) {
			child2.removeCustomerFromRoutes(customer);
		}
		for (int customer : rRoute2) {
			child1.removeCustomerFromRoutes(customer);
		}
		//System.out.println("ch1 rem " +child1);
		//System.out.println("ch2 rem " +child2);
		for (int customer : rRoute1) {
			child2.insertCustomerToRoutes(customer);
		}
		//System.out.println("ch2 ins " +child2);
		for (int customer : rRoute2) {
			child1.insertCustomerToRoutes(customer);
		}
		//System.out.println("ch1 ins " +child1);
		
		add(child1);
		add(child2);
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

