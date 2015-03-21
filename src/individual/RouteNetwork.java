package individual;

import java.util.ArrayList;

import problem.Problem;

public class RouteNetwork extends ArrayList<Route> {
	
	private static final long serialVersionUID = -6180881689433423705L;
	
	public RouteNetwork() {
		super();
	}
	
	public RouteNetwork(int capacity) {
		super(capacity);
	}
	
	public double evaluateTotalCost() {
		double tc = 0;
		for (Route route : this) {
			tc += route.getCost();
		}
		return tc;
	}
	
	private double evaluateTotalCostExcept(int routeIndex) {
		double tc = 0;
		for (int i = 0; i < this.size(); i++) {
			if (i != routeIndex){
				tc += get(i).getCost();
			}		
		}
		return tc;
	}
	
    public boolean removeCustomer(int customer){
    	for (Route route : this){
    		if (route.remove((Object)customer)){
    			if (route.isEmpty()) {
    				remove(route);
    			}
    			return true;
    		}
    	}
    	return false;
    }
    
    public void insertCustomer(int customer) {
    	int bestRouteIndex = -1;
    	int bestIndex = -1;
    	double bestCost = Double.MAX_VALUE;
    	double currentCost;
    	for (int routeIndex = 0; routeIndex < this.size(); routeIndex++) {
    		Route route = this.get(routeIndex);
    		for (int index = 0 ; index <= route.size(); index++) {
    			if (route.isFeasible(index, customer)) {
    				currentCost = this.evaluateTotalCostExcept(routeIndex) + route.getImaginaryCost(index, customer);
    				if (currentCost < bestCost){
    					bestRouteIndex = routeIndex;
	    				bestIndex = index;
	    				bestCost = currentCost;
    				}
    			}
    		}
    	}
    	if (bestIndex == -1){
    		Route newRoute = new Route(Problem.customersNumber);
    		newRoute.add(customer);
    		this.add(newRoute);
    	} else {
    		this.get(bestRouteIndex).add(bestIndex, customer);
    	}
    }

}
