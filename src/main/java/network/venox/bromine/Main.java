package network.venox.bromine;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;

import network.venox.bromine.commands.*;
import network.venox.bromine.commands.tab.*;
import network.venox.bromine.listeners.*;
import network.venox.bromine.managers.FileManager;
import network.venox.bromine.managers.MessageManager;

import org.apache.commons.io.FileUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public class Main extends JavaPlugin {
    public static Main plugin;
    public static MVWorldManager worldManager;
    public static YamlConfiguration config;
    public static YamlConfiguration messages;
    public static YamlConfiguration data;

    /**
     * Everything done on plugin start
     */
    @Override
    public void onEnable() {
        // Main.plugin
        plugin = this;

        // Load files
        new FileManager().loadFiles();

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

        // Register commands
        registerCommand("chattitle", new ChatTitleCommand(), null);
        registerCommand("brreload", new ReloadCommand(), new EmptyTab());

        // Plugin specifics
        final MultiverseCore multiverse = (MultiverseCore) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        if (multiverse != null) {
            worldManager = multiverse.getMVWorldManager();
            registerCommand("reset", new ResetCommand(), null);
        }

        if (data.getBoolean("reset")) {
            getLogger().info(ChatColor.GREEN + "Please disregard any world warnings you may get!");

            // Delete world
            try {
                FileUtils.deleteDirectory(new File("world"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Set reset
            data.set("reset", false);
            new FileManager().save("data", data);
        }
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

        new MessageManager("plugin.no-permission")
                .replace("%permission%", permission)
                .send(sender);
        return false;
    }
}
