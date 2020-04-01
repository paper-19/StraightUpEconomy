package com.wasted_ticks.straightupeconomy.commands;

import com.wasted_ticks.straightupeconomy.StraightUpEconomy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TransferCommand implements CommandExecutor {

    private final StraightUpEconomy plugin;

    public TransferCommand(StraightUpEconomy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {

            Player player = (Player) sender;

            if(args.length != 2) {
                player.sendMessage(command.getUsage());
            } else {
                try {

                    int amount = Integer.parseInt(args[1]);
                    if (amount < 1) {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Invalid amount requested to transfer.");
                        return true;
                    }

                    String requestedPlayerName = args[0];
                    Player requestedPlayer = plugin.getServer().getPlayer(requestedPlayerName);
                    if(requestedPlayer == null) {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Unable to find player: " + requestedPlayerName);
                        return true;
                    }

                    if(this.plugin.getEconomy().has(player, amount) || player.isOp()) {

                        if(!player.isOp()) {
                            this.plugin.getEconomy().withdrawPlayer(player, amount);
                        }
                        this.plugin.getEconomy().depositPlayer(requestedPlayer, amount);

                        if(player.isOp()) {
                            double balance = plugin.getEconomy().getBalance(requestedPlayer);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Deposited $" + amount + " into player account: "+ requestedPlayerName);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Their current balance is: " + balance + " ꜷ");
                        } else {
                            double balance = plugin.getEconomy().getBalance(player);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Your current balance is: " + balance + " ꜷ");
                        }
                    } else {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "You must have sufficient gold ingots to transfer.");
                    }

                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Unable to parse integer.");
                    player.sendMessage(ChatColor.DARK_PURPLE + "Usage:" + ChatColor.LIGHT_PURPLE + command.getUsage());
                }
            }
        }
        return true;
    }
}
