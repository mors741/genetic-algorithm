package ru.bpc.cm.items.routing.pareto.outer;

import java.util.ArrayList;

public class Riders {
	public Riders(Matrix pMatrix) {
		matrix = pMatrix;
	}

	private boolean goodFlag;
	public int errorFlag;

	public boolean isGood() {
		if (!isActual) {
			calcFunc();
			isActual = true;
		}
		return goodFlag;
	}
	public int isError() {

		return errorFlag;
	}

	public void updateRiders(Integer[] items) {
		riderList = new ArrayList<Rider>();
		int i = 0;
		Rider currRider = new Rider();
		int j = 0;
		int windowCount = matrix.getWayNumber() - 1;
		while (i < items.length) {
			if (items[i] == 0 && windowCount == j) {
				j = 0;
				if (currRider.atmList.size() > windowCount)
					riderList.add(currRider);
				currRider = new Rider();
			} else {
				currRider.atmList.add(items[i]);
				j = items[i] == 0 ? j + 1 : j;
			}
			i++;
		}
		if (currRider.atmList.size() > windowCount)
			riderList.add(currRider);

		isActual = false;
	}

	private Matrix matrix;
	private double F;
	boolean isActual;

	public double getActualFunctionValue() {
		if (!isActual) {
			calcFunc();
			isActual = true;
		}
		return F;
	}

	private void calcFunc() {
		F = 0;
		goodFlag = true;
		errorFlag = 0;
		for (int i = 0; i < riderList.size(); i++) {
			ArrayList<Integer> atmList = riderList.get(i).atmList;
			/*if (atmList.size() > matrix.MaxATMInWay) {
				goodFlag = false;
				errorFlag=1;
				return;
			}*/
			//calculating for each possible route, appraising which is the best
			Rider[] riderTryList = new Rider[matrix.geRiderWindowCount()];
			Double[] FList = new Double[matrix.geRiderWindowCount()];
			Double[] FAtmList = new Double[matrix.geRiderWindowCount()];
			for (int windowNum = 0; windowNum < riderTryList.length; windowNum++) {
				Rider rider = new Rider();
				riderTryList[windowNum] = rider;
				rider.atmList = atmList;
				int time = matrix.getStartMinute(windowNum);
				double cost = 0;
				FList[windowNum] = (double) 0;
				FAtmList[windowNum] = (double) 0;
				for (int j = 0; j < rider.atmList.size(); j++) {// + 1
					int oldPoint = j == 0 ? 0 : rider.atmList.get(j - 1);
					int newPoint = j == rider.atmList.size() ? 0
							: rider.atmList.get(j);
					if(j==0)
					{
						rider.WaitInDepoTime=0;
					}
					time += matrix.timeCoeffs[oldPoint][newPoint];
					rider.time.add(time);
					/*if (atmList.size() > matrix.MaxATMInWay) {
						goodFlag = false;
						errorFlag = 1;
						rider.errorFlag=1;
						return;
					}*/

					//if (!matrix.useWindowsFlag)
					//{
					if (!matrix.isInRiderWindow(time, windowNum)) {
						goodFlag = false;
						errorFlag = 2;
						rider.errorFlag=2;
						return;
					//}
					}
					//int waitTime = matrix.isInWindow(newPoint, time);
					int waitTime = 0;
					if (time < matrix.getTimeWindow(newPoint).StartWork);
					{
						waitTime = matrix.getTimeWindow(newPoint).StartWork - time;
					}

					if (j==0){
						rider.timewait.add(0);

						if(waitTime>0){
							rider.WaitInDepoTime=waitTime;
							//time = matrix.getStartMinute(windowNum) + rider.WaitInDepoTime;
							waitTime =0;
							//time += matrix.timeCoeffs[oldPoint][newPoint];
							rider.time.add(time);
						}
					} else {
						/*rider.timewait.add(waitTime - rider.WaitInDepoTime);
						rider.sumWaitTime+=waitTime - rider.WaitInDepoTime;
						if(waitTime>0){//vehicle arrived to ATM before window opening
							time+=waitTime - rider.WaitInDepoTime; //current time + wait time
						}*/
						
						rider.timewait.add(waitTime);
						rider.sumWaitTime+=waitTime;
						if(waitTime>0){//vehicle arrived to ATM before window opening
							time+=waitTime - rider.WaitInDepoTime; //current time + wait time
						}
					}
					
					
					
					time+=matrix.serviceTime[newPoint];
					rider.sumTime+=time;
					//rider.sumTime+=time - (matrix.getStartMinute(windowNum) + rider.WaitInDepoTime);
					
					//if (!matrix.useWindowsFlag){

					if (waitTime == -1) //vehicle arrived to ATM after window closing
					{
						goodFlag = false; //chromosome is bad, there is no point in waiting
						rider.errorFlag=3;
						errorFlag=3;
						return;
					}
					//}
					rider.Money += matrix.amountOfMoney[newPoint]; //check for money in one car 
					if (rider.Money > matrix.MaxMoney) {
						goodFlag = false; 
						rider.errorFlag=4;
						errorFlag=4;
						return;
					}
					rider.Cassettes += matrix.amountOfCassettes[newPoint]; 
					if (rider.Cassettes > matrix.VolumeOneCar) {
						rider.errorFlag=5;
						errorFlag=5;
						goodFlag = false; //check for cassettes in one car
						return;
					}
					rider.length.add(matrix.distanceCoeffs[oldPoint][newPoint]);
					FList[windowNum]+=matrix.distanceCoeffs[oldPoint][newPoint]*matrix.LengthPrice+matrix.serviceTime[newPoint];
					cost += FList[windowNum] + matrix.AtmPrice[newPoint];
					rider.cost.add(cost);
					FAtmList[windowNum] += matrix.AtmPrice[newPoint]
							* matrix.AtmPrice[newPoint];
				}
				
				if (rider.sumTime>matrix.MaxTime)
                {
					goodFlag = false; //if max time constraint exceeded
					rider.errorFlag=6;
					errorFlag=6;
					return;
                }
                if (rider.SumLength>matrix.MaxLength)
                {
                	goodFlag = false; //if max length constraint exceeded
                	rider.errorFlag=7;
					errorFlag=7;
                	return;
                }
                
			}
			if (matrix.type==1)
            {
            	int indexMin = 0;
                for (int j=1;j<matrix.geRiderWindowCount();j++)
                {
                    if ((FList[indexMin]+FAtmList[indexMin])>(FList[j]+FAtmList[j]))
                    {
                        indexMin = j;
                    }
                }
    			riderList.set(i, riderTryList[indexMin]);
                riderTryList[indexMin].WayNum = indexMin;
                F+=FList[indexMin]+Math.sqrt(FAtmList[indexMin]);
            }
            if (matrix.type==2)
            {
            	int indexMin = 0;
                for (int j=1;j<matrix.geRiderWindowCount();j++)
                {
                    if ((riderTryList[indexMin]).getSumTime()>(riderTryList[j]).getSumTime())
                    {
                        indexMin = j;


                    }
                }
    			riderList.set(i, riderTryList[indexMin]);
                riderTryList[indexMin].WayNum = indexMin;
            	F+=riderTryList[indexMin].getSumTime();
            }
            if (matrix.type==3)
            {
            	int indexMin = 0;
                for (int j=1;j<matrix.geRiderWindowCount();j++)
                {
                    if ((FList[indexMin]+FAtmList[indexMin])+(riderTryList[indexMin]).getSumTime()
                    		>(FList[j]+FAtmList[j]+(riderTryList[j]).getSumTime()))
                    {
                        indexMin = j;
                    }
                }
    			riderList.set(i, riderTryList[indexMin]);
                riderTryList[indexMin].WayNum = indexMin;
                F+=FList[indexMin]+Math.sqrt(FAtmList[indexMin]);
            	F+=riderTryList[indexMin].getSumTime();
            }
		}
	}

