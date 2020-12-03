package service.impl;

import bean.Message;
import bean.Users;
import dao.UserInfoDao;
import domain.UserInfo;
import service.UserInfoService;

import java.util.ArrayList;
import java.util.List;

public class UserInfoServiceImpl implements UserInfoService {

    UserInfoDao userInfoDao;

    @Override
    public Users login(Integer userId, String password) {
        UserInfo userInfo = userInfoDao.selectUserInfoByUserIdPassword(userId, password);
        return Users.valueOf(userInfo);
    }

    @Override
    public Message register(Users user, String password) {
        Message message = new Message();
        message.setType("register");
        if (null != getUserById(user.getId())) {
            message.setOperation("fail");
            message.setText("注册失败，用户已存在。");
            return message;
        }
        UserInfo userInfo = UserInfo.valueOf(user);
        userInfo.setUser_password(password);
        int num = userInfoDao.insertUserInfo(userInfo);
        if (num != 0) {
            // 注册成功
            message.setOperation("success");
            message.setText("注册成功");
        } else {
            message.setOperation("fail");
            message.setText("注册失败，未知错误。");
        }
        return message;
    }

    @Override
    public Users getUserById(String userId) {
        UserInfo userInfo = userInfoDao.selectUserInfoByUserId(Integer.valueOf(userId));
        return Users.valueOf(userInfo);
    }

    @Override
    public List<Users> getUserByName(String name) {
        List<UserInfo> userInfo = userInfoDao.selectUserInfoByName(name);
        List<Users> usersList = new ArrayList<>();
        for (UserInfo userInfo1 : userInfo) {
            usersList.add(Users.valueOf(userInfo1));
        }
        return usersList;
    }

    @Override
    public Message writtenOffUser(Integer userId) {
        int num = userInfoDao.deleteUserInfo(userId);
        Message message = new Message();
        message.setType("written off");
        if (num == 0) {
            message.setOperation("fail");
            message.setText("注销失败");
        } else {
            message.setOperation("success");
            message.setText("注销成功");
        }
        return message;
    }

    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }
}
