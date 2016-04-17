package pareto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ru.bpc.cm.items.routing.heneticmethod.Matrix;
import ru.bpc.cm.items.routing.pareto.Pareto;
import ru.bpc.cm.items.routing.pareto.population.Individual;
import ru.bpc.cm.items.routing.SolutionRoutes;
  
  
public class ParetoTest {
	
	String problemName;
	int customerNumber;
	
	Matrix m;
	
	private void test1() {
		m = new Matrix(4);
    	m.ENC = new int[]{0, 1, 3, 2};  //(идентификаторы инкассаций)
    	m.ATM = new String[]{"1", "132964", "155652", "155750"}; //(соотв. идентификаторы банкоматов)
    	m.distanceCoeffs = new int[][]{{0, 17, 39, 19}, {18, 0, 26, 33}, {42, 29, 0, 67}, {20, 33, 66, 0}}; 
    	m.timeCoeffs = new int[][]{{0, 17, 39, 19},{18, 0, 26, 33},{42, 29, 0, 67},{20, 33, 66, 0}}; 
    	m.addTimeWindow(0, 0, 10000, false);
    	m.addTimeWindow(1, 480, 840, false);
    	m.addTimeWindow(2, 600, 1080, false);
    	m.addTimeWindow(3, 480, 840, false);
    	m.addRiderTimeWindow(60, 1380);
    	m.amountOfMoney = new int[]{0, 500000, 500000, 500000};  //(количество денег, которое нужно загрузить в каждый банкомат, может быть 0)
    	m.serviceTime = new int[]{20, 20, 20, 20};  // - вместо этого можно просто int serviceTime (это время обслуживания одного банкомата, сейчас оно одно для всех, но в идеале может различаться для разных типов банкоматов и даже быть уникальным для каждого)
    	m.MaxMoney = 40000000; // (макс количество денег, которое может перевезти машина)
    	m.amountOfCassettes = new int[] {0, 200, 200, 200};  // (кол-во кассет, которое нужно завезти в банкомат) - это сейчас не используется
    	m.VolumeOneCar = 40000000; // (макс кол-во кассет, кот. может перевезти машина) - не используется
    	m.FixPrice = 100.0;  // - фикс стоимость подъезда машины к банкомату
    	m.LengthPrice = 20.0;  // - цена за километр пути
    	m.MaxATMInWay = 7; // - макс кол-во банкоматов в маршруте
    	m.MaxTime = 0; // - макс время которое можно затрачивать на 1 маршрут
    	m.MaxLength = 0; // макс длина одного маршрута
    	m.depot = "1";  // - идентификатор депо
    	m.maxCars = 5;   // - макс доступное кол-во машин
    	m.AtmPrice = new double[] {100.0, 100.0, 100.0, 100.0}; // - стоимость подъезда к каждому банкомату (сейчас не используется)
    	m.currCode = 810; // - валюта всех денежных параметров
    	m.windowMode = 0; // - режим окон для банкоматов, обычный и дефолтный (при котором, каждое окно ставится на максимально возможный промежуток);
		
		/*
		~~~~~~~~~~~~~~~Riders~~~~~~~~~~~~~~~~~~~
		Rider:
			atmList = [1, 2] номера банкоматов в правильном порядке
			cost = [460.0, 1460.0] ? 
			time = [87, 142] ? (60+17, )
			timewait = [0, 458] ?
			length = [17, 26] расстояния между точками (17 - от депо в 1, 26 - от 1 в 2, от 2 в депо не учитывается
			sumWaitTime = 458 сумма timeWait 0 + 458
			sumTime = 727 сумма time + сумма serviceTime + sumWaitTime (87 + 20 + 0 + 142 + 20 + 458 = 727)
			  // тупо! 87 = 60 + 17, а 60 он итак ждет
			Money = 1000000 (500000+500000) - сумма денег для всех банкоматов
			Cassettes = 400 ?
			SumLength = 0 ?
			WaitInDepoTime = 393 время от открытия временного окна водителя до выезда (60 -> 453 (463+17 = 4
			WayNum = 0
		Rider:
			atmList = [3]
			cost = [500.0]
			time = [88]
			timewait = [0]
			length = [19]
			sumWaitTime = 0
			sumTime = 108
			Money = 500000
			Cassettes = 200
			SumLength = 0
			WaitInDepoTime = 392
			WayNum = 0
		~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Rider Number: 1Route Number1 Route time: 87 Route cost: 460.0 Route length: 17 Rider money: 1000000 Route: [1, 3]
		Rider Number: 2Route Number1 Route time: 88 Route cost: 500.0 Route length: 19 Rider money: 500000 Route: [2]
		*/
	}
	
	// 20/05/2015
	// 20/05/2015
	private void test2() {
		m = new Matrix(5);
		m.ENC = new int[]{0, 232583, 232584, 232581, 198008};
		m.ATM = new String[]{"1", "155652", "155696", "210004", "501260"};
		m.distanceCoeffs = new int[][]{{0, 47, 10, 30, 49}, {56, 0, 51, 76, 10}, {53, 6, 0, 24, 50}, {4, 17, 31, 0, 75}, {22, 20, 5000, 5000, 0}};
		m.timeCoeffs = new int[][]{{0, 59, 21, 41, 64}, {66, 0, 60, 76, 21}, {61, 9, 0, 29, 59}, {6, 19, 44, 0, 76}, {26, 27, 0, 0, 0}};
		m.addTimeWindow(0, 0, 10000, false);
		m.addTimeWindow(1, 600, 960, false);
		m.addTimeWindow(2, 600, 1380, false);
		m.addTimeWindow(3, 600, 1380, false);
		m.addTimeWindow(4, 600, 780, false);
		m.addRiderTimeWindow(300, 1380);
		m.amountOfMoney = new int[]{0, 6250000, 15840000, 5160000, 9430000};
		m.serviceTime = new int[]{15, 15, 15, 15, 15};
		m.MaxMoney = 150000000;
		m.amountOfCassettes = new int[]{0, 200, 200, 200, 200};
		m.VolumeOneCar = 150000000;
		m.FixPrice = 3500.0;
		m.LengthPrice = 50.0;
		m.MaxATMInWay = 13;
		m.MaxTime = 0;
		m.MaxLength = 0;
		m.depot = "1";
		m.maxCars = 5;
		m.AtmPrice = new double[]{3500.0, 3500.0, 3500.0, 3500.0, 3500.0};
		m.currCode = 810;
		m.windowMode = 0;
		
		/*~~~~~~~~~~~~~~~Riders~~~~~~~~~~~~~~~~~~~
			Rider:
				atmList = [2, 3, 1, 4]
				cost = [4015.0, 9245.0, 15340.0, 21950.0]
				time = [321, 365, 634, 670]
				timewait = [0, 235, 0, 0]
				length = [10, 24, 17, 10]
				sumWaitTime = 235
				sumTime = 2285
				Money = 36680000
				Cassettes = 800
				SumLength = 0
				WaitInDepoTime = 279
				WayNum = 0
			~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			Rider Number: 1Route Number1 Route time: 321 Route cost: 4015.0 Route length: 10 Rider money: 36680000 Route: [232584, 232581, 232583, 198008]
		
		*/
	}
	
