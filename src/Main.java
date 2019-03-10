import java.util.Random;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Board board = new Board(6);
		board.print();
		board.updateAttackStatus();
		board.printAttackStatus();
//		System.out.println(board.getNumberOfAttackedQueens());
//		board.getSuccessor();

		Board solvedBoard = SimulatedAnnealing(board);
		
		solvedBoard.print();
	}

	public static Board SimulatedAnnealing(Board board) {
		Board currentBoard = board;
		Board nextBoard;
		Random random = new Random();
		
		int deltaE;
		
		for(int t = 0; t < 10; t++) {
			double T = getScheduleValue(t+1);
//			if(T = 0) {
//				return currentBoard;
//			}
			
			nextBoard = currentBoard.getSuccessor();
			deltaE = nextBoard.getNumberOfAttackedQueens() - currentBoard.getNumberOfAttackedQueens();
		
			if (deltaE > 0) {
				currentBoard = nextBoard;
			} else {
//				//calculate probability
				//deltaE is negative
				double probability = Math.exp(deltaE / T);
				System.out.println("Probability = " + probability);
				double randomValue = random.nextDouble();
				System.out.println("Random = " + randomValue);
				
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

