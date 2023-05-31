import java.util.Map;

public interface Basket {

    void addPurchase(String title, int count);

    long sum(Map<String, Integer> prices);
}
