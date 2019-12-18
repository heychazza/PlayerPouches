package io.felux.pouches.command.util;

import io.felux.pouches.Pouches;
import io.felux.pouches.registerable.CommandRegisterable;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandExecutor implements org.bukkit.command.CommandExecutor, TabCompleter {
    private final Pouches plugin;

    public CommandExecutor(Pouches plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (args.length == 0) return CommandRegisterable.getCommandManager().handle(sender, null, new String[]{});
        else
            return CommandRegisterable.getCommandManager().handle(sender, args[0], Arrays.stream(args).skip(1).toArray(String[]::new));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command bukkitCommand, String alias, String[] args) {
        String command = args[0];
        String[] commandArgs = Arrays.stream(args).skip(1).toArray(String[]::new);

        if (command.equals(""))
            return new ArrayList<String>() {{
                for (Map.Entry<String, Method> command : CommandRegisterable.getCommandManager().getCommands().entrySet())
                    if (sender.hasPermission(command.getValue().getAnnotation(Command.class).permission()))
                        add(command.getKey());
            }};
        if (commandArgs.length == 0)
            return new ArrayList<String>() {{
                for (Map.Entry<String, Method> commandPair : CommandRegisterable.getCommandManager().getCommands().entrySet())
                    if (commandPair.getKey().toLowerCase().startsWith(command.toLowerCase()))
                        if (sender.hasPermission(commandPair.getValue().getAnnotation(Command.class).permission()))
                            add(commandPair.getKey());
            }};
        return null;
    }
}