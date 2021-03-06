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
                        player.sendMessage(ChatColor.DARK_GRAY + "Invalid amount requested to transfer.");
                        return true;
                    }

                    String requestedPlayerName = args[0];
                    Player requestedPlayer = plugin.getServer().getPlayer(requestedPlayerName);
                    if(requestedPlayer == null) {
                        player.sendMessage(ChatColor.DARK_GRAY + "Unable to find player: " + requestedPlayerName);
                        return true;
                    }

                    if(this.plugin.getEconomy().has(player, amount) || player.isOp()) {

                        if(!player.isOp()) {
                            this.plugin.getEconomy().withdrawPlayer(player, amount);
                            double balance = plugin.getEconomy().getBalance(player);
                            player.sendMessage(ChatColor.DARK_GRAY + "Your current balance is: " + balance + " Lapis");

                        }
                        this.plugin.getEconomy().depositPlayer(requestedPlayer, amount);
                        if(requestedPlayer.isOnline()) {
                            requestedPlayer.sendMessage(ChatColor.DARK_GRAY + player.getName() + " transferred " + amount + " Lapis into your account.");
                        }

                        if(player.isOp()) {
                            double balance = plugin.getEconomy().getBalance(requestedPlayer);
                            player.sendMessage(ChatColor.DARK_GRAY + "Deposited " + amount + " Lapis into player account: "+ requestedPlayerName);
                            player.sendMessage(ChatColor.DARK_GRAY + "Their current balance is: " + balance + " Lapis");
                        }
                    } else {
                        player.sendMessage(ChatColor.DARK_GRAY + "You must have sufficient lapis to transfer.");
                    }

                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.DARK_GRAY + "Unable to parse integer.");
                    player.sendMessage(ChatColor.DARK_GRAY + "Usage:" + ChatColor.DARK_GRAY + command.getUsage());
                }
            }
        }
        return true;
    }
}
