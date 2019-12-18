package io.felux.pouches.registerable;

import io.felux.pouches.Pouches;
import io.felux.pouches.command.util.CommandExecutor;
import io.felux.pouches.command.util.CommandManager;
import org.bukkit.command.PluginCommand;

public class CommandRegisterable {
    private static final Pouches POUCHES = Pouches.getInstance();
    private static CommandManager commandManager;

    public static void register() {
        commandManager = new CommandManager(Pouches.getInstance());

        PluginCommand pluginCommand = POUCHES.getCommand("io/felux/pouches/nms/v1_8_R3");
        if (pluginCommand == null) {
            throw new RuntimeException("The /pouches command isn't found, contact the developer!");
        }

        pluginCommand.setExecutor(new CommandExecutor(POUCHES));
        if (pluginCommand.getPlugin() != POUCHES) {
            POUCHES.getLogger().warning("/pouches command is being handled by plugin other than " + POUCHES.getDescription().getName() + ". You must use /pouches:pouches instead.");
        }
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }
}
