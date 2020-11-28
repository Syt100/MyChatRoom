package dao;

import bean.Friend;
import domain.CategoryInfo;

import java.util.List;

/**
 * 好友分组
 */
public interface CategoryInfoDao {
    /**
     * 查询分组
     *
     * @param userId 用户id
     * @return 该用户id
     */
    List<CategoryInfo> selectCategory(Integer userId);

    /**
     * 查询好友，以该用户所有的分组、好友形式返回
     *
     * @param userId 用户id
     * @return 好友的列表
     */
    List<Friend> selectFriend(Integer userId);

    /**
     * 插入一个分组
     *
     * @param categoryInfo 要插入的分组对象
     * @return 受影响的行数
     */
    int insertCategory(CategoryInfo categoryInfo);

    /**
     * 根据分组id删除一个分组
     *
     * @param categoryId 分组id
     * @return 受影响的行数
     */
    int deleteCategory(Integer categoryId);
}
