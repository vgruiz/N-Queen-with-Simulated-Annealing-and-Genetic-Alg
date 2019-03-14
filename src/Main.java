import java.util.Random;

public class Main {
	
	static double MUTATION_PROBABILITY = .9;
	static int BOARD_SIZE = 25;
	static int POPULATION_SIZE = 50;
	

	public static void main(String[] args) {	

		runSimulatedAnnealing();
	
		//runGeneticAlgorithm();
	
	}
	
	public static void runSimulatedAnnealing() {
		Board board = new Board(25);
		board.print();
		board.updateAttackStatus();
		board.printAttackStatus();
		
		Board solvedBoard = SimulatedAnnealing(board);
		solvedBoard.print();
		solvedBoard.printAttackStatus();
	}
	
	public static void runGeneticAlgorithm() {
		Board[] boards = new Board[POPULATION_SIZE];
		
		for(int i = 0; i < boards.length; i++) {
			boards[i] = new Board(BOARD_SIZE);
		}
		
		Board solution = GeneticAlgorithm(boards);
		solution.print();
		solution.printAttackStatus();
	}

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
				System.out.println("Solution Found");
				System.out.println("# of generations: " + generation);
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

				//System.out.println(boardX.getFitness());
				//System.out.println(boardY.getFitness());
				//System.out.println(child.getFitness());
				//System.out.println();
				
				newPopulation[i] = child;
			}
			
			population = newPopulation;
			generation++;
		}
		
		//STOP looping when there is a fit enough individual
		//RETURN the best individual in population

		int max = 0;
		//find max fitness value of current population
		for(int i = 0; i < population.length; i++) {
			if(population[i].getFitness() > max) {
				max = population[i].getFitness();
			}
		}
		
		for(int i = 0; i < population.length; i++) {
			if(population[i].getFitness() == max) {
				return population[i];
			}
		}
		
		return null;
	}

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
	 * 
	 * @param boards
	 * @return a Board randomly selected among the top 10 boards with the best fitness
	 */
	public static Board getChild(Board[] boards) {
		Random random = new Random();
		
		int index = (boards.length - 10) + random.nextInt(10); //this calculation returns a random int among the last 10 index values
		//System.out.println(index);
		
		return boards[index];
	}

	public static Board SimulatedAnnealing(Board board) {
		Board currentBoard = board;
		Board nextBoard;
		Random random = new Random();

		int deltaE;

		for(int t = 1; t < 100000; t++) {
			double T = getScheduleValue(t);

			//When temperature is low enough, return currentBoard
			if(T < .05) {
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

	public static double getScheduleValue(int t) {
		return 1/(Math.log(t));
	}

//	public SolutionState SimulatedAnnealing(Problem problem, Schedule schedule) {
//		/**
//		 * 	problem, a problem
//		 * 	schedule, a mapping from time to "temperature"
//		 *
//		 */
//
//		Board currentBoard;
//		Board nextBoard;
//		int T; //a "temperature" controlling probability of downward steps
//				// maybe controlling how many queens are moving on a given turn
//
//		int deltaE;
//
//		currentBoard = MakeNode(InitialState(problem));
//
//		for(int t = 0; t < 1000000; t++) {
//			T = schedule[t];
//			if(T = 0) {
//				return currentBoard;
//			}
//
//			deltaE = Value(nextBoard) - Value(currentBoard);
//
//			if (deltaE > 0) {
//				currentBoard = nextBoard;
//			} else {
//				currentBoard = nextBoard;
//				//"only with probability e^(deltaE/T)
//			}
//		}
//	}
}

