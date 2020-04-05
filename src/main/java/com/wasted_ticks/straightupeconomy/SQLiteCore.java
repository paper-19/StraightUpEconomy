package com.wasted_ticks.straightupeconomy;

import java.io.File;
import java.sql.*;
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
                log.severe("Failed to close database connection! " + e.getMessage());
            }
        }
    }

    public boolean existsTable(String table) {
        try {
            ResultSet tables = getConnection().getMetaData().getTables(null, null, table, null);
            return tables.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean executeUpdate(String query) {
        Statement statement = null;
        try {
            statement = this.getConnection().createStatement();
            statement.executeUpdate(query);
            statement.close();
            return true;
        } catch (SQLException e) {
            log.severe("Failed to execute non-select : ||| " + query + " |||");
            return false;
        }
    }


    public ResultSet executeQuery(String query) {
        try {
            return getConnection().createStatement().executeQuery(query);
        } catch (SQLException ex) {
            log.severe("Failed to execute select : ||| " + query + " |||");
        }
        return null;

    }
}
