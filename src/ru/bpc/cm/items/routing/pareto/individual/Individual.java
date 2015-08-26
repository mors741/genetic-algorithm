package ru.bpc.cm.items.routing.pareto.individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.bpc.cm.items.routing.pareto.outer.SolutionRoutes;
import ru.bpc.cm.items.routing.pareto.problem.Point;
import ru.bpc.cm.items.routing.pareto.problem.Problem;


public class Individual implements SolutionRoutes {
	
	private List<Integer> chromosome;
	private RouteNetwork routes;
	private int routesNumber = 1;
	private int totalCost = 0;
	private int isFeasible = 0;
	private int paretoRank;
	
	
	public Individual() {
		chromosome = new ArrayList<Integer>(Problem.customersNumber);
		routes = new RouteNetwork(Problem.customersNumber);
	}
	
	public void mutate() {
		if (this.paretoRank != 1) {
			routesNumber = routes.size();
			List<Integer> feasibleRoutes = new ArrayList<Integer>(routesNumber);
			for (int i = 0; i < routesNumber; i++){
				if (routes.get(i).size()>1){
					feasibleRoutes.add(i);
				}
			}
			Random rnd = new Random();
	
			Route route = this.routes.get(feasibleRoutes.get(rnd.nextInt(feasibleRoutes.size())));
			route.invert(rnd.nextInt(route.size()));
		}
	}
	
	public void evaluateRoutes() {	
		// Phase 1
		int currentCapacity = Problem.vehicleCapacity;
		// For first customer
		int gene = chromosome.get(0);
		Point customer = Problem.getCustomer(gene);
		int currentTime = Math.max(Problem.getDepot().getReadyTime()
				+ Problem.getDepot().timeTo(customer), customer.getReadyTime());
		routes.add(new Route(Problem.customersNumber));
		currentCapacity -= customer.getDemand();
		
		if (currentCapacity < 0 || currentTime > customer.getDueDate()) { // To late (create new route)
			
			currentCapacity = Problem.vehicleCapacity - customer.getDemand();
			currentTime = Math.max(Problem.getDepot().getReadyTime() + Problem.getDepot().timeTo(customer), customer.getReadyTime());
			
			routes.add(new Route(Problem.customersNumber));
			routesNumber++;
			
		} else if (currentTime < customer.getReadyTime()) { // To early (wait to ReadyTime)
			currentTime=customer.getReadyTime();
		}
		
		currentTime += customer.getServiceTime();
		
		routes.get(routesNumber-1).add(gene);
		int prevGene = gene;
		// For others
		for (int i = 1; i < chromosome.size(); i++) {
			gene = chromosome.get(i);
			customer = Problem.getCustomer(gene);
			
			currentTime += Problem.getCustomer(prevGene).timeTo(customer);
			
			currentCapacity -= customer.getDemand();

			if (currentCapacity < 0 || currentTime > customer.getDueDate()) { // To late (create new route)
				
				currentCapacity = Problem.vehicleCapacity - customer.getDemand();
				currentTime = Math.max(Problem.getDepot().getReadyTime() + Problem.getDepot().timeTo(customer), customer.getReadyTime());
				
				routes.add(new Route(Problem.customersNumber));
				routesNumber++;
				
			} else if (currentTime < customer.getReadyTime()) { // To early (wait to ReadyTime)
				currentTime=customer.getReadyTime();
			}
				
			currentTime += customer.getServiceTime();
			
			routes.get(routesNumber-1).add(gene);
			prevGene = gene;
		}
		
		// Phase 2
		for (int i = 1; i < routesNumber; i++) {
			int prevRouteLastIndex = routes.get(i-1).size()-1;
			int sumCost1 = routes.get(i-1).getCost() + routes.get(i).getCost();
			routes.get(i).add(0, routes.get(i-1).get(prevRouteLastIndex));
			routes.get(i-1).remove(prevRouteLastIndex);
			
			if (!routes.get(i).isFeasible() ||  routes.get(i-1).getCost() + routes.get(i).getCost() > sumCost1) { 
				routes.get(i-1).add(routes.get(i).get(0));
				routes.get(i).remove(0);
			}
		}
		
		totalCost = routes.evaluateTotalCost();
		isFeasible = isFeasible() ? 1 : 0;
	}
	
