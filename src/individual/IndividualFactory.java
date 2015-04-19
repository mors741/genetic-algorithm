package individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import main.GA;
import problem.Point;
import problem.Problem;

public class IndividualFactory {
	
	/**
	 * Generate chromosome using random permutations 
	 */
	public Individual generateRandomChromosome() {
    	Individual indiv = new Individual();
    	// Filling in the order
        for (int i =0; i < Problem.customersNumber; i++){
        	indiv.getChromosome().add(i);   	
        } 
        // And shuffling
        Collections.shuffle(indiv.getChromosome());
        return indiv;
    }
	
	/**
	 * Generate chromosome using greedy procedure from 22p.
	 */
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
        	tempChromosome.remove(randIndex);
        	indiv.getChromosome().add(randCustId);
        	Point randCust = Problem.getCustomer(randCustId);
        	while (true) {
        		// Finding the nearest point		
        		double dist;
            	double minDist = GA.EUCLIDEAN_RADIUS; // If the distance is greater than EUCLIDEAN_RADIUS, then this point is not interesting anyway
            	int nearestId = -1;
            	for (Point p : Problem.customers) {
            		dist = randCust.distanceTo(p);
            		if (dist < minDist &&
            				dist > 0 && // To exclude itself
            				!indiv.getChromosome().contains(p.getId())) { // Still not in list
            			minDist = dist;
            			nearestId = p.getId();
            		}
            	}
            	if (nearestId == -1) { // The nearest point is not exist
            		break;
            	}
            	tempChromosome.remove((Object)nearestId);
            	indiv.getChromosome().add(nearestId);
            	randCust = Problem.getCustomer(nearestId);
        	}
        }
        return indiv;
    }
}
