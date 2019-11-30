package io.felux.pouches.registerable;

import io.felux.pouches.Pouches;
import io.felux.pouches.command.util.CommandExecutor;
import io.felux.pouches.command.util.CommandManager;
import io.felux.pouches.util.Lang;

public class CommandRegisterable {
    private static final Pouches POUCHES = Pouches.getInstance();
    private static CommandManager commandManager;

    public static void register() {
        Lang.init(Pouches.getInstance());
        commandManager = new CommandManager(Pouches.getInstance());
        POUCHES.getCommand("pouches").setExecutor(new CommandExecutor(POUCHES));
        if (POUCHES.getCommand("pouches").getPlugin() != POUCHES) {
            POUCHES.getLogger().warning("/pouches command is being handled by plugin other than " + POUCHES.getDescription().getName() + ". You must use /pouches:pouches instead.");
        }
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }
}
