package hw8;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Класс Автомобиль
class Автомобиль {
    private String номерАвтомобиля;
    private String модель;
    private String цвет;
    private int пробег;
    private double стоимость;

    public Автомобиль(String номерАвтомобиля, String модель, String цвет, int пробег, double стоимость) {
        this.номерАвтомобиля = номерАвтомобиля;
        this.модель = модель;
        this.цвет = цвет;
        this.пробег = пробег;
        this.стоимость = стоимость;
    }

    public String getНомерАвтомобиля() {
        return номерАвтомобиля;
    }

    public String getМодель() {
        return модель;
    }

    public String getЦвет() {
        return цвет;
    }

    public int getПробег() {
        return пробег;
    }

    public double getСтоимость() {
        return стоимость;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s\t%d\t%.2f", номерАвтомобиля, модель, цвет, пробег, стоимость);
    }
}

// Основной класс Main
public class Main {
    public static void main(String[] args) {
        // Форматирование для денежных значений
        DecimalFormat df = new DecimalFormat("#,##0.00");

        // Создание списка автомобилей
        List<Автомобиль> автомобили = new ArrayList<>();
        автомобили.add(new Автомобиль("a123me", "Mercedes", "White", 0, 8300000));
        автомобили.add(new Автомобиль("b873of", "Volga", "Black", 0, 673000));
        автомобили.add(new Автомобиль("w487mn", "Lexus", "Grey", 76000, 900000));
        автомобили.add(new Автомобиль("p987hj", "Volga", "Red", 610, 704340));
        автомобили.add(new Автомобиль("c987ss", "Toyota", "White", 254000, 761000));
        автомобили.add(new Автомобиль("o983op", "Toyota", "Black", 698000, 740000));
        автомобили.add(new Автомобиль("p146op", "BMW", "White", 271000, 850000));
        автомобили.add(new Автомобиль("u893ii", "Toyota", "Purple", 210900, 440000));
        автомобили.add(new Автомобиль("l097df", "Toyota", "Black", 108000, 780000));
        автомобили.add(new Автомобиль("y876wd", "Toyota", "Black", 160000, 1000000));

        // Вывод списка автомобилей
        System.out.println("Автомобили в базе:");
        System.out.println("Number\tModel\tColor\tMileage\tCost");
        автомобили.forEach(System.out::println);

        // Заданные параметры для поиска
        String colorToFind = "Black";
        int mileageToFind = 0;
        int n = 700000;
        int m = 800000;
        String modelToFind = "Toyota";
        String модельДляСреднегоЗначения = "Volvo";

        // 1) Номера автомобилей с заданным цветом или нулевым пробегом
        List<String> номераАвтомобилей = автомобили.stream()
                .filter(авто -> авто.getЦвет().equals(colorToFind) || авто.getПробег() == mileageToFind)
                .map(Автомобиль::getНомерАвтомобиля)
                .collect(Collectors.toList());
        System.out.println("Номера автомобилей по цвету или пробегу: " + String.join(" ", номераАвтомобилей));

        // 2) Количество уникальных автомобилей в ценовом диапазоне от n до m тыс.
        long количествоУникальныхАвтомобилей = автомобили.stream()
                .filter(авто -> авто.getСтоимость() >= n && авто.getСтоимость() <= m)
                .count();
        System.out.println("Уникальные автомобили: " + количествоУникальныхАвтомобилей + " шт.");

        // 3) Цвет автомобиля с минимальной стоимостью
        String цветСМинимальнойСтоимостью = автомобили.stream()
                .min((авто1, авто2) -> Double.compare(авто1.getСтоимость(), авто2.getСтоимость()))
                .map(Автомобиль::getЦвет)
                .orElse("Нет данных");
        System.out.println("Цвет автомобиля с минимальной стоимостью: " + цветСМинимальнойСтоимостью);

        // 4) Средняя стоимость искомой модели
        double средняяСтоимость = автомобили.stream()
                .filter(авто -> авто.getМодель().equals(modelToFind))
                .mapToDouble(Автомобиль::getСтоимость)
                .average()
                .orElse(0);
        System.out.printf("Средняя стоимость модели %s: %.2f%n", modelToFind, средняяСтоимость);

        // Дополнительная проверка: Средняя стоимость модели "Volvo"
        double средняяСтоимостьДляVolvo = автомобили.stream()
                .filter(авто -> авто.getМодель().equals(модельДляСреднегоЗначения))
                .mapToDouble(Автомобиль::getСтоимость)
                .average()
                .orElse(0);
        System.out.printf("Средняя стоимость модели %s: %.2f%n", модельДляСреднегоЗначения, средняяСтоимостьДляVolvo);
    }
}