	public ArrayList<Rider> riderList;
	
	public int getRiderSumWaitTime(int riderNum){
		return riderList.get(riderNum).getSumWaitTime();
	}
	public int getRiderSumTime(int riderNum){
		return riderList.get(riderNum).getSumTime();
	}

	public int getRidersCount() {
		return riderList.size();
	}

	public int getRiderTime(int riderNum, int atmNum) 
	{
		return riderList.get(riderNum).time.get(atmNum);
	}

	public int getRiderLength(int riderNum, int atmNum) {
		return riderList.get(riderNum).length.get(atmNum);
	}

	public int getRiderMoney(int riderNum) {
		return riderList.get(riderNum).Money;
	}

	public int getRiderCassettes(int riderNum) {
		return riderList.get(riderNum).Cassettes;
	}

	public int getRiderAtmNum(int riderNum) {
		return riderList.get(riderNum).WayNum;
	}

	public double getRiderCost(int riderNum, int atmNum){
		return riderList.get(riderNum).cost.get(atmNum);
	}

	public Integer[] getRiderATM(int riderNum) {
		Rider rider = riderList.get(riderNum);
		ArrayList<Integer> atms = rider.atmList;
		Integer[] result = new Integer[atms.size()];
		for (int i = 0; i < atms.size(); i++) {
			result[i] = matrix.ENC[atms.get(i)];
		}
		return result;
	}

}
