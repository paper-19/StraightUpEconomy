package com.wasted_ticks.straightupeconomy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StorageManager {

    private StraightUpEconomy plugin;
    private static SQLiteCore core;

    public StorageManager() {
        plugin = StraightUpEconomy.getInstance();
        this.initiate();
    }

    public static double getBalance(UUID uniqueId) {
        String query = "SELECT * FROM 'sus__economy_accounts' WHERE mojang_uuid = '" + uniqueId.toString() + "';";
        ResultSet rs = core.executeQuery(query);
        if(rs != null) {
            try {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            } catch(SQLException e) { }
        }
        return 0.0;
    }

    public static void createAccount(UUID uniqueId) {
        String query = "INSERT INTO 'sus__economy_accounts' (mojang_uuid, balance) "
                    + " VALUES ('" + uniqueId + "', 0.0); ";
        core.execute(query);
    }

    public static void withdraw(UUID uniqueId, double amount) {
        double balance = getBalance(uniqueId);
        double newBalance = balance - amount;

        String query = "UPDATE 'sus__economy_accounts' SET balance = " + newBalance + " WHERE mojang_uuid = '" + uniqueId.toString() + "';";
        core.execute(query);
    }

    public static void deposit(UUID uniqueId, double amount) {
        double balance = getBalance(uniqueId);
        double newBalance = balance + amount;

        String query = "UPDATE 'sus__economy_accounts' SET balance = " + newBalance + " WHERE mojang_uuid = '" + uniqueId.toString() + "';";
        core.execute(query);
    }

    public void initiate() {
        core = new SQLiteCore(plugin.getDataFolder().getPath());

        if(core.getConnection() != null) {
            if(!core.existsTable("sus__economy_accounts")) {
                String query = "CREATE TABLE IF NOT EXISTS `sus__economy_accounts` ("
                            + " `mojang_uuid` VARCHAR(255), "
                            + " `balance` double(64,2), "
                            + " `date_created` TEXT, "
                            + " `last_deposit_value` double(64,2), "
                            + " `last_deposit_date` TEXT, "
                            + " `last_withdraw_value` double(64,2), "
                            + " `last_withdraw_date` TEXT, "
                            + " PRIMARY KEY (`mojang_uuid`));";
                core.execute(query);
            }
        }
    }

    public void closeConnection() {
        core.close();
    }
    

    public static boolean hasAccount(UUID uniqueId) {
        String query = "SELECT * FROM 'sus__economy_accounts' WHERE mojang_uuid = '" + uniqueId.toString() + "';";
        ResultSet rs = core.executeQuery(query);
        if(rs != null) {
            try {
                if (rs.next()) {
                    return true;
                }
            } catch(SQLException e) { }
        }
        return false;
    }

}
