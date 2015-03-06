package individual;

import java.util.List;  
import java.util.ArrayList;  
import java.util.Random;

import problem.*;


public class Individual {
	
	private List<Integer> chromosome;
	private List<Route> routes;
	private int routesNumber = 1;
	private double totalCost = 0;
	private int paretoRank;
	
	
	public Individual() {
		chromosome = new ArrayList<Integer>(Problem.customersNumber);
		routes = new ArrayList<Route>(Problem.customersNumber);
	}
	
	public void setChromosome(List<Integer> newChromosome) {
        this.chromosome = new ArrayList<Integer>(newChromosome);
    }
	
	public void mutate() {
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
	
	public void evaluateRoutes() {
		double currentCapacity = Problem.vehicleCapacity;
		double currentTime = Problem.getDepot().distanceTo(Problem.getCustomer(chromosome.get(0)))
				+ Problem.getDepot().getReadyTime();
		routes.add(new Route(Problem.customersNumber));
		Point customer = null;
		int prevGene = -1;
		for (int gene : chromosome) {
			customer = Problem.getCustomer(gene);
			
			if (gene != chromosome.get(0)) {
				currentTime += Problem.getCustomer(prevGene).distanceTo(customer);
			}
			
			currentCapacity -= customer.getDemand();

			if (currentCapacity < 0 || currentTime > customer.getDueDate()) { // To late (creat new route)
				if (routes.get(routesNumber-1).isEmpty()) {
					System.out.println("Kosyak"); // TODO Exception 
				}
				
				currentCapacity = Problem.vehicleCapacity - customer.getDemand();
				currentTime = Problem.getDepot().getReadyTime() + Problem.getDepot().distanceTo(customer) + customer.getReadyTime();
				
				routes.add(new Route(Problem.customersNumber));
				routesNumber++;
				
			} else if (currentTime < customer.getReadyTime()) { // To early (wait to ReadyTime)
				currentTime=customer.getReadyTime();
			}
				
			currentTime += customer.getServiceTime();
			
			routes.get(routesNumber-1).add(gene);
			prevGene = gene;
		}
		
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
		
		totalCost = evaluateTotalCost();
		//System.out.print("(" + routesNumber + ", "+ (int)totalCost + ") " + this.toRouteString() + "\n"); // TODO debug only	
	}
	
	private double evaluateTotalCost() {
		double tc = 0;
		for (Route route : routes) {
			tc += route.getCost();
		}
		return tc;
	}
	
    public boolean contains(int gene) {
    	for (int i : chromosome) {
    		if (i == gene) {
    			return true;
    		}
    	}
    	return false;
    }
    
    List<Integer> getChromosome() {
        return this.chromosome;
    }
    
    public int getSize() {

        return chromosome.size();
    }
    
    public int getRoutesNumber() {

        return routesNumber;
    }
    
    public double getTotalCost() {

        return totalCost;
    }
    
	public boolean dominates(Individual other) {
		if ((this.totalCost < other.totalCost && this.routesNumber <= other.routesNumber)
				|| (this.totalCost <= other.totalCost && this.routesNumber < other.routesNumber)) {
			return true;
		} else {
			return false;
		}
	}
    
    public int getParetoRank() {

        return paretoRank;
    }
    
    public void setParetoRank(int paretoRank) {

        this.paretoRank = paretoRank;
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
    
	public String toRouteString() {
    	StringBuilder str = new StringBuilder(chromosome.size()*3);
    	for (List<Integer> route : routes) {
    		str.append("[");
    		for (int gene : route) {
    			str.append(gene+" ");
    		}
    		
    		str.append("] ");
    	}
		return str.toString();
	}
    
    public String toString() {
    	StringBuilder str = new StringBuilder(chromosome.size()*2);
    	for (int i : chromosome) {
    		str.append(i);
    		str.append(" ");
    	}
    	str.append("-- <"+ paretoRank +"> (" + routesNumber + ", " + (int)totalCost + ") "); 	
    	for (List<Integer> route : routes) {
    		str.append("[");
    		for (int gene : route) {
    			str.append(gene+" ");
    		}
    		
    		str.append("] ");
    	}
		return str.toString();
    	
    }
}
