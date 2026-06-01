package network.venox.bromine;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.srnyx.annoyingapi.AnnoyingPlugin;
import xyz.srnyx.annoyingapi.PluginPlatform;
import xyz.srnyx.annoyingapi.file.AnnoyingResource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Bromine extends AnnoyingPlugin {
    @Nullable public Set<String> banIpAllowedPlayers;

    public Bromine() {
        options
                .pluginOptions(pluginOptions -> pluginOptions.updatePlatforms(
                        PluginPlatform.modrinth("bromine"),
                        PluginPlatform.hangar("BromineEssentials", "Venox"),
                        PluginPlatform.spigot("102058")))
                .bStatsOptions(bStatsOptions -> bStatsOptions.id(18033))
                .registrationOptions.automaticRegistration
                .packages("network.venox.bromine.commands")
                .packages("network.venox.bromine.listeners");

        reload();
    }

    @Override @NotNull
    public String getLibsPackage() {
        return "network{}venox{}bromineessentials{}libs{}";
    }

    @Override
    public void reload() {
        final List<String> allowedPlayers = new AnnoyingResource(this, "config.yml").getStringList("ban-ip.allowed-players");
        banIpAllowedPlayers = allowedPlayers.isEmpty() ? null : new HashSet<>(allowedPlayers);
    }

    public boolean isBanIpAllowed(@NotNull Player player) {
        return banIpAllowedPlayers != null && (banIpAllowedPlayers.contains(player.getUniqueId().toString()) || banIpAllowedPlayers.contains(player.getName()));
    }
}
