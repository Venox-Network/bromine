package network.venox.bromine.managers;

import network.venox.bromine.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;


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
    public void send(@NotNull CommandSender sender) {
        sender.sendMessage(message);
    }

    /**
     * @return  The message as a string
     */
    public String string() {
        return message;
    }
}
