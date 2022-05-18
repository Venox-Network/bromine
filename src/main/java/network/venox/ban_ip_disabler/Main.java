package network.venox.ban_ip_disabler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Main extends JavaPlugin implements Listener, CommandExecutor {
    private YamlConfiguration config;

    /**
     * Everything done on plugin start
     */
    @Override
    public void onEnable() {
        config();
        Bukkit.getPluginManager().registerEvents(this, this);

        // Register bipreload command
        final PluginCommand command = Bukkit.getPluginCommand("bipreload");
        if (command != null) {
            command.setExecutor(this);
            command.setTabCompleter(this);
        } else getLogger().warning("Could not register /bipreload");
    }

    /**
     * Called when a command is executed
     */
    @EventHandler
    public void onCommandExecute(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final List<String> list = config.getStringList("allowed-players");

        if (event.getMessage().startsWith("/ban-ip ") && (list.isEmpty() || !list.contains(player.getName()))) {
            String message = config.getString("messages.ban-ip");
            if (message == null) message = "&4&lERROR! &cYou can't use this command";

            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            event.setCancelled(true);
        }
    }

    /**
     * Creates/loads config.yml
     */
    private void config() {
        final File folder = Main.this.getDataFolder();

        // Create
        if (!folder.exists() && folder.mkdir()) Main.this.getLogger().info("Plugin folder created");
        if (!new File(folder, "config.yml").exists()) Main.this.saveResource("config.yml", false);

        // Load
        config = YamlConfiguration.loadConfiguration(new File(folder, "config.yml"));
    }

    /**
     * /bipreload
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) || sender.hasPermission("ban-ip.reload")) {
            config();

            String message = config.getString("messages.reload");
            if (message == null) message = "&a&lSUCCESS! &aPlugin files reloaded";
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
        return true;
    }

    /**
     * /bipreload tab complete
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return new ArrayList<>();
    }
}
