package dao;

import bean.Category;
import bean.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 好友分组
 */
public class CategoryDao extends BaseDao {
    public boolean addCategory(String userId, String categoryName) {// 添加分组
        // TODO
        String sql = "insert into category_info(user_id,name) values(" + userId + ",'" + categoryName + "');";
        int num = operate(sql);
        return num > 0;
    }

    public Category getCategoryByUserId(String userId) {
        String sql = "select * from category_info where user_id='" + userId + "';";
        ResultSet resultSet = select(sql);
        return assembleCategory(userId, resultSet);
    }

    public ArrayList<Users> getCategoryMembers(String categoryId) {

        return null;
    }

    private Category assembleCategory(String userId, ResultSet resultSet) {
        Map<String, String> map = new HashMap<>();
        try {
            while (resultSet.next()) {
                // userId = String.valueOf(resultSet.getInt("user_id"));
                String name = resultSet.getString("name");
                String categoryId = String.valueOf(resultSet.getInt("id"));
                map.put(categoryId, name);
            }
            return new Category(userId, map);
        } catch (SQLException throwable) {
            // TODO
            throwable.printStackTrace();
        }
        return null;
    }


}
