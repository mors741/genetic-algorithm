package chromosome;

import java.util.List;  
import java.util.ArrayList;  
import problem.*;


public class Chromosome {
	
	private List<Integer> chromosome;
	private List<List<Integer>> routes;
	private int routesNumber = 1;
	private double totalCost = 0;
	
	
	public Chromosome() {
		chromosome = new ArrayList<Integer>(Problem.customersNumber);
		routes = new ArrayList<List<Integer>>(Problem.customersNumber);
	}
	
	public void setChromosome(List<Integer> newChromosome) {
        this.chromosome = new ArrayList<Integer>(newChromosome);
    }
	
	public void evaluateRoutes() {
		double currentCapacity = Problem.vehicleCapacity;
		double currentTime = Problem.getDepot().distanceTo(Problem.getCustomer(chromosome.get(0)));
		totalCost = currentTime;
		routes.add(new ArrayList<Integer>(Problem.customersNumber));
		Point customer = null;
		int prevGene = -1;
		System.out.print("["); // TODO debug only
		for (int gene : chromosome) {
			customer = Problem.getCustomer(gene);
			
			if (gene != chromosome.get(0)) {
				currentTime += Problem.getCustomer(prevGene).distanceTo(customer);
				totalCost += Problem.getCustomer(prevGene).distanceTo(customer);
			}
			
			currentCapacity -= customer.getDemand();

			if (currentCapacity < 0 || currentTime > customer.getDueDate()) { // To late (creat new route)
				if (routes.get(routesNumber-1).isEmpty()) {
					System.out.println("Kosyak"); // TODO Exception 
				}
				
				currentCapacity = Problem.vehicleCapacity - customer.getDemand();
				currentTime = Problem.getDepot().distanceTo(customer) + customer.getReadyTime();
				totalCost += Problem.getCustomer(prevGene).distanceTo(Problem.getDepot()) + Problem.getDepot().distanceTo(customer)
						- Problem.getCustomer(prevGene).distanceTo(customer);
				
				routes.add(new ArrayList<Integer>(Problem.customersNumber));
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
		
		totalCost += customer.distanceTo(Problem.getDepot());
		
		System.out.println("]"); // TODO debug only
		System.out.println("Routes: " + routesNumber); // TODO debug only
		System.out.println("Total Cost: " + totalCost); // TODO debug only
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
