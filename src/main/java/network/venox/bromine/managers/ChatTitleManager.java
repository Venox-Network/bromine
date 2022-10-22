package network.venox.bromine.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;


public class ChatTitleManager {
    public void toggle(@NotNull Player player) {
        if (player.getScoreboardTags().contains("chattitle")) {
            player.removeScoreboardTag("chattitle");
            new MessageManager("chat-title.toggle")
                    .replace("%status%", "disabled")
                    .replace("%player%", player.getName())
                    .send(player);
            return;
        }

        player.addScoreboardTag("chattitle");
        new MessageManager("chat-title.toggle")
                .replace("%status%", "enabled")
                .replace("%player%", player.getName())
                .send(player);
    }

    public void send(Player player, String message) {
        for (final Player online : Bukkit.getServer().getOnlinePlayers()) {
            final String messageColor = ChatColor.translateAlternateColorCodes('&', message);
            online.sendTitle(ChatColor.DARK_RED + player.getName() + ":", ChatColor.RED + messageColor, 0, 60, 10);
        }
    }

    public Player getPlayer(String name) {
        for (final OfflinePlayer offline : Bukkit.getOfflinePlayers()) {
            final String opName = offline.getName();
            return opName != null && opName.equalsIgnoreCase(name) ? offline.getPlayer() : null;
        }
        return null;
    }
}
