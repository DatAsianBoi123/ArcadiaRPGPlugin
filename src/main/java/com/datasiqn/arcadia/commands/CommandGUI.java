package com.datasiqn.arcadia.commands;

import com.datasiqn.arcadia.Arcadia;
import com.datasiqn.arcadia.ArcadiaPermission;
import com.datasiqn.arcadia.commands.arguments.ArcadiaArgumentType;
import com.datasiqn.commandcore.commands.builder.ArgumentBuilder;
import com.datasiqn.commandcore.commands.builder.CommandBuilder;

public class CommandGUI {
    private final Arcadia plugin;

    public CommandGUI(Arcadia plugin) {
        this.plugin = plugin;
    }

    public CommandBuilder getCommand() {
        return new CommandBuilder()
                .permission(ArcadiaPermission.PERMISSION_USE_GUI)
                .description("Opens a custom Arcadia GUI")
                .then(ArgumentBuilder.argument(ArcadiaArgumentType.GUI, "gui")
                        .requiresPlayer()
                        .executes(context -> context.getSource().getPlayer().unwrap().openInventory(context.getArguments().get(0, ArcadiaArgumentType.GUI).unwrap().createInventory(plugin))));
    }
}
