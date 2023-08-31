import junit.framework.TestCase;
import org.junit.Test;
import store.Product;
import store.myDatabse;

import java.util.ArrayList;

public class myDatabseTest extends TestCase {

    @Test
    public void testGetProducts() {
        myDatabse data = new myDatabse();
        ArrayList<Product> products = data.getProducts();
        assertTrue(products.size() > 0);
        assertTrue(products != null);
    }
}
