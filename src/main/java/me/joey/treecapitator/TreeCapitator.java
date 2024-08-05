package me.joey.treecapitator;

import me.joey.treecapitator.events.TreeFallEvent;
import me.joey.treecapitator.treelogic.TreeLogic;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Level;

public final class TreeCapitator extends JavaPlugin {
    private static TreeCapitator instance;
    public String VERSION = "2.2.1";
    public static int maxBlocksChopped = 256;

    public static TreeCapitator getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        // plugin startup logic
        instance = this;

        this.getServer().getPluginManager().registerEvents(new TreeFallEvent(), this);

        TreeLogic.loadTreeBlocks();
        TreeLogic.loadAxeItems();

        getLogger().log(Level.INFO, "TreeCapitator v" + VERSION + " is online!");
    }

    @Override
    public void onDisable() {
        // plugin shutdown logic
        instance = null;
    }
}
