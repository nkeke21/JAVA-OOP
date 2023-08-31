// Board.java

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
 */
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;

	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;

	private int[] widths;
	private int[] heights;

	private int maxHeight;
	private int widthFilled;

	/* variables for backUp*/
	private int[] xWidths;
	private int[] xHeights;
	private boolean[][] xGrid;
	int xWidthFilled;
	private int xmaxHeight;


	// Here a few trivial methods are provided:
	private final int BLOCKS = 4;

	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	 */
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		committed = true;

		initializeOrigin();
		initializeBackUps();
	}
	private void initializeOrigin(){
		grid = new boolean[width][height];
		widths = new int[height];
		heights = new int[width];
		maxHeight = 0;
	}
	private void initializeBackUps() {
		xWidths = new int[height];
		xHeights = new int[width];
		xGrid = new boolean[width][height];
		xmaxHeight = 0;
	}


	/**
	 Returns the width of the board in blocks.
	 */
	public int getWidth() {
		return width;
	}


	/**
	 Returns the height of the board in blocks.
	 */
	public int getHeight() {
		return height;
	}


	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	 */
	public int getMaxHeight() {
		return maxHeight;
	}


	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	 */
	public void sanityCheck() {
		if (DEBUG) Checker();
	}

	private void Checker() {
		int[] hei = new int[width];
		int maxH = 0;

		for(int r = 0; r < width; r ++){
			hei[r] = maxCol(r);
			if(hei[r] > maxH) maxH = hei[r];
		}
		if(maxH != maxHeight) throw new RuntimeException("maxHeight error");
		if(!Arrays.equals(hei, heights)) throw new RuntimeException("heights' error");

		int[] rows = new int[height];
		for(int r = 0; r < width; r ++)
			for(int c = 0; c < height; c ++)
				if(grid[r][c]) rows[c] ++;

		if(!Arrays.equals(rows, widths)) new RuntimeException("widths' error");
	}
	private int maxCol(int x){
		for(int c = height - 1; c >= 0; c --){
			if(getGrid(x, c)) return c + 1;
		}
		return 0;
	}
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.

	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	 */
	public int dropHeight(Piece piece, int x) {
		int resultY = 0;

		for(int i = 0; i < piece.getWidth(); i ++){
			int currSkirt = piece.getSkirt()[i];
			if(x + i < width) {
				int currHeight = heights[x + i];
				if(currHeight - currSkirt > resultY) {
					resultY = currHeight - currSkirt;
				}
			}
		}
		return resultY;
	}


	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	 */
	public int getColumnHeight(int x) {
		return heights[x];
	}


	/**
	 Returns the number of filled blocks in
	 the given row.
	 */
	public int getRowWidth(int y) {
		return widths[y];
	}


	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	 */
	public boolean getGrid(int x, int y) {
		if((x < 0 || x >= width) || (y < 0 || y >= height)) return true;
		return grid[x][y];
	}


	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;

	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.

	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	 */
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");

		if((x < 0 || x + piece.getWidth() -1 >= width) || y < 0){
			committed = false;
			return PLACE_OUT_BOUNDS;
		}

		int result = addPiece(piece, x, y);

		committed = false;
		if(result == PLACE_OK  || result == PLACE_ROW_FILLED) sanityCheck();
		return result;
	}

//	private void backUp() {
//		for(int i = 0; i < grid.length; i ++){
//			System.arraycopy(grid[i], 0, xGrid[i], 0, grid[i].length);
//		}
//
//		System.arraycopy(widths, 0, xWidths, 0, widths.length);
//		System.arraycopy(heights, 0, xHeights, 0, heights.length);
//		xWidthFilled = widthFilled;
//		xmaxHeight = maxHeight;
//	}

	private int addPiece(Piece piece, int fx, int fy) {
		int result = PLACE_OK;
		for(int i = 0; i < BLOCKS; i ++){
			TPoint currP = piece.getBody()[i];
			if(getGrid(fx + currP.x, fy + currP.y)) {
				return PLACE_BAD;
			}

			grid[fx + currP.x][fy + currP.y] = true;

			widths[fy + currP.y] ++;
			if(width == widths[fy + currP.y]){
				result = PLACE_ROW_FILLED;
				widthFilled ++;
			}

			if(fy + currP.y > heights[fx + currP.x] - 1) {
				heights[fx + currP.x] = fy + currP.y + 1;
				if(fy + currP.y + 1 > maxHeight) {
					maxHeight = fy + currP.y + 1;
				}
			}
		}
		return result;
	}

	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	 */
	public int clearRows() {
		int rowsCleared = widthFilled;
		while (widthFilled > 0){
			clearing();
		}

		sanityCheck();
		committed = false;
		return rowsCleared;
	}

	private void clearing() {
		maxHeight = 0;
		for(int c = 0; c < height; c ++){
			if(widths[c] == width) {
				for(int c1 = c; c1 < height - 1; c1 ++){
					FromTo(c1);
				}

				for(int r = 0; r < width; r ++) grid[r][height-1] = false;

				for(int k = c; k < height - 1; k ++) widths[k] = widths[k + 1];
				for(int j = 0; j < width; j ++) {
					heights[j] = maxCol(j);
					if(maxHeight < heights[j]) maxHeight = heights[j];
				}

				widths[height - 1] = 0;
				widthFilled --;
				break;
			}
		}
	}

	private void FromTo(int c1) {
		for(int r = 0; r < width; r ++){
			grid[r][c1] = grid[r][c1+1];
		}
	}


	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	 */
	public void undo() {
		if(committed) return;
		committed = true;
		System.arraycopy(xHeights, 0, heights, 0, xHeights.length);
		System.arraycopy(xWidths, 0, widths, 0, xWidths.length);
		widthFilled = xWidthFilled;
		maxHeight = xmaxHeight;

		for(int i = 0; i < xGrid.length; i ++){
			System.arraycopy(xGrid[i], 0, grid[i], 0, xGrid[i].length);
		}

	}


	/**
	 Puts the board in the committed state.
	 */
	public void commit() {
		if(committed) return;
		committed = true;
		System.arraycopy(heights, 0, xHeights, 0, xHeights.length);
		System.arraycopy(widths, 0, xWidths, 0, xWidths.length);
		xWidthFilled = widthFilled;
		xmaxHeight = maxHeight;

		for(int i = 0; i < xGrid.length; i ++){
			System.arraycopy(grid[i], 0, xGrid[i], 0, grid[i].length);
		}
	}



	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility)
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


