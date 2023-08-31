import javafx.scene.PerspectiveCamera;
import javafx.scene.input.PickResult;
import junit.framework.TestCase;

import java.util.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4, pyr5;
	private Piece s, sRotated;
	private Piece sq1, sq2;
	private Piece st1, st2;
	private Piece l11, l12, l13;
	private Piece s2, s21, s22;

	private static final int SENTINEL = 4;
//	public static final String STICK_STR	= "0 0	0 1	 0 2  0 3";
//	public static final String L1_STR		= "0 0	0 1	 0 2  1 0";
//	public static final String L2_STR		= "0 0	1 0 1 1	 1 2";
//	public static final String S1_STR		= "0 0	1 0	 1 1  2 1";
//	public static final String S2_STR		= "0 1	1 1  1 0  2 0";
//	public static final String SQUARE_STR	= "0 0  0 1  1 0  1 1";
//	public static final String PYRAMID_STR	= "0 0  1 0  1 1  2 0";

	protected void setUp() throws Exception {
		super.setUp();

		//Pyramid with rotations
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		pyr5 = pyr4.computeNextRotation();

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
	}
	
	// Here are some sample tests to get you started
	
	public void testSampleSize() {
		//Check size of pyr piece
		System.out.println("testSampleSize");
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());

		//Check size of pyr sqr
		assertEquals(2, sq1.getWidth());
		assertEquals(2, sq1.getHeight());

		//Check size of pyr stick
		assertEquals(1, st1.getWidth());
		assertEquals(4, st1.getHeight());

		//Check size of pyr l1
		assertEquals(2, l11.getWidth());
		assertEquals(3, l11.getHeight());

		//Check size of pyr s2
		assertEquals(3, s2.getWidth());
		assertEquals(2, s2.getHeight());

		//Check size of pyr s
		assertEquals(3, s.getWidth());
		assertEquals(2, s.getHeight());

		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());

		assertEquals(3, pyr3.getWidth());
		assertEquals(2, pyr3.getHeight());

	}

	/* Testing figures sizes after rotations */
	public void testRotatedSize() {
		// Check sizes for pyramid rotation
		assertEquals(2, pyr4.getWidth());
		assertEquals(3, pyr4.getHeight());

		assertEquals(3, pyr5.getWidth());
		assertEquals(2, pyr5.getHeight());

		// Check sizes for square rotation
		assertEquals(2, sq2.getWidth());
		assertEquals(2, sq2.getHeight());

		// Check sizes for stick rotation
		assertEquals(4, st2.getWidth());
		assertEquals(1, st2.getHeight());

		// Check sizes for l1 rotation
		assertEquals(3, l12.getWidth());
		assertEquals(2, l12.getHeight());

		// Check sizes for s rotation
		assertEquals(2, s21.getWidth());
		assertEquals(3, s21.getHeight());

		assertEquals(2, sRotated.getWidth());
		assertEquals(3, sRotated.getHeight());
	}
	
	
	// Test the skirt returned by a few pieces
	public void testSampleSkirt() {
		System.out.println("testSampleSkirt");
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0, 0}, sq1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0}, st1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 0}, s2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0}, l11.getSkirt()));
	}

	public void testRotationSkirt() {
		assertTrue(Arrays.equals(new int[] {0, 1}, pyr4.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0, 0}, sq2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, st2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, s21.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, l12.getSkirt()));
	}
//
	public void testSampleBody(){
		TPoint[] pyr =  new TPoint[SENTINEL];

		TPoint[] s2_Points =  new TPoint[SENTINEL];
		TPoint[] l1_points =  new TPoint[SENTINEL];
		int count = 0;

		pyr[count] = new TPoint(0, 0);
		s2_Points[count] = new TPoint(0, 1);
		l1_points[count++] = new TPoint(0, 0);

		pyr[count] = new TPoint(1, 0);
		s2_Points[count] = new TPoint(1, 1);
		l1_points[count++] = new TPoint(0, 1);

		pyr[count] = new TPoint(1, 1);
		s2_Points[count] = new TPoint(1, 0);
		l1_points[count++] = new TPoint(0, 2);

		pyr[count] = new TPoint(2, 0);
		s2_Points[count] = new TPoint(2, 0);
		l1_points[count] = new TPoint(1, 0);

		for(int i = 0; i < SENTINEL; i ++){
			assertTrue(pyr[i].equals(pyr1.getBody()[i]));
			assertTrue(s2_Points[i].equals(s2.getBody()[i]));
			assertTrue(l1_points[i].equals(l11.getBody()[i]));
		}
	}

	public void testSampleEquals(){
		// Equals itself
		assertTrue(pyr1.equals(pyr1));
		assertTrue(sq1.equals(sq1));
		assertTrue(s2.equals(s2));
		assertTrue(s.equals(s));
		assertTrue(l11.equals(l11));

		// Not Equals, but are instance of piece
		assertFalse(pyr1.equals(sq1));
		assertFalse(s2.equals(s));
		assertFalse(l11.equals(st1));
		assertFalse(s.equals(l11));

		// Equals
		assertTrue(pyr1.equals(pyr5));
		assertTrue(sq1.equals(sq2));

		// Different Instances
		assertFalse(pyr1.equals(pyr1.getWidth()));
		assertFalse(sq1.equals(sq1.getHeight()));
		assertFalse(l11.equals(l11.getSkirt()));
	}

	//	public static final String STICK_STR	= "0 0	0 1	 0 2  0 3";
//	public static final String L1_STR		= "0 0	0 1	 0 2  1 0";
//	public static final String L2_STR		= "0 0	1 0 1 1	 1 2";
//	public static final String S1_STR		= "0 0	1 0	 1 1  2 1";
//	public static final String S2_STR		= "0 1	1 1  1 0  2 0";
//	public static final String SQUARE_STR	= "0 0  0 1  1 0  1 1";
//	public static final String PYRAMID_STR	= "0 0  1 0  1 1  2 0";


	public void testSampleFastRotation() {
		// Fast rotation for Pyramid
		Piece[] pieces = pyr1.getPieces();
		Piece root = pieces[Piece.PYRAMID];
		Piece pyrFastRot2 = root.fastRotation();
		Piece pyrFastRot3 = pyrFastRot2.fastRotation();
		Piece pyrFastRot4 = pyrFastRot3.fastRotation();
		Piece pyrFastRot5 = pyrFastRot4.fastRotation();

		assertTrue(root.equals(pyr1));
		assertTrue(pyrFastRot2.equals(pyr2));
		assertTrue(pyrFastRot3.equals(pyr3));
		assertTrue(pyrFastRot4.equals(pyr4));
		assertTrue(pyrFastRot5.equals(pyr5));

		// Fast rotation for s2
		Piece[] S2_pieces = s2.getPieces();
		Piece s2_root = S2_pieces[Piece.S2];
		Piece s2FastRot2 = s2_root.fastRotation();
		Piece s2FastRot3 = s2FastRot2.fastRotation();

		assertTrue(s2_root.equals(s2));
		assertTrue(s2FastRot2.equals(s21));
		assertTrue(s2FastRot3.equals(s22));
	}

	public void testParsePointsRuntimeExceptionMessage() {
		String invalidString = "1 2 3a 4";
		try {
			Piece pp = new Piece(invalidString);
		} catch (RuntimeException e) {
			assertEquals("Could not parse x,y string:" + invalidString, e.getMessage());
		}
	}
}
