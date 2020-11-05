package mygui.server;

import bean.Message;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 服务端事务处理
 */
public class ServerHandler {

    public static void main(String[] args) {
        String str = "hello";
        str = str + 100;
        System.out.println(str);
    }

    /**
     * 存放客户端的线程组
     */
    private static ThreadGroup threadGroup;

    /**
     * 存放客户端的线程数组
     */
//    private static MultiTalkServerThread[] serverThreads = new MultiTalkServerThread[100];
    private static ArrayList<MultiTalkServerThread> serverThreads;

    /**
     * 待发送客户端数量
     */
    private static int currentSendNumber;

    public ServerHandler() {
        initVariable();
    }

    private void initVariable() {
        threadGroup = new ThreadGroup("客户端线程组");
        serverThreads = new ArrayList<>();
        currentSendNumber = 0;
    }

    /**
     * 监听客户端的连接。一直循环，每当有客户端连接时，创建一个新的线程去处理，继续监听
     */
    public static void listenToClientConnect() {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        try {
            while (listening) {
                MultiServerFrame.appendShowMessage("等待客户端连接\n");
                Socket socket = serverSocket.accept(); // 阻塞等待客户端的连接
                // 当有客户端连接进来后，客户端数量加一
                MultiServerFrame.updateClientCount(1);
                new MultiTalkServerThread(threadGroup, socket).start();// 启动一个线程处理客户端请求
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 以客户端线程数组的形式返回，通过域变量threadGroup获取活跃的客户端线程数组，并返回
     *
     * @return MultiTalkServerThread[] 返回所有的客户端线程，放在数组里
     */
    public static MultiTalkServerThread[] getClientList() {
        // ThreadGroup currentGroup =Thread.currentThread().getThreadGroup();//获取当前线程组
        int noThreads = threadGroup.activeCount();// 获取存放客户端的线程组中活跃线程数量，即客户端的数量
        System.out.println("线程数：" + noThreads);

        // 客户端线程数组，
        MultiTalkServerThread[] lstThreads = new MultiTalkServerThread[noThreads];
        // 把客户端线程组中的所有活动子组的引用复制到数组中。
        threadGroup.enumerate(lstThreads);
        return lstThreads;
    }

    /**
     * @return name, 所有已连接的客户端线程名
     */
    public static ArrayList<String> getClientListName() {
        int noThreads = threadGroup.activeCount();
        MultiTalkServerThread[] lstThreads = new MultiTalkServerThread[noThreads];
        threadGroup.enumerate(lstThreads);

        ArrayList<String> name = new ArrayList<String>();
        for (MultiTalkServerThread thread : lstThreads) {
            name.add(thread.getName());
        }
        return name;
    }

    /**
     * 把names里的客户端数组名称放入serverThreads数组，实现同时多输出
     *
     * @param names 要放入输出流数组outs的客户端的名字数组
     */
    protected static void updateReadyToSendClient(String[] names) {
        MultiTalkServerThread[] lstThreads = MultiServerFrame.getClientList();
        MultiServerFrame.clearServerThread();// 清空数组里里的数据，不然会造成重复发送
        int i = 0;
        for (MultiTalkServerThread thread : lstThreads) {
            System.out.println("线程数量：" + lstThreads.length + " 线程id：" + thread.getId() + " 线程名称：" + thread.getName()
                    + " 线程状态：" + thread.getState());
            // 与每一个线程名匹配，如果是，就加入outs
            for (String name : names) {
                // 选择一个
                if (thread.getName().equals(name)) {
                    serverThreads.set(i++, thread);// 将要准备发送消息的客户端放入
                }
            }
        }
        currentSendNumber = i;
        System.out.println("准备发送到" + i + "个输出流");
    }

    /**
     * 服务端向客户端发消息
     *
     * @param message 要发送的消息
     */
    protected static void sendMessageToClient(Message message) {
        PrintWriter out;
        for (MultiTalkServerThread thread : serverThreads) {
            if (thread != null) {
                out = thread.getOutPut();
                if (out != null) {
                    out.println(JSON.toJSONString(message));
                    out.flush();
                }
            }
        }
    }
}
