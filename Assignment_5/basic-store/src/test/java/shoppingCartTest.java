import junit.framework.TestCase;
import org.junit.Test;
import store.Product;
import store.shoppingCart;

import java.util.ArrayList;

public class shoppingCartTest extends TestCase {
    private final String productId = "HC";
    private final String name = "Classic Hoodie";
    private final String imageFile = "Hoodie.jpg";
    private final double price = 40;

    @Test
    public void testAddProduct() {
        shoppingCart shoppingCart = new shoppingCart();
        Product product = new Product(productId, name, imageFile, price);

        shoppingCart.addProduct(product, 1);
        assertTrue(shoppingCart.contains(product));

        shoppingCart.addProduct(product, -1);
        assertFalse(shoppingCart.contains(product));
    }
    @Test
    public void testContains() {
        shoppingCart shoppingCart = new shoppingCart();
        Product product = new Product(productId, name, imageFile, price);

        shoppingCart.addProduct(product, 1);
        assertTrue(shoppingCart.contains(product));
    }
    @Test
    public void testGetProducts() {
        shoppingCart shoppingCart = new shoppingCart();
        Product product = new Product(productId, name, imageFile, price);
        shoppingCart.addProduct(product, 1);

        ArrayList<Product> products = shoppingCart.getProducts();
        assertTrue(products.size() > 0);
    }
    @Test
    public void testGetValue() {
        shoppingCart shoppingCart = new shoppingCart();
        Product product = new Product(productId, name, imageFile, price);

        shoppingCart.addProduct(product, 1);
        assertEquals(shoppingCart.getValue(product), 1);
    }
}