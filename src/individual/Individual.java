package individual;

import java.util.List;  
import java.util.ArrayList;  
import problem.*;


public class Individual {
	
	private List<Integer> chromosome;
	private List<Route> routes;
	private int routesNumber = 1;
	private double totalCost = 0;
	
	
	public Individual() {
		chromosome = new ArrayList<Integer>(Problem.customersNumber);
		routes = new ArrayList<Route>(Problem.customersNumber);
	}
	
	public void setChromosome(List<Integer> newChromosome) {
        this.chromosome = new ArrayList<Integer>(newChromosome);
    }
	
	public void evaluateRoutes() {
		System.out.println("----------\nPhase 1:"); // TODO debug only
		double currentCapacity = Problem.vehicleCapacity;
		double currentTime = Problem.getDepot().distanceTo(Problem.getCustomer(chromosome.get(0)))
				+ Problem.getDepot().getReadyTime();
		routes.add(new Route(Problem.customersNumber));
		Point customer = null;
		int prevGene = -1;
		System.out.print("["); // TODO debug only
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
				
				System.out.print("] ["); // TODO debug only
			} else if (currentTime < customer.getReadyTime()) { // To early (wait to ReadyTime)
				currentTime=customer.getReadyTime();
			}
				
			currentTime += customer.getServiceTime();
			
			routes.get(routesNumber-1).add(gene);
			prevGene = gene;
			System.out.print(gene+ " "); // TODO debug only
		}
		
		System.out.println("]"); // TODO debug only
		System.out.println("Routes: " + routesNumber); // TODO debug only
		System.out.println("Total Cost: " + evaluateTotalCost()); // TODO debug only
		
		System.out.println("----------\nPhase 2:"); // TODO debug only
		for (int i = 1; i < routesNumber; i++) {
			int prevRouteLastIndex = routes.get(i-1).size()-1;
			if (true) { // можно вставить 4 в итый рут
				routes.get(i).add(0, routes.get(i-1).get(prevRouteLastIndex));
				routes.get(i-1).remove(prevRouteLastIndex);
			}
		}
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
		return str.toString();
    	
    }
}
