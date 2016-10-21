package com.shtyka.dao.daoIlml;

import com.shtyka.dao.OrderDao;
import com.shtyka.entity.Menu;
import com.shtyka.entity.Order;
import com.shtyka.entity.StatusMeal;
import com.shtyka.entity.User;
import com.shtyka.jdbc.DataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class OrderDaoImpl implements OrderDao{
    public static final String SQL_SELECT_ALL_ORDER = "SELECT * FROM `order`;";
    public static final String SQL_DELETE_ALL_ORDER = "DELETE FROM `restoraut`.`order`;";
    public static final String SQL_DELETE_ORDER = "DELETE FROM `restoraut`.`order` WHERE order_id=";
    public static final String SQL_UPDATE_ALL_ORDER = "UPDATE `restoraut`.`order` SET `status_order`= ?;";
    public static final String SQL_CREATE_NEW_ORDER = "INSERT INTO `restoraut`.`order` (`client_id`, `status_order`, `menu_id`) VALUES (?, ?, ?);";

    public boolean create(Object entity) throws SQLException {
        return false;
    }

    public Object read(int id) {
        return null;
    }

    public List update(Object entity) {
        return null;
    }

    public boolean delete(Object entity) {
        return false;
    }
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<Order>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_ORDER);
            while (resultSet.next() && resultSet.getInt("order_id")>0) {
                Order orderClient = new Order();
                orderClient.setClientId(resultSet.getInt("client_id"));
                orderClient.setOrderId(resultSet.getInt("order_id"));
                orderClient.setStatusOrder(StatusMeal.propert.getString(resultSet.getString("status_order")));
                orders.add(orderClient);
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
                System.out.println("Request or table failed.");
            }
        }
        return orders;
    }


    public List<Order> findOrderClient(int id) {
        //ResourceBundle property = ResourceBundle.getBundle("properties/" + Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
        String SQL_SELECT_ALL_MEALNAME = "SELECT `order`.*,menu"+Locale.getDefault().getLanguage()+".meal_name FROM menu"+Locale.getDefault().getLanguage()+" INNER JOIN `order` ON menu"+ Locale.getDefault().getLanguage()+".menu_id=`order`.menu_id INNER JOIN `client`ON `order`.client_id = `client`.client_id WHERE `client`.client_id = ";
        List<Order> orders = new ArrayList<Order>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_MEALNAME+id);
            while (resultSet.next()) {
                Order order = new Order();
                order.setClientId(resultSet.getInt("client_id"));
                order.setOrderId(resultSet.getInt("order_id"));
                order.setStatusOrder("("+resultSet.getString("meal_name") + ") ");//+ property.getString(resultSet.getString("status_order")));
                orders.add(order);
            }
           // ResourceBundle.clearCache();
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
                System.out.println("Request or table failed.");
            }
        }
        return orders;
    }

    public boolean delete(int id) {
        Connection cn = null;
        Statement st = null;
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            st.executeUpdate(SQL_DELETE_ORDER+id);
            st.close();
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        } catch (IOException e) {
            System.out.println("IOException e: ClientDAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: ClientDAO");
        } finally {

            try {
                cn.close();
            } catch (SQLException e) {
                System.out.println("Request or table failed.");
            }
        }
        return false;
    }


    public boolean delete() {
        Connection cn = null;
        Statement st = null;
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            st.executeUpdate(SQL_DELETE_ALL_ORDER);
            st.close();
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        } catch (IOException e) {
            System.out.println("IOException e: ClientDAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: ClientDAO");
        } finally {
            try {
                cn.close();
            } catch (SQLException e) {
                System.out.println("Request or table failed.");
            }
        }
        return false;
    }


    public boolean create(Order order) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.prepareStatement(SQL_CREATE_NEW_ORDER);
            st.setInt(1, order.getOrderId());
            st.setInt(2, order.getClientId());
            st.setString(3, order.getStatusOrder());
            return st.execute();
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
                System.out.println("Request or table failed.");
            }
        }
        return false;
    }

    public Order update(String status) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.prepareStatement(SQL_UPDATE_ALL_ORDER);
            st.setString(1, status);
            st.executeUpdate();
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
                System.out.println("Request or table failed.");
            }
        }
        return null;
    }


    public Order createByMenu(Menu menu, User user) throws SQLException {
        Connection cn = null;
        PreparedStatement st = null;
        Order order = new Order();
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.prepareStatement(SQL_CREATE_NEW_ORDER);
            st.setInt(1, user.getId());
            st.setString(2, StatusMeal.NOREADY.name());
            st.setInt(3,menu.getMenuId());
            st.execute();
            order.setClientId(user.getId());
            order.setStatusOrder(StatusMeal.NOREADY.getValueEnum());
            order.setMenuId(menu);
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        } catch (IOException e) {
            System.out.println("IOException e: ClientDAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: ClientDAO");
        } finally {
            try {
                cn.close();
            } catch (SQLException e) {
                System.out.println("Request or table failed.");
            }
            st.close();
        }
        return order;
    }
}
