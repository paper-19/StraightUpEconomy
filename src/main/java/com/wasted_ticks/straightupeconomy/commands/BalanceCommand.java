package com.wasted_ticks.straightupeconomy.commands;

import com.wasted_ticks.straightupeconomy.StraightUpEconomy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class BalanceCommand implements CommandExecutor {

    private final StraightUpEconomy plugin;

    public BalanceCommand(StraightUpEconomy plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {

            Player player = (Player) sender;

            if(!player.hasPermission("economy.balance")){
                player.sendMessage(ChatColor.LIGHT_PURPLE + "You must have permissions to use this command.");
                return true;
            }


            if(args.length != 0) {
                player.sendMessage(ChatColor.DARK_PURPLE + "Usage:" + ChatColor.LIGHT_PURPLE + command.getUsage());
            } else {
                double balance = plugin.getEconomy().getBalance(player);
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Your current balance is: $" + balance);
            }
        }
        return true;
    }
}