

//Задача 1. Составить программу вывода на экран в одну строку сообщения «Привет, имя_пользователя», где «имя_пользователя» - это введёное в консоль имя, для выполнения данного задания нужно использовать класс Scanner.

package hw3;

import java.util.*;

public class привет {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя пользователя: ");
        String name = scanner.nextLine();
        scanner.close();

        System.out.print("Привет, " +name);

    }
}