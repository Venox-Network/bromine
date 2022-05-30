package network.venox.bromine;

import network.venox.bromine.commands.*;
import network.venox.bromine.commands.tab.*;
import network.venox.bromine.listeners.*;
import network.venox.bromine.managers.ConfigManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public class Main extends JavaPlugin {
    public static Main plugin;
    public static YamlConfiguration config;
    public static YamlConfiguration messages;

    /**
     * Everything done on plugin start
     */
    @Override
    public void onEnable() {
        plugin = this;

        new ConfigManager().loadFiles();

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

        // Register commands
        registerCommand("chattitle", new ChatTitleCommand(), null);
        registerCommand("brreload", new ReloadCommand(), new EmptyTab());
        registerCommand("reset", new ResetCommand(), null);
    }

    /**
     * Registers a command
     *
     * @param   name    The name of the command
     * @param   cmd     The class of the command
     * @param   tab     The class of the tab completer
     */
    private void registerCommand(String name, CommandExecutor cmd, TabCompleter tab) {
        final PluginCommand command = Bukkit.getPluginCommand(name);

        if (command == null) {
            getLogger().warning("Could not register /" + name);
            return;
        }

        command.setExecutor(cmd);
        command.setTabCompleter(tab);
    }

    /**
     * Checks if a player has a permission
     *
     * @param   sender      The sender
     * @param   permission  The permission to check for
     * @return              True if the player has the permission
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean hasPermission(CommandSender sender, String permission) {
        if (sender.hasPermission("bromine." + permission)) return true;

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.config.getString("no-permission"))));
        return false;
    }
}
