// StringCodeTest
// Some test code is provided for the early HW1 problems,
// and much is left for you to add.

import junit.framework.TestCase;

public class StringCodeTest extends TestCase {
	//
	// blowup
	//
	public void testBlowup1() {
		// basic cases
		assertEquals("xxaaaabb", StringCode.blowup("xx3abb"));
		assertEquals("xxxZZZZ", StringCode.blowup("2x3Z"));
	}
	
	public void testBlowup2() {
		// things with digits
		
		// digit at end
		assertEquals("axxx", StringCode.blowup("a2x3"));
		
		// digits next to each other
		assertEquals("a33111", StringCode.blowup("a231"));
		
		// try a 0
		assertEquals("aabb", StringCode.blowup("aa0bb"));

		assertEquals("", StringCode.blowup("0"));

		assertEquals("", StringCode.blowup(""));
	}
	
	public void testBlowup3() {
		// weird chars, empty string
		assertEquals("AB&&,- ab", StringCode.blowup("AB&&,- ab"));

		assertEquals("", StringCode.blowup(""));
		
		// string with only digits
		assertEquals("", StringCode.blowup("2"));
		assertEquals("33", StringCode.blowup("23"));
	}
	
	
	//
	// maxRun
	//
	public void testRun1() {
		assertEquals(2, StringCode.maxRun("hoopla"));
		assertEquals(3, StringCode.maxRun("hoopllla"));
	}
	
	public void testRun2() {
		assertEquals(3, StringCode.maxRun("abbcccddbbbxx"));
		assertEquals(0, StringCode.maxRun(""));
		assertEquals(3, StringCode.maxRun("hhhooppoo"));
	}
	
	public void testRun3() {
		// "evolve" technique -- make a series of test cases
		// where each is change from the one above.
		assertEquals(1, StringCode.maxRun("123"));
		assertEquals(2, StringCode.maxRun("1223"));
		assertEquals(2, StringCode.maxRun("112233"));
		assertEquals(4, StringCode.maxRun("111223333"));
	}

	public void testIntersect1(){

		assertEquals(true, StringCode.stringIntersect("play", "lucky", 1));
		assertEquals(true, StringCode.stringIntersect("world", "word", 2));
		assertEquals(true, StringCode.stringIntersect("palindrome", "home", 3));
		assertEquals(true, StringCode.stringIntersect("fun", "funny", 3));

	}

	public void testIntersect2(){
		assertEquals(false, StringCode.stringIntersect("action", "accuse", 3));
		assertEquals(false, StringCode.stringIntersect("boat", "cable", 2));
		assertEquals(false, StringCode.stringIntersect("dimension", "diversity", 9));
		assertEquals(false, StringCode.stringIntersect("earnings", "evaluate", 20));
	}
	
}
