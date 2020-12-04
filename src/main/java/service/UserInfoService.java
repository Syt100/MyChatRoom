package service;

import bean.Message;
import bean.Users;

import java.util.List;

/**
 * 业务逻辑层service层
 * 用户行为接口，如登录，注册等
 */
public interface UserInfoService {
    /**
     * 用户登录
     *
     * @param userId   用户id
     * @param password 密码
     * @return 登录成功返回Users对象，失败则返回null
     */
    Users login(String userId, String password);

    /**
     * 用户注册
     *
     * @param user     根据输入构造的Users对象
     * @param password 密码
     * @return Message，注册是否成功
     */
    Message register(Users user, String password);

    /**
     * 根据用户id查找用户，最多只能找到一个用户
     *
     * @param userId 用户id
     * @return 一个用户，若没找到则返回null
     */
    Users getUserById(String userId);

    /**
     * 根据用户名模糊查找用户，可能会找到多个用户
     *
     * @param name 用户名
     * @return 如果有多个用户，返回列表
     */
    List<Users> getUserByName(String name);

    /**
     * 注销用户
     *
     * @param userId 拟注销用户的id
     * @return Message，是否注销成功
     */
    Message writtenOffUser(String userId);
}
