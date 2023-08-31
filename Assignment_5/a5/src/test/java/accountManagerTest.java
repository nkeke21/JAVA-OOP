import Dao.accountManager;
import junit.framework.TestCase;
import org.junit.Test;

public class accountManagerTest extends TestCase {
    private final String user = "user";
    private final String password = "passowrd";

    @Test
    public void testGetInstance() {
        accountManager newAccount = accountManager.getInstance();
        assert(newAccount != null);
    }

    @Test
    public void testAdd() {
        accountManager account = new accountManager();
        account.add(user, password);
    }

    @Test
    public void testContainsTest() {
        accountManager account = new accountManager();
        account.add(user, password);
        assertEquals(account.contains(user), true);
    }
    @Test
    public void testCheckPasswordTest() {
        accountManager accountManager = new accountManager();
        accountManager.add(user, password);
        assertTrue(accountManager.checkPassword(user, password));
    }
}