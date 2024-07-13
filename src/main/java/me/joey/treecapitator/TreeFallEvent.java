package me.joey.treecapitator;

import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

public class TreeFallEvent implements Listener {
    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event){
        if (event.getPlayer() == null){
            return;
        }

        if (!TreeCapitator.checkLog(event.getBlock().getType())){
            return;
        }

        if (event.getPlayer().isSneaking()){
            return;
        }

        if (!TreeCapitator.checkAxe(event.getPlayer().getItemInHand())){
            return;
        }

        ArrayList<Block> blocksToBreak = new ArrayList<Block>();

        blocksToBreak.add(event.getBlock());

        ArrayList<Block> blocksChecked = new ArrayList<Block>();


        Damageable axeEnt = (Damageable) event.getPlayer().getItemInHand().getItemMeta();

        int resistance = 1;

        if (axeEnt.hasEnchant(Enchantment.DURABILITY)){
            resistance = axeEnt.getEnchantLevel(Enchantment.DURABILITY) + 1;
        }

        int maxDurability = event.getPlayer().getItemInHand().getType().getMaxDurability();

        int durability = maxDurability - axeEnt.getDamage();

        if (durability == 1){
            return;
        }

        durability = durability * resistance;

        int lastBlockCount = 0;





        while ((blocksToBreak.size() + 1 < durability) && (blocksToBreak.size() < TreeCapitator.maxBlocksChopped)) {
            ArrayList<Block> blocksToBreak2 = new ArrayList<Block>(blocksToBreak);
            for (Block block : blocksToBreak2) {
                if (!blocksChecked.contains(block)) {
                    ArrayList<Block> adjBlocks = TreeCapitator.getAdjacentBlocks(block);

                    blocksChecked.add(block);

                    for (Block potentialBlock : adjBlocks) {
                        if (TreeCapitator.checkLog(potentialBlock.getType()) && !blocksToBreak.contains(potentialBlock)) {
                            if ((blocksToBreak.size() + 1 < durability) && (blocksToBreak.size() < TreeCapitator.maxBlocksChopped)){
                                blocksToBreak.add(potentialBlock);
                            }
                        }
                    }
                }
            }

            if (lastBlockCount == blocksToBreak.size()){
                break;
            }

            lastBlockCount = blocksToBreak.size();

        }

        axeEnt.setDamage(axeEnt.getDamage() + (blocksToBreak.size() / resistance));

        for (Block block : blocksToBreak){
            block.breakNaturally();
        }




        event.getPlayer().getItemInHand().setItemMeta(axeEnt);
    }

}
