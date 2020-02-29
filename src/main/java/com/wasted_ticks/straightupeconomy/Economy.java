package com.wasted_ticks.straightupeconomy;

import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

public class Economy {

    public boolean hasAccount(OfflinePlayer player) {
        return StorageManager.hasAccount(player.getUniqueId());
    }

    public double getBalance(OfflinePlayer player) {
        if(!this.hasAccount(player)) {
            this.createPlayerAccount(player);
            return 0.0;
        } else {
            return StorageManager.getBalance(player.getUniqueId());
        }
    }

    /**
     *
     * @param player
     * @param amount
     * @return
     */
    public boolean has(OfflinePlayer player, double amount) { return this.getBalance(player) >= amount; }

    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        if(this.has(player, amount)) {
            StorageManager.withdraw(player.getUniqueId(), amount);
            double balance = this.getBalance(player);
            return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.SUCCESS, "");
        } else {
            double balance = this.getBalance(player);
            return new EconomyResponse(balance, balance, EconomyResponse.ResponseType.FAILURE, "Insufficent balance to cover transaction.");
        }
    }

    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        double balance = this.getBalance(player);
        StorageManager.deposit(player.getUniqueId(), amount);
        return new EconomyResponse(amount, amount + balance, EconomyResponse.ResponseType.SUCCESS, "");
    }

    public boolean createPlayerAccount(OfflinePlayer player) {
        if(!this.hasAccount(player)) {
            StorageManager.createAccount(player.getUniqueId());
        }
        return true;
    }
}
