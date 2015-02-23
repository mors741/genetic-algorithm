package individual;

import java.util.ArrayList;

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
		double time = Problem.getDepot().getReadyTime();
		return true; // TODO 
	}

}
