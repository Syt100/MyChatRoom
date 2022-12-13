package mygui.login;

import bean.Message;
import bean.Users;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import exception.AccountInputException;
import mygui.login.setupstorage.LogonServerType;
import mygui.login.setupstorage.SetUpStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 登录界面的网络、流的处理线程，接收从服务端发来的登陆成功、登录失败、注册成功等消息。
 * 当登录客户端启动时，便启动本线程，由这个线程处理与服务端通信的事件。
 * 由于可能存在连接服务端失败的情况，只能重新new一个线程
 *
 * @author xuxin
 * @Note 存在线程同步问题：verificationAccountFromServer()
 */
public class LoginTread extends Thread {

    /**
     * Login4类的引用，可以调用其中的方法
     */
    private Login4 login;
    /**
     * 用于和服务端通信的套接字
     */
    private static Socket socket;

    /**
     * 客户端输出流
     */
    protected static PrintWriter out = null;
    /**
     * 客户端输入流
     */
    protected static BufferedReader in = null;

    /**
     * 登录界面是否已经结束
     */
    private static boolean isClose = false;


    /**
     * 将传入，以便调用其方法。
     *
     * @param tg    线程组
     * @param login Login4类对象的引用
     * @param name  设置线程名
     */
    public LoginTread(ThreadGroup tg, Login4 login, String name) {
        super(tg, name);
        this.login = login;
        this.setDaemon(true);
    }

    /**
     * 初始化socket并循环监听服务端的消息，分析服务端发来的反馈信息
     */
    @Override
    public void run() {
        try {
            initSocket();
            while (true) {

                String received = in.readLine();
                System.out.println(getClass() + received);

                JSONObject jsonObject = JSONObject.parseObject(received);
                String type = jsonObject.getString("type");
                String operation = jsonObject.getString("operation");
                // 服务端返回登录反馈信息
                if (type != null && "login".equals(type)) {
                    if ("success".equals(operation)) {// 如果服务端返回登陆成功
                        Users u = jsonObject.getObject("selfUser", Users.class);
                        isClose = true;// 结束自己线程
                        login.skipToFriendList(u, socket, out, in);
                        // break;
                    } else {// 服务端会返回失败原因
                        login.showTipsByTimer(operation);
                        System.out.println(operation);
                        // break;
                    }
                }
                // 服务端返回注册的反馈信息
                if (type != null && "register".equals(type)) {
                    if ("success".equals(operation)) {// 如果服务端返回注册成功
                        login.showTipsByTimer("注册成功！");
                        // break;
                    } else {// 服务端会返回失败原因
                        login.showTipsByTimer(operation);
                        System.out.println(operation);
                        // break;
                    }
                }
                if (isClose || socket == null) {
                    break;
                }
            }
        } catch (IOException e) {
            login.showTipsByTimer("连接出错！");
        } catch (Exception e) {
            login.showTipsByTimer("连接出错！");
        }
        System.out.println("线程退出");
    }

    /**
     * 初始化TCP网络相关
     *
     * @throws Exception Exception
     */
    protected void initSocket() throws Exception {
        if (socket != null) {// 保证只有一个socket，不重复初始化
            return;
        }

        SetUpStorage set = SetUpStorage.getStorage();
        if (set.logonServerType == LogonServerType.UDP) {
            login.showTipsByTimer("暂不支持UDP登录服务器，请切换为TCP或默认");
            return;
        }
        String host = set.loginAddress;
        int port = set.loginPort;
        if (set.logonServerType == LogonServerType.Default) {
            host = "127.0.0.1";
            port = 4444;
        }

        login.showTipsByTimer("正在连接服务器:" + host + ":" + port);
        // 初始化Socket
//		try {
        socket = new Socket(host, port);
//		} catch (IOException e) {
//			System.out.println("无法连接到服务器");
//			login.showTipsByTimer("无法连接到服务器");
//			throw new Exception("无法连接到服务器");
//		}
        login.showTipsByTimer("已连接到服务器:" + host + ":" + port);

        if (out != null || in != null) {// 防止重复初始化流
            return;
        }

        // 初始化输入输出流
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("I/O流出错");
            throw new Exception("I/O流出错");
        }
    }

    /**
     * 从服务器验证账号密码是否匹配
     */
    protected void verificationAccountFromServer() {
        if (socket == null) {
            return;
        }
        Message msg;
        try {
            msg = login.getUserFromInput();
        } catch (AccountInputException e) {
            login.showTipsByTimer(e.getMessage());// 账号或密码为空
            return;
        }
        msg.setType("login");
        out.println(JSON.toJSONString(msg));
        out.flush();
    }

    /**
     * 上传Users到服务器注册账户
     *
     * @param user Users
     */
    protected void registerAccountFromServer(Users user) {
        if (socket == null) {
            return;
        }
        Message msg = new Message();
        msg.setType("register");
        msg.setSelfUser(user);

        out.println(JSON.toJSONString(msg));
        out.flush();
    }

    /**
     * 静态方法，关闭所有的登录线程
     */
    protected static void closeLoginTheard() {
        isClose = true;
    }

    /**
     * 判断是否已经连上了服务器，如果连上了就不用new 本类了。
     *
     * @return 是否已经连上了服务器
     */
    protected static boolean isConnected() {
        if (socket == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 关闭套接字和流
     */
    protected void closeSocketAndStream() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}
