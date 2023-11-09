package network.venox.bromine.listeners;

import network.venox.bromine.Bromine;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.annoyingapi.AnnoyingListener;
import xyz.srnyx.annoyingapi.data.EntityData;
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
     * Called when a message is sent
     */
    @EventHandler
    public void onAsyncPlayerChat(@NotNull AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (new EntityData(plugin, player).get("chattitle") != null) plugin.sendChatTitle(player, event.getMessage());
    }

    /**
     * Called when a command is executed
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocess(@NotNull PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        if (!event.getMessage().startsWith("/ban-ip ") || (plugin.banIpAllowedPlayers != null && plugin.banIpAllowedPlayers.contains(player.getName()))) return;
        event.setCancelled(true);
        new AnnoyingMessage(plugin, "ban-ip.denied").send(player);
    }
}
