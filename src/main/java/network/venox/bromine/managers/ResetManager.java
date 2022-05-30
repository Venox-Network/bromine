package network.venox.bromine.managers;

import org.apache.commons.io.FileUtils;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.IOException;


public class ResetManager {
    public void delete(World world) {
        if (world != null) {
            // Unload
            Bukkit.unloadWorld(world, false);

            // Delete
            try {
                FileUtils.deleteDirectory(world.getWorldFolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
