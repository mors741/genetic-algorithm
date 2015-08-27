package problem;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.bpc.cm.items.routing.heneticmethod.Matrix;
import ru.bpc.cm.items.routing.pareto.population.Individual;
import ru.bpc.cm.items.routing.pareto.population.Route;
import ru.bpc.cm.items.routing.pareto.problem.Problem;

public class GATest {

	@Before
	public void before() {
		System.out.println("Before");
		Matrix m = new Matrix(3);
		Problem.Init(m);
	}

	@After
	public void after() {
		System.out.println("After");
	}

	@Test
	public void testProblem() {

		assertEquals(8, Problem.customersNumber);

		assertEquals(0, Problem.getCustomer(0).getId());
		assertEquals(-1, Problem.getDepot().getId());
		assertEquals("C002", Problem.getCustomer(2).getATM());
		assertEquals(20, Problem.getCustomer(1).getDemand());
		assertEquals(1800, Problem.getCustomer(1).getServiceTime());

		assertEquals(50, Problem.getDepot().distanceTo(Problem.getCustomer(1)));
		assertEquals(30000, Problem.getCustomer(0).timeTo(Problem.getCustomer(1)));

		assertEquals(1, Problem.getCustomer(0).getNearestCustomerId());
		assertEquals(7, Problem.getCustomer(1).getNearestCustomerId());
		assertEquals(1, Problem.getCustomer(2).getNearestCustomerId());
		System.out.println();

	}

	@Test
	public void testIndividual() {
		Individual ind = new Individual();
		ind.setParetoRank(1);
		Route r = new Route(100);
		r.add(1);
		r.add(2);
		ind.getRouteNetwork().add(r);
		r = new Route(100);
		r.add(3);
		r.add(4);
		r.add(5);
		ind.getRouteNetwork().add(r);
		r = new Route(100);
		r.add(7);
		ind.getRouteNetwork().add(r);
		r = new Route(100);
		r.add(6);
		ind.getRouteNetwork().add(r);
		r = new Route(100);
		r.add(0);
		ind.getRouteNetwork().add(r);
		System.out.println(ind);

		/*
		 * assertEquals(6, 7); assertEquals(50,
		 * Problem.getDepot().distanceTo(Problem.getCustomer(1)));
		 * assertEquals(1, Problem.getCustomer(0).getNearestCustomerId());
		 * assertEquals(0, Problem.getCustomer(1).getNearestCustomerId());
		 * assertEquals(1, Problem.getCustomer(2).getNearestCustomerId());
		 * System.out.println(Problem.getCustomer(2).getNearestCustomerId());
		 * 
		 * Point p1 = new Point(1,0,0, 20, 1, 3, 0.5); Point p2 = new
		 * Point(2,1,0, 20, 2, 4, 0.5); System.out.println(p1.distanceTo(p2));
		 * assertEquals(1.0, p1.distanceTo(p2), 0.00001); assertEquals(1,
		 * p1.getId()); assertEquals(1, p1.getId());
		 */
	}

}
