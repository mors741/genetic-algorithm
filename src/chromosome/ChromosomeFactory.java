package chromosome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.GA;
import problem.Point;
import problem.Problem;

public class ChromosomeFactory {
	
    // ���������� ��������� � ������� ��������� ������������
	public Chromosome generateRandomChromosome() {
    	Chromosome chrom = new Chromosome();
    	// �������� �� �������
        for (int i =0; i < Problem.customersNumber; i++){
        	chrom.getChromosome().add(i);   	
        } 
        // � �����������
        Collections.shuffle(chrom.getChromosome());
        return chrom;
    }
	
    // ���������� ��������� � ������� ������ ��������� �� ���. 22
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
        	// ���� ���� ���������, �� ����� ����
        	Point randCust = Problem.getCustomer(randCustId);
        	while (true) {
        		// ������ ���������        		
        		double dist;
            	double minDist = GA.EUCLIDEAN_RADIUS; // ���� ���������� ������ EUCLIDEAN_RADIUS, �� ��� ����� ��� ����� ��� �� ����������
            	int nearestId = -1;
            	for (Point p : Problem.customers) {
            		dist = randCust.distanceTo(p);
            		if (dist < minDist && dist > 0 && !chrom.contains(p.getId())) { //2)����� ��������� ���� ����; 3)��� ��� � ������
            			minDist = dist;
            			nearestId = p.getId();
            		}
            	}
            	if (nearestId == -1) { // �� ���������� ������ ��������� �����
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
