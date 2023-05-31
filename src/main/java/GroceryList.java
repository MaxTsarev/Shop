import java.util.HashMap;

public class GroceryList {

    private HashMap<String, Integer> products = new HashMap<>();


    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(String product, int price) {
        products.put(product, price);
    }
}
