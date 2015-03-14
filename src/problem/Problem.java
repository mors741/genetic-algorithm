package problem; 

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Problem {
	
	public static int customersNumber;
	public static double vehicleCapacity;
	public static Point depot;
	public static List<Point> customers; // First cusomer's id = 0, not 1!
	
	public static void Init(double[][] data, int capacity) {
		vehicleCapacity = capacity;
		customersNumber = data.length-1;
		depot = new Point(-1, data[0][0], data[0][1], data[0][2], data[0][3], data[0][4], data[0][5]);
		customers = new ArrayList<Point>(customersNumber);
		for (int i = 1; i <= customersNumber; i++) {
			customers.add(new Point(i-1,data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], data[i][5]));
		}
	}
	
	public static void Init(String fileName, int custNumber) {
		customersNumber = 0;
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ";";
    	try {
    		 
    		br = new BufferedReader(new FileReader(fileName));
    		line = br.readLine();
    		vehicleCapacity = Integer.parseInt(line.substring(0, line.indexOf(cvsSplitBy)));
    		line = br.readLine();
    		String[] splLine = line.split(cvsSplitBy);
    		depot = new Point(-1, Double.parseDouble(splLine[0]), Double.parseDouble(splLine[1]), Double.parseDouble(splLine[2]),
    				Double.parseDouble(splLine[3]), Double.parseDouble(splLine[4]), Double.parseDouble(splLine[5]));
    		customers = new ArrayList<Point>(custNumber);
    		while (customersNumber < custNumber && (line = br.readLine()) != null) {
    			splLine = line.split(cvsSplitBy);
    			customers.add(new Point(customersNumber++, Double.parseDouble(splLine[0]), Double.parseDouble(splLine[1]), Double.parseDouble(splLine[2]),
        				Double.parseDouble(splLine[3]), Double.parseDouble(splLine[4]), Double.parseDouble(splLine[5])));
    		}
     
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
	}
	
	public static Point getDepot(){
		return depot;
	}
	
	public static Point getCustomer(int i){
		return customers.get(i);
	}
}
