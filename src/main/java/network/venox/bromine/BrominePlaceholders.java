package network.venox.bromine;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xyz.srnyx.annoyingapi.AnnoyingPAPIExpansion;
import xyz.srnyx.annoyingapi.data.EntityData;


public class BrominePlaceholders extends AnnoyingPAPIExpansion {
    @NotNull private final Bromine plugin;

    public BrominePlaceholders(@NotNull Bromine plugin) {
        this.plugin = plugin;
    }

    @Override @NotNull
    public Bromine getAnnoyingPlugin() {
        return plugin;
    }

    @Override @NotNull
    public String getIdentifier() {
        return "bromine";
    }

    @Override @Nullable
    public String onPlaceholderRequest(@Nullable Player player, @NotNull String params) {
        // chattitle
        if (player != null && params.equalsIgnoreCase("chattitle")) return String.valueOf(new EntityData(plugin, player).has("chattitle"));

        // chattitle_PLAYER
        if (params.startsWith("chattitle")) {
            final Player target = Bukkit.getPlayerExact(params.substring(10));
            return target == null ? null : String.valueOf(new EntityData(plugin, target).has("chattitle"));
        }

        return null;
    }
}
