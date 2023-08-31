//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {
	
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	private boolean[][] grid;

	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}
	
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		while (true){
			int currCol = checker();
			/* If there are not any filled rows left we should stop the while loop */
			if(currCol == -1) break;

			/* We should pull down all the collumns above c indexed collumn */
			for(int c = currCol; c < this.grid[0].length - 1; c ++){
				for (int r = 0; r < this.grid.length; r ++){
					this.grid[r][c] = this.grid[r][c + 1];
				}
			}
			/* Last collumns will be false after removed filled collumn */
			for(int r = 0; r < this.grid.length; r ++){
				grid[r][this.grid[0].length - 1] = false;
			}
		}
	}

	/* Checks if there is any filled collumn. If it is, returns the index of the filled collumn */
	int checker(){
		for(int c = 0; c < this.grid[0].length; c ++){
			int checker = 0; // Helping us to detect filled collumn
			for(int r = 0; r < this.grid.length; r ++){
				if(this.grid[r][c] == true) checker ++;
			}
			/* If isFilled return the index of filled collumn */
			if(checker == this.grid.length) return  c;
		}

		return -1; // Otherwise return -1;
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return this.grid; // YOUR CODE HERE
	}
}
