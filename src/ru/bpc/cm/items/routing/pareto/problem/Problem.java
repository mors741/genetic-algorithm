package ru.bpc.cm.items.routing.pareto.problem;

import java.util.ArrayList;
import java.util.List;

import ru.bpc.cm.items.routing.heneticmethod.Matrix;
import ru.bpc.cm.items.routing.heneticmethod.RiderTimeWindow;

/**
 * @author mors741
 *
 */
public class Problem {
	/**
	 * Amount of customers in problem.
	 */
	public static int customersNumber;
	/**
	 * Maximum amount of money that car can store.
	 */
	public static int vehicleCapacity;
	public static Point depot;
	public static int[][] distanceCoeffs;
	public static int[][] timeCoeffs;
	public static List<Point> customers; // First cusomer's id = 0, not 1!
	public static int maxRouteLength;
	/**
	 * Maximum amount of cars that can be used in solution.
	 */
	public static int maxCars;

	/**
	 * Maximum time that driver has.
	 */
	public static int maxRouteTime;
	/**
	 * The price of servicing one ATM.
	 */
	public static double servicePrice;
	/**
	 * The price of one kilometer of the route.
	 */
	public static double kmPrice;
	/**
	 * Maximum amount of ATMs in one route.
	 */
	public static int maxCustInRoute;

	/**
	 * Currency of all cash parameters.
	 */
	public static int currCode;
	/**
	 * ATM window mode. Normal and default (in which, each time window is placed
	 * on the maximum possible period);
	 */
	public static int windowMode;

	/**
	 * Time windows of car drivers.
	 * 
	 * @Unsupported
	 */
	public static ArrayList<RiderTimeWindow> riderTimeWindows;
	/**
	 * Max amount of cassettes that one car can store.
	 * 
	 * @Unsupported
	 */
	public static int volumeOneCar;
	/**
	 * Amount of cassettes that each ATM needs.
	 * 
	 * @Unsupported
	 */
	public static int[] amountOfCassettes;
	/**
	 * The price of servicing each ATM.
	 * 
	 * @Unsupported
	 */
	public static double[] atmPrice;

	public static void Init(Matrix m) {
		vehicleCapacity = m.MaxMoney == 0 ? Integer.MAX_VALUE : m.MaxMoney;
		distanceCoeffs = m.distanceCoeffs;
		timeCoeffs = m.timeCoeffs; // in seconds
		customersNumber = distanceCoeffs.length - 1;

		maxRouteLength = m.MaxLength == 0 ? Integer.MAX_VALUE : m.MaxLength;
		maxCars = m.maxCars == 0 ? Integer.MAX_VALUE : m.maxCars;
		maxCustInRoute = m.MaxATMInWay == 0 ? Integer.MAX_VALUE : m.MaxATMInWay;
		maxRouteTime = m.MaxTime == 0 ? Integer.MAX_VALUE : m.MaxTime;

		depot = new Point(-1, m.ENC[0], m.depot, m.amountOfMoney[0], m.getTimeWindow(0).StartWork,
				m.getTimeWindow(0).EndWork, m.serviceTime[0]);
		customers = new ArrayList<Point>(customersNumber);
		for (int i = 1; i <= customersNumber; i++) {
			customers.add(new Point(i - 1, m.ENC[i], m.ATM[i], m.amountOfMoney[i], m.getTimeWindow(i).StartWork,
					m.getTimeWindow(i).EndWork, m.serviceTime[i]));
		}

		riderTimeWindows = m.getRiderTimeWindows();
		amountOfCassettes = m.amountOfCassettes;
		volumeOneCar = m.VolumeOneCar;
		servicePrice = m.FixPrice;
		kmPrice = m.LengthPrice;
		atmPrice = m.AtmPrice;
		currCode = m.currCode;
		windowMode = m.windowMode;
	}

	public static Point getDepot() {
		return depot;
	}

	public static int distanceBetween(Point p1, Point p2) {
		return distanceCoeffs[p1.getId() + 1][p2.getId() + 1];
	}

	public static int timeBetween(Point p1, Point p2) {
		return timeCoeffs[p1.getId() + 1][p2.getId() + 1];
	}

	public static Point getCustomer(int i) {
		return customers.get(i);
	}
}
