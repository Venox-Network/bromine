package network.venox.bromine.commands;

import network.venox.bromine.Main;
import network.venox.bromine.managers.MessageManager;
import network.venox.bromine.managers.ResetManager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
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

        // Kick players
        for (final Player player : Bukkit.getOnlinePlayers()) player.kickPlayer(new MessageManager("reset.kick").string());

        final ResetManager rm = new ResetManager();

        if (args.length == 0) {
            // Unload/delete worlds
            rm.delete(Bukkit.getWorld("world"));
            rm.delete(Bukkit.getWorld("world_nether"));
            rm.delete(Bukkit.getWorld("world_the_end"));

            // Restart server
            Bukkit.spigot().restart();
            return true;
        }

        if (args.length == 1) {
            final World world = Bukkit.getWorld(args[0]);
            if (world == null) {
                new MessageManager("reset.error").log("warning");
                return true;
            }

            // Store the world's environment for later
            final World.Environment env = world.getEnvironment();

            // Unload/delete world
            rm.delete(world);

            // Create new world
            WorldCreator creator = new WorldCreator(args[0]);
            creator.environment(env);
            creator.createWorld();

            // Send success message
            new MessageManager("reset.success").log("info");
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
