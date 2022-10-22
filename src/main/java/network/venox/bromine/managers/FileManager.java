package network.venox.bromine.managers;

import network.venox.bromine.Main;

import org.bukkit.configuration.file.YamlConfiguration;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;


public class FileManager {
    final File folder = Main.plugin.getDataFolder();

    /**
     * Creates/loads plugin files
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void loadFiles() {
        // Create
        if (!folder.exists()) folder.mkdir();
        createFile("config");
        createFile("messages");
        createFile("data");

        // Load
        Main.config = YamlConfiguration.loadConfiguration(new File(folder, "config.yml"));
        Main.messages = YamlConfiguration.loadConfiguration(new File(folder, "messages.yml"));
        Main.data = YamlConfiguration.loadConfiguration(new File(folder, "data.yml"));
    }

    private void createFile(String name) {
        final String fullName = name + ".yml";
        if (!new File(folder, fullName).exists()) Main.plugin.saveResource(fullName, false);
    }

    public void save(String name, @NotNull YamlConfiguration config) {
        try {
            config.save(new File(folder, name + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
