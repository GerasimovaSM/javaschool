
// Для введенной с клавиатуры буквы английского алфавита нужно вывести слева стоящую букву на стандартной клавиатуре. При этом клавиатура замкнута, т.е. справа от буквы «p» стоит буква «a», а слева от "а" буква "р", также соседними считаются буквы «l» и буква «z», а буква «m» с буквой «q».

package hm5;

import java.util.*;

public class homework5 {
    public static void main(String[] args) {
        char[] алфавит = {
                'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p',
                'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
                'z', 'x', 'c', 'v', 'b', 'n', 'm'
        };

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите букву английского алфавита маленьким регистром: ");
        char inputLetter = scanner.next().charAt(0);

        int index = -1;
        for (int i = 0; i < алфавит.length; i++) {
            if (алфавит[i] == inputLetter) {
                index = i;
                break;
            }
        }


        if (index != -1) {
            char левостоящая;
            if (index == 0) {
                левостоящая = алфавит[алфавит.length - 1];
            } else {
                левостоящая = алфавит[index - 1];
            }
            System.out.println("Слева от буквы '" + inputLetter + "' стоит буква '" + левостоящая + "'.");
        } else {
            System.out.println("Проверьте, что вводите букву английского алфавита и маленьким регистром. Повторите ввод.");
        }

        scanner.close();
    }
}
