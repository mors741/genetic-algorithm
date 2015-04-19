package problem;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GATest {
	
	@Before
	public void before(){
		System.out.println("Before");
		Matrix m = new Matrix();
		Problem.Init(m);
	}
	
	@After
	public void after(){
		System.out.println("After");
	}
	
	@Test
	public void testProblem() {

		assertEquals(8,Problem.customersNumber);
		
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
	public void test() {
		/*
		assertEquals(6, 7);
		assertEquals(50, Problem.getDepot().distanceTo(Problem.getCustomer(1)));
		assertEquals(1, Problem.getCustomer(0).getNearestCustomerId());
		assertEquals(0, Problem.getCustomer(1).getNearestCustomerId());
		assertEquals(1, Problem.getCustomer(2).getNearestCustomerId());
		System.out.println(Problem.getCustomer(2).getNearestCustomerId());

		Point p1 = new Point(1,0,0, 20, 1, 3, 0.5);
		Point p2 = new Point(2,1,0, 20, 2, 4, 0.5);
		System.out.println(p1.distanceTo(p2));
		assertEquals(1.0, p1.distanceTo(p2), 0.00001);
		assertEquals(1, p1.getId());
		assertEquals(1, p1.getId());
		*/
	}

}
