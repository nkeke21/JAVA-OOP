package store;

public class Product{
    private String product_id;
    private String product_name;
    private String product_image;
    private double product_price;

    public Product(String product_id, String product_name, String product_image, double product_price) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_price = product_price;
    }

    public Product(){}

    // Getters
    public String getProduct_id() {
        return product_id;
    }
    public String getProduct_name() {
        return product_name;
    }
    public String getProduct_image() {
        return product_image;
    }
    public double getProduct_price() {
        return product_price;
    }

    // Setters
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    @Override
    public boolean equals(Object obj) {
        Product p = (Product) obj;
        return p.getProduct_id().equals(product_id);
    }
    @Override
    public int hashCode() {
        return product_id.hashCode();
    }


}
