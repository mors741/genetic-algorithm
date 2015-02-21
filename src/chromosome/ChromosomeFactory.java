package chromosome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.GA;
import problem.Point;
import problem.Problem;

public class ChromosomeFactory {
	
    // Генерирует хромосому с помощью случайных перестановок
	public Chromosome generateRandomChromosome() {
    	Chromosome chrom = new Chromosome();
    	// Заполняю по порядку
        for (int i =0; i < Problem.customersNumber; i++){
        	chrom.getChromosome().add(i);   	
        } 
        // И перемешиваю
        Collections.shuffle(chrom.getChromosome());
        return chrom;
    }
	
    // Генерирует хромосому с помощью жадной процедуры на стр. 22
	public Chromosome generateGreedyChromosome() {
		Chromosome chrom = new Chromosome();
    	Random random = new Random();
    	List<Integer> tempChromosome = new ArrayList<Integer>(Problem.customersNumber);
        for (int i =0; i < Problem.customersNumber; i++){
        	tempChromosome.add(i);
        }
        
        while (!tempChromosome.isEmpty()) {
        	int randIndex = (int)(random.nextInt(tempChromosome.size()));
        	int randCustId = tempChromosome.get(randIndex);
        	//System.out.println(tempChromosome);
        	tempChromosome.remove(randIndex);
        	chrom.getChromosome().add(randCustId);
        	// Если есть ближайшая, то новый цикл
        	Point randCust = Problem.getCustomer(randCustId);
        	while (true) {
        		// Найдем ближайшую        		
        		double dist;
            	double minDist = GA.EUCLIDEAN_RADIUS; // Если расстояние больше EUCLIDEAN_RADIUS, то все равно эта точка нас не интересует
            	int nearestId = -1;
            	for (Point p : Problem.customers) {
            		dist = randCust.distanceTo(p);
            		if (dist < minDist && dist > 0 && !chrom.contains(p.getId())) { //2)чтобы исключить саму себя; 3)Еще нет в списке
            			minDist = dist;
            			nearestId = p.getId();
            		}
            	}
            	if (nearestId == -1) { // не существует нужной ближайшей точки
            		break;
            	}
            	tempChromosome.remove((Object)nearestId);
            	chrom.getChromosome().add(nearestId);
            	randCust = Problem.getCustomer(nearestId);
        	}
        	//System.out.println(chromosome);
        }
        return chrom;
    }
}
