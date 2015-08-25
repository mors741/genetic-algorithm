package ru.bpc.cm.items.routing.pareto.outer;

import java.util.ArrayList;

public class Rider {
	ArrayList<Integer> atmList;
	public ArrayList<Double> cost;
	public ArrayList<Integer> time;
	public ArrayList<Integer> timewait;
	public ArrayList<Integer> length;
	public Integer sumWaitTime;
	public Integer sumTime;
	public Integer Money;
	public Integer Cassettes;
	public Integer SumLength;
	public Integer errorFlag;

	
	public Integer WaitInDepoTime;
	   
	public int WayNum;

	public Rider() {
		atmList = new ArrayList<Integer>();
		time = new ArrayList<Integer>();
		length = new ArrayList<Integer>();
		timewait = new ArrayList<Integer>();
		cost = new ArrayList<Double>();
		sumWaitTime = 0;
		sumTime = 0;
		Money = 0;
		Cassettes = 0;
		SumLength=0;
		WaitInDepoTime=0;
		errorFlag=0;
	}
	
	 public int getWaitInDepoTime()
	 {
	  return WaitInDepoTime;
	 }
	
	public int getAtmTimeWait(int atmNum)
    {
         return timewait.get(atmNum);
    }
	
	public int getSumWaitTime(){
		return sumWaitTime;
	}

	public Integer getSumTime() {
		return sumTime;
	}

	public ArrayList<Integer> GetAtmList() {
		return atmList;
	}

	public int GetWayNum() {
		return WayNum;
	}

	public int GetAtmTime(int atmNum) {
		return time.get(atmNum);
	}

	public int GetLength(int atmNum) {
		return length.get(atmNum);
	}

	public double GetAtmCost(int atmNum) {
		return cost.get(atmNum);
	}
	
	public int getSumLength()
	 {
	  return SumLength;
	 }
}