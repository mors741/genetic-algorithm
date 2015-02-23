package individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.GA;
import problem.Point;
import problem.Problem;

public class IndividualFactory {
	
    // ���������� ��������� � ������� ��������� ������������
	public Individual generateRandomChromosome() {
    	Individual indiv = new Individual();
    	// �������� �� �������
        for (int i =0; i < Problem.customersNumber; i++){
        	indiv.getChromosome().add(i);   	
        } 
        // � �����������
        Collections.shuffle(indiv.getChromosome());
        return indiv;
    }
	
    // ���������� ��������� � ������� ������ ��������� �� ���. 22
	public Individual generateGreedyChromosome() {
		Individual indiv = new Individual();
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
        	indiv.getChromosome().add(randCustId);
        	// ���� ���� ���������, �� ����� ����
        	Point randCust = Problem.getCustomer(randCustId);
        	while (true) {
        		// ������ ���������        		
        		double dist;
            	double minDist = GA.EUCLIDEAN_RADIUS; // ���� ���������� ������ EUCLIDEAN_RADIUS, �� ��� ����� ��� ����� ��� �� ����������
            	int nearestId = -1;
            	for (Point p : Problem.customers) {
            		dist = randCust.distanceTo(p);
            		if (dist < minDist && dist > 0 && !indiv.contains(p.getId())) { //2)����� ��������� ���� ����; 3)��� ��� � ������
            			minDist = dist;
            			nearestId = p.getId();
            		}
            	}
            	if (nearestId == -1) { // �� ���������� ������ ��������� �����
            		break;
            	}
            	tempChromosome.remove((Object)nearestId);
            	indiv.getChromosome().add(nearestId);
            	randCust = Problem.getCustomer(nearestId);
        	}
        	//System.out.println(chromosome);
        }
        return indiv;
    }
}
