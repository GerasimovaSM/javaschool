//Необходимо реализовать приложение, принимающее список пользователей, продуктов и обрабатывающее покупку пользователя.
//
//Подробное описание функционала приложения
//1. Создать классы Покупатель (Person) и Продукт (Product).
//Характеристики Покупателя: имя, сумма денег и пакет с продуктами (массив объектов типа Продукт). Имя не может быть пустой строкой и не может быть короче 3 символов. Деньги не могут быть отрицательным числом.
//Если Покупатель может позволить себе Продукт, то Продукт добавляется в пакет. Если у Покупателя недостаточно денег, то добавление не происходит.
//Характеристики Продукта: название и стоимость. Название продукта не может быть пустой строкой, оно должно быть. Стоимость продукта не может быть отрицательным числом.
//2. Поля в классах должны быть private, доступ к полям осуществляется через геттеры и сеттеры или конструктор класса.
//3. В классах переопределены методы toString(), equals(), hashcode().
//4. Создать в классе App метод main и проверить работу приложения. Данные Покупателей и Продукты вводятся с клавиатуры, для считывания данных потребуется использовать класс Scanner. Продукты в цикле выбираются покупателями по очереди и, пока не введено слово END, наполняется пакет.
//5. Обработать следующие ситуации:
//а. Если покупатель не может позволить себе продукт, то напечатайте соответствующее сообщение ("[Имя человека] не может позволить себе [Название продукта]").
//б. Если ничего не куплено, выведите имя человека, за которым следует "Ничего не куплено".
//в. В случае неверного ввода - сообщение: "Деньги не могут быть отрицательными", пустого имени - сообщение: "Имя не может быть пустым" или длина имени менее 3 символов – сообщение: "Имя не может быть короче 3 символов".


package attestation1;

import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод покупателей
        Person[] people = new Person[10]; // Максимум 10 покупателей
        int personCount = 0;

        System.out.println("Ввод покупателей и их бюджета:");
        while (true) {
            System.out.print("Введите Имя покупателя или END для завершения формирования списка: ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("END")) {
                break;
            }

            System.out.print("Введите сумму денег у покупателя: ");
            double money = Double.parseDouble(scanner.nextLine());

            try {
                Person person = new Person(name, money);
                people[personCount] = person;
                personCount++;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Ввод продуктов
        Product[] products = new Product[10]; // Максимум 10 продуктов
        int productCount = 0;

        System.out.println("Ввод продуктов и их стоимости:");
        while (true) {
            System.out.print("Введите название продукта или END для завершения формирования списка: ");
            String productName = scanner.nextLine();
            if (productName.equalsIgnoreCase("END")) {
                break;
            }

            System.out.print("Введите стоимость продукта: ");
            double productCost = Double.parseDouble(scanner.nextLine());

            try {
                Product product = new Product(productName, productCost);
                products[productCount] = product;
                productCount++;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Обработка покупок
        System.out.println("Обработка покупок:");
        while (true) {
            System.out.print("Введите Имя покупателя и продукт, который он хочет купить (в формате Имя покупателя-Продукт), или END для завершения обработки: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("END")) {
                break;
            }

            // Разделение ввода на имя и продукт
            String[] parts = input.split("-");
            if (parts.length != 2) {
                System.out.println("Неверный формат ввода. Используйте формат 'Имя покупателя-Продукт'.");
                continue;
            }

            String buyerName = parts[0].trim();
            String productName = parts[1].trim();

            // Поиск покупателя
            Person buyer = null;
            for (int i = 0; i < personCount; i++) {
                if (people[i].getName().equalsIgnoreCase(buyerName)) {
                    buyer = people[i];
                    break;
                }
            }

            if (buyer == null) {
                System.out.println("Покупатель с именем " + buyerName + " не найден.");
                continue;
            }

            // Поиск продукта
            Product productToBuy = null;
            for (int i = 0; i < productCount; i++) {
                if (products[i].getName().equalsIgnoreCase(productName)) {
                    productToBuy = products[i];
                    break;
                }
            }

            if (productToBuy == null) {
                System.out.println("Продукт с названием " + productName + " не найден.");
                continue;
            }

            // Покупка
            if (buyer.canAfford(productToBuy)) {
                buyer.addProduct(productToBuy);
                System.out.println(buyer.getName() + " купил(а) " + productToBuy.getName());
            } else {
                System.out.println(buyer.getName() + " не может позволить себе " + productToBuy.getName());
            }
        }

        // Вывод списка покупок
        System.out.println("Список покупок:");
        for (int i = 0; i < personCount; i++) {
            Person person = people[i];
            StringBuilder purchaseInfo = new StringBuilder(person.getName() + " - ");
            if (person.getProductCount() > 0) {
                for (int j = 0; j < person.getProductCount(); j++) {
                    Product product = person.getProducts()[j];
                    purchaseInfo.append(product.getName()).append(", ");
                }
                // Удаляем последнюю запятую и пробел
                purchaseInfo.setLength(purchaseInfo.length() - 2);
            } else {
                purchaseInfo.append("Ничего не куплено");
            }
            System.out.println(purchaseInfo);
        }

        scanner.close();
    }
}

class Person {
    private String name;
    private double money;
    private Product[] products;
    private int productCount;

    public Person(String name, double money) {
        setName(name);
        setMoney(money);
        this.products = new Product[10]; // Максимум 10 продуктов
        this.productCount = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Имя не может быть короче 3 символов");
        }
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Деньги не могут быть отрицательными");
        }
        this.money = money;
    }

    public boolean canAfford(Product product) {
        return money >= product.getCost();
    }

    public void addProduct(Product product) {
        if (productCount < products.length) {
            products[productCount] = product;
            productCount++;
            money -= product.getCost();
        }
    }

    public Product[] getProducts() {
        return products;
    }

    public int getProductCount() {
        return productCount;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Person{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", products=[");
        for (int i = 0; i < productCount; i++) {
            result.append(products[i]).append(", ");
        }
        result.append("]}");
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (Double.compare(person.money, money) != 0) return false;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(money);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

class Product {
    private String name;
    private double cost;

    public Product(String name, double cost) {
        setName(name);
        setCost(cost);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Стоимость продукта не может быть отрицательной");
        }
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Double.compare(product.cost, cost) != 0) return false;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
