import junit.framework.TestCase;

public class BankTest extends TestCase {
    private static final int MAX_THREAD = 50;
    private static final int ONE_TRHEAD = 1;
    private static final String SMALL = "small.txt";
    private static final String K_5 = "5k.txt";
    private static final String K_100 = "100k.txt";

    public void testBankSmall(){
        Bank result_small = new Bank();
        result_small.processFile(SMALL, ONE_TRHEAD);
        String resultStr = result_small.getResultStr();

        for (int i = 1; i < MAX_THREAD; i ++){
            Bank tmp = new Bank();
            tmp.processFile(SMALL, i);
            assertEquals(resultStr, tmp.getResultStr());
        }

        System.out.println("Small test passed!!!");
    }

    public void testBank5K(){
        Bank result_5K = new Bank();
        result_5K.processFile(K_5, ONE_TRHEAD);
        String resultStr = result_5K.getResultStr();

        for (int i = 1; i < MAX_THREAD; i ++){
            Bank tmp = new Bank();
            tmp.processFile(K_5, i);
            assertEquals(resultStr, tmp.getResultStr());
        }

        System.out.println("5K test passed!!!");
    }

    public void testBank100K(){
        Bank result_100K = new Bank();
        result_100K.processFile(K_100, ONE_TRHEAD);
        String resultStr = result_100K.getResultStr();

        for (int i = 1; i < MAX_THREAD; i ++){
            Bank tmp = new Bank();
            tmp.processFile(K_100, i);
            assertEquals(resultStr, tmp.getResultStr());
        }

        System.out.println("100K test passed!!!");
    }
}
