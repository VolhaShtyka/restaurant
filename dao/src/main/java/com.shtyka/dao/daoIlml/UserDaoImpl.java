package com.shtyka.dao.daoIlml;


import com.shtyka.dao.UserDao;
import com.shtyka.entity.User;
import com.shtyka.jdbc.DataSource;
import com.shtyka.jdbc.JdbcTemplate;
import com.shtyka.util.ManagerSQL;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final String SQL_SELECT_ALL_CLIENTS = ManagerSQL.getProperty("SQL_SELECT_ALL_CLIENTS");
    private static final String SQL_SELECT_ALL_ORDERS = ManagerSQL.getProperty("SQL_SELECT_ALL_ORDERS");
    private static final String SQL_DELETE_CLIENT = ManagerSQL.getProperty("SQL_DELETE_CLIENT");


    public List<User> update(User entity) throws SQLException {
        List<User> users = new ArrayList<User>();
        JdbcTemplate jc = new JdbcTemplate();
        ResultSet resultSet = JdbcTemplate.st.executeQuery(SQL_SELECT_ALL_CLIENTS);
        while (resultSet.next()) {
            User user = new User();
            user.setName(resultSet.getString("client_name"));
            user.setTableNumber(resultSet.getInt("table_number"));
            users.add(user);
        }
        resultSet.close();
        jc.closeJdbcTemplate();
        return users;
    }

    public User findByLogin(String login) throws SQLException {
        JdbcTemplate jc = new JdbcTemplate();
        User user = new User();
        ResultSet resultSet = JdbcTemplate.st.executeQuery(SQL_SELECT_ALL_CLIENTS);
        while (resultSet.next()) {

            if (resultSet.getString("client_name").equals(login)) {
                user.setName(resultSet.getString("client_name"));
                user.setTableNumber(resultSet.getInt("table_number"));
                user.setId(resultSet.getInt("client_id"));
            }
        }
        resultSet.close();
        jc.closeJdbcTemplate();
        return user;
    }

    public List<User> findAll() throws SQLException {
        List<User> clients = new ArrayList<User>();
        JdbcTemplate jc = new JdbcTemplate();
        ResultSet resultSet = JdbcTemplate.st.executeQuery(SQL_SELECT_ALL_CLIENTS);
        while (resultSet.next()) {
            if (resultSet.getInt("role_id") == 2) {
                User client = new User();
                client.setId(resultSet.getInt("client_id"));
                client.setName(resultSet.getString("client_name"));
                client.setTableNumber(resultSet.getInt("table_number"));
                clients.add(client);
            }
        }
        resultSet.close();
        jc.closeJdbcTemplate();
        return clients;
    }

    public User findById(int id) throws SQLException {
        JdbcTemplate jc = new JdbcTemplate();
        User user = new User();
        ResultSet resultSet = JdbcTemplate.st.executeQuery(SQL_SELECT_ALL_CLIENTS);
        while (resultSet.next()) {
            if (id == resultSet.getInt("client_id")) {
                user.setName(resultSet.getString("client_name"));
                user.setTableNumber(resultSet.getInt("table_number"));
                user.setId(resultSet.getInt("client_id"));
            }
            }
            resultSet.close();
            JdbcTemplate.closeJdbcTemplate();
            return user;
        }

    public int countOrder(User user) throws SQLException {
        int sum = 0;
        JdbcTemplate jc = new JdbcTemplate();
        ResultSet resultSet = JdbcTemplate.st.executeQuery(SQL_SELECT_ALL_ORDERS + user.getId());
        while (resultSet.next()) {
            if (!resultSet.getString("status_order").equals("CHEKED")) {
                sum += (resultSet.getInt("price"));
            }
        }
        resultSet.close();
        JdbcTemplate.closeJdbcTemplate();
        return sum;
    }

    public boolean delete(int id) {
        Connection cn = null;
        Statement st = null;
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            st.executeUpdate(SQL_DELETE_CLIENT + id);
        } catch (SQLException e) {
            System.out.println("Request or table failed. Client not pay for order and clean.");
        } catch (IOException e) {
            System.out.println("IOException e: ClientDAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: ClientDAO");
        } finally {

            try {
                st.close();
                cn.close();
            } catch (SQLException e) {
                System.out.println("Request or table failed.");
            }
        }
        return false;
    }

    public boolean create(User entity) {
        return false;
    }

    public User read(int id) {
        return null;
    }
}
