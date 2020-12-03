package service.impl;

import bean.Friend;
import bean.Message;
import dao.CategoryInfoDao;
import domain.CategoryInfo;
import service.CategoryInfoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryInfoServiceImpl implements CategoryInfoService {

    CategoryInfoDao categoryInfoDao;

    @Override
    public Message addCategory(String ownerId, String categoryName) {
        CategoryInfo categoryInfo = new CategoryInfo();
        categoryInfo.setUser_id(Integer.valueOf(ownerId));
        categoryInfo.setName(categoryName);
        int num = categoryInfoDao.insertCategory(categoryInfo);
        Message message = new Message();
        message.setType("add category");
        if (num != 0) {
            message.setOperation("success");
            message.setText("添加成功");
        } else {
            message.setOperation("fail");
            message.setText("添加失败");
        }
        return message;
    }

    @Override
    public Message deleteCategory(String categoryId) {
        int num = categoryInfoDao.deleteCategory(Integer.valueOf(categoryId));
        Message message = new Message();
        message.setType("delete category");
        if (num != 0) {
            message.setOperation("success");
            message.setText("删除成功");
        } else {
            message.setOperation("fail");
            message.setText("删除失败");
        }
        return message;
    }

    @Override
    public Map<Integer, List<Friend>> getFriends(String ownerId) {
        List<Friend> friends = categoryInfoDao.selectFriend(Integer.valueOf(ownerId));
        Map<Integer, List<Friend>> map = new HashMap<>();
        friends.forEach(i -> {
            if (map.containsKey(i.getCategory_id())) {
                map.get(i.getCategory_id()).add(i);
            } else {
                List<Friend> item = new ArrayList<>();
                item.add(i);
                map.put(i.getCategory_id(), item);
            }
        });
        return map;
    }

    public void setCategoryInfoDao(CategoryInfoDao categoryInfoDao) {
        this.categoryInfoDao = categoryInfoDao;
    }
}
