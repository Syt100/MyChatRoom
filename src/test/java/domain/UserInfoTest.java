package domain;

import com.github.pagehelper.PageHelper;
import dao.UserInfoDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import util.MybatisUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserInfoTest {

    @Test
    public void testSelectUserInfo() throws IOException {
        // 访问mybatis读取user_info表中的数据
        // 1. 定义mybatis主配置文件的名称，从类路径的根开始(target/classes)
        String config = "mybatis.xml";
        // 2. 读取这个config表示的文件
        InputStream in = Resources.getResourceAsStream(config);
        // 3. 创建SQLSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 4. 创建SqlSessionFactory对象
        SqlSessionFactory factory = builder.build(in);
        // 5. 【重要】获取SqlSession对象，从SqlSessionFactory中获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // 6. 【重要】指定要执行的SQL语句的标识。SQL映射文件中的namespace+"."+标签的id值
        String sqlId = "dao.UserInfoDao" + "." + "selectAllUserInfo";
        // 7. 执行SQL语句，通过sqlId找到语句
        List<UserInfo> userInfoList = sqlSession.selectList(sqlId);
        // 8. 输出结果
        userInfoList.forEach(System.out::println);
        // 9. 关闭SqlSession
        sqlSession.close();

    }

    @Test
    public void testInsertUserInfo() throws IOException {
        // 1. 定义mybatis主配置文件的名称，从类路径的根开始(target/classes)
        String config = "mybatis.xml";
        // 2. 读取这个config表示的文件
        InputStream in = Resources.getResourceAsStream(config);
        // 3. 创建SQLSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 4. 创建SqlSessionFactory对象
        SqlSessionFactory factory = builder.build(in);
        // 5. 【重要】获取SqlSession对象，从SqlSessionFactory中获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // 6. 【重要】指定要执行的SQL语句的标识。SQL映射文件中的namespace+"."+标签的id值
        String sqlId = "dao.UserInfoDao" + "." + "insertUserInfo";
        // 7. 执行SQL语句，通过sqlId找到语句
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(1237);
        userInfo.setUser_name("测试1237");
        userInfo.setUser_password("1234qw7");
        userInfo.setUser_signature("我是测试1237");
        // 第一个参数为sqlId，第二个为参数
        int nums = sqlSession.insert(sqlId, userInfo);
        // mybatis不是默认提交事务的，在insert update delete 后手动提交事务
        sqlSession.commit();
        // 8. 输出结果
        System.out.println("执行insert结果" + nums);
        // 9. 关闭SqlSession
        sqlSession.close();
    }

    @Test
    public void testMybatis01() {
        // 测试封装MybatisUtils工具类
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        String sqlId = "dao.UserInfoDao.selectAllUserInfo";
        List<UserInfo> userInfoList = sqlSession.selectList(sqlId);
        userInfoList.forEach(System.out::println);
        // 关闭SqlSession
        sqlSession.close();
    }

    @Test
    public void testMybatis02() {
        // 测试Mybatis使用动态代理获取Dao
        // 使用Mybatis的动态代理方式，使用SQLSession.getMapper(dao接口)能够获取dao接口对应的实现类对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserInfoDao dao = sqlSession.getMapper(UserInfoDao.class);
        dao.selectAllUserInfo().forEach(System.out::println);
        System.out.println(dao.selectUserInfoByUserId(1234));
        System.out.println(dao.selectUserInfoByUserIdPassword(
                1234, "1234qwer"
        ));
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUser_id(1238);
//        userInfo.setUser_name("测试1238");
//        userInfo.setUser_password("1234qw8");
//        userInfo.setUser_signature("我是测试1238");
//        dao.insertUserInfo(userInfo);
//        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDeleteUserInfo() {
        // 测试插入和删除
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserInfoDao dao = sqlSession.getMapper(UserInfoDao.class);

        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(1238);
        userInfo.setUser_name("测试1238");
        userInfo.setUser_password("1234qw8");
        userInfo.setUser_signature("我是测试1238");
        System.out.println(dao.insertUserInfo(userInfo));
//        System.out.println(dao.deleteUserInfo(1238));
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectUserInfoByName() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserInfoDao dao = sqlSession.getMapper(UserInfoDao.class);
        dao.selectUserInfoByName("测试").forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testPageHelper() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserInfoDao dao = sqlSession.getMapper(UserInfoDao.class);
        // 使用PageHelper指定页码与每页数量
        PageHelper.startPage(2, 2);
        dao.selectAllUserInfo().forEach(System.out::println);
        sqlSession.close();
    }
}