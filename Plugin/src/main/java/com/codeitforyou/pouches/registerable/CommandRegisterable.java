package com.codeitforyou.pouches.registerable;

import com.codeitforyou.lib.api.command.CommandManager;
import com.codeitforyou.pouches.Pouches;
import com.codeitforyou.pouches.command.*;
import com.codeitforyou.pouches.util.Lang;

import java.util.Arrays;

public class CommandRegisterable {
    private static final Pouches POUCHES = Pouches.getInstance();
    private static CommandManager commandManager;

    public static void register() {
        commandManager = new CommandManager(
                Arrays.asList(
                        HelpCommand.class,
                        GiveCommand.class,
                        GiveAllCommand.class,
                        ReloadCommand.class,
                        CreateCommand.class,
                        ListCommand.class
                ), POUCHES.getDescription().getName().toLowerCase(), POUCHES);

        commandManager.setMainCommand(MainCommand.class);
        CommandManager.Locale locale = commandManager.getLocale();

        locale.setUnknownCommand(Lang.ERROR_INVALID_COMMAND.asString(Lang.PREFIX.asString()));
        locale.setPlayerOnly(Lang.ERROR_PLAYER_ONLY.asString(Lang.PREFIX.asString()));
        locale.setUsage(Lang.COMMAND_USAGE.asString(Lang.PREFIX.asString(), "{usage}"));
        locale.setNoPermission(Lang.ERROR_NO_PERMISSION_COMMAND.asString(Lang.PREFIX.asString()));

        Arrays.asList("pouch", "pouches").forEach(commandManager::addAlias);
        commandManager.register();
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }
}
