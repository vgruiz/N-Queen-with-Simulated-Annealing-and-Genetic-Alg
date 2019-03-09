import java.util.Random;

public class Board {
//	Queen[][] gameBoard;
	int[] queens;
	boolean[] attackStatus;
	int n;
	Random random = new Random();
	
	public Board(int n) {
		//gameBoard = new Queen[8][8];
		queens = new int[n];
		attackStatus = new boolean[n];
		this.n = n;
		setInitialPositions();
	}
	
	public void setInitialPositions() {
		for(int i = 0; i < queens.length; i++) {
			queens[i] = random.nextInt(n);
		}
	}
	
	public void print() {
		for(int j = 0; j < n; j++) {
			for(int i = 0; i < n; i++) {
				if(i == n - 1 && queens[i] == j) {
					System.out.println(" X ");
				} else if(i == n - 1) {
					System.out.println(" | ");
				} else if(queens[i] == j) {
					System.out.print(" X ");
				} else {
					System.out.print(" | ");					
				}
				
			}
		}
	}
	
	//TODO: Finish this lol
	public void generateRandomSuccessor() {
		int randCol = random.nextInt(n);
		int randRow = random.nextInt(n);
		
		queens[randCol] = randRow;
		
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
				//System.out.println("Horizontal @ "+ queens[i] + ", " + i + " and " + row +", " + col);
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
	
	public int getNumberOfAttackedQueens() {
		int count = 0;
		
		for(int i = 0; i < queens.length; i++) {
			if(attackStatus[i] == false) {
				for(int j = 0; j < queens.length; j++) {
					if(i == j) {
						continue;
					}					
					//checking for horizontals
					if(queens[i] == queens[j]) {
						
					}
					//checking for diagonals
					int colDiff = Math.abs(i - j);
					int rowDiff = Math.abs(queens[i] - queens[j]);
					if (rowDiff == colDiff) {
						
					}
				}
				
			}
			
			
//			
//			if(i == col && queens[i] == row) {
//				continue;
//			}
//			
//			//checking for horizontals
//			if(queens[i] == row) {
//				//System.out.println("Horizontal @ "+ queens[i] + ", " + i + " and " + row +", " + col);
//				return true;
//			}
//			
//			//checking for diagonals
//			int colDiff = Math.abs(i - col);
//			int rowDiff = Math.abs(queens[i] - row);
//			if (rowDiff == colDiff) {
//				return true;
//			}
		}
	}
	
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
	
	public void isSolved() {}
	
}
