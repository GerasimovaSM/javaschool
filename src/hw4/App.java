
// Реализовать класс Телевизор. У класса есть поля, свойства и методы. Проверить работу в классе App, методе main.

package hw4;

import java.util.*;

class телевизор {

    private String модель;
    private int диагональ;
    private int цена;

    public телевизор(String модель, int диагональ, int цена) {
        this.модель = модель;
        this.диагональ = диагональ;
        this.цена = цена;
    }

    public String getМодель() {
        return модель;
    }

    public int getДиагональ() {
        return диагональ;
    }

    public int getЦена() {
        return цена;
    }

    public void ВыводИнфы() {
        System.out.println("Модель: " + модель + ", Диагональ: " + диагональ + " дюймов, Цена: " + цена + " руб.");
    }
}

public class App {
    public static void main(String[] args) {
        Random random = new Random();

        телевизор телевизор1 = new телевизор("Samsung", random.nextInt(30)+6, random.nextInt(50000)+10000);
        телевизор телевизор2 = new телевизор("LG", random.nextInt(30) + 6, random.nextInt(50000) + 10000);
        телевизор телевизор3 = new телевизор("Sony", random.nextInt(30) + 6, random.nextInt(50000) + 10000);

        телевизор1.ВыводИнфы();
        телевизор2.ВыводИнфы();
        телевизор3.ВыводИнфы();
    }
}
