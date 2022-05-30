package network.venox.bromine.managers;

import network.venox.bromine.Main;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;


public class ConfigManager {
    /**
     * Creates/loads plugin files
     */
    public void loadFiles() {
        final File folder = Main.plugin.getDataFolder();

        // Create
        if (!folder.exists() && folder.mkdir()) Main.plugin.getLogger().info("Plugin folder created");
        if (!new File(folder, "config.yml").exists()) Main.plugin.saveResource("config.yml", false);

        // Load
        Main.config = YamlConfiguration.loadConfiguration(new File(folder, "config.yml"));
        Main.messages = YamlConfiguration.loadConfiguration(new File(folder, "messages.yml"));
    }
}
