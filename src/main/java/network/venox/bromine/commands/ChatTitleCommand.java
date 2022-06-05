package network.venox.bromine.commands;

import network.venox.bromine.Main;
import network.venox.bromine.managers.ChatTitleManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ChatTitleCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!Main.hasPermission(sender, "chattitle")) return true;
        final Player player = (Player) sender;
        final ChatTitleManager ctm = new ChatTitleManager();

        // chattitle
        if (args.length == 0) {
            ctm.toggle(player);
            return true;
        }

        // chattitle <player>
        final Player target = ctm.getPlayer(args[0]);
        if (args.length == 1 && target != null) {
            ctm.toggle(target);
            return true;
        }

        // chattitle <message>
        final StringBuilder sb = new StringBuilder();
        for (final String arg : args) {
            sb.append(arg).append(" ");
        }
        ctm.send(player, sb.toString());
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final List<String> suggestions = new ArrayList<>();
        final List<String> results = new ArrayList<>();

        if (args.length == 1) for (final Player player : Bukkit.getOnlinePlayers()) suggestions.add(player.getName());

        for (final String suggestion : suggestions) if (suggestion.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) results.add(suggestion);
        return results;
    }
}