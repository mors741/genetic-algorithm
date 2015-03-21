package individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import problem.Point;
import problem.Problem;


public class Individual {
	
	private List<Integer> chromosome;
	private RouteNetwork routes;
	private int routesNumber = 1;
	private double totalCost = 0;
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
		double currentCapacity = Problem.vehicleCapacity;
		// For first customer
		int gene = chromosome.get(0);
		Point customer = Problem.getCustomer(gene);
		double currentTime = Math.max(Problem.getDepot().getReadyTime()
				+ Problem.getDepot().distanceTo(customer), customer.getReadyTime());
		routes.add(new Route(Problem.customersNumber));
		currentCapacity -= customer.getDemand();
		
		if (currentCapacity < 0 || currentTime > customer.getDueDate()) { // To late (create new route)
			
			currentCapacity = Problem.vehicleCapacity - customer.getDemand();
			currentTime = Math.max(Problem.getDepot().getReadyTime() + Problem.getDepot().distanceTo(customer), customer.getReadyTime());
			
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
			
			currentTime += Problem.getCustomer(prevGene).distanceTo(customer);
			
			currentCapacity -= customer.getDemand();

			if (currentCapacity < 0 || currentTime > customer.getDueDate()) { // To late (create new route)
				
				currentCapacity = Problem.vehicleCapacity - customer.getDemand();
				currentTime = Math.max(Problem.getDepot().getReadyTime() + Problem.getDepot().distanceTo(customer), customer.getReadyTime());
				
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
			double sumCost1 = routes.get(i-1).getCost() + routes.get(i).getCost();
			routes.get(i).add(0, routes.get(i-1).get(prevRouteLastIndex));
			routes.get(i-1).remove(prevRouteLastIndex);
			
			if (!routes.get(i).isFeasible() ||  routes.get(i-1).getCost() + routes.get(i).getCost() > sumCost1) { 
				routes.get(i-1).add(routes.get(i).get(0));
				routes.get(i).remove(0);
			}
		}
		
		totalCost = routes.evaluateTotalCost();
	}
	
	public boolean theSameRoutesAs(Individual indiv){
		
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
		if ((this.totalCost < other.totalCost && this.routesNumber <= other.routesNumber)
				|| (this.totalCost <= other.totalCost && this.routesNumber < other.routesNumber)) {
			return true;
		} else {
			return false;
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
    	str.append("<"+ paretoRank +"> (" + routesNumber + "; " + String.format("%.2f", totalCost) + ") "); 	
    	for (List<Integer> route : routes) {
    		str.append("[");
    		for (int gene : route) {
    			str.append(gene+" ");
    		}
    		
    		str.append("] ");
    	}
		return str.toString();
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
    
    public double getTotalCost() {
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
}
