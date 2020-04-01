package com.wasted_ticks.straightupeconomy;

import com.wasted_ticks.straightupeconomy.commands.BalanceCommand;
import com.wasted_ticks.straightupeconomy.commands.DepositCommand;
import com.wasted_ticks.straightupeconomy.commands.TransferCommand;
import com.wasted_ticks.straightupeconomy.commands.WithdrawCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class StraightUpEconomy extends JavaPlugin {

    private static StraightUpEconomy instance;
    private static final Logger logger = Logger.getLogger("Minecraft");
    private StorageManager storageManager;
    private SUSEconomy econ;

    public static StraightUpEconomy getInstance() {
        return instance;
    }

    public static Logger getLog() {
        return logger;
    }

    @Override
    public void onDisable() {
        storageManager.closeConnection();
    }

    @Override
    public void onEnable() {
        this.instance = this;

        this.econ = new SUSEconomy();
        this.storageManager = new StorageManager();

        this.getCommand("balance").setExecutor(new BalanceCommand(this));
        this.getCommand("deposit").setExecutor(new DepositCommand(this));
        this.getCommand("withdraw").setExecutor(new WithdrawCommand(this));
        this.getCommand("transfer").setExecutor(new TransferCommand(this));

        this.getServer().getServicesManager().register(Economy.class, econ, this, ServicePriority.Normal);

        RegisteredServiceProvider<Economy> economyProvider = this.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        logger.info("Hooked Vault");

    }

    public SUSEconomy getEconomy() {
        return this.econ;
    }

}
