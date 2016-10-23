package com.shtyka.jdbc;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTemplate {
    private static Connection cn = null;
    public static Statement st = null;
    public static PreparedStatement preparedStatement = null;
    public JdbcTemplate() {
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        } catch (IOException e) {
            System.out.println("IOException e: DAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: DAO");
        }
    }
    public JdbcTemplate(String inquirySQL) {
        try {
            cn = DataSource.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(inquirySQL);
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        } catch (IOException e) {
            System.out.println("IOException e: DAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: DAO");
        }
    }
    public static void closeJdbcTemplate()  {
        try {
            st.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        }
    }
    public static void closeJdbcTemplatePrepStat()  {
        try {
            preparedStatement.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        }
    }
}
