package dao;

import domain.UserInfo;

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
    List<UserInfo> selectUserInfo();

    /**
     * 插入数据
     *
     * @param userInfo 表示要插入到数据库的数据
     * @return int 表示执行insert操作后的影响数据库的行数
     */
    int insertUserInfo(UserInfo userInfo);
}
