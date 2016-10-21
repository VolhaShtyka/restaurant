package com.shtyka.dao.daoIlml;

import com.shtyka.dao.MenuDao;
import com.shtyka.entity.Menu;
import com.shtyka.jdbc.DataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuDaoImpl implements MenuDao {

    public static final String SQL_SELECT_ALL_MENU = "SELECT * FROM menu";
    public Menu read(int id) {
        return null;
    }

    public boolean delete(Menu entity) {
        return false;
    }
    public Menu findEntityById(int id) {
        Connection cn = null;
        Statement st = null;
        Menu menu = new Menu();
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_MENU+Locale.getDefault().getLanguage());
            while (resultSet.next()) {
                if (resultSet.getInt("menu_id") == id) {
                    menu.setMealName(resultSet.getString("meal_name"));
                    menu.setMenuId(id);
                    menu.setPrice(resultSet.getInt("price"));
                    menu.setWeight(resultSet.getInt("weight"));
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
                System.out.println("Request or table failed.");
            }
        }
        return menu;
    }

    public List<Menu> findAll() {
        List<Menu> menu = new ArrayList<Menu>();
        Connection cn = null;
        Statement st = null;
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_MENU+ Locale.getDefault().getLanguage());
            while (resultSet.next()) {
                Menu menuClient = new Menu();
                menuClient.setMenuId(resultSet.getInt("menu_id"));
                menuClient.setMealName(resultSet.getString("meal_name"));
                menuClient.setPrice(resultSet.getInt("price"));
                menuClient.setWeight(resultSet.getInt("weight"));
                menu.add(menuClient);
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
        return menu;
    }

    public Menu findByLogin(String login) {
        return null;
    }

    public int countOrder(MenuDao t) {
        return 0;
    }

    public boolean deleteById(int id) {
        return false;
    }

    public Menu findById(int id) {
        Connection cn = null;
        Statement st = null;
        Menu menu = new Menu();
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.createStatement();
            ResultSet resultSet = st.executeQuery(SQL_SELECT_ALL_MENU+Locale.getDefault().getLanguage());
            while (resultSet.next()) {
                if (resultSet.getInt("menu_id") == id) {
                    menu.setMealName(resultSet.getString("meal_name"));
                    menu.setMenuId(id);
                    menu.setPrice(resultSet.getInt("price"));
                    menu.setWeight(resultSet.getInt("weight"));
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
                System.out.println("Request or table failed.");
            }
        }
        return menu;
    }

    public boolean delete(MenuDao entity) {
        return false;
    }

    public boolean create(Menu menu) throws SQLException {
        String SQL_CREATE_NEW_MENURU = "INSERT INTO `restoraut`.`menuru` (`meal_name`, `price`, `weight`) VALUES (?, ?, ?);";
        String SQL_CREATE_NEW_MENUEN = "INSERT INTO `restoraut`.`menuen` (`meal_name`, `price`, `weight`) VALUES (?, ?, ?);";
        Connection cn = null;
        PreparedStatement st = null;
        PreparedStatement stat = null;
        String[] poiskNameMeal = menu.getMealName().split("-");
        try {
            cn = DataSource.getInstance().getConnection();
            st = cn.prepareStatement(SQL_CREATE_NEW_MENUEN);
            st.setString(1, poiskNameMeal[0]);
            st.setInt(2, menu.getPrice());
            st.setInt(3, menu.getWeight());
            st.execute();
            stat = cn.prepareStatement(SQL_CREATE_NEW_MENURU);
            stat.setString(1,  poiskNameMeal[1]);
            stat.setInt(2, menu.getPrice());
            stat.setInt(3, menu.getWeight());
            stat.execute();
        } catch (SQLException e) {
            System.out.println("Request or table failed.");
        } catch (IOException e) {
            System.out.println("IOException e: ClientDAO");
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException e: ClientDAO");
        } finally {
            st.close();
            stat.close();
            try {
                cn.close();
            } catch (SQLException e) {
                System.out.println("Request or table failed.");
            }
        }

        return false;
    }

    public List<Menu> update(Menu entity) {
        return null;
    }
}
