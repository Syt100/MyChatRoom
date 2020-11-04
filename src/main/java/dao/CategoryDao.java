package dao;

import bean.Category;
import bean.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 好友分组
 */
public class CategoryDao extends BaseDao {

    /**
     * 添加好友分组
     *
     * @param userId       用户ID
     * @param categoryName 分组名称
     * @return 添加是否成功
     */
    public boolean addCategory(String userId, String categoryName) {
        String sql = "insert into category_info(user_id,name) values(" + userId + ",'" + categoryName + "');";
        int num = operate(sql);
        return num > 0;
    }

    /**
     * 删除好友分组
     *
     * @param categoryId 分组ID
     * @return 删除是否成功
     */
    public boolean deleteCategory(String categoryId) {
        try {
            // 设置事物回滚，因此禁用自动提交
            getConnect().setAutoCommit(false);
            Savepoint savepoint = getConnect().setSavepoint();
            String sql = "delete from category_info where id=" + categoryId + ";";
            int num = operate(sql);
            if (num > 0) {
                int num2 = operate("delete from category_member where category_id=" + categoryId + ";");
                commit();
                return num2 > 0;
            } else {
                getConnect().rollback(savepoint);
                commit();
                return false;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    /**
     * 通过用户ID获取分组
     *
     * @param userId 用户ID
     * @return Category
     */
    public Category getCategoryByUserId(String userId) {
        String sql = "select * from category_info where user_id='" + userId + "';";
        ResultSet resultSet = select(sql);
        return assembleCategory(userId, resultSet);
    }

    /**
     * 获取分组成员
     *
     * @param categoryId 分组ID
     * @return ArrayList<Users>
     */
    public ArrayList<Users> getCategoryMembers(String categoryId) {
        String sql = "select * from category_member where category_id=" + categoryId + ";";
        ResultSet resultSet = select(sql);
        ArrayList<Users> users = new ArrayList<>();
        UserDao userDao = new UserDao();
        try {
            while (resultSet.next()) {
                String userId = String.valueOf(resultSet.getInt("user_id"));
                users.add(userDao.getUserById(userId));
            }
            return users;
        } catch (SQLException e) {
            // TODO
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 组装好友分组
     *
     * @param userId    用户ID
     * @param resultSet 结果集
     * @return Category
     */
    private Category assembleCategory(String userId, ResultSet resultSet) {
        Map<String, String> categoryName = new HashMap<>();
        Map<String, ArrayList<Users>> categoryMember = new HashMap<>();
        try {
            while (resultSet.next()) {
                // userId = String.valueOf(resultSet.getInt("user_id"));
                String name = resultSet.getString("name");
                String categoryId = String.valueOf(resultSet.getInt("id"));
                categoryName.put(categoryId, name);
                categoryMember.put(categoryId, getCategoryMembers(categoryId));
            }
            return new Category(userId, categoryName, categoryMember);
        } catch (SQLException throwable) {
            // TODO
            throwable.printStackTrace();
        }
        return null;
    }
}
