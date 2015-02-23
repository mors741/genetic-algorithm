package individual;

import java.util.ArrayList;

import problem.Point;
import problem.Problem;

public class Route extends ArrayList<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Route(int customersNumber) {
		super(customersNumber);
	}

	public double getCost() {
		double res = Problem.getDepot().distanceTo(Problem.getCustomer(this.get(0)));
		int i;
		for (i = 0; i < this.size()-1; i++) {
			res += Problem.getCustomer(this.get(i)).distanceTo(Problem.getCustomer(this.get(i+1)));
		}
		return res + Problem.getCustomer(this.get(i)).distanceTo(Problem.getDepot());
	}
	
	public boolean isFeasible() {
		// Check capacity
		int capacity = 0;
		for (int gene : this) {
			capacity += Problem.getCustomer(gene).getDemand();
		}
		if (capacity > Problem.vehicleCapacity) {
			return false;
		}
		// Check time windows
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
		
		return true; // TODO 
	}
	
	public int getLastGene() {
		return this.get(this.size()-1);
	}

}
