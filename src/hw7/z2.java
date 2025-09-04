package hw7;

import java.util.Arrays;
import java.util.Scanner;

public class z2 {
    public static void main(String[] args) {
        // Создаем объект Scanner для чтения ввода с консоли
        Scanner scanner = new Scanner(System.in);

        // Считываем две строки с консоли
        System.out.print("Введите первую строку (s): ");
        String s = scanner.nextLine();

        System.out.print("Введите вторую строку (t): ");
        String t = scanner.nextLine();

        // Проверяем, являются ли строки анаграммами
        boolean isAnagram = areAnagrams(s, t);

        // Выводим результат
        System.out.println(isAnagram);
    }

    public static boolean areAnagrams(String s, String t) {
        // Если длины строк различаются, они не могут быть анаграммами
        if (s.length() != t.length()) {
            return false;
        }

        // Преобразуем строки в массивы символов
        char[] sArray = s.toLowerCase().toCharArray();
        char[] tArray = t.toLowerCase().toCharArray();

        // Сортируем массивы символов
        Arrays.sort(sArray);
        Arrays.sort(tArray);

        // Сравниваем отсортированные массивы
        return Arrays.equals(sArray, tArray);
    }
}