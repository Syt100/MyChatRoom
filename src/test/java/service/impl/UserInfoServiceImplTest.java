package service.impl;

import bean.Message;
import bean.Users;
import dao.UserInfoDao;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import util.MybatisUtils;

public class UserInfoServiceImplTest {

    UserInfoServiceImpl userInfoService;

    @Before
    public void setUp() throws Exception {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserInfoDao dao = sqlSession.getMapper(UserInfoDao.class);
        userInfoService = new UserInfoServiceImpl();
        userInfoService.setUserInfoDao(dao);
    }

    @Test
    public void login() {
        // 能够登陆成功
        Users users = userInfoService.login(1234, "1234qwer");
        // 不能登陆成功
        Users users1 = userInfoService.login(1234, "1234");
        System.out.println(users);
        System.out.println(users1);
    }

    @Test
    public void register() {
        Users users = new Users();
        users.setId("1239");
        users.setName("测试用户1239");
        users.setSignature("我是测试用户1239");
        // 事务不是自动提交
        Message message = userInfoService.register(users, "1239pass");
        System.out.println(message);
    }

    @Test
    public void getUserById() {
        System.out.println(userInfoService.getUserById("1234"));
    }

    @Test
    public void getUserByName() {
        System.out.println(userInfoService.getUserByName("嘿嘿"));
    }

    @Test
    public void writtenOffUser() {
        Message message = userInfoService.writtenOffUser(1238);
        System.out.println(message);
    }
}