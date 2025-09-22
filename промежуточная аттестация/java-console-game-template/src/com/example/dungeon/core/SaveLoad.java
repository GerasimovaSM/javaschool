package com.example.dungeon.core;

import com.example.dungeon.model.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SaveLoad {
    private static final Path SAVE = Paths.get("save.txt");
    private static final Path SCORES = Paths.get("scores.csv");

    /**
     * Сохранение состояния игры.
     *
     * @param state Состояние игры.
     */
    public static void save(GameState state) {
        try (BufferedWriter writer = Files.newBufferedWriter(SAVE)) {
            // Сохраняем данные игрока
            Player player = state.getPlayer();
            writer.write("player;" + player.getName() + ";" + player.getHp() + ";" + player.getAttack());
            writer.newLine();

            // Сохраняем инвентарь
            List<Item> inventory = player.getInventoryItems();
            String inventoryData = inventory.stream()
                    .map(item -> item.getClass().getSimpleName() + ":" + item.getName())
                    .collect(Collectors.joining(",")); // Используем Collectors.joining
            writer.write("inventory;" + inventoryData);
            writer.newLine();

            // Сохраняем текущую комнату
            writer.write("room;" + state.getCurrent().getName());
            writer.newLine();

            System.out.println("Сохранено в " + SAVE.toAbsolutePath());
            writeScore(player.getName(), state.getScore()); // Используем метод getScore
        } catch (IOException e) {
            throw new UncheckedIOException("Не удалось сохранить игру", e);
        }
    }

    /**
     * Загрузка состояния игры.
     *
     * @param state Состояние игры.
     */
    public static void load(GameState state) {
        if (!Files.exists(SAVE)) {
            System.out.println("Сохранение не найдено.");
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(SAVE)) {
            Map<String, String> data = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Удаляем пробелы и невидимые символы
                if (line.isEmpty()) continue; // Пропускаем пустые строки

                // Разделяем строку на ключ и значение
                String[] parts = line.split(";", 2);
                if (parts.length != 2) {
                    System.out.println("Ошибка: некорректная строка в файле сохранения: " + line);
                    continue;
                }

                String key = parts[0].trim();
                String value = parts[1].trim();
                data.put(key, value);
            }

            // Восстановление игрока
            Player player = state.getPlayer();
            String playerData = data.get("player");
            if (playerData == null || playerData.isBlank()) {
                System.out.println("Ошибка: данные игрока отсутствуют в файле сохранения.");
                return;
            }

            String[] playerFields = playerData.split(";");
            if (playerFields.length != 3) {
                System.out.println("Ошибка: некорректные данные игрока в файле сохранения. Ожидается 3 поля, получено: " + playerFields.length);
                return;
            }

            try {
                player.setName(playerFields[0].trim()); // Имя
                player.setHp(Integer.parseInt(playerFields[1].trim())); // Здоровье
                player.setAttack(Integer.parseInt(playerFields[2].trim())); // Атака
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: некорректные числовые данные игрока.");
                return;
            }

            // Восстановление инвентаря
            player.getInventoryItems().clear();
            String inventoryData = data.get("inventory");
            if (inventoryData != null && !inventoryData.isBlank()) {
                for (String itemData : inventoryData.split(",")) {
                    String[] parts = itemData.trim().split(":");
                    if (parts.length < 2) continue;
                    switch (parts[0].trim()) {
                        case "Potion" -> player.addItem(new Potion(parts[1].trim(), 5));
                        case "Key" -> player.addItem(new Key(parts[1].trim()));
                        case "Weapon" -> player.addItem(new Weapon(parts[1].trim(), 3));
                        default -> {}
                    }
                }
            }

            // Восстановление текущей комнаты
            String roomName = data.get("room");
            if (roomName != null && !roomName.isBlank()) {
                Room currentRoom = state.getCurrent();
                if (currentRoom != null && currentRoom.getName().equals(roomName.trim())) {
                    System.out.println("Игра загружена.");
                } else {
                    System.out.println("Ошибка: комната " + roomName.trim() + " не найдена.");
                }
            } else {
                System.out.println("Ошибка: данные комнаты отсутствуют в файле сохранения.");
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Не удалось загрузить игру", e);
        }
    }

    /**
     * Получение очков из состояния игры (заглушка).
     *
     * @param state Состояние игры.
     * @return Количество очков.
     */
    private static int getScore(GameState state) {
        return state.getScore();
    }

    /**
     * Вывод таблицы рекордов.
     */
    public static void printScores() {
        if (!Files.exists(SCORES)) {
            System.out.println("Пока нет результатов.");
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(SCORES)) {
            System.out.println("Таблица лидеров (топ-10):");
            reader.lines().skip(1).map(l -> l.split(","))
                    .map(a -> new Score(a[1], Integer.parseInt(a[2])))
                    .sorted(Comparator.comparingInt(Score::score).reversed())
                    .limit(10)
                    .forEach(s -> System.out.println(s.player() + " — " + s.score()));
        } catch (IOException e) {
            System.err.println("Ошибка чтения результатов: " + e.getMessage());
        }
    }

    /**
     * Запись очков в файл.
     *
     * @param player Имя игрока.
     * @param score  Количество очков.
     */
    private static void writeScore(String player, int score) {
        try (BufferedWriter writer = Files.newBufferedWriter(SCORES, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            boolean header = !Files.exists(SCORES);
            if (header) {
                writer.write("ts,player,score");
                writer.newLine();
            }
            writer.write(LocalDateTime.now() + "," + player + "," + score);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Не удалось записать очки: " + e.getMessage());
        }
    }

    private record Score(String player, int score) {}
}