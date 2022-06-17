package me.joey.treecapitator;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.logging.Level;

public final class TreeCapitator extends JavaPlugin {
    public String VERSION = "2.0";
    public static ArrayList<Material> treeBlocks = new ArrayList<Material>();

    public static ArrayList<Material> axeItems = new ArrayList<Material>();

    public static int maxBlocksChopped = 100;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new TreeFallEvent(), this);

        treeBlocks.add(Material.ACACIA_LOG);
        treeBlocks.add(Material.BIRCH_LOG);
        treeBlocks.add(Material.DARK_OAK_LOG);
        treeBlocks.add(Material.JUNGLE_LOG);
        treeBlocks.add(Material.OAK_LOG);
        treeBlocks.add(Material.SPRUCE_LOG);
        treeBlocks.add(Material.MANGROVE_LOG);
        treeBlocks.add(Material.CRIMSON_STEM);
        treeBlocks.add(Material.WARPED_STEM);

        axeItems.add(Material.WOODEN_AXE);
        axeItems.add(Material.STONE_AXE);
        axeItems.add(Material.IRON_AXE);
        axeItems.add(Material.GOLDEN_AXE);
        axeItems.add(Material.DIAMOND_AXE);
        axeItems.add(Material.NETHERITE_AXE);

        getLogger().log(Level.INFO, "TreeCapitator v" + VERSION + " is online!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    public static boolean checkLog(Material material){
        for (Material log : treeBlocks){
            if (material.equals(log)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkAxe(ItemStack item){
        Material tool = item.getType();
        for (Material axe : axeItems){
            if (tool.equals(axe)){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Block> getAdjacentBlocks(Block block){
        ArrayList<Block> blocks = new ArrayList<Block>();

        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                for (int k = -1; k <= 1; k++){
                    blocks.add(block.getWorld().getBlockAt(block.getLocation().add(i, j, k)));
                }
            }
        }

        blocks.remove(block.getWorld().getBlockAt(block.getLocation().add(0, 0, 0)));
        return blocks;
    }

    public static void sendMessage(Player p, String message){
        p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "TreeCapitator" + ChatColor.WHITE + "] " + message);

    }
}
