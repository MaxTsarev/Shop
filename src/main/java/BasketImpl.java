import java.util.Map;

public class BasketImpl implements Basket {

    protected int size;

    protected Purchase[] purchases;

    public BasketImpl(int size) {
        this.size = size;
        purchases = new Purchase[size];
    }

    @Override
    public void addPurchase(String title, int count) {
        addProducts(title, count);
    }

    @Override
    public long sum(Map<String, Integer> prices) {
        return sumProducts(prices);
    }


    public void addProducts(String title, int count) {
        for (int i = 0; i < purchases.length; i++) {
            if (purchases[i] == null) {
                purchases[i] = new Purchase(title, count);
                return;
            }
            if (purchases[i].title.equals(title)) {
                purchases[i].count += count;
                return;
            }
        }
    }

    public long sumProducts(Map<String, Integer> prices) {
        long sum = 0;
        System.out.println("КОРЗИНА:");
        for (int i = 0; i < purchases.length; i++) {
            Purchase purchase = purchases[i];
            if (purchase == null) continue;
            System.out.println("\t" + purchase.title + " " + purchase.count + " шт. в сумме " + (purchase.count * prices.get(purchase.title)) + " руб.");
            sum += (long) purchase.count * prices.get(purchase.title);
        }
        return sum;
    }
}


