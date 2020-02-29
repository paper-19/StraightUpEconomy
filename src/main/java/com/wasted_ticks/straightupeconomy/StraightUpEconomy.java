package com.wasted_ticks.straightupeconomy;

import com.wasted_ticks.straightupeconomy.commands.BalanceCommand;
import com.wasted_ticks.straightupeconomy.commands.DepositCommand;
import com.wasted_ticks.straightupeconomy.commands.WithdrawCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class StraightUpEconomy extends JavaPlugin {

    private static StraightUpEconomy instance;
    private static final Logger logger = Logger.getLogger("Minecraft");
    private StorageManager storageManager;
    private Economy econ;

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

        this.econ = new Economy();
        this.storageManager = new StorageManager();

        this.getCommand("balance").setExecutor(new BalanceCommand(this));
        this.getCommand("deposit").setExecutor(new DepositCommand(this));
        this.getCommand("withdraw").setExecutor(new WithdrawCommand(this));

    }

    public Economy getEconomy() {
        return this.econ;
    }

}
