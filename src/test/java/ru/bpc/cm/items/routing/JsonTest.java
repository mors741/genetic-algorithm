package ru.bpc.cm.items.routing;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import ru.bpc.cm.items.routing.pareto.Pareto;
import ru.bpc.cm.items.routing.pareto.problem.Point;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class JsonTest {
	
	public static Matrix testErma() throws IOException {
        ClassLoader classLoader = JsonTest.class.getClassLoader();
		InputStream jsonInputStream = classLoader.getResourceAsStream("final_matrix.json");
		InputStream csvInputStream = classLoader.getResourceAsStream("customers.csv");

        return createMatrix(
        		getCustomersFromCsv(csvInputStream),
				getDistTimeMatrixFromJson(jsonInputStream)
		);
	}

	private static LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Integer>>> getDistTimeMatrixFromJson(InputStream jsonInputStream) throws IOException {
		return JsonPath.read(jsonInputStream, "$");
	}

	private static ArrayList<Point> getCustomersFromCsv(InputStream custInputStream) throws IOException {
		String cvsSplitBy = ";";
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
			if (start >= end) {
				start = 0;
				end = 23*3600 + 59*60 + 0;
			}
            if (!customers.isEmpty() && splLine[0].equals(customers.get(customers.size()-1).getATM())) {
                customers.get(customers.size()-1).addDemand(demand);
            } else {
                customers.add(new Point(n,n,id,demand,start,end,service));
            }
        }
		System.out.println("Customers count = " + customers.size());
		return customers;
	}

	private static Matrix createMatrix(ArrayList<Point> customers, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Integer>>> distTimeMatrix) {
		Matrix m = new Matrix(customers.size());
		for (int i = 0; i < customers.size(); i++){
			Point cust = customers.get(i);
			m.ENC[i] = cust.getId();
			m.ATM[i] = cust.getATM();
			m.amountOfMoney[i] = cust.getDemand();
			m.amountOfCassettes[i] = i==0 ? 0 : 200;
			m.serviceTime[i] = cust.getServiceTime();
			m.addTimeWindow(cust.getId(), cust.getReadyTime(), cust.getDueDate(), false);
			m.AtmPrice[i] = 1;
			for (int j = 0; j < customers.size(); j++) {
				Point custJ = customers.get(j);
				 m.distanceCoeffs[i][j] = distTimeMatrix.get(cust.getATM()).get(custJ.getATM()).get("distance");
				 m.timeCoeffs[i][j] = distTimeMatrix.get(cust.getATM()).get(custJ.getATM()).get("time");
			}
		}
		m.addRiderTimeWindow(60, 1380);

		m.MaxMoney = 225;
		m.VolumeOneCar = 40000000; // (макс кол-во кассет, кот. может перевезти машина) - не используется
		m.FixPrice = 100.0;  // - фикс стоимость подъезда машины к банкомату
		m.LengthPrice = 20.0;  // - цена за километр пути
		m.MaxATMInWay = 0; // - макс кол-во банкоматов в маршруте
		m.MaxTime = 0; // - макс время которое можно затрачивать на 1 маршрут
		m.MaxLength = 0; // макс длина одного маршрута
		m.depot = "1";  // - идентификатор депо
		m.maxCars = 0;   // - макс доступное кол-во машин
		m.currCode = 810; // - валюта всех денежных параметров
		m.windowMode = 0; // - режим окон для банкоматов, обычный и дефолтный (при котором, каждое окно ставится на максимально возможный промежуток);
		return m;
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