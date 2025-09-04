package hw7;

import java.util.HashSet;
import java.util.Set;

public class PowerfulSet {

    // Метод для нахождения пересечения двух наборов
    public <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1); // Создаем копию set1
        result.retainAll(set2); // Оставляем только элементы, которые есть в set2
        return result;
    }

    // Метод для нахождения объединения двух наборов
    public <T> Set<T> union(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1); // Создаем копию set1
        result.addAll(set2); // Добавляем все элементы из set2
        return result;
    }

    // Метод для нахождения относительного дополнения первого набора относительно второго
    public <T> Set<T> relativeComplement(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1); // Создаем копию set1
        result.removeAll(set2); // Удаляем все элементы, которые есть в set2
        return result;
    }

    // Пример использования
    public static void main(String[] args) {
        PowerfulSet powerfulSet = new PowerfulSet();

        Set<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        Set<Integer> set2 = new HashSet<>();
        set2.add(0);
        set2.add(1);
        set2.add(2);
        set2.add(4);

        System.out.println("Intersection: " + powerfulSet.intersection(set1, set2));
        System.out.println("Union: " + powerfulSet.union(set1, set2));
        System.out.println("Relative Complement: " + powerfulSet.relativeComplement(set1, set2));
    }
}