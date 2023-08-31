import java.lang.reflect.AnnotatedParameterizedType;
import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.

	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
			"1 6 4 0 0 0 0 0 2",
			"2 0 0 4 0 3 9 1 0",
			"0 0 5 0 8 0 4 0 7",
			"0 9 0 0 0 6 5 0 0",
			"5 0 0 1 0 2 0 0 8",
			"0 0 8 9 0 0 0 3 0",
			"8 0 9 0 4 0 2 0 0",
			"0 7 3 5 0 9 0 0 1",
			"4 0 0 0 0 0 6 7 9");


	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
			"530070000",
			"600195000",
			"098000060",
			"800060003",
			"400803001",
			"700020006",
			"060000280",
			"000419005",
			"000080079");

	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
			"3 7 0 0 0 0 0 8 0",
			"0 0 1 0 9 3 0 0 0",
			"0 4 0 7 8 0 0 0 3",
			"0 9 3 8 0 0 0 1 2",
			"0 0 0 0 4 0 0 0 0",
			"5 2 0 0 0 6 7 9 0",
			"6 0 0 0 2 1 0 4 0",
			"0 0 0 5 3 0 9 0 0",
			"0 3 0 0 0 0 0 5 1");


	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	private int solutions = 0;
	private int[][] board;
	private List<spot> blankSpots;
	private String solutionText;
	boolean isSolved = false;
	long startTime = 0;
	long endTime = 0;
	long elapsedTime = 0;

	// Provided various static utility methods to
	// convert data formats to int[][] grid.

	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}


	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}

		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}


	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);

		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}

	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		board = ints;
		solutionText = "";
		sortList();
	}

	public Sudoku(String text) {
		this(Sudoku.textToGrid(text));
	}

	private void sortList() {
		blankSpots = new ArrayList<spot>();
		for (int r = 0; r < SIZE; r ++){
			for (int c = 0; c < SIZE; c ++){
				if(board[r][c] == 0) blankSpots.add(new spot(r, c));
			}
		}
		Collections.sort(blankSpots);
	}


	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		startTime = System.currentTimeMillis();
		solutions = 0;
		wrapper(0);
		endTime = System.currentTimeMillis();
		elapsedTime = endTime - startTime;
		return solutions;
	}

	private void wrapper(int startIndex) {
		if (solutions >= MAX_SOLUTIONS) {
			return;
		}
		if (startIndex == blankSpots.size()) {
			/* First solution expressed by string */
			if (solutionText.isEmpty()) {
				isSolved = true;
				solutionText = toString();
			}
			solutions++;
			return;

		}
		/* Current spot */
		spot curr = blankSpots.get(startIndex);
		HashSet<Integer> canBe = curr.assignable();

		/* Go through what values current spot can take and call recursion function for each of them */
		for (int val : canBe) {
			curr.set(val);
			wrapper(startIndex + 1);
		}
		curr.set(0);
	}

	public String getSolutionText() {
		if (!isSolved) solve();
		return solutionText;
	}

	public long getElapsed() {
		if(!isSolved) solve();
		return elapsedTime;
	}

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(int r = 0; r < board.length; r ++){
			for(int c = 0; c < board[r].length; c++){
				s.append(board[r][c]);
				s.append(" ");
			}
			s.append("\n");
		}
		return s.toString();
	}

	public class spot  implements Comparable<spot>{
		private int row;
		private int col;
		private int availableNums;

		/* Initialize spot */
		public spot(int r, int c) {
			row = r;
			col = c;
			availableNums = assignable().size();
		}

		/* Change the value of spot*/
		public void set(int val) {
			board[row][col] = val;
		}

		/* All the values that current spot can be */
		private HashSet<Integer> assignable() {
			HashSet<Integer> set = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
			/* Through the columns */
			for(int i = 0; i < SIZE; i ++){
				if(board[row][i] != 0) set.remove(board[row][i]);
			}
			/* Through the rows */
			for(int i = 0; i < SIZE; i ++){
				if(board[i][col] != 0) set.remove(board[i][col]);
			}

			/* In square */
			int x = (row / PART) * PART;
			int y = (col / PART) * PART;
			for(int i = x ; i < x + PART; i ++){
				for (int j = y; j < y + PART; j ++){
					if(board[i][j] != 0) set.remove(board[i][j]);
				}
			}
			return set;
		}

		/* Comparable function that will be needed in sorting algorithm */
		@Override
		public int compareTo(spot other) {
			return availableNums - other.availableNums;
		}
	}

}
