package problem;

import main.GA;


public class Point{
	private int id;
	private double x;
	private double y;
	private double demand;
	private double rtime;
	private double ddate;
	private double stime;
	
	public Point(int id, double x, double y, double demand, double rtime, double ddate, double stime) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.demand = demand;
		this.rtime = rtime;
		this.ddate = ddate;
		this.stime = stime;
	}
	
	public int getId(){
		return id;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getDemand(){
		return demand;
	}
	
	public double getReadyTime(){
		return rtime;
	}
	
	public double getDueDate(){
		return ddate;
	}
	
	public double getServiceTime(){
		return stime;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.id);	
	}
	
	public double distanceTo(Point p) {
		return Math.sqrt(Math.pow(this.y-p.y, 2)+Math.pow(this.x-p.x, 2));
	}
	
	// Возвращает номер ближайшей точки в пределах выбранного Евклидового радиуса
	// или -1, если такой точки не сущестует
	public int getNearestCustomerId() {
		double dist;
    	double minDist = GA.EUCLIDEAN_RADIUS;
    	int minId = -1;
    	for (Point p : Problem.customers) {
    		dist = this.distanceTo(p);
    		if (dist < minDist && dist > 0) {
    			minDist = dist;
    			minId = p.getId();
    		}
    	}
    	return minId;
	}
}
