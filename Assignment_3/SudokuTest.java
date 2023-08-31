import java.util.*;
import junit.framework.TestCase;

public class SudokuTest extends TestCase{
    public void testSudoku(){
        System.out.println("test1");
        String input = "";
        for(int i = 0; i < 9; i ++){
            for(int j = 0; j < 9; j ++){
                if(j == 8) input += "0\n";
                else input += "0 ";
            }
        }
        Sudoku sudoku = new Sudoku(input);

        int solutions = sudoku.solve();
        assertEquals(solutions, 100);

        System.out.println("solution Text: " + sudoku.getSolutionText());
        System.out.println("Elapsed Time: " + sudoku.getElapsed());
    }

    public void testSudoku1(){
        System.out.println("test2");
        int[][] testGrid = Sudoku.stringsToGrid(
                "1 0 0 0 0 0 0 0 0",
                      "0 2 0 0 0 0 0 0 0",
                      "0 0 3 0 0 0 0 0 0",
                      "0 0 0 4 0 0 0 0 0",
                      "0 0 0 0 5 0 0 0 0",
                      "0 0 0 0 0 6 0 0 0",
                      "0 0 0 0 0 0 7 0 0",
                      "0 0 0 0 0 0 0 8 0",
                      "0 0 0 0 0 0 0 0 9");

        Sudoku sudoku = new Sudoku(testGrid);
        int solutions = sudoku.solve();
        assertEquals(100, solutions);

        System.out.println("solution Text: " + sudoku.getSolutionText());
        System.out.println("Elapsed Time: " + sudoku.getElapsed());
    }

    public void testSudoku2(){
        System.out.println("test3");
        int[][] testGrid = Sudoku.stringsToGrid(
                "1 0 0 0 7 0 0 0 0",
                      "0 0 6 0 1 0 0 8 0",
                      "0 0 0 0 3 0 0 0 0",
                      "0 0 4 0 0 0 2 9 1",
                      "3 6 7 9 0 1 0 5 0",
                      "0 0 0 0 0 0 6 7 0",
                      "0 0 5 0 8 0 0 0 6",
                      "2 3 0 1 0 0 0 0 0",
                      "0 0 0 0 0 5 0 0 2");
        Sudoku sudoku = new Sudoku(testGrid);

        int solutions = sudoku.solve();
        System.out.println(solutions);
        assertEquals(solutions, 49);

        System.out.println("solution Text: " + sudoku.getSolutionText());
        System.out.println("Elapsed Time: " + sudoku.getElapsed());
    }

    public void testSudoku3(){
        System.out.println("test4");
        int[][] testGrid = Sudoku.stringsToGrid(
                "1 2 3 4 5 6 7 8 0",
                "0 0 0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0 0 0");
        Sudoku sudoku = new Sudoku(testGrid);

        int solutions = sudoku.solve();
        System.out.println(solutions);
        assertEquals(solutions, 100);

        System.out.println("solution Text: " + sudoku.getSolutionText());
        System.out.println("Elapsed Time: " + sudoku.getElapsed());
    }

    public void testMain(){
        System.out.println("main");
        Sudoku.main(null);
    }

    public void testTextToGridRuntimeExceptionMessage() {
        String invalidString = "1 2 3a 4";
        try {
            Sudoku sudoku = new Sudoku(Sudoku.textToGrid(invalidString));
        } catch (RuntimeException e) {
            assertEquals("Could get :" + invalidString, e.getMessage());
        }
    }
}
