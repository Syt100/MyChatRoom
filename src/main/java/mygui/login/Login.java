package mygui.login;

import bean.Users;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 这个接口定义实现登录界面的方法
 *
 * @author xuxin
 */
public interface Login {

    /**
     * 从登录界面跳转到好友列表界面
     *
     * @param user   user
     * @param socket socket
     * @param out    out
     * @param in     in
     */
    public void skipToFriendList(Users user, Socket socket, PrintWriter out, BufferedReader in);
}
