package ru.bpc.cm.items.routing;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import ru.bpc.cm.items.routing.pareto.Pareto;
import ru.bpc.cm.items.routing.pareto.problem.Point;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class JsonTest {
	
	public static Matrix testErma() throws IOException {
        String cvsSplitBy = ";";
        ClassLoader classLoader = JsonTest.class.getClassLoader();
        InputStream jsonInputStream = classLoader.getResourceAsStream("final_matrix.json");
        InputStream custInputStream = classLoader.getResourceAsStream("customers.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(custInputStream, "UTF-8"));
        String line;
		ArrayList<Point> customers = new ArrayList<Point>();
        while ((line = br.readLine()) != null) {
            String[] splLine = line.split(cvsSplitBy);
			int n = customers.size();
			String id = splLine[0];
			int demand = splLine[1].isEmpty() ? 0 : Integer.parseInt(splLine[1]);
			int service = toSecs(splLine[2]);
			int start = toSecs(splLine[3]);
			int end = toSecs(splLine[4]);
            if (!customers.isEmpty() && splLine[0].equals(customers.get(customers.size()-1).getATM())) {
				customers.get(customers.size()-1).addDemand(demand);
            } else {
				customers.add(new Point(n,n,id,demand,start,end,service));
			}
        }
        System.out.println("Customers count = " + customers.size());

		// TODO: 03.12.16 add depot to CSV

        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Integer>>> dist = JsonPath.read(jsonInputStream, "$");
        for (Map.Entry<String, LinkedHashMap<String, LinkedHashMap<String, Integer>>> atm : dist.entrySet()) {
//            System.out.println(atm.getKey());
        }
        System.out.println(dist.get("000004345").get("000004346").get("distance"));
		System.out.println(dist.keySet().size());
		System.exit(0);

        return null;
	}

	private static int toSecs(String s) {
		String[] time = s.split(":");
		int hours = Integer.parseInt(time[0]);
		int mins = Integer.parseInt(time[1]);
		int secs = Integer.parseInt(time[2]);
		return hours*3600 + mins*60 + secs;
	}

	@Test
	public void testProblem() throws IOException {
		Matrix m = testErma();
		
    	Pareto solver = new Pareto(m);
    	Pareto.SHOW_DEBUG = true;
        
    	SolutionRoutes result = solver.computeResult();
    	
    	System.out.println(result.getStatus());
    	
    	for (ArrayList<Integer> route : result.getRoutes()) {
    		System.out.print("[");
    		for (Integer pid : route) {
    			System.out.print(pid + " ");
    		}
    		System.out.println("]");
    	}
    	
    	for (ArrayList<Integer> route : result.getRoutes()) {
    		int order = 1;
    		int pointTime = m.getTimeWindows().get(0).StartWork; // start time of depot
    		pointTime -= m.serviceTime[0]; // if not serving depot in the beginning
    		int prevCust = 0;
    		for (Integer pid : route) {
    			pointTime = Math.max(pointTime + m.timeCoeffs[prevCust][pid] + m.serviceTime[prevCust], m.getTimeWindows().get(pid).StartWork);
    			System.out.println(pid + " " + order + " "  +pointTime);
    			prevCust = pid;
    			order++;
    		}
    	}

	}
    	 	
}  