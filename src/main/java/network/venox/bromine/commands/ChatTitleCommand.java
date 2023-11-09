package network.venox.bromine.commands;

import network.venox.bromine.Bromine;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.annoyingapi.command.AnnoyingCommand;
import xyz.srnyx.annoyingapi.command.AnnoyingSender;
import xyz.srnyx.annoyingapi.data.EntityData;
import xyz.srnyx.annoyingapi.message.AnnoyingMessage;
import xyz.srnyx.annoyingapi.message.DefaultReplaceType;
import xyz.srnyx.annoyingapi.utility.BukkitUtility;

import java.util.Set;


public class ChatTitleCommand extends AnnoyingCommand {
    @NotNull private final Bromine plugin;

    public ChatTitleCommand(@NotNull Bromine plugin) {
        this.plugin = plugin;
    }

    @Override @NotNull
    public Bromine getAnnoyingPlugin() {
        return plugin;
    }

    @Override @NotNull
    public String getPermission() {
        return "bromine.chattitle";
    }

    @Override
    public void onCommand(@NotNull AnnoyingSender sender) {
        final String[] args = sender.args;

        // No arguments
        if (args.length == 0) {
            if (sender.checkPlayer()) toggle(sender.getPlayer());
            return;
        }

        // chattitle <player>
        if (args.length == 1) {
            final Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                toggle(target);
                return;
            }
        }

        // chattitle <message>
        if (sender.checkPlayer()) plugin.sendChatTitle(sender.getPlayer(), String.join(" ", args));
    }

    @Override @NotNull
    public Set<String> onTabComplete(@NotNull AnnoyingSender sender) {
        return BukkitUtility.getOnlinePlayerNames();
    }

    private void toggle(@NotNull Player player) {
        final EntityData data = new EntityData(plugin, player);
        final boolean wasEnabled = data.has("chattitle");
        if (wasEnabled) {
            data.remove("chattitle");
        } else {
            data.set("chattitle", true);
        }
        new AnnoyingMessage(plugin, "chat-title.toggle")
                .replace("%status%", !wasEnabled, DefaultReplaceType.BOOLEAN)
                .replace("%player%", player.getName())
                .send(player);
    }
}
