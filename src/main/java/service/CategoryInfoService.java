package service;

import bean.Friend;
import bean.Message;

import java.util.List;
import java.util.Map;

public interface CategoryInfoService {
    /**
     * 添加好友分组
     *
     * @param ownerId      分组所属用户的id
     * @param categoryName 分组名称
     * @return Message
     */
    Message addCategory(String ownerId, String categoryName);

    /**
     * 删除分组
     *
     * @param categoryId 分组的id
     * @return Message
     */
    Message deleteCategory(String categoryId);

    /**
     * 获取分组，返回一个Map，key为分组id，value为该分组id对应的好友List
     *
     * @param ownerId 分组所属用户的id
     * @return 该用户所有的分组，以及每个分组对应的好友，key表示分组id，value表示该分组下的所有用户
     */
    Map<Integer, List<Friend>> getFriends(String ownerId);
}
