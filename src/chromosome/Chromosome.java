package chromosome;

import java.util.List;  
import java.util.Collections;  
import java.util.ArrayList;  
import java.util.Random;

import main.GA;
import problem.*;


public class Chromosome {
	
	private List<Integer> chromosome;
	private List<List<Integer>> routes;
	private int routesNumber;
	private int totalCost;
	
	
	public Chromosome() {
		chromosome = new ArrayList<Integer>(Problem.customersNumber);
		routes = new ArrayList<List<Integer>>(Problem.customersNumber);
	}
	
	public void setChromosome(List<Integer> newChromosome) {
        this.chromosome = new ArrayList<Integer>(newChromosome);
    }
	
	public void evaluateRoutes() {
		int currentRouteIndex = 0;
		int currentCapacity = Problem.vehicleCapacity;
		routes.add(new ArrayList<Integer>(Problem.customersNumber));
		// TODO Exception if first gene is incorrect
		if (chromosome.get(0) % 2 == 0) {
			System.out.println("Kosyak");
		}
		for (int gene : chromosome) {
			if (gene % 2 == 0) { // TODO New route condition
				routes.add(new ArrayList<Integer>(Problem.customersNumber));
				currentRouteIndex++;
			}
			routes.get(currentRouteIndex).add(gene);
		}
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
    
    public int getTotalCost() {

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
