package network.venox.bromine.commands;

import network.venox.bromine.Main;
import network.venox.bromine.managers.FileManager;
import network.venox.bromine.managers.MessageManager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ResetCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!Main.hasPermission(sender, "reset")) return true;

        if (args.length == 0) {
            // Kick players
            for (final Player player : Bukkit.getOnlinePlayers()) player.kickPlayer(new MessageManager("reset.kick").string());

            // Delete worlds
            Main.data.set("reset", true);
            new FileManager().save("data", Main.data);
            Main.worldManager.deleteWorld("world_nether");
            Main.worldManager.deleteWorld("world_the_end");

            // Restart server
            Bukkit.spigot().restart();
            return true;
        }

        if (args.length == 1) {
            final World world = Bukkit.getWorld(args[0]);
            if (world == null) {
                new MessageManager("reset.error")
                        .replace("%world%", args[0])
                        .send(sender);
                return true;
            }

            // Kick players
            for (final Player player : Bukkit.getOnlinePlayers()) {
                if (player.getWorld().equals(world)) player.kickPlayer(new MessageManager("reset.kick").string());
            }

            if (world.getName().equals("world")) {
                // Set reset stuff
                Main.data.set("reset", true);
                new FileManager().save("data", Main.data);

                // Restart server
                Bukkit.spigot().restart();

                return true;
            }

            // Delete world
            Main.worldManager.deleteWorld(world.getName());

            // Create new world
            //noinspection deprecation
            Main.worldManager.addWorld(
                    args[0],
                    world.getEnvironment(),
                    String.valueOf(world.getSeed()),
                    world.getWorldType(),
                    world.canGenerateStructures(),
                    null
            );

            // Send success message
            new MessageManager("reset.success")
                    .replace("%world%", args[0])
                    .send(sender);
            return true;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        final List<String> suggestions = new ArrayList<>();
        final List<String> results = new ArrayList<>();

        if (args.length == 1) for (final World world : Bukkit.getWorlds()) suggestions.add(world.getName());

        for (final String suggestion : suggestions) if (suggestion.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) results.add(suggestion);
        return results;
    }
}
