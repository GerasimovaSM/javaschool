package com.example.dungeon.commands;

import com.example.dungeon.core.Command;
import com.example.dungeon.core.InvalidCommandException;
import com.example.dungeon.model.GameState;
import java.util.List;

public class MoveCommand implements Command {
    @Override
    public void execute(GameState ctx, List<String> args) {
        if (args.size() < 1) {
            throw new InvalidCommandException("Не указано направление.");
        }
        String direction = args.get(0);
        ctx.move(direction);
    }
}