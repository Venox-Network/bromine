package network.venox.bromine.commands;

import network.venox.bromine.Main;
import network.venox.bromine.managers.ChatTitleManager;
import network.venox.bromine.managers.MessageManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;


public class ChatTitleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!Main.hasPermission(sender, "chattitle")) return true;
        final Player player = (Player) sender;
        final ChatTitleManager ctm = new ChatTitleManager();

        if (args.length == 0) {
            ctm.toggle(player);
            return true;
        }

        if (args.length == 1) {
            if (ctm.getPlayer(args[0]) == null) {
                new MessageManager("plugin.invalid-player")
                        .replace("%player%", args[0])
                        .send(sender);
                return true;
            }

            ctm.toggle(ctm.getPlayer(args[0]));
            return true;
        }

        return true;
    }
}