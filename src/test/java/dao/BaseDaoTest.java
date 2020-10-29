package dao;

import org.junit.Test;

public class BaseDaoTest {

    @Test
    public void select() {
        BaseDao dao = new BaseDao();
        System.out.println(dao.select("select * from user_info"));
    }
}