	private boolean theSameRoutesAs(Individual indiv){
		
		if (this.routes.size() != indiv.routes.size()) {
			return false;
		}
		Route route;
		for (int i = 0; i < this.routes.size(); i++) {
			route = this.routes.get(i);
			if (route.size() != indiv.routes.get(i).size()){
				return false;
			}
			for (int j = 0; j < route.size(); j++) {
				if (route.get(j) != indiv.routes.get(i).get(j)) {
					return false;
				}
			}
		}
		return true;
	}
    
    public Individual clone() {
    	Individual clone = new Individual();
    	clone.chromosome = new ArrayList<Integer>(this.chromosome); // No need to
    	for (Route route : this.routes) {
    		clone.routes.add(new Route(route));
    	}
    	clone.paretoRank = this.paretoRank;
    	clone.totalCost = this.totalCost;
    	clone.routesNumber = this.routesNumber;
        return clone;
    }

	public boolean dominates(Individual other) {
		if (this.totalCost <= other.totalCost 
				&& this.routesNumber <= other.routesNumber 
				&& this.isFeasible >= other.isFeasible
				&& (this.totalCost < other.totalCost
						|| this.routesNumber < other.routesNumber
						|| this.isFeasible > other.isFeasible)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * according to maxLength and maxCars constraints
	 */
	public boolean isFeasible() {
		int maxRouteLen = 0;
		for (Route route : routes){
			if (route.getCost() > maxRouteLen){
				maxRouteLen = route.getCost();
			}
		}
		if (maxRouteLen > Problem.maxRouteLength || this.routesNumber > Problem.maxCars){
			return false;
		} else {
			return true;
		}
	}
    
    public void backToChromosome() {
    	chromosome.clear();
    	for (Route r : routes) {
    		for (int gene : r) {
    			chromosome.add(gene);
    		}
    	}
    	routes.clear();
    	routesNumber = 1;
    	totalCost = 0;
    	paretoRank = 0;
    }
    
    public String toString() {
    	StringBuilder str = new StringBuilder(chromosome.size()*2);
    	/*for (int i : chromosome) {
    		str.append(i);
    		str.append(" ");
    	}
    	str.append("-- ");*/
    	str.append("<"+ paretoRank +"> (" + routesNumber + "; " + totalCost + "; " + isFeasible + ") "); 	
    	for (List<Integer> route : routes) {
    		str.append("[");
    		for (int gene : route) {
    			str.append(gene+" ");
    		}
    		
    		str.append("] ");
    	}
		return str.toString();
    }
    
    public boolean equals(Object obj){
    	return this.theSameRoutesAs((Individual)obj);
    }
    
	public RouteNetwork getRouteNetwork() {
		return routes;
	}
    
    List<Integer> getChromosome() {
        return this.chromosome;
    }
    
    public Route getRoute(int i) {
        return routes.get(i);
    }
    
    public int getRoutesNumber() {
        return routesNumber;
    }
    
    public int getIsFeasible() {
        return isFeasible;
    }
    
    public int getTotalCost() {
        return totalCost;
    }
    
    public int getParetoRank() {
        return paretoRank;
    }
    
    public void setParetoRank(int paretoRank) {
        this.paretoRank = paretoRank;
    }
    
	public void setChromosome(List<Integer> newChromosome) {
        this.chromosome = new ArrayList<Integer>(newChromosome);
    }
	
	public ArrayList<? extends ArrayList<Integer>> getRoutes() {
		ArrayList<ArrayList<Integer>> aRoutes = new ArrayList<ArrayList<Integer>>(); 
		for (Route route : this.getRouteNetwork()){
			Route aRoute = new Route(route.size());
			for (Integer point : route) {
				aRoute.add(point + 1);
			}
			aRoutes.add(aRoute);
		}
		return aRoutes;
	}
}
