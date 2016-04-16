package ru.bpc.cm.items.routing.pareto;

import ru.bpc.cm.items.routing.heneticmethod.Matrix;
import ru.bpc.cm.items.routing.pareto.population.Individual;
import ru.bpc.cm.items.routing.pareto.population.Population;
import ru.bpc.cm.items.routing.pareto.problem.Problem;

public class Pareto {

	public static int POPULATION_SIZE = 10;
	public static int GENERATION_SPAN = 10;
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

		// long time = System.currentTimeMillis();
		// long routesTime = 0;
		// long paretoTime = 0;
		// long mateTime = 0;
		// long mutationTime = 0;
		// long backTime = 0;

		for (int i = 0; i < GENERATION_SPAN - 1; i++) {
			population.evaluateRoutes();
			// routesTime += System.currentTimeMillis() - time;
			// time = System.currentTimeMillis();

			population.determineParetoRanks();
			// paretoTime += System.currentTimeMillis() - time;
			// time = System.currentTimeMillis();

			System.out.println(" -------------------------------------" + i + " -------------------------------------");
			population.showInverse();

			population.mate();
			// mateTime += System.currentTimeMillis() - time;
			// time = System.currentTimeMillis();

			population.mutation();
			// mutationTime += System.currentTimeMillis() - time;
			// time = System.currentTimeMillis();

			population.backToChromosome();
			// backTime += System.currentTimeMillis() - time;
			// time = System.currentTimeMillis();

		}
		population.evaluateRoutes();
		population.determineParetoRanks();

		return population.getResult();

		// System.out.println("routesTime: " + routesTime);
		// System.out.println("paretoTime: " + paretoTime);
		// System.out.println("mateTime: " + mateTime);
		// System.out.println("mutationTime: " + mutationTime);
		// System.out.println("backTime: " + backTime);
		// System.out.println("total: " +
		// (routesTime+paretoTime+mateTime+mutationTime+backTime));
		// System.out.println();
	}
}