// Test cases for CharGrid -- a few basic tests are provided.
import junit.framework.TestCase;

import java.util.HashMap;

public class CharGridTest extends TestCase {

	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', 'z'},
				{'x'},
				{'x', 'b', 'z'},
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.charArea('a'));
		assertEquals(3, cg.charArea('z'));
	}

	public void testCharArea5() {
		char[][] grid = new char[][] {
				{' ', ' ', 'p'},
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x'},
				{'p', 'p', 'p', 'p'},
				{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
				{' ', ' ', 'p', ' ', 'y', 'y', 'y'},
				{'z', 'z', 'z', 'z'},
				{' '},
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(63, cg.charArea(' '));
		assertEquals(20, cg.charArea('p'));
		assertEquals(4, cg.charArea('z'));
		assertEquals(3, cg.charArea('x'));
		assertEquals(6, cg.charArea('y'));
	}


	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}

	public void testCharArea3() {
		char[][] grid = new char[][] {
				{'x', 'a', 'b', 'k', 'o'},
				{'b', ' ', 'b', 'l', 'f'},
				{'c', 'c', 'c', 'b', 'x'}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.charArea('n'));
		assertEquals(15, cg.charArea('x'));
		assertEquals(3, cg.charArea('c'));
	}

	public void testCharArea4() {
		char[][] grid = new char[][] {
				{'a', 'b', 'c', 'd'},
				{'b', 'b', 'c', 'd'},
				{'c', 'c', 'c', 'd'},
				{'d', 'd', 'd', 'd'}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.charArea('e'));
		assertEquals(1, cg.charArea('a'));
		assertEquals(4, cg.charArea('b'));
		assertEquals(9, cg.charArea('c'));
		assertEquals(16, cg.charArea('d'));
	}

	public void testcountPlus1() {
		char[][] grid = new char[][] {
				{' ', ' ', 'p', ' ', ' ', ' '},
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x'},
				{'p', 'p', 'p', 'p', 'p', ' ', 'x', 'x', 'x'},
				{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
				{' ', ' ', 'p', ' ', 'y', 'y', 'y'},
				{'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z'},
				{' ', ' ', 'x', 'x', ' ', 'y'},
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(2, cg.countPlus());
	}

	public void testcountPlus2() {
		char[][] grid = new char[][] {
				{' ', ' ', ' '},
				{' ', ' ', ' '},
				{' ', ' ', ' '}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}

	public void testcountPlus3() {
		char[][] grid = new char[][] {
				{' ', 'x', ' ', ' ', ' ', ' '},
				{'x', 'x', 'x', ' ', 's', ' '},
				{' ', 'x', 'y', 's', 's', 's'},
				{' ', 'y', 'y', 'y', 's', ' '},
				{' ', 't', 'y', ' ', ' ', ' '},
				{'t', 't', 't', 'p', ' ', ' '},
				{' ', 't', 'p', 'p', 'p', ' '},
				{' ', ' ', 'p', 'p', ' ', ' '},
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(5, cg.countPlus());
	}
}