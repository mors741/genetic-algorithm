package problem;

import java.util.ArrayList;


public class Matrix {
	public int [] ENC; //(идентификаторы инкассаций)
	public String [] ATM = {"Depot", "A000", "B001", "C002", "D003", "E004","F005", "G006", "H007"}; //(соотв. идентификаторы банкоматов)
	public int[][] distanceCoeffs = {{0, 40, 50, 30, 20, 60, 140, 150, 130}, {40, 0, 30 ,50, 140, 50, 30, 130, 50}, {50, 130, 0, 40, 50, 30, 40, 115, 10}, {30, 50, 40, 0, 55,40, 150, 130, 130}, {10, 70, 20, 50, 0,40, 50, 30, 10},{40, 40, 190, 140, 140, 0, 40, 50, 30}, {40, 40, 190, 140, 140, 40, 0, 50, 30}, {40, 40, 190, 140, 140, 40, 50,0, 30}, {40, 40, 190, 140, 140, 40, 50, 30, 0}}; 
	public int[][] timeCoeffs = {{0, 15000, 30000, 25000, 11000, 30000, 25000, 5000, 2500}, {15000, 0, 30000, 25000, 8000, 12500 ,25000, 27500, 12000}, {28000, 22500, 0, 21000, 25000, 18000, 15000, 11000, 2500}, {25000, 9000, 21000, 0, 8000, 5000, 31000, 13000, 5000}, {11000, 12000, 3000, 4000, 0, 8000, 5000, 31000, 5000}, {7000, 16000, 5000, 5000, 12000, 0, 8000, 5000, 1000}, {51000, 2000, 3000, 24000, 8000, 5000, 0, 1000, 5000}, {1000, 22000, 3000, 24000, 8000, 5000, 1000, 0, 5000}, {1000, 2000, 3000, 4000, 8000, 5000, 1000, 5000, 0}}; 
	private ArrayList<TimeWindow> timeWindows = new ArrayList<TimeWindow>();
	//private ArrayList<RiderTimeWindow> riderTimeWindows;
	public int[] amountOfMoney = {0, 10, 20, 30, 20, 20, 10, 20, 30}; //(количество денег, которое нужно загрузить в каждый банкомат, может быть 0)
	public int[] serviceTime = {0, 1800, 1800, 1800, 1800, 1800, 1800, 1800, 1800}; // - вместо этого можно просто int serviceTime (это время обслуживания одного банкомата, сейчас оно одно для всех, но в идеале может различаться для разных типов банкоматов и даже быть уникальным для каждого)
	public int MaxMoney = 80; // (макс количество денег, которое может перевезти машина)
	public int[] amountOfCassettes; // (кол-во кассет, которое нужно завезти в банкомат) - это сейчас не используется
	public int VolumeOneCar; // (макс кол-во кассет, кот. может перевезти машина) - не используется
	public double FixPrice; // - фикс стоимость подъезда машины к банкомату
	public double LengthPrice; // - цена за километр пути
	public int MaxATMInWay; // - макс кол-во банкоматов в маршруте
	public int MaxTime; // - макс время которое можно затрачивать на 1 маршрут
	public int MaxLength = 400; // макс длина одного маршрута
	public String depot = "Depot"; // - идентификатор депо
	public int maxCars = 5; // - макс доступное кол-во машин
	public double[] AtmPrice; // - стоимость подъезда к каждому банкомату (сейчас не используется)
	public int currCode; // - валюта всех денежных параметров
	public int windowMode; // - режим окон для банкоматов, обычный и дефолтный (при котором, каждое окно ставится на максимально возможный промежуток);
	public boolean useWindowsFlag; // - учитывать ли временные окна (сейчас не используется)
	
	public Matrix(){
		timeWindows.add(new TimeWindow(0, 1380));
		timeWindows.add(new TimeWindow(60, 360));
		timeWindows.add(new TimeWindow(120, 600));
		timeWindows.add(new TimeWindow(1040, 1320));
		timeWindows.add(new TimeWindow(60, 400));
		timeWindows.add(new TimeWindow(200, 1000));
		timeWindows.add(new TimeWindow(940, 1020));
		timeWindows.add(new TimeWindow(160, 300));
		timeWindows.add(new TimeWindow(200, 400));
	}
	
	public TimeWindow getTimeWindow(int i){
		return timeWindows.get(i);
	}
}
