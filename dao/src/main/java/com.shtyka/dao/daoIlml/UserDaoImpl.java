package com.shtyka.dao.daoIlml;


import com.shtyka.dao.UserDao;
import com.shtyka.entity.User;
import com.shtyka.jdbc.DataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    public static final String SQL_SELECT_ALL_CLIENTS = "SELECT * FROM client";
    public static final String SQL_SELECT_ALL_ORDERS = "SELECT menuru.price,`order`.status_order FROM menuru INNER JOIN `order` ON menuru.menu_id=`order`.menu_id INNER JOIN `client`ON `order`.client_id = `client`.client_id WHERE `client`.client_id =";

    public static final String SQL_DELETE_CLIENT = "DELETE FROM client WHERE client_id=";
    public boolean create(Object entity) throws SQLException {
        return false;
    }

    public Object read(int id) {
        return null;
    }

    public List update(Object entity) {
        List<User> clients = new ArrayList<User>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_CLIENTS);
            while (resultSet.next()) {
                User client = new User();
                client.setName(resultSet.getString("client_name"));
                client.setTableNumber(resultSet.getInt("table_number"));
                clients.add(client);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        } catch (IOException e) {
            System.out.println("IOException e: ClientDAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: ClientDAO");
        } finally {

            try {
                st.close();
                cn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return clients;
    }

    public boolean delete(Object entity) {
        return false;
    }

    public User findByLogin(String login) throws SQLException {
        Connection cn = null;
        Statement st = null;
        User user = new User();
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_CLIENTS);
            while (resultSet.next()) {

                if (resultSet.getString("client_name").equals(login)) {
                    user.setName(resultSet.getString("client_name"));
                    user.setTableNumber(resultSet.getInt("table_number"));
                    user.setId(resultSet.getInt("client_id"));
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        } catch (IOException e) {
            System.out.println("IOException e: ClientDAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: ClientDAO");
        } finally {
            st.close();
            try {
                cn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return user;
    }
    public List<User> findAll() throws SQLException {
        List<User> clients = new ArrayList< User>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_CLIENTS);
            while (resultSet.next()) {
                if(resultSet.getInt("role_id") == 2){
                    User client = new User();
                    client.setId(resultSet.getInt("client_id"));
                    client.setName(resultSet.getString("client_name"));
                    client.setTableNumber(resultSet.getInt("table_number"));
                    clients.add(client);
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        } catch (IOException e) {
            System.out.println("IOException e: ClientDAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: ClientDAO");
        } finally {
            st.close();
            try {
                cn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return clients;
    }
    public User findById(int id) {
        Connection cn = null;
        Statement st = null;
        User client= new User();
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_CLIENTS);
            while (resultSet.next()) {
                if (resultSet.getInt("client_id") == id) {
                    client.setId(resultSet.getInt("cient_id"));
                    client.setName(resultSet.getString("client_name"));
                    client.setTableNumber(resultSet.getInt("table_number"));
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        } catch (IOException e) {
            System.out.println("IOException e: ClientDAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: ClientDAO");
        } finally {
            try {
                st.close();
                cn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return client;
    }
    public int countOrder(User user) {
        Connection cn = null;
        Statement st = null;
        int sum = 0;
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_ORDERS + user.getId());
            while (resultSet.next()) {
                if (!resultSet.getString("status_order").equals("CHEKED")) {
                    sum += (resultSet.getInt("price"));
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        } catch (IOException e) {
            System.out.println("IOException e: ClientDAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: ClientDAO");
        } finally {

            try {
                st.close();
                cn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return sum;
    }
    public boolean deleteById(int id) {
        Connection cn = null;
        Statement st = null;
        try{
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            st.executeUpdate(SQL_DELETE_CLIENT+id);
        }catch(SQLException e){
            System.out.println("Request or table failed. Client not pay for order and clean.");
        } catch (IOException e) {
            System.out.println("IOException e: ClientDAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: ClientDAO");
        }finally{

            try {
                st.close();
                cn.close();
            } catch (SQLException e) {
                System.out.println("Request or table failed.");
            }
        }
        return false;
    }
}
