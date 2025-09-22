package com.example.dungeon.commands;

import com.example.dungeon.core.Command;
import com.example.dungeon.model.GameState;
import java.util.List;

public class InventoryCommand implements Command {
    @Override
    public void execute(GameState ctx, List<String> args) {
        ctx.showInventory();
    }
}