package network.venox.bromine.managers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


public class ChatTitleManager {
    public void toggle(Player player) {
        if (player.getScoreboardTags().contains("chattitle")) {
            player.removeScoreboardTag("chattitle");
            new MessageManager("chat-title")
                    .replace("%status%", "disabled")
                    .send(player);
            return;
        }

        player.addScoreboardTag("chattitle");
        new MessageManager("chat-title")
                .replace("%status%", "enabled")
                .send(player);
    }

    public Player getPlayer(String name) {
        for (OfflinePlayer offline : Bukkit.getOfflinePlayers()) {
            String opName = offline.getName();
            if (opName == null || !opName.equalsIgnoreCase(name)) return null;

            return offline.getPlayer();
        }
        return null;
    }
}
