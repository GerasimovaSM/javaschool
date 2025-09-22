package com.example.dungeon.core;

import com.example.dungeon.commands.*;

public class CommandFactory {
    public static Command createCommand(String commandName) {
        switch (commandName.toLowerCase()) {
            case "move":
                return new MoveCommand();
            case "take":
                return new TakeCommand();
            case "inventory":
                return new InventoryCommand();
            case "use":
                return new UseCommand();
            case "fight":
                return new FightCommand();
            default:
                throw new InvalidCommandException("Неизвестная команда.");
        }
    }
}