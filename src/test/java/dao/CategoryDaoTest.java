package dao;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CategoryDaoTest {
    CategoryDao dao = new CategoryDao();

    @Test
    public void addCategory() {
        System.out.println(dao.addCategory("1234", "分组2"));
        assertTrue("结果", dao.addCategory("1234", "分组1"));
    }

    @Test
    public void deleteCategory() {
        System.out.println(dao.deleteCategory("2"));
    }

    @Test
    public void getCategoryByUserId() {
        System.out.println(dao.getCategoryByUserId("1234"));
    }

    @Test
    public void getCategoryMembers() {
        System.out.println(dao.getCategoryMembers("2"));
    }
}