import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        GroceryList groceryList = new GroceryList();
        groceryList.setProducts("Хлеб", 56);
        groceryList.setProducts("Масло", 153);
        groceryList.setProducts("Колбаса", 211);
        groceryList.setProducts("Пирожок", 45);

        Basket basket = new BasketImpl(groceryList.getProducts().size());

        System.out.println("В МАГАЗИНЕ В НАЛИЧИИ");
        for (Map.Entry<String, Integer> productAndPrice : groceryList.getProducts().entrySet()) {
            System.out.println(productAndPrice.getKey() + " за " + productAndPrice.getValue() + " руб./шт.");
        }


        System.out.println("Введите два слова: название товара и количество. Или end");
        Scanner scanner = new Scanner(System.in);


        while (true) {
            String line = scanner.nextLine();
            if ("end".equals(line)) break;
            String[] parts = line.split(" ");
            String product = parts[0];
            int count = Integer.parseInt(parts[1]);
            basket.addPurchase(product, count);
        }
        System.out.println("ИТОГО: " + basket.sum(groceryList.getProducts()));
    }
}

