package dao;

import domain.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 接口操作UserInfo表
 */
public interface UserInfoDao {
    /**
     * 查询UserInfo表的所有数据
     *
     * @return UserInfo 集合，每行一个UserInfo对象
     */
    List<UserInfo> selectAllUserInfo();

    /**
     * 根据Id查询UserInfo表中的UserInfo
     *
     * @param id 用户id
     * @return 一个UserInfo对象
     */
    UserInfo selectUserInfoByUserId(Integer id);

    /**
     * 根据用户名查找用户，可能会有多个用户
     *
     * @param name 用户名，直接传入参数即可，不需要预处理为<code>%name%</code>的形式
     * @return 可能会有多个用户，返回列表
     */
    List<UserInfo> selectUserInfoByName(String name);

    /**
     * 通过用户id和密码查找用户
     *
     * @param id       用户id
     * @param password 用户密码
     * @return 一个UserInfo对象
     */
    UserInfo selectUserInfoByUserIdPassword(@Param("userId") Integer id,
                                            @Param("userPass") String password);

    /**
     * 插入用户信息
     *
     * @param userInfo 表示要插入到数据库的数据
     * @return int 表示执行insert操作后的影响数据库的行数
     */
    int insertUserInfo(UserInfo userInfo);

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return int 删除的用户数量
     */
    int deleteUserInfo(Integer userId);
}
