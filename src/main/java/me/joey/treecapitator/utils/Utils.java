package me.joey.treecapitator.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
    public static void sendMessage(Player p, String message){
        p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "TreeCapitator" + ChatColor.WHITE + "] " + message);
    }
}
