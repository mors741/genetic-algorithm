package individual;

import java.util.ArrayList;

import problem.Point;
import problem.Problem;

import common.ArrayShuffler;

public class Route extends ArrayList<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int[] invertArray = {-2,-1,1,2};
	
	public Route(int customersNumber) {
		super(customersNumber);
	}
	
	public Route(Route route) {
		super(route);
	}
	
	void invert(int startPoz) {
		
		ArrayShuffler.shuffle(invertArray);
		
		for (int i = 0; i < 4; i++){
			if (startPoz + invertArray[i] < this.size() && startPoz + invertArray[i] >= 0) {				
				int temp = this.get(startPoz);
				this.set(startPoz, this.get(startPoz + invertArray[i]));
				this.set(startPoz + invertArray[i], temp);
				break;
			}
		}

	}

	public double getCost() {
		double res = Problem.getDepot().distanceTo(Problem.getCustomer(this.get(0)));
		int i;
		for (i = 0; i < this.size()-1; i++) {
			res += Problem.getCustomer(this.get(i)).distanceTo(Problem.getCustomer(this.get(i+1)));
		}
		return res + Problem.getCustomer(this.get(i)).distanceTo(Problem.getDepot());
	}
	
	public Route clone() {
		Route clone = new Route(this);
		return clone;
	}
	
	public boolean isFeasible() {
		// Check capacity constrain
		int capacity = 0;
		for (int gene : this) {
			capacity += Problem.getCustomer(gene).getDemand();
		}
		if (capacity > Problem.vehicleCapacity) {
			return false;
		}
		// Check time windows constrain
		double time = Problem.getDepot().distanceTo(Problem.getCustomer(this.get(0)))
				+ Problem.getDepot().getReadyTime();
		for (int i = 0; i < this.size(); i++) {			
			Point customer = Problem.getCustomer(this.get(i));
			
			if (i != 0) {
				time += Problem.getCustomer(this.get(i-1)).distanceTo(customer);
			}
			
			if (time > customer.getDueDate()) {
				return false;
			} else if (time < customer.getReadyTime()) {
				time = customer.getReadyTime();
			}
			
			time += customer.getServiceTime();
		}
		
		if (time + Problem.getCustomer(this.getLastGene()).distanceTo(Problem.getDepot()) > Problem.getDepot().getDueDate()) {
			return false;
		}
		
		return true;
	}
	
	public int getLastGene() {
		return this.get(this.size()-1);
	}
}
