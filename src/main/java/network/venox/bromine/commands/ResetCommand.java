package network.venox.bromine.commands;

import network.venox.bromine.Bromine;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.mvplugins.multiverse.core.world.MultiverseWorld;
import org.mvplugins.multiverse.core.world.options.CreateWorldOptions;
import org.mvplugins.multiverse.core.world.options.DeleteWorldOptions;
import org.mvplugins.multiverse.external.vavr.collection.List;
import xyz.srnyx.annoyingapi.command.AnnoyingCommand;
import xyz.srnyx.annoyingapi.command.AnnoyingSender;
import xyz.srnyx.annoyingapi.message.AnnoyingMessage;
import xyz.srnyx.annoyingapi.parents.Registrable;
import xyz.srnyx.annoyingapi.utility.BukkitUtility;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;


@Registrable.Ignore
public class ResetCommand extends AnnoyingCommand {
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
        if (plugin.worldManager == null) {
            sender.invalidArguments();
            return;
        }
        final String[] args = sender.args;

        // No arguments
        if (args.length == 0) {
            // Kick players
            final String reason = new AnnoyingMessage(plugin, "reset.kick").toString(sender);
            Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(reason));

            // Delete worlds
            plugin.data.setSave("reset", true);
            final MultiverseWorld nether = plugin.worldManager.getWorld("world_nether").getOrNull();
            if (nether != null) plugin.worldManager.deleteWorld(DeleteWorldOptions
                    .world(nether)
                    .keepFiles(Collections.singletonList("paper-world.yml")));
            final MultiverseWorld end = plugin.worldManager.getWorld("world_the_end").getOrNull();
            if (end != null) plugin.worldManager.deleteWorld(DeleteWorldOptions
                    .world(end)
                    .keepFiles(Collections.singletonList("paper-world.yml")));

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
        final MultiverseWorld multiverseWorld = plugin.worldManager.getWorld(world.getName()).getOrNull();
        if (multiverseWorld != null) plugin.worldManager.deleteWorld(DeleteWorldOptions.world(multiverseWorld));

        // Create new world
        plugin.worldManager.createWorld(CreateWorldOptions
                .worldName(args[0])
                .environment(world.getEnvironment())
                .seed(world.getSeed())
                .worldType(world.getWorldType())
                .generateStructures(world.canGenerateStructures()));

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
