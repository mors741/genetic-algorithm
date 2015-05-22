package main;

import individual.Individual;
import individual.IndividualFactory;
import individual.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population extends ArrayList<Individual> {

	private static final long serialVersionUID = 7697698702244322610L;
	
	//public long testTime1 = 0;
	//public long testTime2 = 0;
	//public long testTime3 = 0;
	//private long time;
	
	public Population(){
		super(GA.POPULATION_SIZE);
	}
	
	public Population(Population population){
		super(population);
	}
	
	public void initialize() {
		int i;
    	for (i = 0; i < GA.POPULATION_SIZE * GA.INIT_RAND_RATE; i++) {
    		add(IndividualFactory.generateRandomChromosome());
    	}
    	while (i < GA.POPULATION_SIZE){		
    		add(IndividualFactory.generateGreedyChromosome());
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
		int i=0;
		for (Individual eliteParent : parents){
			if (eliteParent.getParetoRank() == 1){
				if (!this.contains(eliteParent)){
					this.add(eliteParent.clone());
					//System.out.println("))))))) Taking " + eliteParent + " to next ganeration");
					i++;
				}
			} else {
				break;
			}
		}
		if (i % 2 == 1){
			this.add(parents.get(0));
			//System.out.println("))))))) Taking " + parents.get(0) + " to next ganeration again :)");
			i++;
		}
		for ( ; i < GA.POPULATION_SIZE; i+=2){
			crossover(parents.get(tournamentWiner()), parents.get(tournamentWiner()));
		}		
	}
	
	public void mutation(){
		Random rand = new Random();
		for (Individual indiv : this) {
			if (rand.nextDouble() < GA.MUTATION_RATE) {
				indiv.mutate();
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
	
	private void crossover(Individual parent1, Individual parent2){
		Individual child1 = parent1.clone();
		Individual child2 = parent2.clone();
		Random rand = new Random();
		Route rRoute1 = child1.getRoute(rand.nextInt(child1.getRoutesNumber())).clone();		
		Route rRoute2 = child2.getRoute(rand.nextInt(child2.getRoutesNumber())).clone();
		Collections.shuffle(rRoute1);
		Collections.shuffle(rRoute2);
		
		//time = System.currentTimeMillis();
		for (int customer : rRoute1) {
			child2.getRouteNetwork().removeCustomer(customer);
		}
		for (int customer : rRoute2) {
			child1.getRouteNetwork().removeCustomer(customer);
		}
		
		//testTime1 += System.currentTimeMillis() - time;
		//time = System.currentTimeMillis();
		
		for (int customer : rRoute1) {
			child2.getRouteNetwork().insertCustomer(customer);
		}
		for (int customer : rRoute2) {
			child1.getRouteNetwork().insertCustomer(customer);
		}
		
		//testTime2 += System.currentTimeMillis() - time;
		
		add(child1);
		add(child2);
		
	}
	
	public void backToChromosome(){
		for (Individual indiv : this) {
			indiv.backToChromosome();
		}
	}

	public void show() {
    	for (int i = 0; i < GA.POPULATION_SIZE; i++) {
    		System.out.println(i+": "+get(i));
    	}
    }
	
	public void showInverse() {
    	for (int i = GA.POPULATION_SIZE - 1; i >= 0; i--) {
    		System.out.println(i+": "+get(i));
    	}
    }
	
	public void showOptimal() {
    	for (int i = GA.POPULATION_SIZE - 1; i >= 0; i--) {
    		if (get(i).getParetoRank() == 1) {
    			System.out.println(i+": "+get(i));
    		}
    	}
    }
	    
}

