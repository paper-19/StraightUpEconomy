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
                        player.sendMessage(ChatColor.LIGHT_PURPLE + target.getDisplayName() + "'s balance is: " + balance + " ꜷ");
                    } else {
                        player.sendMessage(ChatColor.DARK_PURPLE + "Unable to resolve selected player.");
                    }
                } else {
                    player.sendMessage(ChatColor.DARK_PURPLE + "Usage:" + ChatColor.LIGHT_PURPLE + command.getUsage());
                }
            } else {
                double balance = plugin.getEconomy().getBalance(player);
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Your current balance is: " + balance + " ꜷ");
            }
        }
        return true;
    }
}