# Shop

![logo](https://bugaga.ru/uploads/posts/2018-06/1529686572_obyavleniya-8.jpg)

## Исходный код:

### Основной класс
~~~ java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HashMap<String, Integer> products = new HashMap<>();
        products.put("Хлеб", 56);
        products.put("Масло", 153);
        products.put("Колбаса", 211);
        products.put("Пирожок", 45);

        System.out.println("В МАГАЗИНЕ В НАЛИЧИИ");
        for (Map.Entry<String, Integer> productAndPrice : products.entrySet()) {
            System.out.println(productAndPrice.getKey() + " за " + productAndPrice.getValue() + " руб./шт.");
        }

        System.out.println("Введите два слова: название товара и количество. Или end");
        Scanner scanner = new Scanner(System.in);
        Purchase purchase = new Purchase();
        while (true) {
            String line = scanner.nextLine();
            if ("end".equals(line)) break;
            String[] parts = line.split(" ");
            String product = parts[0];
            int count = Integer.parseInt(parts[1]);
            purchase.addPurchase(product, count);
        }
        long sum = purchase.sum(products);
        System.out.println("ИТОГО: " + sum);
    }
}
~~~

### Вспомогательный класс

~~~ java
import java.util.Map;

public class Purchase {
    protected String title;
    protected int count;
    protected Purchase[] purchases = new Purchase[4];

    public Purchase(String title, int count) {
        this.title = title;
        this.count = count;
    }

    public Purchase() {
    }

    public void addPurchase(String title, int count) {
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

    public long sum(Map<String, Integer> prices) {
        long sum = 0;
        System.out.println("КОРЗИНА:");
        for (int i = 0; i < purchases.length; i++) {
            Purchase purchase = purchases[i];
            if (purchase == null) continue;
            System.out.println("\t" + purchase.title + " " + purchase.count + " шт. в сумме " + (purchase.count * prices.get(purchase.title)) + " руб.");
            sum += purchase.count * prices.get(purchase.title);
        }
        return sum;
    }
}
~~~

### В представленном коде есть нарушение следующих принципов:
### 1. Правило Magic
В поле вспомогательного класса напрямую используется число
~~~ java 
protected Purchase[] purchases = new Purchase[4]; 
~~~   
его нужно заменить на переменную, которая добавляется через конструктор:
~~~ java
    protected int size;

    protected Purchase[] purchases;

    public BasketImpl(int size) {
        this.size = size;
        purchases = new Purchase[size];
    }
~~~
### 2. Single-responsibility principle
Вспомогательный класс выполняет роль корзины, покупки(что нужно положить в корзину). Также добавляет покупку в корзину, считает сумму продуктов.
Данный функционал избыточен, по этому были созданы несколько классов, которые разделяют логику вспомогательного класса.
* Класс BasketImpl выполняет роль корзины с методами добавления покупки и подсчета общей суммы
* Класс Purchase реализует покупку(название продукта и его количество)

Также логика формирования списка прайс-листа была выделена в класс GroceryList
### 3. Dependency inversion principle
Чтобы зависеть от абстракции, а не от имплементации был создан интерфейс Basket c реализацией в классе BasketImpl
~~~ java
import java.util.Map;

public interface Basket {

    void addPurchase(String title, int count);

    long sum(Map<String, Integer> prices);
}
~~~
~~~ java
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
    ...
~~~