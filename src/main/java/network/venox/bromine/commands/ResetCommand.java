package network.venox.bromine.commands;

import network.venox.bromine.Bromine;

import org.bukkit.Bukkit;
import org.bukkit.World;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.annoyingapi.command.AnnoyingCommand;
import xyz.srnyx.annoyingapi.command.AnnoyingSender;
import xyz.srnyx.annoyingapi.message.AnnoyingMessage;
import xyz.srnyx.annoyingapi.utility.BukkitUtility;

import java.util.Set;


public class ResetCommand implements AnnoyingCommand {
    @NotNull private final Bromine plugin;

    public ResetCommand(@NotNull Bromine plugin) {
        this.plugin = plugin;
    }

    @Override @NotNull
    public Bromine getAnnoyingPlugin() {
        return plugin;
    }

    @Override @NotNull
    public String getPermission() {
        return "bromine.reset";
    }

    @Override
    public void onCommand(@NotNull AnnoyingSender sender) {
        final String[] args = sender.args;

        // No arguments
        if (args.length == 0) {
            // Kick players
            final String reason = new AnnoyingMessage(plugin, "reset.kick").toString(sender);
            Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(reason));

            // Delete worlds
            plugin.data.setSave("reset", true);
            plugin.worldManager.deleteWorld("world_nether");
            plugin.worldManager.deleteWorld("world_the_end");

            // Restart server
            Bukkit.spigot().restart();
            return;
        }

        // <world>
        final World world = Bukkit.getWorld(args[0]);
        if (world == null) {
            sender.invalidArgument(args[0]);
            return;
        }

        // Kick players
        final String reason = new AnnoyingMessage(plugin, "reset.kick").toString(sender);
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getWorld().equals(world))
                .forEach(player -> player.kickPlayer(reason));

        if (world.getUID().equals(Bukkit.getWorlds().get(0).getUID())) {
            // Set reset stuff
            plugin.data.setSave("reset", true);

            // Restart server
            Bukkit.spigot().restart();
            return;
        }

        // Delete world
        plugin.worldManager.deleteWorld(world.getName());

        // Create new world
        //noinspection deprecation
        plugin.worldManager.addWorld(
                args[0],
                world.getEnvironment(),
                String.valueOf(world.getSeed()),
                world.getWorldType(),
                world.canGenerateStructures(),
                null);

        // Send success message
        new AnnoyingMessage(plugin, "reset.success")
                .replace("%world%", args[0])
                .send(sender);
    }

    @Override @NotNull
    public Set<String> onTabComplete(@NotNull AnnoyingSender sender) {
        return BukkitUtility.getWorldNames();
    }
}
