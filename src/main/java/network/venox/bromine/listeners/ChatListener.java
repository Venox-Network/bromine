package network.venox.bromine.listeners;

import network.venox.bromine.Bromine;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.jetbrains.annotations.NotNull;
import xyz.srnyx.annoyingapi.AnnoyingListener;
import xyz.srnyx.annoyingapi.message.AnnoyingMessage;


public class ChatListener extends AnnoyingListener {
    @NotNull private final Bromine plugin;

    public ChatListener(@NotNull Bromine plugin) {
        this.plugin = plugin;
    }

    @Override @NotNull
    public Bromine getAnnoyingPlugin() {
        return plugin;
    }

    /**
     * Called when a command is executed
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocess(@NotNull PlayerCommandPreprocessEvent event) {
        // Check if command is /ban-ip
        if (!event.getMessage().contains("ban-ip")) return;

        // Check if player allowed
        final Player player = event.getPlayer();
        if (plugin.isBanIpAllowed(player)) return;

        // Prevent use
        event.setCancelled(true);
        new AnnoyingMessage(plugin, "ban-ip.denied").send(player);
    }

    /**
     * For command blocks
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(@NotNull ServerCommandEvent event) {
        // Check if command is /ban-ip
        if (!event.getCommand().contains("ban-ip")) return;

        // Prevent use
        event.setCancelled(true);
        new AnnoyingMessage(plugin, "ban-ip.denied").send(event.getSender());
    }
}
