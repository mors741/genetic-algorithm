package ru.bpc.cm.items.routing.pareto.main;

import java.util.ArrayList;
import java.util.Arrays;

import ru.bpc.cm.items.routing.pareto.individual.Individual;
import ru.bpc.cm.items.routing.pareto.outer.Matrix;
import ru.bpc.cm.items.routing.pareto.outer.SolutionRoutes;
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
    	//Problem.Init("resources/C101.csv", 100);
    	Problem.Init(problem);
    	population = new Population();
    }
    
    public Individual computeResult() {
    	population.initialize();
    	
    	//long time = System.currentTimeMillis();
    	//long routesTime = 0;
    	//long paretoTime = 0;
    	//long mateTime = 0;
    	//long mutationTime = 0;
    	//long backTime = 0;
    	
    	for (int i = 0; i < GENERATION_SPAN - 1; i++) {
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
		population.evaluateRoutes();
		population.determineParetoRanks();
		System.out.println(" -------------------------------------" + (GENERATION_SPAN - 1) +" -------------------------------------");
		population.showOptimal();
		
		return population.getResult();
    	
    	//System.out.println("routesTime: " + routesTime);
    	//System.out.println("paretoTime: " + paretoTime);
    	//System.out.println("mateTime: " + mateTime);
    	//System.out.println("mutationTime: " + mutationTime);
    	//System.out.println("backTime: " + backTime);
    	//System.out.println("total: " + (routesTime+paretoTime+mateTime+mutationTime+backTime));
    	//System.out.println();
    }
  
    public static void main(String[] args) {
    	Matrix m = new Matrix(4);
    	m.ENC = new int[]{0, 1, 3, 2};  //(идентификаторы инкассаций)
    	m.ATM = new String[]{"1", "132964", "155652", "155750"}; //(соотв. идентификаторы банкоматов)
    	m.distanceCoeffs = new int[][]{{0, 17, 39, 19}, {18, 0, 26, 33}, {42, 29, 0, 67}, {20, 33, 66, 0}}; 
    	m.timeCoeffs = new int[][]{{0, 17, 39, 19},{18, 0, 26, 33},{42, 29, 0, 67},{20, 33, 66, 0}}; 
    	m.addTimeWindow(0, 0, 10000, false);
    	m.addTimeWindow(1, 480, 840, false);
    	m.addTimeWindow(2, 600, 1080, false);
    	m.addTimeWindow(3, 480, 840, false);
    	m.addRiderTimeWindow(60, 1380);
    	m.amountOfMoney = new int[]{0, 500000, 500000, 500000};  //(количество денег, которое нужно загрузить в каждый банкомат, может быть 0)
    	m.serviceTime = new int[]{20, 20, 20, 20};  // - вместо этого можно просто int serviceTime (это время обслуживания одного банкомата, сейчас оно одно для всех, но в идеале может различаться для разных типов банкоматов и даже быть уникальным для каждого)
    	m.MaxMoney = 40000000; // (макс количество денег, которое может перевезти машина)
    	m.amountOfCassettes = new int[] {0, 200, 200, 200};  // (кол-во кассет, которое нужно завезти в банкомат) - это сейчас не используется
    	m.VolumeOneCar = 40000000; // (макс кол-во кассет, кот. может перевезти машина) - не используется
    	m.FixPrice = 100.0;  // - фикс стоимость подъезда машины к банкомату
    	m.LengthPrice = 20.0;  // - цена за километр пути
    	m.MaxATMInWay = 7; // - макс кол-во банкоматов в маршруте
    	m.MaxTime = 0; // - макс время которое можно затрачивать на 1 маршрут
    	m.MaxLength = 0; // макс длина одного маршрута
    	m.depot = "1";  // - идентификатор депо
    	m.maxCars = 5;   // - макс доступное кол-во машин
    	m.AtmPrice = new double[] {100.0, 100.0, 100.0, 100.0}; // - стоимость подъезда к каждому банкомату (сейчас не используется)
    	m.currCode = 810; // - валюта всех денежных параметров
    	m.windowMode = 0; // - режим окон для банкоматов, обычный и дефолтный (при котором, каждое окно ставится на максимально возможный промежуток);
    	Pareto solver = new Pareto(m);
    	Pareto.POPULATION_SIZE = 10;
    	Pareto.GENERATION_SPAN = 10;
    	Pareto.CROSSOVER_RATE = 0.8;
    	Pareto.MUTATION_RATE = 0.1;
        
    	Pareto.INIT_RAND_RATE = 0.9;
    	Pareto.EUCLIDEAN_RADIUS = 30;
    	SolutionRoutes result = solver.computeResult();
    	
    	for (ArrayList<Integer> route : result.getRoutes()) {
    		System.out.print("[");
    		for (Integer pid : route) {
    			System.out.print(pid + " ");
    		}
    		System.out.println("]");
    	}
    	
    	for (ArrayList<Integer> route : result.getRoutes()) {
    		int order = 1;
    		int pointTime = m.getTimeWindows().get(0).StartWork; // start time of depot
    		pointTime -= m.serviceTime[0]; // if not serving depot in the beginning
    		int prevCust = 0;
    		for (Integer pid : route) {
    			pointTime = Math.max(pointTime + m.timeCoeffs[prevCust][pid] + m.serviceTime[prevCust], m.getTimeWindows().get(pid).StartWork);
    			System.out.println(pid + " " + order + " "  +pointTime);
    			prevCust = pid;
    			order++;
    		}
    	}
    	
    	

   }  
}  