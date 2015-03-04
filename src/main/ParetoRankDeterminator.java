package main;

import individual.Individual;

import java.util.ArrayList;
import java.util.List;

public class ParetoRankDeterminator {
	public ParetoRankDeterminator() {
		
	}
	
	private boolean isNotDominated(Individual indiv, List<Individual> population) {
		for (Individual i : population) {
			if (i.dominates(indiv)){
				return false;
			}
		}
		return true;
	}
	
	public void determineParetoRanks(List<Individual> population) {
		List<Individual> tempPop = new ArrayList<Individual>(population);
		int currentRank = 1;
		int n = population.size();
		Individual indiv;
		while (n != 0) {
			for (int i = 0; i < n;i++){
				indiv = tempPop.get(i);
				if (isNotDominated(indiv, tempPop)){
					indiv.setParetoRank(currentRank);
				}
			}
			for (int i = 0; i < n;i++){
				indiv = tempPop.get(i);
				if (indiv.getParetoRank() == currentRank){
					tempPop.remove(i);
					i--;
					n--;
				}
			}
			currentRank++;
		}
	}
}
