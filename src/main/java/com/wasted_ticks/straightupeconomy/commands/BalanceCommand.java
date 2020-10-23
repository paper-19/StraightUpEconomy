package com.wasted_ticks.straightupeconomy.commands;

import com.wasted_ticks.straightupeconomy.StraightUpEconomy;
import org.bukkit.Bukkit;
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

            if(args.length != 0) {
                if(args.length == 1 && player.isOp()) {
                    String playerName = args[0];
                    if(null != Bukkit.getPlayer(playerName)) {
                        Player target = Bukkit.getPlayer(playerName);
                        double balance = plugin.getEconomy().getBalance(target);
                        player.sendMessage(ChatColor.DARK_GRAY + target.getDisplayName() + "'s balance is: " + balance + " Lapis");
                    } else {
                        player.sendMessage(ChatColor.DARK_GRAY + "Unable to resolve selected player.");
                        player.sendMessage(ChatColor.DARK_GRAY + "Usage:" + ChatColor.LIGHT_PURPLE + command.getUsage());
                    }
                } else {
                    player.sendMessage(ChatColor.DARK_GRAY + "Invalid amount of arguments provided.");
                    player.sendMessage(ChatColor.DARK_GRAY + "Usage:" + ChatColor.DARK_GRAY + command.getUsage());
                }
            } else {
                double balance = plugin.getEconomy().getBalance(player);
                player.sendMessage(ChatColor.DARK_GRAY + "Your current balance is: " + balance + " Lapis");
            }
        }
        return true;
    }
}