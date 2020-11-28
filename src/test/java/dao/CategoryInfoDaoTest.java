package dao;

import bean.Friend;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import util.MybatisUtils;

import java.util.List;

public class CategoryInfoDaoTest {

    @Test
    public void selectCategory() {
    }

    @Test
    public void selectFriend() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        CategoryInfoDao dao = sqlSession.getMapper(CategoryInfoDao.class);
        List<Friend> friends = dao.selectFriend(1234);
        sqlSession.close();
        friends.forEach(System.out::println);
        // 去重，获取分组数量
        System.out.println(friends.stream().map(Friend::getCategory_id).distinct().count());
    }

    @Test
    public void insertCategory() {
    }

    @Test
    public void deleteCategory() {
    }
}