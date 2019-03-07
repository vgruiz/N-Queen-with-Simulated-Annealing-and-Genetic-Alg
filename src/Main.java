
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public SolutionState SimulatedAnnealing(Problem problem, Schedule schedule) {
		/**
		 * 	problem, a problem
		 * 	schedule, a mapping from time to "temperature"
		 * 
		 */
		
		Board currentBoard;
		Board nextBoard;
		int T; //a "temperature" controlling probability of downward steps
				// maybe controlling how many queens are moving on a given turn
		
		int deltaE;
		
		currentBoard = MakeNode(InitialState(problem));
		
		for(int t = 0; t < 1000000; t++) {
			T = schedule[t];
			if(T = 0) {
				return currentBoard;
			}
			
			deltaE = Value(nextBoard) - Value(currentBoard);
			
			if (deltaE > 0) {
				currentBoard = nextBoard;
			} else {
				currentBoard = nextBoard;
				//"only with probability e^(deltaE/T)
			}
		}
	}
}

