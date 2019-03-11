import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		Board board = new Board(25);
//		board.print();
//		board.updateAttackStatus();
//		board.printAttackStatus();
//		Board solvedBoard = SimulatedAnnealing(board);
//		solvedBoard.print();
//		solvedBoard.printAttackStatus();

		Board board = new Board(4);
		board.print();
	}

	public static void GeneticAlgorithm(Board[] boards) {

		//boards should have 4 board objects
		//x = randomly selected board from boards[] based on their fitness level

	}

	public static Board randomlySelectedBoard() {

	}

	public static Board SimulatedAnnealing(Board board) {
		Board currentBoard = board;
		Board nextBoard;
		Random random = new Random();

		int deltaE;

		for(int t = 0; t < 100000; t++) {
			double T = getScheduleValue(t+1);

			//checking for solution
			if(currentBoard.getNumberOfAttackedQueens() == 0) {
				return currentBoard;
			}

			//get random successor and compare
			nextBoard = currentBoard.getSuccessor();
			deltaE = nextBoard.getNumberOfAttackedQueens() - currentBoard.getNumberOfAttackedQueens();

			//if deltaE is negative, that means nextBoard was closer to the solution than currentBoard
			if (deltaE < 0) {
				//GOOD MOVE
				currentBoard = nextBoard;
			} else {
				//BAD MOVE
//				//calculate probability+
					//deltaE is negative
				double probability = Math.exp(-deltaE / T);
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
		return 100/t;
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

