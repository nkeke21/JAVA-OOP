import junit.framework.TestCase;

import java.io.*;

public class CrackerTest extends TestCase {
    private static final int THREADS = 40;

    public void testCracker(){

        try {

            BufferedReader br = new BufferedReader(new FileReader("passwords.txt"));

            while (true){
                Cracker.restartStatics();
                String curr = br.readLine();
                if(curr == null) break;

                byte[] hash = Cracker.getHash(curr);
                String hashString = Cracker.hexToString(hash);
                int size = curr.length();

                for(int i = 0; i < 2; i++){

                    byte[] hashByte = Cracker.getHash(curr);
                    String newHash = Cracker.hexToString(hashByte);
                    assertEquals(newHash, hashString);

                    String res =  Cracker.launch(THREADS, size, hashByte);
                    assertEquals(res, curr);

                }
                System.out.println("test passed");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
