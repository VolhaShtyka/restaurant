package com.shtyka.dao.daoIlml;

import com.shtyka.entity.User;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UserDaoImplTest {
    @Test
    public void findByLogin() throws Exception {
            UserDaoImpl user = new UserDaoImpl();
            User userOne = user.findByLogin("Katy");
            User userTwo = new User();
            userTwo.setName("Katy");

            userTwo.setTableNumber(8);
        userTwo.setId(2);
        //userTwo.setRoleId(2);
        //userTwo.setPassword("1587");
        //userTwo.setRoleName("user");
        assertNotNull(userOne);
           // assertEquals(userTwo, userOne);
        }

    }