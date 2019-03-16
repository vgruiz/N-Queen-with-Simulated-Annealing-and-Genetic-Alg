//import java.io.BufferedWriter;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Main {
	
	final static double MUTATION_PROBABILITY = .8;
	final static int BOARD_SIZE = 25;
	final static int POPULATION_SIZE = 50;	
	static int totalGenerationCount = 0;
	static int numIncorrectSolutions = 0;

	public static void main(String[] args) throws IOException {	
		
		System.out.println("SIMULATED ANNEALING");
		
		for(int i = 0; i < 3; i++) {
			Board board = new Board(BOARD_SIZE);
			Board solution = SimulatedAnnealing(board);
			solution.print();
			System.out.println();
		}
		
		System.out.println("\n\nGENETIC ALGORITHM");
		
		for(int i = 0; i < 3; i++) {
			Board[] boards = new Board[POPULATION_SIZE];
			for(int j = 0; j < boards.length; j++) {
				boards[j] = new Board(BOARD_SIZE);
			}
			Board solution = GeneticAlgorithm(boards);
			solution.print();
			System.out.println();
		}
	}
	
	/**
	 *  For testing the Simulated Annealing solution
	 */
	public static void runSimulatedAnnealing() {
		Board board = new Board(25);
		//board.print();
		board.updateAttackStatus();
		//board.printAttackStatus();
		
		Board solvedBoard = SimulatedAnnealing(board);
		if(!solvedBoard.isSolved()) {
			numIncorrectSolutions++;
		}
	}
	
	/**
	 *  For testing the Genetic Algorithm solution
	 */
	public static void runGeneticAlgorithm() {
		Board[] boards = new Board[POPULATION_SIZE];
		
		for(int i = 0; i < boards.length; i++) {
			boards[i] = new Board(BOARD_SIZE);
		}
		
		Board solution = GeneticAlgorithm(boards);
		
		if(!solution.isSolved()) {
			numIncorrectSolutions++;
		}
	}

	/**
	 * The Simulated Annealing solution based on the pseudocode provided in lecture
	 * @param board
	 * @return
	 */
	public static Board SimulatedAnnealing(Board board) {
		Board currentBoard = board;
		Board nextBoard;
		Random random = new Random();
	
		int deltaE;
	
		for(int t = 1; t < 100000; t++) {
			double T = getScheduleValue(t);
	
			//When temperature is low enough, return currentBoard
			if(T < .1) {
				//System.out.println(t);
				return currentBoard;
			}
	
			//get random successor and compare
			nextBoard = currentBoard.getSuccessor();
			deltaE = nextBoard.getFitness() - currentBoard.getFitness();
			
			//if deltaE is negative, that means nextBoard was closer to the solution than currentBoard
			if (deltaE > 0) {
				//GOOD MOVE
				
				currentBoard = nextBoard;
			} else {
				//BAD MOVE
				
				//calculate probability
				//deltaE is negative
				double probability = Math.exp(deltaE / T);
				//System.out.println("Probability = " + probability);
				double randomValue = random.nextDouble();
				//System.out.println("Random = " + randomValue);
	
				if(randomValue < probability) {
					currentBoard = nextBoard;
				}
			}
		}
	
		return currentBoard;
	}

	/**
	 * The Genetic Algorithm, based on the pseudocode provided in lecture.
	 * @param boards
	 * @return a solved Board
	 */
	public static Board GeneticAlgorithm(Board[] boards) {
		Board[] population = boards;
		Random random = new Random();
		boolean solutionFound = false;
		int generation = 0;
		
		while(solutionFound == false) {
			Board[] newPopulation = new Board[population.length];
			
			sortByFitness(population);
			
			//checking if the best board in the population is at the maximum fitness value
			if(population[population.length - 1].getFitness() == population[0].maxFitness) {
				//System.out.println("Solution Found");
				//System.out.println(generation);
				totalGenerationCount = totalGenerationCount + generation;
				return population[population.length - 1];
			}
			
			for(int i = 0; i < population.length; i++) {
				Board boardX = getChild(population);
				Board boardY = getChild(population);
				Board child = boardX.reproduceWith(boardY);
				
				if(random.nextDouble() < MUTATION_PROBABILITY) { /* small random probability*/ 
					child = child.mutate();
					//System.out.println("mutation occured");
				}
				
				newPopulation[i] = child;
			}
			
			population = newPopulation;
			generation++;
		}
		
		return null;
	}

	/**
	 * Sorting the population of boards[] by the fitness value. The fitness value is the number of non-attacking queen pairs.
	 * For Genetic Algorithm
	 * @param boards
	 */
	public static void sortByFitness(Board[] boards) {
		double total = 0;	//stores the total fitness of all boards in the boards[] array
		
		//sum the total fitness of the population
		for(int i = 0; i < boards.length; i++) {
			total = total + boards[i].getFitness();
		}
		
		//to turn each fitnessArray[] value to a percentage of its value divided by the total fitness
		for(int i = 0; i < boards.length; i++) {
			boards[i].fitnessProbability = boards[i].fitness / total;
		}
		
		//perform selection sort
		for(int i = 0; i < boards.length - 1; i++) {
			int minIndex = i;
			for(int j = i+1; j < boards.length; j++) {
				if(boards[j].fitnessProbability < boards[minIndex].fitnessProbability) {
					minIndex = j;
				}
			}
			
			Board tmp = boards[minIndex];
			boards[minIndex] = boards[i];
			boards[i] = tmp;
		}
	}
	
	/**
	 * For Simulated Annealing
	 * @param boards, which are already be sorted by fitness
	 * @return a Board randomly selected among the top 10 boards with the best fitness
	 */
	public static Board getChild(Board[] boards) {
		Random random = new Random();
		
		//this calculation returns a random int among the last 10 index values
		int index = (boards.length - 10) + random.nextInt(10); 
		//System.out.println(index);
		
		return boards[index];
	}

	/**
	 * For Simulated Annealing
	 * @param t, the current search cost
	 * @return ( 1 / log t )
	 */
	public static double getScheduleValue(int t) {
		return 1/(Math.log(t));
	}

}

