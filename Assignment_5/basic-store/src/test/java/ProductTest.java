import junit.framework.TestCase;
import org.junit.Test;
import store.Product;

public class ProductTest extends TestCase {
    private final String productId = "HC";
    private final String name = "Classic Hoodie";
    private final String imageFile = "Hoodie.jpg";
    private final double price = 40;

    @Test
    public void testGetProduct_id() {
        Product product = new Product(productId, name, imageFile, price);
        assertEquals(product.getProduct_id(), productId);
    }
    @Test
    public void testGetProduct_name() {
        Product product = new Product(productId, name, imageFile, price);
        assertEquals(product.getProduct_name(), name);
    }
    @Test
    public void testGetProduct_image() {
        Product product = new Product(productId, name, imageFile, price);
        assertEquals(product.getProduct_image(), imageFile);
    }
    @Test
    public void testGetProduct_price() {
        Product product = new Product(productId, name, imageFile, price);
        assertEquals(product.getProduct_price(), price);
    }
    @Test
    public void testSetProduct_id() {
        Product product = new Product();
        product.setProduct_id(productId);
        assertEquals(product.getProduct_id(), productId);
    }
    @Test
    public void testSetProduct_name() {
        Product product = new Product();
        product.setProduct_name(name);
        assertEquals(product.getProduct_name(), name);
    }
    @Test
    public void testSetProduct_image() {
        Product product = new Product();
        product.setProduct_image(imageFile);
        assertEquals(product.getProduct_image(), imageFile);
    }
    @Test
    public void testSetProduct_price() {
        Product product = new Product();
        product.setProduct_price(price);
        assertEquals(product.getProduct_price(), price);
    }
    @Test
    public void testTestEquals() {
        Product first_product = new Product(productId, name, imageFile, price);
        Product second_product = new Product(productId, name, imageFile, price);
        assertTrue(first_product.equals(second_product));
    }
    @Test
    public void testTestHashCode() {
        Product product = new Product(productId, name, imageFile, price);
        int hashCode = product.hashCode();
    }
}