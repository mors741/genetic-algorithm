package ru.bpc.cm.items.routing.pareto;

import java.util.ArrayList;
import java.util.List;

import ru.bpc.cm.items.routing.heneticmethod.Matrix;
import ru.bpc.cm.items.routing.pareto.population.Individual;
import ru.bpc.cm.items.routing.pareto.population.Population;
import ru.bpc.cm.items.routing.pareto.problem.Problem;

public class Pareto {

	public static int POPULATION_SIZE = 300;
	public static int GENERATION_SPAN = 350;
	public static double CROSSOVER_RATE = 0.8;
	public static double MUTATION_RATE = 0.1;

	public static double INIT_RAND_RATE = 0.9;
	public static double EUCLIDEAN_RADIUS = 3;
	
	private Population population;

	public Pareto(Matrix problem) {
		Problem.initialize(problem);
		population = new Population();
	}
	
	public Individual computeResult() {
		population.initialize();
		List<Individual> optimalIndividualList = new ArrayList<Individual>();
		
		population.evaluateRoutes();
		population.determineParetoRanks();
		
		System.out.println(" ------------------------------------- 0 -------------------------------------");
		population.showInverse();

		for (int i = 1; i < GENERATION_SPAN; i++) {
			population.mate();
			population.mutation();
			population.backToChromosome();
			population.evaluateRoutes();
			population.determineParetoRanks();

			System.out.println(" ------------------------------------- " + i + " -------------------------------------");
			population.showInverse();
		}
		System.out.println("OPTIMAL");
		optimalIndividualList = population.getOptimalList();
		for (Individual i : population.getOptimalList()) {
			System.out.println(i);
		}
		return optimalIndividualList.get(0);
	}
}