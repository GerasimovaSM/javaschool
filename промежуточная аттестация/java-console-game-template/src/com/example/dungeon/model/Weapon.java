package com.example.dungeon.model;

public class Weapon extends Item {
    private final int bonus; // Бонус к атаке

    public Weapon(String name, int bonus) {
        super(name, "оружие"); // Указываем тип "оружие"
        this.bonus = bonus;
    }

    @Override
    public void apply(Player player) {
        player.setAttack(player.getAttack() + bonus); // Увеличиваем атаку игрока
        System.out.println("Оружие экипировано. Атака теперь: " + player.getAttack());
        player.removeItem(this); // Удаляем оружие из инвентаря
    }
}