package network.venox.bromine.managers;

import network.venox.bromine.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class MessageManager {
    private String message;

    /**
     * Initializes a new {@link MessageManager}
     *
     * @param   key The location of the message in the messages file
     */
    public MessageManager(String key) {
        final String messageKey = Main.messages.getString(key);
        final String prefix = Main.messages.getString("plugin.prefix");

        if (messageKey == null) {
            this.message = key;
            return;
        }

        if (prefix == null) {
            this.message = ChatColor.translateAlternateColorCodes('&', messageKey);
            return;
        }

        this.message = ChatColor.translateAlternateColorCodes('&', messageKey.replace("%prefix%", prefix));
    }

    /**
     * Replaces {@code before} with {@code after}
     *
     * @param   before  The string to replace
     * @param   after   The replacement for the {@code before}
     *
     * @return          MessageManager
     */
    public MessageManager replace(String before, String after) {
        message = message.replace(before, after);
        return this;
    }

    /**
     * Sends the message to the {@code sender}
     *
     * @param   sender  The person/thing that should be sent the message
     */
    public void send(CommandSender sender) {
        sender.sendMessage(message);
    }

    /**
     * Logs the message to the console
     *
     * @param   level   The level of logger  to use
     */
    public void log(String level) {
        if (level.equals("info")) {
            Main.plugin.getLogger().info(message);
            return;
        }

        if (level.equals("warning")) {
            Main.plugin.getLogger().warning(message);
            return;
        }

        if (level.equals("severe")) {
            Main.plugin.getLogger().severe(message);
            return;
        }

        if (level.equals("fine")) {
            Main.plugin.getLogger().fine(message);
            return;
        }

        if (level.equals("finer")) {
            Main.plugin.getLogger().finer(message);
            return;
        }

        if (level.equals("finest")) {
            Main.plugin.getLogger().finest(message);
            return;
        }

        Main.plugin.getLogger().warning("(INVALID LOG LEVEL) " + message);
    }

    /**
     * @return  The message as a string
     */
    public String string() {
        return message;
    }
}
