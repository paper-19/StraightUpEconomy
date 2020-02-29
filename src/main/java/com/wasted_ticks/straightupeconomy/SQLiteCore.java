package com.wasted_ticks.straightupeconomy;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class SQLiteCore {

    private Connection connection;
    private String location;
    private String database;
    private File file;
    private Logger log;

    public SQLiteCore(String location) {
        this.location = location;
        this.database = "StraightUpEconomy";
        this.log = StraightUpEconomy.getLog();
        this.initialize();
    }

    private void initialize() {
        if(this.file == null) {
            File folder = new File(this.location);

            if(!folder.exists()) {
                folder.mkdir();
            }

            this.file = new File(folder.getAbsolutePath() + File.separator + database + ".db");
        }

        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.file.getAbsolutePath());
        } catch (SQLException e) {
        }
    }

    public Connection getConnection() {
        if(this.connection == null) {
            this.initialize();
        }
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean execute(String query) {
        try {
            this.getConnection().createStatement().execute(query);
            return true;
        } catch (SQLException ex) {
            log.severe("Failed to execute query : ||| " + query + " |||");
            return null;
        }
    }

    public Boolean existsTable(String table) {
        try {
            ResultSet tables = getConnection().getMetaData().getTables(null, null, table, null);
            return tables.next();
        } catch (SQLException e) {
            return false;
        }
    }


    public ResultSet executeQuery(String query) {
        try {
            ResultSet rs = getConnection().createStatement().executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            log.severe("Failed to execute query : ||| " + query + " |||");
        }
        return null;

    }
}
