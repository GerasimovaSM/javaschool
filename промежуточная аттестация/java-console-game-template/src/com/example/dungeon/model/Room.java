package com.example.dungeon.model;

import java.util.*;
import java.util.stream.Collectors;

public class Room {
    private final String name;
    private final String description;
    private final Map<String, Room> neighbors = new HashMap<>();
    private final List<Item> items = new ArrayList<>();
    private Monster monster;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Room> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(String direction, Room room) {
        neighbors.put(direction, room);
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    /**
     * Описание комнаты.
     *
     * @return Полное описание комнаты.
     */
    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": ").append(description).append("\n");
        sb.append("Выходы: ").append(String.join(", ", neighbors.keySet())).append("\n");
        if (!items.isEmpty()) {
            sb.append("Предметы: ").append(items.stream().map(Item::getName).collect(Collectors.joining(", "))).append("\n");
        }
        if (monster != null) {
            sb.append("В комнате монстр: ").append(monster.getName()).append(" (ур. ").append(monster.getLevel()).append(")\n");
        }
        return sb.toString();
    }
}