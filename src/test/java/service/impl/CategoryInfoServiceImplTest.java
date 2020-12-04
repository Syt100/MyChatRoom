package service.impl;

import bean.Friend;
import bean.Message;
import dao.CategoryInfoDao;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.CategoryInfoService;
import util.MybatisUtils;

import java.util.List;
import java.util.Map;

public class CategoryInfoServiceImplTest {

    CategoryInfoServiceImpl service = new CategoryInfoServiceImpl();

    @Before
    public void setUp() throws Exception {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        CategoryInfoDao dao = sqlSession.getMapper(CategoryInfoDao.class);
        service.setCategoryInfoDao(dao);
    }

    @Test
    public void addCategory() {
        Message message = service.addCategory("1234", "同学");
        System.out.println(message);
    }

    @Test
    public void deleteCategory() {
        Message message = service.deleteCategory("7");
        System.out.println(message);
    }

    @Test
    public void getFriends() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        CategoryInfoService categoryInfoService = context.getBean(CategoryInfoService.class);
        Map<Integer, List<Friend>> map = categoryInfoService.getFriends("1234");
//        Map<Integer, List<Friend>> map = service.getFriends("1234");
        map.forEach((integer, friends1) -> System.out.println(integer + ":" + friends1));
    }
}