package com.shtyka.dao.daoIlml;

import com.shtyka.dao.MenuDao;
import com.shtyka.entity.Menu;
import com.shtyka.jdbc.JdbcTemplate;
import com.shtyka.util.ManagerSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuDaoImpl extends MenuDao<Menu> {

    private static final String SQL_SELECT_ALL_MENU = ManagerSQL.getProperty("SQL_SELECT_ALL_MENURU");
    private static final String SQL_SELECT_ALL_MENUEN = ManagerSQL.getProperty("SQL_SELECT_ALL_MENUEN");
//    private static final String SQL_CREATE_NEW_MENURU = ManagerSQL.getProperty("SQL_CREATE_NEW_MENURU");
//    private static final String SQL_CREATE_NEW_MENUEN = ManagerSQL.getProperty("SQL_CREATE_NEW_MENUEN");

    public Menu findEntityById(int id) throws SQLException {
        JdbcTemplate jc = new JdbcTemplate();
        Menu menu = new Menu();
        ResultSet resultSet;
        if (Locale.getDefault().equals("RU")) {
            resultSet = JdbcTemplate.st.executeQuery(SQL_SELECT_ALL_MENU);
        } else {
            resultSet = JdbcTemplate.st.executeQuery(SQL_SELECT_ALL_MENUEN);
        }
        while (resultSet.next()) {
            if (resultSet.getInt("menu_id") == id) {
                menu.setMealName(resultSet.getString("meal_name"));
                menu.setMenuId(resultSet.getInt("menu_id"));
                menu.setPrice(resultSet.getInt("price"));
                menu.setWeight(resultSet.getInt("weight"));
            }
        }
        resultSet.close();
        JdbcTemplate.closeJdbcTemplate();
        return menu;
    }

    public List<Menu> findAll() throws SQLException {
        List<Menu> menu = new ArrayList<Menu>();
        JdbcTemplate jc = new JdbcTemplate();
        ResultSet resultSet = JdbcTemplate.st.executeQuery(SQL_SELECT_ALL_MENU);
        while (resultSet.next()) {
            Menu menuClient = new Menu();
            menuClient.setMenuId(resultSet.getInt("menu_id"));
            menuClient.setMealName(resultSet.getString("meal_name"));
            menuClient.setPrice(resultSet.getInt("price"));
            menuClient.setWeight(resultSet.getInt("weight"));
            menu.add(menuClient);
        }
        resultSet.close();
        jc.closeJdbcTemplate();
        return menu;
    }


//    public boolean create(Menu menu) {
//        Connection cn = null;
//        PreparedStatement st = null;
//        PreparedStatement stat = null;
//        String[] poiskNameMeal = menu.getMealName().split("-");
//        try {
//            cn = DataSource.getInstance().getConnection();
//            st = cn.prepareStatement(SQL_CREATE_NEW_MENUEN);
//            st.setString(1, poiskNameMeal[0]);
//            st.setInt(2, menu.getPrice());
//            st.setInt(3, menu.getWeight());
//            st.execute();
//            stat = cn.prepareStatement(SQL_CREATE_NEW_MENURU);
//            stat.setString(1, poiskNameMeal[1]);
//            stat.setInt(2, menu.getPrice());
//            stat.setInt(3, menu.getWeight());
//            stat.execute();
//        } catch (SQLException e) {
//            System.out.println("Request or table failed.");
//        } catch (IOException e) {
//            System.out.println("IOException e: ClientDAO");
//        } catch (PropertyVetoException e) {
//            System.out.println("PropertyVetoException e: ClientDAO");
//        } finally {
//            try {
//                st.close();
//                stat.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                cn.close();
//            } catch (SQLException e) {
//                System.out.println("Request or table failed.");
//            }
//        }
//
//        return false;
//    }
}