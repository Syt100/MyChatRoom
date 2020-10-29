package dao;

import org.junit.Test;

public class UserDaoTest {

    @Test
    public void login() {
        UserDao userDao = new UserDao();
        System.out.println(userDao.login("1234", "1234qwer"));
    }
}