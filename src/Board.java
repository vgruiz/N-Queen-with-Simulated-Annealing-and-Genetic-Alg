import java.util.Arrays;
import java.util.Random;

public class Board {
	Random random = new Random();

	int[] queens;	//represents the queens' position on the game board
	boolean[] attackStatus;	//true if queen[i] is in an attack position
	int n;	//size of the board, n x n
	double fitness; //number of non-attacking queen pairs
	double fitnessProbability; //fitness / total fitness of the population (for Genetic Algorithm)
	double maxFitness;	//stores max fitness value for a board of size n x n

	/**
	 * Constructor
	 * @param n, size of the board, n x n
	 */
	public Board(int n) {
		queens = new int[n];
		attackStatus = new boolean[n];
		Arrays.fill(attackStatus, false);
		this.n = n;
		setInitialPositions();
		maxFitness = n * (n-1)/2;
	}

	/**
	 * Constructor
	 * @param queenArray, an int array of queen positions
	 */
	public Board(int[] queenArray) {
		queens = queenArray;
		n = queens.length;
		attackStatus = new boolean[n];
		Arrays.fill(attackStatus, false);
		maxFitness = n * (n-1)/2;
	}

	/**
	 * Randomly sets the queens' position. Used when initializing boards for Simulated Annealing.
	 */
	public void setInitialPositions() {
		for(int i = 0; i < queens.length; i++) {
			queens[i] = random.nextInt(n);
		}
	}

	/**
	 * 
	 * @return the number of non-attacking pairs of queens
	 */
	public int getFitness() {
		int fitness = 0;
		
		for(int i = 0; i < n; i++) {
			//int curColumn = i;
			int curRow = queens[i];

			for(int j = i+1; j < n; j++) {
				//now for every column i we are checking, we will start with j which is starting at i + 1
				int colDiff = Math.abs(i - j);
				int rowDiff = Math.abs(curRow - queens[j]);
	
				if (rowDiff != colDiff && curRow != queens[j]) {
					fitness++; //for the queens in column i and j, they do not land on the same row or same diagonal, and therefore are a non-attacking pair
				}
				
			}
		}
		
		this.fitness = fitness;
		return fitness;
	}
	
	/**
	 *
	 * @param column
	 * @returns true if it is in an attack position, if it is in the same row or diagonal as another queen
	 */
	public boolean isAttacked(int column) {
		int col = column;
		int row = queens[column];

		for(int i = 0; i < queens.length; i++) {
			if(i == col && queens[i] == row) {
				continue;
			}

			//checking for horizontals
			if(queens[i] == row) {
				return true;
			}

			//checking for diagonals
			int colDiff = Math.abs(i - col);
			int rowDiff = Math.abs(queens[i] - row);
			if (rowDiff == colDiff) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Updated the attackStatus boolean array, which was usually printed. This was primarily used for troubleshooting of other methods.
	 */
	public void updateAttackStatus() {
		//wipe the array
		Arrays.fill(attackStatus, false);

		for(int i = 0; i < queens.length; i++) {
			// if this loop finds a conflict, it sets both columns get attackStatus[x] set to true
			if(attackStatus[i] == false) {
				for(int j = 0; j < queens.length; j++) {
					if(i == j) {
						continue;
					}
					int colDiff = Math.abs(i - j);
					int rowDiff = Math.abs(queens[i] - queens[j]);
					if (rowDiff == colDiff) {
						//checking for diagonals
						attackStatus[i] = true;
						attackStatus[j] = true;
					} else if(queens[i] == queens[j]) {
						//checking for horizontals
						attackStatus[i] = true;
						attackStatus[j] = true;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @return a board with a random queen having a random movement within its column
	 */
	public Board getSuccessor() {
		int totalLoopCount = 0;

		//select a queen in attack position
		updateAttackStatus();

		int[] queenArrayCopy = new int[n];
		System.arraycopy(queens, 0, queenArrayCopy, 0, queens.length);

		int column = random.nextInt(n);

		while(column < attackStatus.length){
			if(attackStatus[column] == true) {
				break;
			} else {
				//System.out.println("not being attacked " + column);
			}

			column++;
			if(column == attackStatus.length) {
				column = 0;
			}
			totalLoopCount++;
			if(totalLoopCount == n) {
				break;
			}
		}

		//System.out.println("successor column: " + column);

		//move queen randomly
		queenArrayCopy[column] = random.nextInt(n);

		//return board

		return new Board(queenArrayCopy);
	}

	/**
	 * 
	 * @param boardY
	 * @return a new offspring of this.queens[] and boardY.queens[]
	 */
	public Board reproduceWith(Board boardY) {
		Board x = this;
		Board y = boardY;
		Board child = new Board(x.n);
		
		Random random = new Random();
		int c = random.nextInt(n);

		for(int i = 0; i < x.n; i++) {
			if(i < c) {
				child.queens[i] = x.queens[i];
			} else {
				child.queens[i] = y.queens[i];
			}
		}
		
		return child;
	}

	/**
	 * 
	 * @return a Board that has one column randomly changed, a mutation
	 */
	public Board mutate() {
		Random random = new Random();
		int randomColumn = random.nextInt(n);
		
		queens[randomColumn] = random.nextInt(n);
		
		return this;
	}

	/**
	 * 
	 * @return true if the board is solved
	 */
	public boolean isSolved() {
		updateAttackStatus();
		
		for(int i = 0; i < n; i++) {
			if(isAttacked(i)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * 
	 * @return the number of attacked queens on the board
	 */
	public int getNumberOfAttackedQueens() {
		int count = 0;
	
		updateAttackStatus();
	
		for(int i = 0; i < queens.length; i++) {
			if(attackStatus[i]) { count++; }
		}
	
		return count;
	}

	/**
	 * Prints the chess board. Q's are queens.
	 */
	public void print() {
		for(int j = 0; j < n; j++) {
			for(int i = 0; i < n; i++) {
				if(i == n - 1 && queens[i] == j) {
					System.out.println("Q ");
				} else if(i == n - 1) {
					System.out.println("- ");
				} else if(queens[i] == j) {
					System.out.print("Q ");
				} else {
					System.out.print("- ");
				}
	
			}
		}
	}

	/**
	 * Used for troubleshooting.
	 */
	public void printStatus() {
		for(int i = 0; i < queens.length; i++) {
			System.out.println(queens[i] + " " + i);
		}
	
		for(int i = 0; i < queens.length; i++) {
			for(int j = 0; j < queens.length; j++) {
				if(i == j && queens[i] == queens[j]) {
					continue;
				}
	
				if(queens[i] == queens[j]) {
					System.out.println("Horizontal @ "+ queens[i] + ", " + i + " and " + queens[j]+", " + j);
				}
	
				//checking for diagonals
				int colDiff = Math.abs(i - j);
				int rowDiff = Math.abs(queens[i] - queens[j]);
				if (rowDiff == colDiff) {
					System.out.println("Diagonal @ "+ queens[i] + ", " + i + " and " + queens[j]+", " + j);
				}
			}
		}
	}

	/**
	 * Prints a T under the column if that queen is in an attack position.
	 */
	public void printAttackStatus()	{
		for(int i = 0; i < attackStatus.length; i++) {
			if(attackStatus[i]) {
				System.out.print(" T ");
			} else {
				System.out.print(" _ ");
			}
		}
		System.out.println();
	}

}
