package com.wasted_ticks.straightupeconomy.commands;

import com.wasted_ticks.straightupeconomy.StraightUpEconomy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class WithdrawCommand implements CommandExecutor {

    private final StraightUpEconomy plugin;

    public WithdrawCommand(StraightUpEconomy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length != 1) {
                player.sendMessage(command.getUsage());
            } else {
                try {
                    int amount = Integer.parseInt(args[0]);
                    if(amount < 1) {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Invalid amount requested to withdraw.");
                        return true;
                    }
                    if(this.plugin.getEconomy().has(player, amount)) {
                        this.plugin.getEconomy().withdrawPlayer(player, amount);
                        HashMap<Integer, ItemStack> stack = player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, amount));
                        if(!stack.isEmpty()) {
                            int refund = stack.get(0).getAmount();
                            this.plugin.getEconomy().depositPlayer(player, refund);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Unable to fill full transaction cost, try cleaning your inventory.");
                        }
                        double balance = plugin.getEconomy().getBalance(player);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Your current balance is: $" + balance);
                    } else {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "You must have sufficient gold ingots to withdraw.");
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.DARK_PURPLE + "Usage:" + ChatColor.LIGHT_PURPLE + command.getUsage());
                }
            }
        }

        return true;
    }
}
