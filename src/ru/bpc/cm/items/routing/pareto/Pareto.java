package ru.bpc.cm.items.routing.pareto;

import java.util.ArrayList;
import java.util.List;

import ru.bpc.cm.items.routing.heneticmethod.Matrix;
import ru.bpc.cm.items.routing.pareto.population.Individual;
import ru.bpc.cm.items.routing.pareto.population.Population;
import ru.bpc.cm.items.routing.pareto.problem.Problem;

public class Pareto {

	public static int POPULATION_SIZE = 300;
	public static double CROSSOVER_RATE = 0.8;
	public static double MUTATION_RATE = 0.1;

	public static double INIT_RAND_RATE = 0.9;
	public static double EUCLIDEAN_RADIUS = 3;
	
	public static double MAX_SAME_RESULT = 5;
	
	private Population population;

	public Pareto(Matrix problem) {
		Problem.initialize(problem);
		population = new Population();
	}
	
	public Individual computeResult() {
		
		population.initialize();
		List<Individual> prevOptimalIndividualList = new ArrayList<Individual>();
		
		population.evaluateRoutes();
		population.determineParetoRanks();
		
		System.out.println(" ------------------------------------- 0 -------------------------------------");
		population.showInverse();
		
		int generationCounter = 1;
		List<Individual> optimalIndividualList = null;
		boolean areListsIdentical;
		int sameResultCounter = 0;
		while (sameResultCounter < MAX_SAME_RESULT) {
			population.mate();
			population.mutation();
			population.backToChromosome();
			population.evaluateRoutes();
			population.determineParetoRanks();

			System.out.println(" ------------------------------------- " + (generationCounter++) + " -------------------------------------");
			population.showInverse();
			System.out.println("OPTIMAL");
			optimalIndividualList = population.getOptimalList();
			for (Individual i : optimalIndividualList) {
				System.out.println(i);
			}
				
			areListsIdentical = true;
			if (optimalIndividualList.size() == prevOptimalIndividualList.size()) {		
				for (int i = 0; i < optimalIndividualList.size(); i++) {
					if (optimalIndividualList.get(i).getRoutesNumber() != prevOptimalIndividualList.get(i).getRoutesNumber()
							|| optimalIndividualList.get(i).getTotalCost() != prevOptimalIndividualList.get(i).getTotalCost()) {
						areListsIdentical = false;
					}				
				}
				if (areListsIdentical) {
					sameResultCounter++;
				} else {
					sameResultCounter = 0;
				}
			}
			prevOptimalIndividualList = optimalIndividualList;
			System.out.println("SAME: " + sameResultCounter);
		}
		System.out.println("OPTIMAL");
		for (Individual i : population.getOptimalList()) {
			System.out.println(i);
		}
		return optimalIndividualList.get(0);
	}
}