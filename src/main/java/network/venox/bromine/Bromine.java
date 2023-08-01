package network.venox.bromine;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;

import network.venox.bromine.commands.ResetCommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.annoyingapi.AnnoyingPlugin;
import xyz.srnyx.annoyingapi.PluginPlatform;
import xyz.srnyx.annoyingapi.file.AnnoyingData;
import xyz.srnyx.annoyingapi.file.AnnoyingFile;
import xyz.srnyx.annoyingapi.file.AnnoyingResource;
import xyz.srnyx.annoyingapi.message.AnnoyingMessage;
import xyz.srnyx.annoyingapi.message.BroadcastType;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;


public class Bromine extends AnnoyingPlugin {
    public Set<String> banIpAllowedPlayers;
    public AnnoyingData data;
    public MVWorldManager worldManager;

    public Bromine() {
        options
                .pluginOptions(pluginOptions -> pluginOptions.updatePlatforms(
                        PluginPlatform.modrinth("bromine"),
                        PluginPlatform.hangar(this, "Venox"),
                        PluginPlatform.spigot("102058")))
                .bStatsOptions(bStatsOptions -> bStatsOptions.id(18033))
                .registrationOptions
                .automaticRegistration(automaticRegistration -> automaticRegistration
                        .packages(
                                "network.venox.bromine.commands",
                                "network.venox.bromine.listeners")
                        .ignoredClasses(ResetCommand.class))
                .papiExpansionToRegister(() -> new BrominePlaceholders(this));
        reload();
    }

    @Override
    public void reload() {
        banIpAllowedPlayers = new HashSet<>(new AnnoyingResource(this, "config.yml").getStringList("ban-ip.allowed-players"));
        data = new AnnoyingData(this, "data.yml", new AnnoyingFile.Options<>().canBeEmpty(false));
    }

    /**
     * Everything done on plugin start
     */
    @Override
    public void enable() {
        // Plugin specifics
        final Plugin multiverse = Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        if (multiverse != null) {
            worldManager = ((MultiverseCore) multiverse).getMVWorldManager();
            new ResetCommand(this).register();
        }

        // World reset
        if (data.getBoolean("reset")) new BukkitRunnable() {
            public void run() {
                log(Level.SEVERE, "Please disregard any world warnings you may get!");

                // Delete world
                try {
                    Files.delete(Bukkit.getWorlds().get(0).getWorldFolder().toPath());
                } catch (final IOException e) {
                    e.printStackTrace();
                }

                // Set reset
                data.setSave("reset", null);
                // Restart server
                Bukkit.getServer().shutdown();
            }
        }.runTaskLater(this, 1L);
    }

    public void sendChatTitle(@NotNull Player player, @NotNull String message) {
        new AnnoyingMessage(this, "chat-title.format")
                .replace("%player%", player.getName())
                .replace("%message%", message)
                .broadcast(BroadcastType.FULL_TITLE, 0, 60, 10);
    }
}
