package com.example.dungeon.model;

public class Monster extends Entity {
    private final int level;
    private final int defense;

    public Monster(String name, int level, int hp) {
        super(name, hp);
        this.level = level;
        this.defense = level;
    }

    public int getLevel() {
        return level;
    }

    public int getDefense() {
        return defense;
    }

    public void attackPlayer(Player player) {
        int damage = Math.max(0, level - player.getDefense());
        player.setHp(player.getHp() - damage);
        System.out.println("Монстр отвечает на " + damage + ". Ваше HP: " + player.getHp());
    }
}