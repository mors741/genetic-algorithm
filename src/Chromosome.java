import java.util.List;  
import java.util.Collections;  
import java.util.ArrayList;  
import java.util.Random;

  
public class Chromosome {
	
	private List<Integer> chromosome;
	private List<List<Integer>> routes;
	private int routesNumber;
	private int totalCost;
	
	
	public Chromosome() {
		chromosome = new ArrayList<Integer>(Problem.customersNumber);
		routes = new ArrayList<List<Integer>>(Problem.customersNumber);
	}
	
	public void setChromosome(List<Integer> newChromosome) {
        this.chromosome = new ArrayList<Integer>(newChromosome);
    }
	
	public void evaluateRoutes() {
		int currentRouteIndex = 0;
		int currentCapacity = Problem.vehicleCapacity;
		routes.add(new ArrayList<Integer>(Problem.customersNumber));
		// TODO Exception if first gene is incorrect
		if (chromosome.get(0) % 2 == 0) {
			System.out.println("Kosyak");
		}
		for (int gene : chromosome) {
			if (gene % 2 == 0) { // TODO New route condition
				routes.add(new ArrayList<Integer>(Problem.customersNumber));
				currentRouteIndex++;
			}
			routes.get(currentRouteIndex).add(gene);
		}
	}
    
    public boolean contains(int gene) {
    	for (int i : chromosome) {
    		if (i == gene) {
    			return true;
    		}
    	}
    	return false;
    }
    
    // Генерирует хромосому с помощью случайных перестановок
    public void generateRandomChromosome() {
        for (int i =0; i < Problem.customersNumber; i++){
        	chromosome.add(i);   	
        } 	
        Collections.shuffle(chromosome);
    }
   
    // Генерирует хромосому с помощью жадной процедуры на стр. 22
    public void generateGreedyChromosome() {
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
        	chromosome.add(randCustId);
        	// Если есть ближайшая, то новый цикл
        	Point randCust = Problem.getCustomer(randCustId);
        	while (true) {
        		// Найдем ближайшую        		
        		double dist;
            	double minDist = GA.EUCLIDEAN_RADIUS; // Если расстояние больше EUCLIDEAN_RADIUS, то все равно эта точка нас не интересует
            	int nearestId = -1;
            	for (Point p : Problem.customers) {
            		dist = randCust.distanceTo(p);
            		if (dist < minDist && dist > 0 && !this.contains(p.getId())) { //2)чтобы исключить саму себя; 3)Еще нет в списке
            			minDist = dist;
            			nearestId = p.getId();
            		}
            	}
            	if (nearestId == -1) { // не существует нужной ближайшей точки
            		break;
            	}
            	tempChromosome.remove((Object)nearestId);
            	chromosome.add(nearestId);
            	randCust = Problem.getCustomer(nearestId);
        	}
        	//System.out.println(chromosome);
        }
        
    }
    
    public List<Integer> getChromosome() {
        return this.chromosome;
    }
    
    public int getSize() {

        return chromosome.size();
    }
    
    public int getRoutesNumber() {

        return routesNumber;
    }
    
    public int getTotalCost() {

        return totalCost;
    }
    
	public String toRouteString() {
    	StringBuilder str = new StringBuilder(chromosome.size()*3);
    	for (List<Integer> route : routes) {
    		str.append("[");
    		for (int gene : route) {
    			str.append(gene+" ");
    		}
    		
    		str.append("] ");
    	}
		return str.toString();
	}
    
    public String toString() {
    	StringBuilder str = new StringBuilder(chromosome.size()*2);
    	for (int i : chromosome) {
    		str.append(i);
    		str.append(" ");
    	}
		return str.toString();
    	
    }
}
