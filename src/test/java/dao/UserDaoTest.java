package dao;

import bean.Users;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserDaoTest {
    UserDao userDao = new UserDao();

    @Test
    public void login() {
        String result = userDao.login("1234", "1234qwer").toString();
        System.out.println(result);
        assertEquals("loginTest", "id= 1234,name= 哈哈,password= null,friends= null", result);
    }

    @Test
    public void register() {
        Users user = new Users();
        user.setId("1235");
        user.setName("嘿嘿");
        user.setSignature("我是嘿嘿");
        System.out.println(userDao.register(user, "1234abc123"));
    }

    @Test
    public void getUserById() {
        System.out.println(userDao.getUserById("1234"));
    }

    @Test
    public void getUserByName() {
        System.out.println(userDao.getUserByName("哈哈"));
    }
}