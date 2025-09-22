package com.example.dungeon.model;

public class Key extends Item {
    public Key(String name) {
        super(name, "ключ"); // Указываем тип "ключ"
    }

    @Override
    public void apply(Player player) {
        // Логика использования ключа (например, открытие двери)
        System.out.println("Использован ключ: " + getName());
    }
}