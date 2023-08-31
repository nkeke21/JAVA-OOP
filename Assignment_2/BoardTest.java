import junit.framework.TestCase;

import java.util.HashSet;


public class BoardTest extends TestCase {
	Board board;
	int width = 8;
	int height = 20;
	private Piece pyr1, pyr2, pyr3, pyr4, pyr5;
	private Piece s, sRotated;
	private Piece sq1, sq2;
	private Piece st1, st2;
	private Piece l11, l12, l13;
	private Piece s2, s21, s22;

	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	
	protected void setUp() throws Exception {
		board = new Board(width, height);
		board.sanityCheck();

		// Add Figures
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		pyr5 = pyr4.computeNextRotation();
	//fosfomedi,
		sq1 = new Piece(Piece.SQUARE_STR);
		sq2 = sq1.computeNextRotation();

		st1 = new Piece(Piece.STICK_STR);
		st2 = st1.computeNextRotation();

		l11 = new Piece(Piece.L1_STR);
		l12 = l11.computeNextRotation();

		s2 = new Piece(Piece.S2_STR);
		s21 = s2.computeNextRotation();
		s22 = s21.computeNextRotation();

		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();

		board.commit();
	}
	public void testSample(){
		assertEquals(width, board.getWidth());
		assertEquals(height, board.getHeight());
		assertEquals(0, board.getMaxHeight());
	}
	
	// Check the basic width/height/max after the one placement
	public void testSample1() {
		// Place some figures on the board
		int x1 = board.place(pyr1, 0 ,0);
		board.committed = true;
		int x2 = board.place(pyr2, 1, 0);
		board.committed = true;
		int x3 = board.place(pyr1, 3, 0);
		board.committed = true;
		int x4 = board.place(sq1, 2, 1);
		board.committed = true;
		int x5 = board.place(sq1.computeNextRotation(), 6, 0);
		board.commit();
		int x6 = board.place(l12, -1, 0);
		board.committed = true;
		board.place(sq1, 0, 1);
		board.undo();

		board.place(pyr3, 4, 1);
		board.committed = true;
		board.place(s21, 0, 1);
		board.committed = true;

		// Check if they are correctly placed
		assertEquals(x1, board.PLACE_OK);
		assertEquals(x2, board.PLACE_BAD);
		assertEquals(x3, board.PLACE_OK);
		assertEquals(x4, board.PLACE_OK);
		assertEquals(x5, board.PLACE_ROW_FILLED);
		assertEquals(x6, board.PLACE_OUT_BOUNDS);

		assertEquals(board.getMaxHeight(), 4);
		assertEquals(board.getColumnHeight(0), 3);
		assertEquals(board.getColumnHeight(1), 4);
		assertEquals(board.getColumnHeight(5), 3);

		assertEquals(board.getRowWidth(0), 8);
		assertEquals(board.getRowWidth(1), 8);
		assertEquals(board.getRowWidth(2), 7);

		// Filled or Not
		assertTrue(board.getGrid(1, 0));
		assertTrue(board.getGrid(2, 2));

		assertFalse(board.getGrid(1, 4));
		assertFalse(board.getGrid(2, 3));

		board.sanityCheck();

		int cleared = board.clearRows();

		board.sanityCheck();

		assertEquals(cleared, 2);
		assertEquals(board.getMaxHeight(), 2);

		assertEquals(board.getRowWidth(0), 7);
		assertEquals(board.getRowWidth(1), 1);

		assertEquals(board.getColumnHeight(1), 2);
		assertEquals(board.getColumnHeight(7), 0);
		System.out.println(board.toString());
	}
	
	// Place sRotated into the board, then check some measures
	public void testDropHeight() {
		int y1 = board.dropHeight(sq1, 0);
		int x1 = board.place(sq1, 0, y1);
		board.committed = true;
		assertEquals(y1, 0);
		assertEquals(x1, board.PLACE_OK);

		int y2 = board.dropHeight(l12, 2);
		int x2 = board.place(l12, 2, y2);
		board.committed = true;
		assertEquals(y2, 0);
		assertEquals(x2, board.PLACE_OK);

		int y3 = board.dropHeight(sRotated, 0);
		int x3 = board.place(sRotated, 0, y3);
		board.committed = true;
		assertEquals(y3, 2);
		assertEquals(x3, board.PLACE_OK);

		int y4 = board.dropHeight(l12, 5);
		int x4 = board.place(l12, 5, y4);
		board.commit();
		assertEquals(y4, 0);
		assertEquals(x4, board.PLACE_ROW_FILLED);

		board.place(sq1, 2, 1);
		board.committed = true;
		board.place(s, 5, 1);
		board.undo();

		board.sanityCheck();

		int cleared = board.clearRows();
		board.sanityCheck();
		assertEquals(cleared, 1);
		assertEquals(board.getRowWidth(0), 4);
		assertEquals(board.getMaxHeight(), 4);

		System.out.println(board.toString());
	}

	
	// Makre  more tests, by putting together longer series of 
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.
	
	
}