	// 09/04/2015
	// 09/04/2015
	private void test3() {
		m = new Matrix(25);
		m.ENC = new int[]{0, 63177, 63189, 63182, 63180, 63201, 63188, 63194, 63187, 63190, 63198, 63179, 63176, 63178, 63196, 63192, 63202, 63204, 63183, 63184, 63181, 63191, 63200, 63185, 63186};
		m.ATM = new String[]{"1", "202524", "202576", "202580", "202640", "202658", "202670", "202682", "202688", "202722", "202748", "202758", "202778", "202784", "202790", "202800", "202900", "210004", "300284", "300454", "451013", "451054", "451084", "451104", "451106"};
		m.distanceCoeffs = new int[][]{{0, 10, 19, 16, 8, 13, 15, 8, 13, 16, 10, 10, 6, 19, 8, 8, 14, 12, 10, 16, 19, 6, 20, 8, 6}, {17, 0, 17, 11, 24, 18, 22, 8, 12, 27, 5, 18, 26, 17, 6, 22, 9, 11, 21, 18, 13, 21, 17, 10, 12}, {20, 12, 0, 13, 26, 20, 25, 15, 14, 20, 14, 21, 37, 15, 9, 24, 23, 21, 14, 20, 13, 23, 5, 22, 14}, {5, 4, 12, 0, 18, 11, 6, 11, 12, 21, 20, 12, 12, 3, 15, 8, 25, 14, 7, 6, 19, 22, 23, 12, 8}, {21, 20, 11, 21, 0, 20, 25, 9, 11, 30, 10, 17, 29, 21, 10, 25, 7, 8, 14, 19, 17, 4, 26, 22, 14}, {1, 7, 12, 10, 7, 0, 5, 14, 12, 26, 19, 9, 10, 2, 15, 5, 15, 12, 14, 6, 11, 20, 11, 21, 7}, {18, 18, 12, 25, 19, 23, 0, 10, 13, 28, 2, 20, 27, 19, 4, 23, 13, 13, 10, 7, 12, 16, 10, 17, 7}, {15, 13, 5, 21, 9, 20, 3, 0, 4, 23, 7, 14, 22, 15, 4, 19, 11, 9, 13, 18, 16, 5, 25, 2, 21}, {13, 6, 10, 9, 6, 5, 13, 11, 0, 23, 18, 8, 9, 3, 14, 5, 14, 11, 14, 17, 12, 6, 20, 7, 20}, {12, 5, 9, 17, 11, 10, 11, 9, 19, 0, 16, 12, 14, 4, 11, 10, 21, 14, 10, 12, 13, 13, 15, 13, 14}, {4, 11, 10, 18, 12, 16, 2, 1, 20, 12, 0, 12, 20, 11, 7, 16, 17, 9, 10, 22, 26, 19, 19, 8, 27}, {19, 8, 19, 17, 7, 12, 19, 17, 33, 24, 5, 0, 9, 15, 19, 5, 15, 8, 8, 18, 20, 12, 20, 7, 21}, {13, 6, 13, 10, 9, 11, 7, 11, 27, 18, 4, 10, 0, 8, 13, 6, 9, 6, 22, 25, 27, 7, 32, 6, 37}, {20, 6, 11, 17, 15, 12, 20, 18, 26, 25, 15, 11, 9, 0, 20, 9, 21, 17, 9, 10, 16, 11, 10, 12, 12}, {5, 10, 10, 4, 17, 6, 15, 4, 23, 10, 10, 18, 10, 8, 0, 14, 9, 7, 13, 10, 11, 11, 13, 11, 12}, {5, 9, 8, 6, 16, 10, 14, 6, 18, 10, 10, 18, 9, 5, 14, 0, 15, 14, 28, 16, 19, 30, 23, 21, 21}, {21, 17, 19, 31, 26, 22, 20, 19, 27, 26, 34, 20, 22, 30, 32, 27, 0, 6, 11, 20, 11, 21, 2, 7, 18}, {16, 12, 24, 19, 23, 10, 11, 28, 19, 27, 19, 3, 23, 13, 13, 20, 21, 0, 13, 17, 10, 18, 14, 9, 14}, {12, 6, 3, 13, 13, 12, 29, 20, 13, 10, 15, 8, 13, 5, 28, 37, 11, 28, 0, 9, 26, 21, 9, 15, 20}, {10, 11, 9, 20, 20, 36, 27, 12, 12, 23, 4, 24, 16, 13, 19, 15, 3, 22, 4, 0, 21, 13, 2, 5, 11}, {11, 10, 7, 10, 11, 25, 19, 11, 11, 14, 7, 15, 12, 8, 9, 16, 11, 17, 3, 4, 0, 15, 12, 8, 21}, {15, 20, 7, 8, 24, 7, 16, 23, 15, 19, 12, 12, 15, 24, 26, 8, 25, 4, 27, 19, 5, 0, 11, 16, 6}, {7, 8, 19, 17, 30, 24, 8, 4, 8, 19, 20, 12, 9, 20, 19, 6, 22, 14, 8, 21, 19, 10, 0, 16, 15}, {25, 9, 11, 29, 12, 12, 24, 22, 12, 20, 4, 9, 20, 15, 7, 15, 14, 8, 14, 19, 10, 11, 8, 0, 19}, {8, 10, 29, 11, 7, 18, 14, 12, 14, 3, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 0}};
		m.timeCoeffs = new int[][]{{0, 23, 29, 28, 18, 28, 28, 19, 26, 27, 20, 20, 14, 36, 16, 15, 25, 23, 20, 23, 30, 15, 34, 18, 13}, {28, 0, 26, 20, 35, 28, 39, 19, 18, 32, 9, 21, 33, 28, 10, 33, 12, 17, 35, 30, 26, 33, 37, 21, 20}, {30, 21, 0, 21, 36, 30, 35, 24, 19, 19, 23, 22, 35, 28, 15, 35, 29, 28, 28, 32, 23, 34, 16, 32, 24}, {16, 10, 20, 0, 28, 21, 16, 22, 18, 25, 22, 14, 21, 8, 20, 23, 29, 21, 16, 13, 27, 33, 37, 21, 17}, {32, 30, 23, 30, 0, 27, 43, 19, 19, 35, 13, 20, 36, 31, 13, 34, 9, 13, 27, 33, 29, 14, 35, 33, 25}, {4, 18, 21, 21, 18, 0, 14, 23, 19, 32, 23, 13, 15, 9, 21, 14, 26, 18, 24, 15, 19, 29, 18, 33, 12}, {27, 25, 19, 34, 27, 38, 0, 18, 17, 32, 5, 21, 33, 27, 5, 32, 17, 20, 22, 16, 16, 25, 18, 29, 16}, {23, 22, 13, 29, 21, 34, 11, 0, 10, 28, 9, 16, 28, 22, 6, 28, 15, 16, 25, 31, 28, 16, 34, 7, 32}, {23, 17, 19, 21, 17, 14, 22, 17, 0, 31, 22, 12, 16, 9, 20, 14, 25, 17, 31, 29, 19, 16, 32, 22, 30}, {21, 19, 16, 30, 23, 27, 19, 14, 25, 0, 21, 17, 26, 13, 19, 29, 27, 23, 20, 22, 18, 21, 25, 25, 23}, {11, 20, 16, 26, 19, 31, 8, 4, 24, 14, 0, 14, 26, 19, 12, 24, 21, 14, 21, 31, 34, 29, 27, 22, 37}, {28, 20, 29, 24, 15, 32, 27, 22, 38, 27, 9, 0, 16, 24, 25, 13, 19, 14, 18, 27, 24, 20, 25, 16, 27}, {19, 14, 20, 15, 16, 26, 17, 13, 30, 19, 5, 15, 0, 15, 17, 11, 19, 11, 42, 47, 33, 17, 49, 19, 46}, {39, 18, 26, 35, 35, 31, 37, 33, 28, 33, 23, 20, 21, 0, 32, 23, 36, 29, 19, 25, 24, 23, 25, 27, 26}, {13, 22, 23, 12, 28, 18, 33, 11, 31, 18, 17, 28, 21, 16, 0, 26, 20, 15, 25, 18, 13, 17, 21, 21, 18}, {10, 15, 12, 10, 22, 15, 26, 13, 20, 10, 10, 22, 15, 9, 20, 0, 17, 17, 33, 21, 26, 36, 34, 31, 26}, {32, 22, 23, 37, 30, 29, 27, 21, 29, 28, 35, 28, 27, 34, 36, 33, 0, 12, 15, 25, 16, 27, 4, 10, 24}, {22, 17, 30, 23, 32, 17, 15, 31, 20, 32, 23, 5, 27, 16, 19, 25, 24, 0, 19, 21, 17, 25, 17, 15, 18}, {15, 11, 8, 23, 19, 13, 31, 20, 18, 14, 18, 13, 15, 10, 36, 35, 21, 35, 0, 17, 36, 29, 17, 28, 27}, {17, 19, 19, 30, 25, 37, 32, 17, 22, 30, 8, 30, 24, 28, 32, 27, 9, 35, 15, 0, 33, 24, 12, 15, 20}, {29, 21, 17, 23, 18, 28, 23, 15, 21, 21, 21, 27, 19, 14, 11, 21, 16, 24, 6, 6, 0, 20, 18, 13, 27}, {20, 28, 14, 11, 28, 9, 17, 29, 19, 23, 16, 20, 28, 38, 35, 24, 34, 15, 38, 29, 16, 0, 31, 26, 14}, {16, 21, 28, 24, 38, 27, 12, 7, 23, 26, 25, 20, 15, 27, 29, 11, 29, 22, 14, 28, 26, 18, 0, 23, 20}, {35, 16, 15, 35, 17, 15, 30, 26, 17, 25, 7, 16, 27, 27, 13, 27, 21, 14, 26, 26, 18, 21, 16, 0, 33}, {16, 15, 35, 16, 14, 29, 24, 18, 23, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
		m.addTimeWindow(0, 0, 10000, false);
		m.addTimeWindow(1, 600, 1380, false);
		m.addTimeWindow(2, 600, 1380, false);
		m.addTimeWindow(3, 600, 1380, false);
		m.addTimeWindow(4, 600, 1380, false);
		m.addTimeWindow(5, 600, 1380, false);
		m.addTimeWindow(6, 600, 1380, false);
		m.addTimeWindow(7, 60, 120, true);
		m.addTimeWindow(8, 60, 120, true);
		m.addTimeWindow(9, 60, 120, true);
		m.addTimeWindow(10, 60, 120, true);
		m.addTimeWindow(11, 600, 1380, false);
		m.addTimeWindow(12, 600, 1380, false);
		m.addTimeWindow(13, 600, 1380, false);
		m.addTimeWindow(14, 600, 1380, false);
		m.addTimeWindow(15, 600, 1380, false);
		m.addTimeWindow(16, 600, 1380, false);
		m.addTimeWindow(17, 600, 1380, false);
		m.addTimeWindow(18, 600, 1380, false);
		m.addTimeWindow(19, 600, 1380, false);
		m.addTimeWindow(20, 600, 1380, false);
		m.addTimeWindow(21, 600, 1380, false);
		m.addTimeWindow(22, 600, 1380, false);
		m.addTimeWindow(23, 600, 1380, false);
		m.addTimeWindow(24, 60, 1380, false);
		m.addRiderTimeWindow(60, 1380);
		m.amountOfMoney = new int[]{0, 21673940, 0, 16350000, 12500000, 12750000, 12750000, 12750000, 12750000, 12750000, 12750000, 10170000, 9690000, 12160000, 12750000, 12750000, 12750000, 5160000, 23434030, 20788850, 22466710, 0, 22919120, 24701800, 26640000};
		m.serviceTime = new int[]{15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
		m.MaxMoney = 150000000;
		m.amountOfCassettes = new int[]{0, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200};
		m.VolumeOneCar = 150000000;
		m.FixPrice = 3500.0;
		m.LengthPrice = 50.0;
		m.MaxATMInWay = 13;
		m.MaxTime = 0;
		m.MaxLength = 0;
		m.depot = "1";
		m.maxCars = 5;
		m.AtmPrice = new double[]{3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0};
		m.currCode = 810;
		m.windowMode = 0;
		 
		/*~~~~~~~~~~~~~~~Riders~~~~~~~~~~~~~~~~~~~
		   Rider:
			 atmList = [7, 3, 6, 11, 17, 4, 20, 12, 22, 13]
			 cost = [3915.0, 8895.0, 14190.0, 20500.0, 27225.0, 35115.0, 43870.0, 53240.0, 64225.0, 76225.0]
			 time = [79, 123, 631, 667, 696, 743, 787, 821, 885, 927]
			 timewait = [0, 477, 0, 0, 0, 0, 0, 0, 0, 0]
			 length = [8, 21, 6, 20, 8, 23, 17, 12, 32, 20]
			 sumWaitTime = 477
			 sumTime = 6986
			 Money = 136915830
			 Cassettes = 2000
			 SumLength = 0
			 WaitInDepoTime = 0
			 WayNum = 0
		   Rider:
			 atmList = [10, 19, 16, 18, 24, 21]
			 cost = [4015.0, 9145.0, 14440.0, 20300.0, 27175.0, 284065.0]
			 time = [80, 126, 624, 654, 696, 711]
			 timewait = [0, 474, 0, 0, 0, 0]
			 length = [10, 22, 3, 11, 20, 5000]
			 sumWaitTime = 474
			 sumTime = 3455
			 Money = 96362880
			 Cassettes = 1200
			 SumLength = 0
			 WaitInDepoTime = 0
			 WayNum = 0
		   Rider:
			 atmList = [8, 23, 5, 1, 14]
			 cost = [4165.0, 8695.0, 13840.0, 19350.0, 25175.0]
			 time = [86, 123, 630, 663, 688]
			 timewait = [0, 477, 0, 0, 0]
			 length = [13, 7, 12, 7, 6]
			 sumWaitTime = 477
			 sumTime = 2742
			 Money = 84625740
			 Cassettes = 1000
			 SumLength = 0
			 WaitInDepoTime = 0
			 WayNum = 0
		   Rider:
			 atmList = [9, 15, 2]
			 cost = [4315.0, 9145.0, 14390.0]
			 time = [87, 131, 627]
			 timewait = [0, 469, 0]
			 length = [16, 10, 8]
			 sumWaitTime = 469
			 sumTime = 1359
			 Money = 25500000
			 Cassettes = 600
			 SumLength = 0
			 WaitInDepoTime = 0
			 WayNum = 0
		 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 Rider Number: 1Route Number1 Route time: 79 Route cost: 3915.0 Route length: 8 Rider money: 136915830 Route: [63194, 63182, 63188, 63179, 63204, 63180, 63181, 63176, 63200, 63178]
		 Rider Number: 2Route Number1 Route time: 80 Route cost: 4015.0 Route length: 10 Rider money: 96362880 Route: [63198, 63184, 63202, 63183, 63186, 63191]
		 Rider Number: 3Route Number1 Route time: 86 Route cost: 4165.0 Route length: 13 Rider money: 84625740 Route: [63187, 63185, 63201, 63177, 63196]
		 Rider Number: 4Route Number1 Route time: 87 Route cost: 4315.0 Route length: 16 Rider money: 25500000 Route: [63190, 63192, 63189]
		*/
 	}
	
	// 01/04/2015
	private void test4() {	
		m = new Matrix(20);
		m.ENC = new int[]{0, 29990, 29983, 29977, 29978, 29988, 29992, 29987, 29986, 29991, 29980, 29985, 29995, 29976, 29982, 29979, 29984, 29996, 29994, 29981};
		m.ATM = new String[]{"1", "202524", "202576", "202580", "202640", "202670", "202682", "202688", "202722", "202748", "202784", "202800", "210004", "300284", "300454", "451013", "451054", "451084", "451104", "451106"};
		m.distanceCoeffs = new int[][]{{0, 10, 19, 16, 8, 15, 8, 13, 16, 10, 19, 8, 14, 12, 10, 16, 19, 6, 8, 6}, {17, 0, 17, 11, 22, 12, 5, 18, 26, 17, 6, 22, 9, 11, 21, 18, 13, 21, 10, 12}, {20, 12, 0, 13, 25, 14, 14, 21, 37, 15, 9, 24, 23, 21, 14, 20, 13, 23, 22, 14}, {5, 4, 12, 0, 6, 12, 20, 12, 12, 3, 15, 8, 25, 14, 7, 6, 19, 22, 12, 8}, {21, 20, 11, 25, 0, 11, 10, 17, 29, 21, 10, 25, 7, 8, 14, 6, 11, 20, 11, 7}, {18, 18, 12, 23, 13, 0, 2, 20, 27, 19, 4, 23, 13, 13, 10, 7, 12, 16, 10, 7}, {15, 13, 5, 20, 4, 7, 0, 14, 22, 15, 4, 19, 11, 9, 13, 18, 16, 5, 25, 21}, {13, 6, 10, 5, 11, 18, 8, 0, 9, 3, 14, 5, 14, 11, 14, 17, 12, 6, 20, 20}, {12, 5, 9, 10, 9, 16, 12, 14, 0, 4, 11, 10, 21, 14, 10, 12, 13, 13, 15, 14}, {4, 11, 10, 16, 1, 12, 12, 20, 11, 0, 7, 16, 17, 9, 22, 25, 27, 7, 32, 37}, {20, 6, 11, 17, 18, 25, 15, 11, 9, 20, 0, 9, 21, 17, 13, 10, 11, 11, 13, 12}, {5, 9, 8, 6, 14, 10, 10, 18, 9, 5, 14, 0, 15, 14, 6, 11, 20, 11, 2, 7}, {18, 16, 12, 23, 11, 19, 27, 19, 3, 23, 13, 13, 0, 20, 21, 13, 17, 18, 14, 9}, {14, 12, 13, 12, 20, 13, 10, 15, 8, 13, 5, 28, 37, 0, 11, 28, 26, 21, 9, 15}, {20, 9, 20, 27, 12, 12, 23, 4, 24, 16, 13, 19, 15, 3, 0, 22, 21, 13, 2, 5}, {11, 7, 11, 19, 11, 11, 14, 7, 15, 12, 8, 9, 16, 11, 3, 0, 4, 15, 12, 8}, {20, 8, 7, 16, 23, 15, 19, 12, 12, 15, 24, 26, 8, 25, 27, 19, 0, 5, 11, 16}, {8, 17, 24, 8, 4, 8, 19, 20, 12, 9, 20, 19, 6, 14, 8, 21, 19, 0, 10, 25}, {11, 12, 12, 24, 22, 12, 20, 4, 9, 20, 15, 7, 14, 8, 14, 19, 10, 19, 0, 10}, {11, 7, 18, 14, 12, 14, 3, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 0}};
		m.timeCoeffs = new int[][]{{0, 23, 29, 28, 18, 28, 19, 26, 27, 20, 36, 15, 25, 23, 20, 23, 30, 15, 18, 13}, {28, 0, 26, 20, 39, 18, 9, 21, 33, 28, 10, 33, 12, 17, 35, 30, 26, 33, 21, 20}, {30, 21, 0, 21, 35, 19, 23, 22, 35, 28, 15, 35, 29, 28, 28, 32, 23, 34, 32, 24}, {16, 10, 20, 0, 16, 18, 22, 14, 21, 8, 20, 23, 29, 21, 16, 13, 27, 33, 21, 17}, {32, 30, 23, 43, 0, 19, 13, 20, 36, 31, 13, 34, 9, 13, 24, 15, 19, 29, 18, 12}, {27, 25, 19, 38, 17, 0, 5, 21, 33, 27, 5, 32, 17, 20, 22, 16, 16, 25, 18, 16}, {23, 22, 13, 34, 10, 9, 0, 16, 28, 22, 6, 28, 15, 16, 25, 31, 28, 16, 34, 32}, {23, 17, 19, 14, 17, 22, 12, 0, 16, 9, 20, 14, 25, 17, 31, 29, 19, 16, 32, 30}, {21, 19, 16, 27, 14, 21, 17, 26, 0, 13, 19, 29, 27, 23, 20, 22, 18, 21, 25, 23}, {11, 20, 16, 31, 4, 14, 14, 26, 19, 0, 12, 24, 21, 14, 42, 47, 33, 17, 49, 46}, {39, 18, 26, 35, 33, 33, 23, 20, 21, 32, 0, 23, 36, 29, 25, 18, 13, 17, 21, 18}, {10, 15, 12, 10, 26, 10, 10, 22, 15, 9, 20, 0, 17, 17, 12, 15, 25, 16, 4, 10}, {24, 22, 17, 32, 15, 20, 32, 23, 5, 27, 16, 19, 0, 25, 24, 19, 21, 25, 17, 15}, {18, 15, 23, 13, 20, 18, 14, 18, 13, 15, 10, 36, 35, 0, 21, 35, 36, 29, 17, 28}, {27, 19, 25, 32, 17, 22, 30, 8, 30, 24, 28, 32, 27, 9, 0, 35, 33, 24, 12, 15}, {20, 17, 18, 23, 15, 21, 21, 21, 27, 19, 14, 11, 21, 16, 6, 0, 6, 20, 18, 13}, {28, 11, 9, 17, 29, 19, 23, 16, 20, 28, 38, 35, 24, 34, 38, 29, 0, 16, 31, 26}, {21, 24, 27, 12, 7, 23, 26, 25, 20, 15, 27, 29, 11, 22, 14, 28, 26, 0, 18, 35}, {15, 17, 15, 30, 26, 17, 25, 7, 16, 27, 27, 13, 21, 14, 26, 26, 18, 33, 0, 15}, {16, 14, 29, 24, 18, 23, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
		m.addTimeWindow(0, 0, 10000, false);
		m.addTimeWindow(1, 600, 1380, false);
		m.addTimeWindow(2, 600, 1380, false);
		m.addTimeWindow(3, 600, 1380, false);
		m.addTimeWindow(4, 600, 1380, false);
		m.addTimeWindow(5, 600, 1380, false);
		m.addTimeWindow(6, 600, 1380, false);
		m.addTimeWindow(7, 600, 1380, false);
		m.addTimeWindow(8, 600, 1380, false);
		m.addTimeWindow(9, 600, 1380, false);
		m.addTimeWindow(10, 600, 1380, false);
		m.addTimeWindow(11, 600, 1380, false);
		m.addTimeWindow(12, 600, 1380, false);
		m.addTimeWindow(13, 600, 1380, false);
		m.addTimeWindow(14, 600, 1380, false);
		m.addTimeWindow(15, 600, 1380, false);
		m.addTimeWindow(16, 600, 1380, false);
		m.addTimeWindow(17, 600, 1380, false);
		m.addTimeWindow(18, 600, 1380, false);
		m.addTimeWindow(19, 60, 1380, false);
		m.addRiderTimeWindow(60, 1380);
		m.amountOfMoney = new int[]{0, 20966260, 0, 15350000, 12500000, 12750000, 10190000, 12730000, 12750000, 12750000, 6590000, 12750000, 11660000, 18621080, 19835990, 21051350, 24334480, 22296530, 21203760, 24670000};
		m.serviceTime = new int[]{15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
		m.MaxMoney = 150000000;
		m.amountOfCassettes = new int[]{0, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200};
		m.VolumeOneCar = 150000000;
		m.FixPrice = 3500.0;
		m.LengthPrice = 50.0;
		m.MaxATMInWay = 13;
		m.MaxTime = 0;
		m.MaxLength = 0;
		m.depot = "1";
		m.maxCars = 5;
		m.AtmPrice = new double[]{3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0};
		m.currCode = 810;
		m.windowMode = 0;

		/*
		~~~~~~~~~~~~~~~Riders~~~~~~~~~~~~~~~~~~~
			Rider:
				atmList = [14, 18, 7, 1, 12, 8, 9, 4, 13]
				cost = [4015.0, 8145.0, 12490.0, 17150.0, 22275.0, 27565.0, 33070.0, 38640.0, 44625.0]
				time = [80, 107, 622, 654, 681, 701, 729, 748, 776]
				timewait = [0, 493, 0, 0, 0, 0, 0, 0, 0]
				length = [10, 2, 4, 6, 9, 3, 4, 1, 8]
				sumWaitTime = 493
				sumTime = 5726
				Money = 143017090
				Cassettes = 1800
				SumLength = 0
				WaitInDepoTime = 520
				WayNum = 0
			Rider:
				atmList = [11, 19, 6, 5, 10, 17, 3, 15, 16, 2]
				cost = [3915.0, 8195.0, 12640.0, 17450.0, 22475.0, 28065.0, 34070.0, 40390.0, 46925.0, 53825.0]
				time = [75, 100, 122, 624, 644, 676, 703, 731, 752, 776]
				timewait = [0, 0, 478, 0, 0, 0, 0, 0, 0, 0]
				length = [8, 7, 3, 7, 4, 11, 8, 6, 4, 7]
				sumWaitTime = 478
				sumTime = 5831
				Money = 149982360
				Cassettes = 2000
				SumLength = 0
				WaitInDepoTime = 525
				WayNum = 0
		 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 Rider Number: 1Route Number1 Route time: 80 Route cost: 4015.0 Route length: 10 Rider money: 143017090 Route: [29982, 29994, 29987, 29990, 29995, 29986, 29991, 29978, 29976]
		 Rider Number: 2Route Number1 Route time: 75 Route cost: 3915.0 Route length: 8 Rider money: 149982360 Route: [29985, 29981, 29992, 29988, 29980, 29996, 29977, 29979, 29984, 29983]
		 */
 	}
	
	// 28/03/2015
	private void test5() {	
		m = new Matrix(20);
		m.ENC = new int[]{0, 22, 14, 8, 9, 19, 24, 18, 17, 23, 11, 26, 16, 28, 7, 13, 10, 15, 27, 12};
		m.ATM = new String[]{"1", "202524", "202576", "202580", "202640", "202670", "202682", "202688", "202722", "202748", "202784", "202790", "202800", "210004", "300284", "300454", "451013", "451054", "451104", "451106"};
		m.distanceCoeffs = new int[][]{{0, 10, 19, 16, 8, 15, 8, 13, 16, 10, 19, 8, 8, 14, 10, 16, 19, 6, 8, 6}, {17, 0, 17, 11, 22, 8, 12, 5, 18, 26, 17, 6, 9, 11, 21, 18, 13, 21, 10, 12}, {20, 12, 0, 13, 25, 15, 14, 14, 21, 37, 15, 9, 23, 21, 14, 20, 13, 23, 22, 14}, {5, 4, 12, 0, 6, 11, 12, 20, 12, 12, 3, 15, 25, 14, 7, 6, 19, 22, 12, 8}, {21, 20, 11, 25, 0, 9, 11, 10, 17, 29, 21, 10, 7, 8, 14, 6, 11, 20, 11, 7}, {18, 18, 12, 23, 10, 0, 13, 2, 20, 27, 19, 4, 13, 13, 10, 7, 12, 16, 10, 7}, {15, 13, 5, 20, 3, 4, 0, 7, 14, 22, 15, 4, 11, 9, 13, 18, 16, 5, 25, 21}, {13, 6, 10, 5, 13, 11, 18, 0, 8, 9, 3, 14, 14, 11, 14, 17, 12, 6, 20, 20}, {12, 5, 9, 10, 11, 9, 16, 12, 0, 14, 4, 11, 21, 14, 10, 12, 13, 13, 15, 14}, {4, 11, 10, 16, 2, 1, 12, 12, 20, 0, 11, 7, 17, 9, 22, 25, 27, 7, 32, 37}, {20, 6, 11, 17, 20, 18, 25, 15, 11, 9, 0, 20, 21, 17, 9, 10, 16, 11, 10, 12}, {5, 10, 10, 4, 15, 4, 10, 10, 18, 10, 8, 0, 9, 7, 13, 10, 11, 11, 13, 12}, {5, 9, 8, 6, 14, 6, 10, 10, 18, 9, 5, 15, 0, 14, 6, 11, 20, 11, 2, 7}, {18, 16, 12, 23, 10, 11, 19, 27, 19, 3, 13, 13, 20, 0, 21, 13, 17, 18, 14, 9}, {14, 12, 13, 13, 12, 20, 13, 10, 15, 13, 5, 28, 37, 11, 0, 28, 26, 21, 9, 15}, {20, 9, 20, 20, 27, 12, 12, 23, 24, 16, 13, 19, 15, 3, 22, 0, 21, 13, 2, 5}, {11, 7, 10, 11, 19, 11, 11, 14, 15, 12, 8, 9, 16, 11, 3, 4, 0, 15, 12, 8}, {20, 7, 8, 7, 16, 23, 15, 12, 12, 9, 20, 19, 6, 14, 8, 21, 19, 0, 10, 25}, {9, 11, 12, 12, 24, 22, 12, 4, 9, 20, 15, 7, 14, 8, 14, 19, 10, 19, 0, 8}, {10, 11, 7, 18, 14, 12, 3, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 0}};
		m.timeCoeffs = new int[][]{{0, 23, 29, 28, 18, 28, 19, 26, 27, 20, 36, 16, 15, 25, 20, 23, 30, 15, 18, 13}, {28, 0, 26, 20, 39, 19, 18, 9, 21, 33, 28, 10, 12, 17, 35, 30, 26, 33, 21, 20}, {30, 21, 0, 21, 35, 24, 19, 23, 22, 35, 28, 15, 29, 28, 28, 32, 23, 34, 32, 24}, {16, 10, 20, 0, 16, 22, 18, 22, 14, 21, 8, 20, 29, 21, 16, 13, 27, 33, 21, 17}, {32, 30, 23, 43, 0, 19, 19, 13, 20, 36, 31, 13, 9, 13, 24, 15, 19, 29, 18, 12}, {27, 25, 19, 38, 18, 0, 17, 5, 21, 33, 27, 5, 17, 20, 22, 16, 16, 25, 18, 16}, {23, 22, 13, 34, 11, 10, 0, 9, 16, 28, 22, 6, 15, 16, 25, 31, 28, 16, 34, 32}, {23, 17, 19, 14, 22, 17, 22, 0, 12, 16, 9, 20, 25, 17, 31, 29, 19, 16, 32, 30}, {21, 19, 16, 27, 19, 14, 21, 17, 0, 26, 13, 19, 27, 23, 20, 22, 18, 21, 25, 23}, {11, 20, 16, 31, 8, 4, 14, 14, 26, 0, 19, 12, 21, 14, 42, 47, 33, 17, 49, 46}, {39, 18, 26, 35, 37, 33, 33, 23, 20, 21, 0, 32, 36, 29, 19, 25, 24, 23, 25, 26}, {13, 22, 23, 12, 33, 11, 18, 17, 28, 21, 16, 0, 20, 15, 25, 18, 13, 17, 21, 18}, {10, 15, 12, 10, 26, 13, 10, 10, 22, 15, 9, 17, 0, 17, 12, 15, 25, 16, 4, 10}, {24, 22, 17, 32, 17, 15, 20, 32, 23, 5, 16, 19, 25, 0, 24, 19, 21, 25, 17, 15}, {18, 15, 23, 19, 13, 20, 18, 14, 18, 15, 10, 36, 35, 21, 0, 35, 36, 29, 17, 28}, {27, 19, 30, 25, 32, 17, 22, 30, 30, 24, 28, 32, 27, 9, 35, 0, 33, 24, 12, 15}, {20, 17, 23, 18, 23, 15, 21, 21, 27, 19, 14, 11, 21, 16, 6, 6, 0, 20, 18, 13}, {28, 14, 11, 9, 17, 29, 19, 16, 20, 15, 27, 29, 11, 22, 14, 28, 26, 0, 18, 35}, {16, 15, 17, 15, 30, 26, 17, 7, 16, 27, 27, 13, 21, 14, 26, 26, 18, 33, 0, 16}, {15, 16, 14, 29, 24, 18, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
		m.addTimeWindow(0, 0, 10000, false);
		m.addTimeWindow(1, 600, 1380, false);
		m.addTimeWindow(2, 600, 1380, false);
		m.addTimeWindow(3, 600, 1380, false);
		m.addTimeWindow(4, 600, 1380, false);
		m.addTimeWindow(5, 600, 1380, false);
		m.addTimeWindow(6, 600, 1380, false);
		m.addTimeWindow(7, 600, 1380, false);
		m.addTimeWindow(8, 600, 1380, false);
		m.addTimeWindow(9, 600, 1380, false);
		m.addTimeWindow(10, 600, 1380, false);
		m.addTimeWindow(11, 600, 1380, false);
		m.addTimeWindow(12, 600, 1380, false);
		m.addTimeWindow(13, 600, 1380, false);
		m.addTimeWindow(14, 600, 1380, false);
		m.addTimeWindow(15, 600, 1380, false);
		m.addTimeWindow(16, 600, 1380, false);
		m.addTimeWindow(17, 600, 1380, false);
		m.addTimeWindow(18, 600, 1380, false);
		m.addTimeWindow(19, 60, 1380, false);
		m.addRiderTimeWindow(60, 1380);
		m.amountOfMoney = new int[]{0, 17388850, 0, 11340000, 12500000, 12750000, 10700000, 10690000, 12190000, 12750000, 10640000, 12750000, 10660000, 6160000, 20321080, 17135990, 18528760, 24149390, 16388850, 17560000};
		m.serviceTime = new int[]{15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
		m.MaxMoney = 150000000;
		m.amountOfCassettes = new int[]{0, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200};
		m.VolumeOneCar = 150000000;
		m.FixPrice = 3500.0;
		m.LengthPrice = 50.0;
		m.MaxATMInWay = 13;
		m.MaxTime = 0;
		m.MaxLength = 0;
		m.depot = "1";
		m.maxCars = 5;
		m.AtmPrice = new double[]{3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0, 3500.0};
		m.currCode = 810;
		m.windowMode = 0;
	/*
	 ~~~~~~~~~~~~~~~Riders~~~~~~~~~~~~~~~~~~~
	   Rider:
		 atmList = [2, 16, 15, 13, 9, 5, 11, 3, 14]
		 cost = [4465.0, 9595.0, 14940.0, 20450.0, 26125.0, 31865.0, 37820.0, 43990.0, 50525.0]
		 time = [89, 127, 621, 645, 665, 684, 704, 731, 762]
		 timewait = [0, 473, 0, 0, 0, 0, 0, 0, 0]
		 length = [19, 13, 4, 3, 3, 1, 4, 4, 7]
		 sumWaitTime = 473
		 sumTime = 5636
		 Money = 111735830
		 Cassettes = 1800
		 SumLength = 0
		 WaitInDepoTime = 511
		 WayNum = 0
	   Rider:
		 atmList = [4, 19, 6, 17, 12, 18, 7, 8, 10, 1]
		 cost = [3915.0, 8195.0, 12640.0, 17350.0, 22375.0, 27515.0, 32870.0, 38640.0, 44625.0, 50925.0]
		 time = [78, 105, 127, 631, 657, 676, 698, 725, 753, 786]
		 timewait = [0, 0, 473, 0, 0, 0, 0, 0, 0, 0]
		 length = [8, 7, 3, 5, 6, 2, 4, 8, 4, 6]
		 sumWaitTime = 473
		 sumTime = 5859
		 Money = 142867090
		 Cassettes = 2000
		 SumLength = 0
		 WaitInDepoTime = 522
		 WayNum = 0
	 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 Rider Number: 1Route Number1 Route time: 89 Route cost: 4465.0 Route length: 19 Rider money: 111735830 Route: [14, 10, 13, 28, 23, 19, 26, 8, 7]
	 Rider Number: 2Route Number1 Route time: 78 Route cost: 3915.0 Route length: 8 Rider money: 142867090 Route: [9, 12, 24, 15, 16, 27, 18, 17, 11, 22]
	*/
	}
	
	// 03/04/2015
	private void test6() {	
		m = new Matrix(4);
		m.ENC = new int[]{0, 42283, 42280, 42282};
		m.ATM = new String[]{"1", "202778", "202790", "202900"};
		m.distanceCoeffs = new int[][]{{0, 6, 8, 8}, {7, 0, 27, 9}, {6, 23, 0, 26}, {20, 5000, 5000, 0}};
		m.timeCoeffs = new int[][]{{0, 14, 16, 18}, {17, 0, 30, 19}, {18, 31, 0, 30}, {27, 0, 0, 0}};
		m.addTimeWindow(0, 0, 10000, false);
		m.addTimeWindow(1, 600, 1380, false);
		m.addTimeWindow(2, 600, 1380, false);
		m.addTimeWindow(3, 600, 1380, false);
		m.addRiderTimeWindow(60, 1380);
		m.amountOfMoney = new int[]{0, 12750000, 12750000, 12750000};
		m.serviceTime = new int[]{15, 15, 15, 15};
		m.MaxMoney = 150000000;
		m.amountOfCassettes = new int[]{0, 200, 200, 200};
		m.VolumeOneCar = 150000000;
		m.FixPrice = 3500.0;
		m.LengthPrice = 50.0;
		m.MaxATMInWay = 13;
		m.MaxTime = 0;
		m.MaxLength = 0;
		m.depot = "1";
		m.maxCars = 5;
		m.AtmPrice = new double[]{3500.0, 3500.0, 3500.0, 3500.0};
		m.currCode = 810;
		m.windowMode = 0;
		/*
		~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		~~~~~~~~~~~~~~~Riders~~~~~~~~~~~~~~~~~~~
		  Rider:
			atmList = [2, 1, 3]
			cost = [3915.0, 8995.0, 14540.0]
			time = [76, 122, 634]
			timewait = [0, 478, 0]
			length = [8, 23, 9]
			sumWaitTime = 478
			sumTime = 1355
			Money = 38250000
			Cassettes = 600
			SumLength = 0
			WaitInDepoTime = 524
			WayNum = 0
		~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Rider Number: 1Route Number1 Route time: 76 Route cost: 3915.0 Route length: 8 Rider money: 38250000 Route: [42280, 42283, 42282]
		*/
	}
	
	// Custom
	// violation
	private void test7() {	
		m = new Matrix(4);
    	m.ENC = new int[]{0, 1, 3, 2};  //(идентификаторы инкассаций)
    	m.ATM = new String[]{"1", "132964", "155652", "155750"}; //(соотв. идентификаторы банкоматов)
    	m.distanceCoeffs = new int[][]{{0, 1, 1, 1}, {1, 0, 26, 33}, {1, 29, 0, 67}, {1, 33, 66, 0}}; 
    	m.timeCoeffs = new int[][]{{0, 17, 39, 19},{18, 0, 26, 33},{42, 29, 0, 67},{20, 33, 66, 0}}; 
    	m.addTimeWindow(0, 0, 10000, false);
    	m.addTimeWindow(1, 480, 840, false);
    	m.addTimeWindow(2, 600, 1080, false);
    	m.addTimeWindow(3, 480, 840, false);
    	m.addRiderTimeWindow(60, 1380);
    	m.amountOfMoney = new int[]{0, 500000, 500000, 500000};  //(количество денег, которое нужно загрузить в каждый банкомат, может быть 0)
    	m.serviceTime = new int[]{20, 20, 20, 20};  // - вместо этого можно просто int serviceTime (это время обслуживания одного банкомата, сейчас оно одно для всех, но в идеале может различаться для разных типов банкоматов и даже быть уникальным для каждого)
    	m.MaxMoney = 40000000; // (макс количество денег, которое может перевезти машина)
    	m.amountOfCassettes = new int[] {0, 200, 200, 200};  // (кол-во кассет, которое нужно завезти в банкомат) - это сейчас не используется
    	m.VolumeOneCar = 40000000; // (макс кол-во кассет, кот. может перевезти машина) - не используется
    	m.FixPrice = 100.0;  // - фикс стоимость подъезда машины к банкомату
    	m.LengthPrice = 20.0;  // - цена за километр пути
    	m.MaxATMInWay = 1; // - макс кол-во банкоматов в маршруте
    	m.MaxTime = 0; // - макс время которое можно затрачивать на 1 маршрут
    	m.MaxLength = 0; // макс длина одного маршрута
    	m.depot = "1";  // - идентификатор депо
    	m.maxCars = 5;   // - макс доступное кол-во машин
    	m.AtmPrice = new double[] {100.0, 100.0, 100.0, 100.0}; // - стоимость подъезда к каждому банкомату (сейчас не используется)
    	m.currCode = 810; // - валюта всех денежных параметров
    	m.windowMode = 0; // - режим окон для банкоматов, обычный и дефолтный (при котором, каждое окно ставится на максимально возможный промежуток);
		
		/*
		~~~~~~~~~~~~~~~Riders~~~~~~~~~~~~~~~~~~~
		Rider:
			atmList = [1, 2] номера банкоматов в правильном порядке
			cost = [460.0, 1460.0] ? 
			time = [87, 142] ? (60+17, )
			timewait = [0, 458] ?
			length = [17, 26] расстояния между точками (17 - от депо в 1, 26 - от 1 в 2, от 2 в депо не учитывается
			sumWaitTime = 458 сумма timeWait 0 + 458
			sumTime = 727 сумма time + сумма serviceTime + sumWaitTime (87 + 20 + 0 + 142 + 20 + 458 = 727)
			  // тупо! 87 = 60 + 17, а 60 он итак ждет
			Money = 1000000 (500000+500000) - сумма денег для всех банкоматов
			Cassettes = 400 ?
			SumLength = 0 ?
			WaitInDepoTime = 393 время от открытия временного окна водителя до выезда (60 -> 453 (463+17 = 4
			WayNum = 0
		Rider:
			atmList = [3]
			cost = [500.0]
			time = [88]
			timewait = [0]
			length = [19]
			sumWaitTime = 0
			sumTime = 108
			Money = 500000
			Cassettes = 200
			SumLength = 0
			WaitInDepoTime = 392
			WayNum = 0
		~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Rider Number: 1Route Number1 Route time: 87 Route cost: 460.0 Route length: 17 Rider money: 1000000 Route: [1, 3]
		Rider Number: 2Route Number1 Route time: 88 Route cost: 500.0 Route length: 19 Rider money: 500000 Route: [2]
		*/
	}
	
	private void testSolomon(String problemName, int customersNumber) {	
		
		m = new Matrix(customersNumber+1);
		m.ENC = new int[customersNumber+1];
		m.ATM = new String[customersNumber+1];
    	m.amountOfMoney = new int[customersNumber+1];  //(количество денег, которое нужно загрузить в каждый банкомат, может быть 0)
    	m.serviceTime = new int[customersNumber+1];  // - вместо этого можно просто int serviceTime (это время обслуживания одного банкомата, сейчас оно одно для всех, но в идеале может различаться для разных типов банкоматов и даже быть уникальным для каждого)
    	m.amountOfCassettes = new int[customersNumber+1];  // (кол-во кассет, которое нужно завезти в банкомат) - это сейчас не используется
    	m.AtmPrice = new double[customersNumber+1]; // - стоимость подъезда к каждому банкомату (сейчас не используется)
		
		for (int i = 0; i <= customersNumber; i++){
			m.ENC[i] = i;
			m.ATM[i] = Integer.toString(i);
			m.amountOfCassettes[i] = i==0 ? 0 : 200;
			m.AtmPrice[i] = 100.0;
		}
		
		
		int i = 0;
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ";";
    	int[][] coordinates = new int[customersNumber+1][2];
    	try {
    		 
    		br = new BufferedReader(new FileReader("resources/"+problemName+".csv"));
    		line = br.readLine();
    		m.MaxMoney = Integer.parseInt(line.substring(0, line.indexOf(cvsSplitBy)));

    		while (i <= customersNumber && (line = br.readLine()) != null) {
    			String[] splLine = line.split(cvsSplitBy);
    			coordinates[i][0] = (int)Double.parseDouble(splLine[0]);
        		coordinates[i][1] = (int)Double.parseDouble(splLine[1]);
        		m.amountOfMoney[i] = (int)Double.parseDouble(splLine[2]);
        		m.addTimeWindow(i, (int)Double.parseDouble(splLine[3]), (int)Double.parseDouble(splLine[4]), false);
        		m.serviceTime[i] = (int)Double.parseDouble(splLine[5]);
    			i++;
    		}
    		
    		m.distanceCoeffs = new int[customersNumber+1][customersNumber+1];
    		m.timeCoeffs = new int[customersNumber+1][customersNumber+1];
    		for (int k = 0; k <=  customersNumber; k++) {
    			for (int l = 0; l <=  customersNumber; l++) {
    				double dist = Math.sqrt(Math.pow((coordinates[k][0] - coordinates[l][0]), 2) + Math.pow((coordinates[k][1] - coordinates[l][1]), 2));
    				m.distanceCoeffs[k][l] = (int) (dist*10); // to prevent loosing accuracy *10. Not 1000 because of Solomon results
    				m.timeCoeffs[k][l] = (int) dist; // if multiply this, time window constraints will be violated
    			}
    		}
    		
    		m.addRiderTimeWindow(60, 1380);
    		
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
        	System.out.println();
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
	
	@Before
    public  void before() {
//		test3();
//		problemName = "C201";
//		customerNumber = 10;
//		testSolomon(problemName, customerNumber);
	}
    	
	@Test
	public void testProblem() {
//    	Pareto solver = new Pareto(m);
//    	Pareto.POPULATION_SIZE = 100;
//    	Pareto.CROSSOVER_RATE = 0.8;
//    	Pareto.MUTATION_RATE = 0.1;
//        
//    	Pareto.INIT_RAND_RATE = 0.9;
//    	Pareto.EUCLIDEAN_RADIUS = 30;
//    	solver.computeResult();
		

//		for (int t = 10; t <= 40; t+=5){ // for R & RC
//		for (int t = 60; t <= 90; t+=5){
		for (int t = 70; t <= 100; t+=5){
			problemName = "C201";
			customerNumber = t;
			testSolomon(problemName, customerNumber);
			
			int routesNumber = 0;
			int totalCost = 0;

			if (problemName.equals("C201")) {
				if (customerNumber == 70) {
					routesNumber = 3;
					totalCost = 4993;
				} else if (customerNumber == 75) {
					routesNumber = 3;
					totalCost = 5092;				
				} else if (customerNumber == 80) {
					routesNumber = 3;
					totalCost = 5223;
				} else if (customerNumber == 85) {
					routesNumber = 3;
					totalCost = 5250;
				} else if (customerNumber == 90) {
					routesNumber = 3;
					totalCost = 5452;
				} else if (customerNumber == 95) {
					routesNumber = 3;
					totalCost = 5742;
				} else {
					routesNumber = 3;
					totalCost = 5891;
				}
			} else if (problemName.equals("R201")) {
				if (customerNumber == 10) {
					routesNumber = 1;
					totalCost = 2536;
				} else if (customerNumber == 15) {
					routesNumber = 1;
					totalCost = 3529;				
				} else if (customerNumber == 20) {
					routesNumber = 2;
					totalCost = 4093;
				} else if (customerNumber == 25) {
					routesNumber = 2;
					totalCost = 5227;
				} else if (customerNumber == 30) {
					routesNumber = 2;
					totalCost = 5737;
				} else if (customerNumber == 35) {
					routesNumber = 2;
					totalCost = 6632;
				} else {
					routesNumber = 2;
					totalCost = 7926;
				}
			} else {
				if (customerNumber == 10) {
					routesNumber = 1;
					totalCost = 1941;
				} else if (customerNumber == 15) {
					routesNumber = 1;
					totalCost = 3349;				
				} else if (customerNumber == 20) {
					routesNumber = 2;
					totalCost = 3982;
				} else if (customerNumber == 25) {
					routesNumber = 2;
					totalCost = 4313;
				} else if (customerNumber == 30) {
					routesNumber = 2;
					totalCost = 6796;
				} else if (customerNumber == 35) {
					routesNumber = 2;
					totalCost = 7587;
				} else {
					routesNumber = 2;
					totalCost = 9337;
				}
			}
			
			System.out.println("pop-size, "+ problemName + " - " + customerNumber);
	    	for (int i = 1; i <= 40; i++) {	
	    		int optimalFound = 0;
	    		int totalTime = 0;
	    		for (int j = 0; j < 10; j++) {
	        		Pareto p = new Pareto(m);
	        		//TODO пересчитать евклидов радиус!!
	        		Pareto.POPULATION_SIZE = i*10;
	        		Pareto.EUCLIDEAN_RADIUS = 30;
	        		long time = System.currentTimeMillis();
	        		Individual result = p.computeResult();
//	        		System.out.println(result);
	    			totalTime += System.currentTimeMillis() - time;
								
	    			if (result.getRoutesNumber()==routesNumber && result.getTotalCost()<totalCost*1.02) {
	        			optimalFound++;
	        		}      		
	    		}
	    		System.out.println((i*10) + "," + optimalFound*10);
	    		
	    	}
		}
//		System.out.println("pop-size, "+ problemName + " - " + customerNumber);
//    	for (int i = 1; i <= 40; i++) {	
//    		int optimalFound = 0;
//    		int totalTime = 0;
//    		for (int j = 0; j < 10; j++) {
//        		Pareto p = new Pareto(m);
//        		//TODO пересчитать евклидов радиус!!
//        		Pareto.POPULATION_SIZE = i*10;
//        		Pareto.EUCLIDEAN_RADIUS = 30;
//        		long time = System.currentTimeMillis();
//        		Individual result = p.computeResult();
////        		System.out.println(result);
//    			totalTime += System.currentTimeMillis() - time;
//					
//    			//C201
////        		if (result.getRoutesNumber()==1 && result.getTotalCost()<1942*1.02) { //10
////        		if (result.getRoutesNumber()==1 && result.getTotalCost()<2295*1.02) { //15
////        		if (result.getRoutesNumber()==1 && result.getTotalCost()<2650*1.02) { //20
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<2147*1.02) { //25
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<2324*1.02) { //30
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<2545*1.02) { //35
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<3251*1.02) { //40
//    			
//    			//R201
////        		if (result.getRoutesNumber()==1 && result.getTotalCost()<2536*1.02) { //10
////        		if (result.getRoutesNumber()==1 && result.getTotalCost()<3529*1.02) { //15
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<4093*1.02) { //20
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<5227*1.02) { //25
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<5737*1.02) { //30
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<6632*1.02) { //35
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<7926*1.02) { //40
//					
//    			//RC201
////        		if (result.getRoutesNumber()==1 && result.getTotalCost()<1941*1.02) { //10
////        		if (result.getRoutesNumber()==1 && result.getTotalCost()<3349*1.02) { //15
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<3982*1.02) { //20
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<4313*1.02) { //25
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<6796*1.02) { //30
////        		if (result.getRoutesNumber()==2 && result.getTotalCost()<7587*1.02) { //35
////            	if (result.getRoutesNumber()==2 && result.getTotalCost()<9337*1.02) { //40
//    			
//    			if (result.getRoutesNumber()==routesNumber && result.getTotalCost()<totalCost*1.02) {
//        			optimalFound++;
//        		}      		
//    		}
////    		System.out.println((i*10) + "," + optimalFound*10 + "," + totalTime/20);
//    		System.out.println((i*10) + "," + optimalFound*10);
//    		
//    	}
    	
//    	for (ArrayList<Integer> route : result.getRoutes()) {
//    		System.out.print("[");
//    		for (Integer pid : route) {
//    			System.out.print(pid + " ");
//    		}
//    		System.out.println("]");
//    	}
//    	
//    	for (ArrayList<Integer> route : result.getRoutes()) {
//    		int order = 1;
//    		int pointTime = m.getTimeWindows().get(0).StartWork; // start time of depot
//    		pointTime -= m.serviceTime[0]; // if not serving depot in the beginning
//    		int prevCust = 0;
//    		for (Integer pid : route) {
//    			pointTime = Math.max(pointTime + m.timeCoeffs[prevCust][pid] + m.serviceTime[prevCust], m.getTimeWindows().get(pid).StartWork);
//    			System.out.println(pid + " " + order + " "  +pointTime);
//    			prevCust = pid;
//    			order++;
//    		}
//    	}

	}
    	 	
}  