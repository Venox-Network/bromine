package network.venox.bromine.commands;

import network.venox.bromine.Main;
import network.venox.bromine.managers.ConfigManager;
import network.venox.bromine.managers.MessageManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;


public class ReloadCommand implements CommandExecutor {
    /**
     * /brreload
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!Main.hasPermission(sender, "reload")) return true;

        new ConfigManager().loadFiles();
        new MessageManager("reload").send(sender);
        return true;
    }
}
