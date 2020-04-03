package com.wasted_ticks.straightupeconomy.commands;

import com.wasted_ticks.straightupeconomy.StraightUpEconomy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class DepositCommand implements CommandExecutor {

    private final StraightUpEconomy plugin;

    public DepositCommand(StraightUpEconomy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {

            Player player = (Player) sender;

            if(args.length != 1) {
                player.sendMessage(command.getUsage());
            } else {
                try {
                    int amount = Integer.parseInt(args[0]);
                    if(amount < 1) {
                        player.sendMessage(ChatColor.DARK_GRAY + "Invalid amount requested to deposit.");
                        return true;
                    }
                    if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), amount)){
                        this.removeItems(player.getInventory(),Material.GOLD_INGOT, amount);
                        plugin.getEconomy().depositPlayer(player, (double) amount);
                        double balance = plugin.getEconomy().getBalance(player);
                        player.sendMessage(ChatColor.DARK_GRAY + "Your current balance is: " + balance + " Au");
                    } else {
                        player.sendMessage(ChatColor.DARK_GRAY + "You must have sufficient gold ingots to deposit.");
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.DARK_GRAY + "Unable to parse integer.");
                    player.sendMessage(ChatColor.DARK_GRAY + "Usage:" + ChatColor.LIGHT_PURPLE + command.getUsage());
                }
            }
        }
        return true;
    }



    public static void removeItems(Inventory inventory, Material type, int amount) {
        if (amount <= 0) return;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (type == is.getType()) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                } else {
                    inventory.clear(slot);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }

}
