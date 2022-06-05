package network.venox.bromine.listeners;

import network.venox.bromine.Main;
import network.venox.bromine.managers.ChatTitleManager;
import network.venox.bromine.managers.MessageManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;


public class ChatListener implements Listener {
    /**
     * Called when a message is sent
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (player.getScoreboardTags().contains("chattitle")) new ChatTitleManager().send(player, event.getMessage());
    }

    /**
     * Called when a command is executed
     */
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final List<String> list = Main.config.getStringList("ban-ip_allowed-players");

        if (event.getMessage().startsWith("/ban-ip ") && (list.isEmpty() || !list.contains(player.getName()))) {
            event.setCancelled(true);
            new MessageManager("ban-ip.denied").send(player);
        }
    }
}
