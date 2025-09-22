package com.example.dungeon.model;

import com.example.dungeon.core.InvalidCommandException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Scanner;

public class GameState {
    private Player player;
    private Room current;
    private int score = 0; // Поле для хранения очков

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Room getCurrent() {
        return current;
    }

    public void setCurrent(Room room) {
        this.current = room;
    }

    public int getScore() {
        return score; // Возвращает текущее количество очков
    }

    public void addScore(int points) {
        this.score += points; // Увеличивает количество очков
    }

    /**
     * Команда move: перемещение между комнатами.
     *
     * @param direction Направление (north, south, east, west).
     * @throws InvalidCommandException Если направление недоступно.
     */
    public void move(String direction) throws InvalidCommandException {
        if (direction == null || direction.trim().isEmpty()) {
            throw new InvalidCommandException("Направление не указано.");
        }

        Map<String, Room> neighbors = current.getNeighbors();
        if (neighbors == null) {
            throw new InvalidCommandException("В этой комнате нет выходов.");
        }

        String dirKey = direction.toLowerCase();
        Room nextRoom = neighbors.get(dirKey);

        if (nextRoom == null) {
            throw new InvalidCommandException("Нет пути в направлении " + direction);
        }

        current = nextRoom;
        System.out.println("Вы перешли в: " + current.getName());
        System.out.println(current.describe()); // Выводим описание новой комнаты
    }

    /**
     * Команда take: взять предмет из комнаты и добавить в инвентарь.
     *
     * @param itemName Название предмета.
     */
    public void take(String itemName) {
        Item item = current.getItems().stream()
                .filter(i -> i.getName().trim().equalsIgnoreCase(itemName.trim())) // Игнорируем регистр и пробелы
                .findFirst()
                .orElse(null);

        if (item == null) {
            System.out.println("Предмет " + itemName.trim() + " не найден.");
            return;
        }

        player.addItem(item);
        current.removeItem(item);
        System.out.println("Взято: " + item.getName());
    }

    /**
     * Команда inventory: вывод инвентаря игрока.
     */
    public void showInventory() {
        System.out.println("- Инвентарь:");
        List<Item> inventory = player.getInventoryItems();
        if (inventory.isEmpty()) {
            System.out.println("Инвентарь пуст.");
        } else {
            Map<String, List<Item>> groupedItems = inventory.stream()
                    .collect(Collectors.groupingBy(Item::getType));
            groupedItems.forEach((type, items) -> {
                System.out.println(type + " (" + items.size() + "):");
                items.forEach(item -> System.out.println(" - " + item.getName()));
            });
        }
    }

    /**
     * Команда use: использование предмета из инвентаря.
     *
     * @param itemName Название предмета.
     */
    public void use(String itemName) {
        Item item = player.findItemByName(itemName);
        if (item == null) {
            System.out.println("Предмет " + itemName + " не найден в инвентаре.");
            return;
        }
        item.apply(player);
    }

    /**
     * Команда fight: начало битвы с монстром в текущей комнате.
     */
    public void fight() {
        Monster monster = current.getMonster();
        if (monster == null) {
            System.out.println("В этой комнате нет монстров.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        while (player.getHp() > 0 && monster.getHp() > 0) {
            System.out.println("Ваше HP: " + player.getHp());
            System.out.println("HP монстра: " + monster.getHp());
            System.out.println("1. Атаковать");
            System.out.println("2. Использовать предмет");
            System.out.println("3. Убежать");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    player.attackMonster(monster);
                    if (monster.getHp() > 0) {
                        monster.attackPlayer(player);
                    }
                    break;
                case 2:
                    System.out.println("Введите название предмета:");
                    String itemName = scanner.next();
                    use(itemName);
                    break;
                case 3:
                    System.out.println("Вы убежали!");
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
        if (player.getHp() <= 0) {
            System.out.println("Вы погибли!");
            System.exit(0);
        } else {
            System.out.println("Вы победили " + monster.getName() + "!");
            current.setMonster(null);
        }
    }
}