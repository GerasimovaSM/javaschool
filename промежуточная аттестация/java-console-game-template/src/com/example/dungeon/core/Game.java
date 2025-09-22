package com.example.dungeon.core;

import com.example.dungeon.model.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Game {
    private final GameState state = new GameState();
    private final Map<String, Command> commands = new LinkedHashMap<>();

    static {
        WorldInfo.touch("Game");
    }

    public Game() {
        registerCommands();
        bootstrapWorld();
    }

    private void registerCommands() {
        // Команда help: список всех команд
        commands.put("help", (ctx, a) -> System.out.println("Команды: " + String.join(", ", commands.keySet())));

        // Команда gc-stats: вывод статистики памяти
        commands.put("gc-stats", (ctx, a) -> {
            Runtime rt = Runtime.getRuntime();
            long used = rt.totalMemory() - rt.freeMemory();
            long free = rt.freeMemory();
            long total = rt.totalMemory();
            System.out.println("Память: used=" + used + " free=" + free + " total=" + total);
        });

        // Команда alloc: демонстрация работы GC
        commands.put("alloc", (ctx, a) -> {
            if (a.isEmpty()) {
                throw new InvalidCommandException("Не указано количество объектов.");
            }
            int count = Integer.parseInt(a.get(0));
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                items.add(new Potion("Зелье-" + i, 5));
            }
            System.out.println("Создано " + count + " объектов.");
            items.clear(); // Освобождаем память
            System.gc(); // Вызываем сборщик мусора
            System.out.println("Сборщик мусора вызван.");
        });

        // Команда about: информация о проекте
        commands.put("about", (ctx, a) -> {
            System.out.println("DungeonMini — текстовая RPG игра.");
            System.out.println("Версия: 1.0");
            System.out.println("Автор: Ваше имя или команда");
        });

        // Остальные команды
        commands.put("look", (ctx, a) -> System.out.println(ctx.getCurrent().describe()));
        commands.put("move", (ctx, a) -> {
            if (a.isEmpty()) {
                throw new InvalidCommandException("Не указано направление.");
            }
            ctx.move(a.get(0));
        });
        commands.put("take", (ctx, a) -> {
            if (a.isEmpty()) {
                throw new InvalidCommandException("Не указано название предмета.");
            }
            String itemName = String.join(" ", a); // Объединяем все аргументы в одно название
            ctx.take(itemName);
        });
        commands.put("inventory", (ctx, a) -> ctx.showInventory());
        commands.put("use", (ctx, a) -> {
            if (a.isEmpty()) {
                throw new InvalidCommandException("Не указано название предмета.");
            }
            String itemName = String.join(" ", a); // Объединяем все аргументы в одно название
            ctx.use(itemName);
        });
        commands.put("fight", (ctx, a) -> ctx.fight());
        commands.put("save", (ctx, a) -> SaveLoad.save(ctx));
        commands.put("load", (ctx, a) -> SaveLoad.load(ctx));
        commands.put("scores", (ctx, a) -> SaveLoad.printScores());
        commands.put("exit", (ctx, a) -> {
            System.out.println("Пока!");
            System.exit(0);
        });
    }

    private void bootstrapWorld() {
        Player hero = new Player("Герой", 20, 5);
        state.setPlayer(hero);

        Room square = new Room("Площадь", "Каменная площадь с фонтаном.");
        Room forest = new Room("Лес", "Шелест листвы и птичий щебет.");
        Room cave = new Room("Пещера", "Темно и сыро.");

        square.addNeighbor("north", forest);
        forest.addNeighbor("south", square);
        forest.addNeighbor("east", cave);
        cave.addNeighbor("west", forest);

        forest.getItems().add(new Potion("Малое зелье", 5)); // Добавляем зелье в комнату
        forest.setMonster(new Monster("Волк", 1, 8)); // Добавляем монстра

        state.setCurrent(square);
    }

    public void run() {
        System.out.println("DungeonMini (TEMPLATE). 'help' — команды.");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("> ");
                String line = in.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.isEmpty()) continue;
                List<String> parts = Arrays.asList(line.split("\\s+"));
                String cmd = parts.get(0).toLowerCase(Locale.ROOT);
                List<String> args = parts.subList(1, parts.size());
                Command c = commands.get(cmd);
                try {
                    if (c == null) throw new InvalidCommandException("Неизвестная команда: " + cmd);
                    c.execute(state, args);
                } catch (InvalidCommandException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Непредвиденная ошибка: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
}

    /*
     * Пример ошибки компиляции:
     * Если в коде команды "move" забыть закрыть кавычку:
     *
     * private void exampleCompilationError() {
     *     System.out.println("Hello, world!); // Ошибка: отсутствует закрывающая кавычка.
     * }
     *
     * Компилятор выдаст ошибку: "unclosed string literal".
     */

    /*
     * Пример ошибки выполнения:
     * Если в команде "fight" попытаться разделить на ноль:
     *
     * private void exampleRuntimeError() {
     *     int result = 10 / 0; // Ошибка: ArithmeticException: деление на ноль.
     * }
     *
     * Во время выполнения программы будет выброшено исключение ArithmeticException.
     */
