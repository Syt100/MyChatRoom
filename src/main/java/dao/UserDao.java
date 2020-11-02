package dao;

import bean.Message;
import bean.Users;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends BaseDao {

    /**
     * 用户登录
     *
     * @param id   用户id
     * @param pass 密码
     * @return Users
     */
    public Users login(String id, String pass) {
        String sql = "select * from user_info u where u.user_id = " +
                Integer.valueOf(id) + " and u.user_password = '" + pass + "'";
        ResultSet result = select(sql);
        return assembleUser(result);
    }

    /**
     * 注册
     *
     * @param user Users
     * @param pass 密码
     * @return Message
     */
    public Message register(Users user, String pass) {
        Message message = new Message();
        message.setType("register");
        if (null != getUserById(user.getId())) {
            message.setOperation("fail");
            message.setText("注册失败，用户已存在。");
            return message;
        }
        String sql = "insert into user_info(user_id,user_name,user_password,user_signature) values("
                + user.getId() + ",'" + user.getName() + "','" + pass + "'";
        if (user.getSignature() == null || "".equals(user.getSignature())) {
            sql += ",null";
        } else {
            sql += ",'" + user.getSignature() + "'";
        }
        sql += ")";
        System.out.println(sql);

        int num = operate(sql);
        if (num != 0) {
            // 注册成功
            message.setOperation("success");
        } else {
            message.setOperation("fail");
            message.setText("注册失败，未知错误。");
        }
        return message;
    }

    /**
     * 通过ID查找用户
     *
     * @param id 用户id
     * @return Users
     */
    public Users getUserById(String id) {
        String sql = "select * from user_info where user_id=" + id;
        ResultSet resultSet = select(sql);
        return assembleUser(resultSet);
    }

    /**
     * 通过名称查找用户
     *
     * @param name 用户名
     * @return Users
     */
    @SuppressWarnings("unused")
    public Users getUserByName(String name) {
        String sql = "select * from user_info where user_name='" + name + "'";
        ResultSet resultSet = select(sql);
        return assembleUser(resultSet);
    }

    /**
     * 组装Users类
     *
     * @param result 结果集
     * @return Users
     */
    private Users assembleUser(ResultSet result) {
        try {
            if (null != result && result.next()) {
                String id = String.valueOf(result.getInt("user_id"));
                String userName = result.getString("user_name");
                String signature = result.getString("user_signature");
                Users user = new Users();
                user.setId(id);
                user.setName(userName);
                user.setSignature(signature);
                return user;
            }
        } catch (SQLException e) {
            // TODO
            e.printStackTrace();
        }
        return null;
    }
}
