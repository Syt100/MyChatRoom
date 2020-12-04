package mygui.server;

import bean.Message;
import bean.Users;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.UserInfoService;
import service.impl.UserInfoServiceImpl;
import util.XMLOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 服务端的线程。每个线程对应连接到一个客户端
 *
 * @author xuxin
 */
public class MultiTalkServerThread extends Thread {
    /**
     * 用于和客户端通信的Socket
     */
    private Socket socket;
    /**
     * 用于表示当前线程的账号ID，
     */
    private String id;

    /**
     * 线程名
     */
    private String name = null;
    /**
     * 每当有新客户端练上来，就要在客户端列表上加一个客户端的名字。 此操作只执行一次，用于在循环里判断是否已经添加
     */
    private boolean isAddToList = false;
    private boolean localBye = false;// 本机说拜拜
    private boolean clientBye = false;// 客户端输入流说拜拜
    private boolean isLogin = false; // 是否登录
    private PrintWriter out;

    /**
     * Spring
     */
    private ClassPathXmlApplicationContext applicationContext;

    /**
     * 用于发送给客户端的Message对象消息
     */
    private Message msg = new Message();

    public MultiTalkServerThread(ThreadGroup tg, Socket socket) {
        super(tg, "null");
        this.socket = socket;
        msg.setStatus(1);
        msg.setSelfName("server");
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {// 循环从客户端读入数据
                if (localBye && clientBye) {
                    break;
                }
                if (!clientBye) {// 如果客户端没有说拜拜
                    String received = clientIn.readLine();
                    JSONObject jsonObject = JSONObject.parseObject(received);
                    String type = jsonObject.getString("type");

                    if (type != null) {
                        // 消息来源为登录界面的登录验证请求
                        if (type.equals("login")) {
                            processLogin(jsonObject);
                        }
                        // 消息来源为登录界面的注册请求
                        if (type.equals("register")) {
                            processRegister(jsonObject);
                        }
                        // 消息来源为聊天界面的聊天请求
                        if (type.equals("talk")) {
                            processTalk(jsonObject);
                            System.out.println("收到了一条消息");
                        }
                        // 群聊
                        if (type.equals("GroupChat")) {
                            processGroupChat(jsonObject);
                        }
                        // 客户端注销
                        if (type.equals("exit")) {
                            // TODO 注销处理
                            processExit();
                            break;
                        }
                    }
                    MultiServerFrame.appendShowMessage("从客户端接收：" + received + "\n");

                    if (isLogin && !isAddToList) {
                        if (name != null) {
                            this.setName(name);
                            MultiServerFrame.addClientShowToList(name);
                        } else {
                            this.setName("无法获取名称");
                            MultiServerFrame.addClientShowToList("无法获取名称");
                        }
                        isAddToList = true;
                    }

                }
            }
            out.close();
            clientIn.close();
            socket.close();
        } catch (IOException e) {
            processExit();
        }
    }

    private void processExit() {
        System.out.println("客户端" + name + "断开了连接");
        MultiServerFrame.updateClientCount(-1);
        MultiServerFrame.removeClientShowToList(name);
        isLogin = false;
    }

    /**
     * 处理登录请求
     *
     * @param jo jo
     */
    private void processLogin(JSONObject jo) {
        // 从JSON字符串中获取用户信息
        String userId = jo.getString("selfId");
        String userPassword = jo.getString("text");

        UserInfoService userInfoService = applicationContext.getBean(UserInfoServiceImpl.class);
        Users user = userInfoService.login(userId, userPassword);
        System.out.println(user);
        Message mg = new Message();
        if (user != null) {// 登录成功
            isLogin = true;
            name = userId;
            mg.setSelfUser(user);
            mg.setType("login");
            mg.setOperation("success");// 登陆成功
        } else {// 登陆失败，密码错误等
            mg.setType("login");
            mg.setOperation("用户不存在或密码错误");
        }
        out.println(JSON.toJSONString(mg));
        out.flush();
    }

    /**
     * 处理注册请求
     *
     * @param jo
     */
    private void processRegister(JSONObject jo) {
        // 特殊字符作为分隔符时需要使用\\进行转义(比如使用\\作为分隔符的话，则转义为\\\\)
        // 特殊字符有 .$|()[{^?*+\\
        // String[] rec = received.split("\\.");

        // 从JSON字符串中获取用户
        Users user = jo.getObject("selfUser", Users.class);

        if (isAddToList == false) {// 如果是客户端第一次连接，就设置线程名，不需要修改
            name = user.getId() + "注册中";
            this.setName(name);
        } else {// 客户端不是第一次连接，因为用户的ID可能会改变，因此要修改线程名，与当前登录的客户端同步，不然导致服务端显示的列表信息删不掉
            MultiServerFrame.updateClientShowName(name, user.getId() + "注册中");
            name = user.getId() + "注册中";// 更新线程名
            this.setName(name);
        }

        XMLOperation xml = new XMLOperation();
        xml.addUser(user);

        Message mg = new Message();
        mg.setSelfUser(user);
        mg.setType("register");
        mg.setOperation("success");// 注册成功

        out.println(JSON.toJSONString(mg));
        out.flush();
    }

    /**
     * 处理聊天请求
     *
     * @param jo
     */
    private void processTalk(JSONObject jo) {
        // 从JSON字符串中获取消息发送方和接收方
        String selfId = jo.getString("selfId");
        String targetId = jo.getString("targetId");

        if (isAddToList == false) {// 如果是客户端第一次连接，就设置线程名，不需要修改
            name = selfId;
            this.setName(name);
        } else {// 客户端不是第一次连接，因为用户的ID可能会改变，因此要修改线程名，与当前登录的客户端同步，不然导致服务端显示的列表信息删不掉
            MultiServerFrame.updateClientShowName(name, selfId);
            name = selfId;// 更新线程名
            this.setName(name);
        }

        String name[] = {targetId};
        MultiServerFrame.updateReadyToSendClient(name);
        // Message mg = jo.toJavaObject(Message.class);
        Message mg = new Message();
        mg.setType("talk");
        mg.setSelfId(selfId);
        mg.setSelfName(jo.getString("selfName"));
        mg.setTargetId(targetId);
        mg.setTargetName(jo.getString("targetName"));
        mg.setText(jo.getString("text"));

        MultiServerFrame.sendMessageToClient(mg);
        System.out.println("有人聊天了");
    }

    /**
     * 处理群聊请求
     *
     * @param jo
     */
    private void processGroupChat(JSONObject jo) {
        // 从JSON字符串中获取消息发送方和接收方
        String selfId = jo.getString("selfId");
        String targetId = jo.getString("targetId");

        if (isAddToList == false) {// 如果是客户端第一次连接，就设置线程名，不需要修改
            name = selfId;
            this.setName(name);
        } else {// 客户端不是第一次连接，因为用户的ID可能会改变，因此要修改线程名，与当前登录的客户端同步，不然导致服务端显示的列表信息删不掉
            MultiServerFrame.updateClientShowName(name, selfId);
            name = selfId;// 更新线程名
            this.setName(name);
        }

        MultiServerFrame.updateReadyToSendAllClient();

        Message mg = new Message();
        mg.setType("talk");
        mg.setSelfId(selfId);
        mg.setSelfName(jo.getString("selfName"));
        mg.setTargetId(targetId);
        mg.setTargetName(jo.getString("targetName"));
        mg.setText(jo.getString("text"));

        MultiServerFrame.sendMessageToClient(mg);
        System.out.println("群聊中");
    }

    /**
     * 获取客户端的输出流
     *
     * @return PrintWriter out
     */
    public PrintWriter getOutPut() {
        return out;
    }

    /**
     * 获取客户端的msg
     *
     * @return Message msg
     */
    protected Message getMessage() {
        return msg;
    }
}
