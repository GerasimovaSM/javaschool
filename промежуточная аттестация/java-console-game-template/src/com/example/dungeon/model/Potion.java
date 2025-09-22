package com.example.dungeon.model;

public class Potion extends Item {
    private final int heal;

    public Potion(String name, int heal) {
        super(name, "Potion"); // Указываем тип "Potion"
        this.heal = heal;
    }

    @Override
    public void apply(Player player) {
        player.setHp(player.getHp() + heal);
        System.out.println("Выпито зелье: +" + heal + " HP. Текущее HP: " + player.getHp());
        player.removeItem(this); // Удаляем зелье из инвентаря
    }
}