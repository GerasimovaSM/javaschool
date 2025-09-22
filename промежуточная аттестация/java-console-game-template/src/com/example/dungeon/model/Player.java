package com.example.dungeon.model;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private int attack;
    private int defense;
    private final List<Item> inventory = new ArrayList<>();

    public Player(String name, int hp, int attack) {
        super(name, hp);
        this.attack = attack;
        this.defense = 1;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public List<Item> getInventoryItems() {
        return inventory;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public Item findItemByName(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void attackMonster(Monster monster) {
        int damage = Math.max(0, attack - monster.getDefense());
        monster.setHp(monster.getHp() - damage);
        System.out.println("Вы бьёте " + monster.getName() + " на " + damage + ". HP монстра: " + monster.getHp());
    }
}