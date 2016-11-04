package com.shtyka.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.shtyka.util.ManagerJDBC;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static DataSource datasource;
    private final ComboPooledDataSource cpds;
    private DataSource() throws IOException, SQLException, PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass(ManagerJDBC.getProperty("driverDB"));
        cpds.setJdbcUrl(ManagerJDBC.getProperty("url"));
        cpds.setUser(ManagerJDBC.getProperty("loginDB"));
        cpds.setPassword(ManagerJDBC.getProperty("passwordDB"));
        cpds.setMinPoolSize(Integer.parseInt(ManagerJDBC.getProperty("minPoolSize")));
        cpds.setAcquireIncrement(Integer.parseInt(ManagerJDBC.getProperty("acquireIncrement")));
        cpds.setMaxPoolSize(Integer.parseInt(ManagerJDBC.getProperty("maxPoolSize")));
        cpds.setMaxStatements(Integer.parseInt(ManagerJDBC.getProperty("maxStatements")));
    }
    public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException {
        if (datasource == null) {
            datasource = new DataSource();
            return datasource;
        } else {
            return datasource;
        }
    }
    public Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }
}
