package hw7;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class z1 {

    public static <T> Set<T> getUniqueElements(ArrayList<T> list) {
           return new HashSet<>(list);
    }

    public static void main(String[] args) {
        // Пример использования
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(2);
        numbers.add(1);

        Set<Integer> uniqueNumbers = getUniqueElements(numbers);
        System.out.println(uniqueNumbers); // Вывод: [1, 2, 3]
    }
}