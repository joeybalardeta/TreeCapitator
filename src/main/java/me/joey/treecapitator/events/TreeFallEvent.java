package me.joey.treecapitator.events;

import me.joey.treecapitator.treelogic.TreeLogic;

import org.bukkit.GameMode;
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
        // conditions to not trigger TreeCapitator
        if (event.getPlayer().isSneaking()){
            return;
        }
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        if (!TreeLogic.checkLog(event.getBlock().getType())){
            return;
        }
        if (!TreeLogic.checkAxe(event.getPlayer().getItemInHand())){
            return;
        }

        // axe 'resistance' calculation
        Damageable axeEnt = (Damageable) event.getPlayer().getItemInHand().getItemMeta();

        int resistance = 1;
        if (axeEnt.hasEnchant(Enchantment.UNBREAKING)){
            resistance = axeEnt.getEnchantLevel(Enchantment.UNBREAKING) + 1;
        }

        int maxDurability = event.getPlayer().getItemInHand().getType().getMaxDurability();
        int durability = maxDurability - axeEnt.getDamage();
        if (durability == 1){
            return;
        }
        durability = durability * resistance;


        ArrayList<Block> blocksToBreak = TreeLogic.getBlocksToBreak(durability, event.getBlock());


        axeEnt.setDamage(axeEnt.getDamage() + (blocksToBreak.size() / resistance));

        for (Block block : blocksToBreak){
            block.breakNaturally();
        }

        event.getPlayer().getItemInHand().setItemMeta(axeEnt);
    }

}
