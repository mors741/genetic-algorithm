package problem; 

import java.util.ArrayList;
import java.util.List;


public class Problem {
	
	public static int customersNumber;
	public static int vehicleCapacity;
	public static Point depot;
	public static List<Point> customers; // нумерация с 0, а не с 1!
	
	public static void Init(double[][] data, int capacity) {
		vehicleCapacity = capacity;
		customersNumber = data.length-1;
		depot = new Point(-1,data[0][0], data[0][1], data[0][2], data[0][3], data[0][4], data[0][5]);
		customers = new ArrayList<Point>(customersNumber);
		for (int i = 1; i <= customersNumber; i++) {
			customers.add(new Point(i-1,data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], data[i][5]));
		}
	}
	
	public static Point getDepot(){
		return depot;
	}
	
	public static Point getCustomer(int i){
		return customers.get(i);
	}
	
	
	
}
