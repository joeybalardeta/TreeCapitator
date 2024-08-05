package me.joey.treecapitator.treelogic;

import me.joey.treecapitator.TreeCapitator;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.logging.Level;

public class TreeLogic {
    public static ArrayList<Material> treeBlocks = new ArrayList<Material>();
    public static ArrayList<Material> axeItems = new ArrayList<Material>();

    public static void loadTreeBlocks() {
        treeBlocks.add(Material.ACACIA_LOG);
        treeBlocks.add(Material.BIRCH_LOG);
        treeBlocks.add(Material.DARK_OAK_LOG);
        treeBlocks.add(Material.JUNGLE_LOG);
        treeBlocks.add(Material.OAK_LOG);
        treeBlocks.add(Material.SPRUCE_LOG);
        treeBlocks.add(Material.MANGROVE_LOG);
        treeBlocks.add(Material.CHERRY_LOG);
        treeBlocks.add(Material.CRIMSON_STEM);
        treeBlocks.add(Material.WARPED_STEM);
    }

    public static void loadAxeItems() {
        axeItems.add(Material.WOODEN_AXE);
        axeItems.add(Material.STONE_AXE);
        axeItems.add(Material.IRON_AXE);
        axeItems.add(Material.GOLDEN_AXE);
        axeItems.add(Material.DIAMOND_AXE);
        axeItems.add(Material.NETHERITE_AXE);
    }

    public static ArrayList<Block> getBlocksToBreak(int axeDurability, Block block){
        ArrayList<Block> checkedBlocks = new ArrayList<Block>();
        ArrayList<Block> blocksToBreak = new ArrayList<Block>();

        blocksToBreak.add(block);

        int iterations = 0;
        while (blocksToBreak.size() < TreeCapitator.maxBlocksChopped && (blocksToBreak.size() + 1 < axeDurability)) {
            ArrayList<Block> blocksToAdd = new ArrayList<Block>();

            for (Block b : blocksToBreak) {
                if (!checkedBlocks.contains(b)) {
                    ArrayList<Block> adjacentBlocks = getAdjacentBlocks(b, b.getType());
                    for (Block a : adjacentBlocks) {
                        if (!blocksToBreak.contains(a)) {
                            blocksToAdd.add(a);
                        }
                    }
                    checkedBlocks.add(b);
                }
            }

            if (blocksToAdd.isEmpty()) {
                break;
            }

            blocksToBreak.addAll(blocksToAdd);
        }


        return blocksToBreak;
    }

    public static ArrayList<Block> getAdjacentBlocks(Block block) {
        ArrayList<Block> blocks = new ArrayList<Block>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    blocks.add(block.getWorld().getBlockAt(block.getLocation().add(i, j, k)));
                }
            }
        }

        blocks.remove(block.getWorld().getBlockAt(block.getLocation().add(0, 0, 0)));
        return blocks;
    }

    public static ArrayList<Block> getAdjacentBlocks(Block block, Material material) {
        ArrayList<Block> blocks = new ArrayList<Block>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (block.getWorld().getBlockAt(block.getLocation().add(i, j, k)).getType().equals(material)) {
                        blocks.add(block.getWorld().getBlockAt(block.getLocation().add(i, j, k)));
                    }
                }
            }
        }

        blocks.remove(block.getWorld().getBlockAt(block.getLocation().add(0, 0, 0)));
        return blocks;
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
}
