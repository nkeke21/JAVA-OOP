// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.
import java.util.HashMap;

public class CharGrid {
	private char[][] grid;
	int maxWidth;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a grid.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}

	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		int result = 0;
		/* frequency HashMap Saves the amount of each character appears in grid */
		HashMap<Character, Integer> frequency = new HashMap<Character, Integer>();
		fill(frequency);
		/* Test case when there is no character in the grid equals input character */
		if(!frequency.containsKey(ch)) return 0;

		/* Check all the rectangles in grid, if specified rectangle satisfies the rule (contains all the input chs)
		 *  We then must check if it is the smallest rectangle */
		for(int r = 0; r < this.grid.length; r ++){
			for(int c = 0; c < maxWidth; c ++){
				for(int r1 = r; r1 < this.grid.length; r1 ++){
					for(int c1 = c; c1 < maxWidth; c1 ++){
						if(containAll(r, c, r1, c1, frequency, ch)){
							if(result == 0 || (result > (r1 - r + 1) * (c1 - c + 1))) {
								result = (r1 - r + 1) * (c1 - c + 1);
							}
						}
					}
				}
			}
		}
		return result;
	}

	/* Return if the specified rectangle contains all the specified Characters*/
	boolean containAll(int r, int c, int r1, int c1, HashMap<Character, Integer> frequency, char ch){
		int count = 0;
		for(int i = r; i <= r1; i ++){
			for(int j = c; j <= c1; j ++){
				if(j >= grid[i].length) break;
				if(this.grid[i][j] == ch) count ++;
			}
		}
		return count == frequency.get(ch);
	}

	/* Filling the hashMap with key: specified Character and value amount of times they appear in grid */
	void fill(HashMap<Character, Integer> frequency){
		for(int r = 0; r < this.grid.length; r ++){
			if(grid[r].length > maxWidth) maxWidth = grid[r].length;
			for(int c = 0; c < this.grid[r].length; c ++){
				if(frequency.containsKey(this.grid[r][c])){
					Integer value = frequency.get(this.grid[r][c]);
					frequency.put(this.grid[r][c], value + 1);
				} else frequency.put(this.grid[r][c], 1);
			}
		}
	}

	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */

	/* This method returns the amount of pluses in grid by going through the grid.
	 *  start from the left wing of plus and check if its next Character matches current one.
	 *  Then count the length of middle line, after that we should check if lengths of north and
	 *  south lines satisfies the condition. */
	public int countPlus() {
		int count = 0; // stores amount of plus in the grid
		/* Go through the grid */
		for(int r = 0; r < grid.length; r ++){
			for(int c = 0; c < grid[r].length - 1; c ++){
				/* If next Character matches the current one, it has got chance to be part of plus */
				if((grid[r][c] == grid[r][c + 1])){
					int length = 0; // length for saving the length of middle line
					for(int i = c; i < grid[r].length; i ++){
						if(grid[r][i] == grid[r][c])  {
							length ++;
						} else break;
					}
					/* Method helping us to detect if it's plus! after that we should move by length to the right
					 *  not to consider next some unprofitable cases */
					if(plus(r, c, grid, grid[r][c], length)) count ++;
					c += length;
				}
			}
		}
		return count;
	}

	boolean plus(int r, int c, char[][] grd, char ch, int length){

		if(length < 3 || length %2 == 0) return  false; // Does not specify the condition
		int collumn = c + length/2; // Middle point of the middle line
		int up = 0; int down = 0; // Stores information about length of north and south line of plus
		/* Go throught up and down from the middle point of the middle line of plus */
		for(int i = r - 1; i >= 0; i --){
			if(collumn >= grid[i].length || grd[i][collumn] != ch) break;
			else up ++;
		}
		for (int i = r + 1; i < grd.length; i ++){
			if(collumn >= grid[i].length || grd[i][collumn] != ch) break;
			else down ++;
		}

		/* If the lengths of up and down part of the plus equals each other and their sum
		 * plus one equals the middle length of the plus, it's plus !!! */
		if(up == down && up + down + 1 == length) return true;

		return false;
	}
}