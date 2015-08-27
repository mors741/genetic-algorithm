package ru.bpc.cm.items.routing.pareto.individual;

import java.util.ArrayList;

import ru.bpc.cm.items.routing.pareto.common.ArrayShuffler;
import ru.bpc.cm.items.routing.pareto.problem.Point;
import ru.bpc.cm.items.routing.pareto.problem.Problem;

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

	public int getDistance() {
		if (this.isEmpty()){
			System.out.println("Customer can not be reached from depot without time window constraint violation");
		}
		int res = Problem.getDepot().distanceTo(Problem.getCustomer(this.get(0)));
		int i;
		for (i = 0; i < this.size()-1; i++) {
			res += Problem.getCustomer(this.get(i)).distanceTo(Problem.getCustomer(this.get(i+1)));
		}
		return res + Problem.getCustomer(this.get(i)).distanceTo(Problem.getDepot());
	}
	
	public int getTime() {
		int pointTime = Problem.getDepot().getReadyTime() + Problem.getDepot().getServiceTime(); // start time of depot
		pointTime -= Problem.getDepot().getServiceTime(); // if not serving depot in the beginning
		int prevCust = 0;
		for (Integer pid : this) {
			pointTime = Math.max(pointTime + Problem.getCustomer(prevCust).timeTo(Problem.getCustomer(pid)) + Problem.getCustomer(prevCust).getServiceTime(), Problem.getCustomer(pid).getReadyTime());
			prevCust = pid;
		}
		pointTime += Problem.getCustomer(prevCust).timeTo(Problem.getDepot());
		return pointTime;
	}
	
	public int getImaginaryDistance(int index, int cust) {
		int res;
		if (index == 0) {
			res = Problem.getDepot().distanceTo(Problem.getCustomer(cust));
			res += Problem.getCustomer(cust).distanceTo(Problem.getCustomer(this.get(0)));
			int i;
			for (i = 0; i < this.size()-1; i++) {
				res += Problem.getCustomer(this.get(i)).distanceTo(Problem.getCustomer(this.get(i+1)));
			}
			return res + Problem.getCustomer(this.get(i)).distanceTo(Problem.getDepot());	
		} else {
			res = Problem.getDepot().distanceTo(Problem.getCustomer(this.get(0)));
			int i;
			for (i = 0; i < index-1; i++) {
				res += Problem.getCustomer(this.get(i)).distanceTo(Problem.getCustomer(this.get(i+1)));
			}
			
			res += Problem.getCustomer(this.get(i)).distanceTo(Problem.getCustomer(cust));
			if (index != this.size()) {
				res += Problem.getCustomer(cust).distanceTo(Problem.getCustomer(this.get(i+1)));
			} else {
				return res + Problem.getCustomer(cust).distanceTo(Problem.getDepot());
			}
			
			for (i = index; i < this.size()-1; i++) {
				res += Problem.getCustomer(this.get(i)).distanceTo(Problem.getCustomer(this.get(i+1)));
			}
			return res + Problem.getCustomer(this.get(i)).distanceTo(Problem.getDepot());	
		}

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
		int time = Math.max(Problem.getDepot().getReadyTime()
				+ Problem.getDepot().timeTo(Problem.getCustomer(this.get(0)))
				, Problem.getCustomer(this.get(0)).getReadyTime());
		for (int i = 0; i < this.size(); i++) {			
			Point customer = Problem.getCustomer(this.get(i));
			
			if (i != 0) {
				time += Problem.getCustomer(this.get(i-1)).timeTo(customer);
			}
			
			if (time > customer.getDueDate()) {
				return false;
			} else if (time < customer.getReadyTime()) {
				time = customer.getReadyTime();
			}
			
			time += customer.getServiceTime();
		}
		
		if (time + Problem.getCustomer(this.getLastGene()).timeTo(Problem.getDepot()) > Problem.getDepot().getDueDate()) {
			return false;
		}
		
		return true;
	}
	
	public boolean isFeasible(int index, int cust) {
		// Check capacity constrain
		int capacity = 0;
		for (int gene : this) {
			capacity += Problem.getCustomer(gene).getDemand();
		}
		capacity += Problem.getCustomer(cust).getDemand();
		if (capacity > Problem.vehicleCapacity) {
			return false;
		}
		// Check time windows constrain
		int time;
		Point customer;
		if (index == 0) {
			time = Math.max(Problem.getDepot().getReadyTime()
					+ Problem.getDepot().timeTo(Problem.getCustomer(cust))
					, Problem.getCustomer(cust).getReadyTime());
			customer = Problem.getCustomer(cust);
			
			if (time > customer.getDueDate()) {
				return false;
			}
			
			time += customer.getServiceTime();
			
			customer = Problem.getCustomer(this.get(0));
			time += Problem.getCustomer(cust).timeTo(customer);				
			if (time > customer.getDueDate()) {
				return false;
			} else if (time < customer.getReadyTime()) {
				time = customer.getReadyTime();
			}
			
			time += customer.getServiceTime();
			
			for (int i = 1; i < this.size(); i++) {
				customer = Problem.getCustomer(this.get(i));				
				time += Problem.getCustomer(this.get(i-1)).timeTo(customer);				
				if (time > customer.getDueDate()) {
					return false;
				} else if (time < customer.getReadyTime()) {
					time = customer.getReadyTime();
				}
				
				time += customer.getServiceTime();
			}
			
		} else {
			time = Math.max(Problem.getDepot().getReadyTime()
					+ Problem.getDepot().timeTo(Problem.getCustomer(this.get(0)))
					, Problem.getCustomer(this.get(0)).getReadyTime());
			customer = Problem.getCustomer(this.get(0));		
			if (time > customer.getDueDate()) {
				return false;
			}			
			time += customer.getServiceTime();	
			int i;
			for (i = 1; i < index; i++) { // Before index	
				customer = Problem.getCustomer(this.get(i));
				
				time += Problem.getCustomer(this.get(i-1)).timeTo(customer);
				
				if (time > customer.getDueDate()) {
					return false;
				} else if (time < customer.getReadyTime()) {
					time = customer.getReadyTime();
				}
				
				time += customer.getServiceTime();
			}
			
			// index
			customer = Problem.getCustomer(cust);
			time += Problem.getCustomer(this.get(i-1)).timeTo(customer);
			if (time > customer.getDueDate()) {
				return false;
			} else if (time < customer.getReadyTime()) {
				time = customer.getReadyTime();
			}
			
			time += customer.getServiceTime();
			
			// index + 1
			if (index != this.size()) {
				customer = Problem.getCustomer(this.get(index));
				time += Problem.getCustomer(cust).timeTo(customer);
				if (time > customer.getDueDate()) {
					return false;
				} else if (time < customer.getReadyTime()) {
					time = customer.getReadyTime();
				}
				
				time += customer.getServiceTime();
				
				// other
				for (i = index+1; i < this.size(); i++) {
					customer = Problem.getCustomer(this.get(i));
					
					time += Problem.getCustomer(this.get(i-1)).timeTo(customer);
					
					if (time > customer.getDueDate()) {
						return false;
					} else if (time < customer.getReadyTime()) {
						time = customer.getReadyTime();
					}
					
					time += customer.getServiceTime();
				}
			}
		}
		if (index == this.size()) {// on the last place
			if (time + Problem.getCustomer(cust).timeTo(Problem.getDepot()) > Problem.getDepot().getDueDate()) {
				return false;
			}
		} else {
			if (time + Problem.getCustomer(this.getLastGene()).timeTo(Problem.getDepot()) > Problem.getDepot().getDueDate()) {
				return false;
			}
		}
		return true;
	}
	
	public int getLastGene() {
		return this.get(this.size()-1);
	}
}
