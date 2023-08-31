// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import junit.framework.TestCase;

public class TabooTest extends TestCase {
    public void testTabloo1(){
        List<Character> l = new ArrayList<Character>();
        l.add('a'); l.add('c');  l.add(null); l.add('a');  l.add('b');

        HashSet<Character> expected = new HashSet<Character>();
        expected.add('b'); expected.add('c');

        Taboo<Character> t = new Taboo<Character>(l);

        assertEquals(expected,  t.noFollow('a'));
    }

    public void testTabloo2(){
        List<Character> l = new ArrayList<Character>();
        l.add('a'); l.add('a');  l.add('a'); l.add('a');  l.add('a');

        HashSet<Character> expected = new HashSet<Character>();
        expected.add('a');

        Taboo<Character> t = new Taboo<Character>(l);

        assertEquals(expected,  t.noFollow('a'));
    }

    public void testTabloo3(){
        List<Character> l = new ArrayList<Character>();
        l.add('a'); l.add('e');  l.add('b'); l.add('e');  l.add(null); l.add(null);

        HashSet<Character> expected = new HashSet<Character>();
        expected.add('b');

        Taboo<Character> t = new Taboo<Character>(l);

        assertEquals(expected,  t.noFollow('e'));
    }

    public void testTabloo4(){
        List<Character> l = new ArrayList<Character>();
        l.add('a'); l.add(null);  l.add('b'); l.add('e');  l.add(null); l.add(null);

        HashSet<Character> expected = new HashSet<Character>();

        Taboo<Character> t = new Taboo<Character>(l);

        assertEquals(expected,  t.noFollow('a'));
    }

    public void testReduce1(){
        List<Character> l = new ArrayList<Character>();
        l.add('a'); l.add('a');  l.add('a'); l.add('a');  l.add('a');
        Taboo<Character> t = new Taboo<Character>(l);
        t.reduce(l);

        List<Character> expected = new ArrayList<Character>();
        expected.add('a');


        assertEquals(expected,  l);
    }

    public void testReduce2(){
        List<Character> l = new ArrayList<Character>();
        List<Character> s = new ArrayList<Character>();
        l.add('a'); l.add(null);  l.add(null); l.add('c');  l.add('b');
        s.add('c'); s.add('b');  s.add('a'); s.add('a');  s.add('c');
        Taboo<Character> t = new Taboo<Character>(l);
        t.reduce(s);

        List<Character> expected = new ArrayList<Character>();
        expected.add('c'); expected.add('a'); expected.add('a'); expected.add('c');


        assertEquals(expected,  s);
    }

    public void testReduce3(){
        List<Character> l = new ArrayList<Character>();
        List<Character> s = new ArrayList<Character>();
        l.add('a'); l.add('b');  l.add('c'); l.add('b');  l.add('a');
        s.add('b'); s.add('a');  s.add('c'); s.add('b');  s.add('a');
        Taboo<Character> t = new Taboo<Character>(l);
        t.reduce(s);

        List<Character> expected = new ArrayList<Character>();
        expected.add('b'); expected.add('b');


        assertEquals(expected,  s);
    }

    public void testReduce4(){
        List<Character> rule = new ArrayList<Character>();
        List<Character> inp = new ArrayList<Character>();
        rule.add('a'); rule.add('c');  rule.add('a'); rule.add('b');
        inp.add('a'); inp.add('c');  inp.add('b'); inp.add('x');  inp.add('c'); inp.add('a');
        Taboo<Character> t = new Taboo<Character>(rule);
        t.reduce(inp);
        List<Character> expected = new ArrayList<Character>();
        expected.add('a'); expected.add('x'); expected.add('c');


        assertEquals(expected,  inp);
    }

    public void testReduce5(){
        List<Integer> l = new ArrayList<Integer>();
        List<Integer> s = new ArrayList<Integer>();
        l.add(1); l.add(null);  l.add(6); l.add(3);  l.add(2);
        s.add(3); s.add(2);  s.add(6); s.add(3);  s.add(1);
        Taboo<Integer> t = new Taboo<Integer>(l);
        t.reduce(s);

        List<Integer> expected = new ArrayList<Integer>();
        expected.add(3); expected.add(6); expected.add(1);


        assertEquals(expected,  s);
    }

    public void testReduce6(){
        List<Integer> l = new ArrayList<Integer>();
        List<Integer> s = new ArrayList<Integer>();
        l.add(1); l.add(2);  l.add(null); l.add(2);  l.add(1);
        s.add(8); s.add(6);  s.add(4); s.add(2);  s.add(1); s.add(2);
        Taboo<Integer> t = new Taboo<Integer>(l);
        t.reduce(s);

        List<Integer> expected = new ArrayList<Integer>();
        expected.add(8); expected.add(6); expected.add(4); expected.add(2); expected.add(2);


        assertEquals(expected,  s);
    }

}
