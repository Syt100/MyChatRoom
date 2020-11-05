package mygui.server;

import bean.Message;
import com.alee.laf.WebLookAndFeel;
import com.alibaba.fastjson.JSON;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 多线程的服务端，可以与多个客户端通信
 *
 * @author xuxin
 */
public class MultiServerFrame {

    private static JFrame frame;
    private static JPanel contentPane;
    private static JTextField textField_ip;
    private static JTextField textField_port;

    private static int port = 4444;
    private static String host = "127.0.0.1";
    private static JTextArea textArea_showMessage;

    // 结束标记
    private static boolean localBye = false;// 本机说拜拜
    private static boolean clientBye = false;// 客户端输入流说拜拜

    private static JButton btn_send;
    private static JTextArea textArea_inputMessage;
    private static JButton btn_close;
    private static JTextField textField_count;
    private static JList<String> list;
    private static final DefaultListModel<String> dlm = new DefaultListModel<>();
    protected static int clientNumber = 0;

    /**
     * 存放客户端的线程组
     */
    private static ThreadGroup threadGroup = new ThreadGroup("客户端线程组");

    /**
     * 存放客户端的线程数组
     */
    private static MultiTalkServerThread[] serverThreads = new MultiTalkServerThread[100];

    private static int currentSendNumber = 0;

    private static Message msg = new Message();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        WebLookAndFeel.install();
        new MultiServerFrame();
    }

    /**
     * Create the application.
     */
    public MultiServerFrame() {
        initGUI();
        frame.setVisible(true);
    }

    /**
     * 初始化图形界面和事件
     */
    private void initGUI() {
        frame = new JFrame();
        frame.setTitle("服务端-多线程");
        frame.setBounds(100, 100, 580, 380);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        frame.setContentPane(contentPane);

        JLabel lbl_ip = new JLabel("服务端IP");
        lbl_ip.setBounds(32, 21, 48, 15);
        contentPane.add(lbl_ip);

        textField_ip = new JTextField();
        textField_ip.setBounds(90, 18, 66, 21);
        contentPane.add(textField_ip);
        textField_ip.setColumns(10);
        textField_ip.setText(host);

        JLabel lbl_port = new JLabel("端口");
        lbl_port.setBounds(181, 21, 30, 15);
        contentPane.add(lbl_port);

        textField_port = new JTextField();
        textField_port.setColumns(10);
        textField_port.setBounds(221, 18, 66, 21);
        textField_port.setText("" + port);
        contentPane.add(textField_port);

        btn_send = new JButton("发送");
        btn_send.setBounds(480, 310, 76, 23);
        contentPane.add(btn_send);
        btn_send.addActionListener(new SendMessage());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(32, 56, 368, 192);
        contentPane.add(scrollPane);

        textArea_showMessage = new JTextArea();
        scrollPane.setViewportView(textArea_showMessage);
        textArea_showMessage.setLineWrap(true);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(32, 258, 368, 75);
        contentPane.add(scrollPane_1);

        textArea_inputMessage = new JTextArea();
        scrollPane_1.setViewportView(textArea_inputMessage);
        textArea_inputMessage.setLineWrap(true);

        btn_close = new JButton("关闭连接");
        btn_close.setBounds(463, 277, 93, 23);
        contentPane.add(btn_close);
        btn_close.addActionListener(new SendMessage());

        JLabel lbl_clientCount = new JLabel("客户端数量");
        lbl_clientCount.setBounds(297, 21, 66, 15);
        contentPane.add(lbl_clientCount);

        textField_count = new JTextField();
        textField_count.setColumns(10);
        textField_count.setBounds(373, 18, 27, 21);
        flushClientCountShow();
        contentPane.add(textField_count);

        JLabel lbl_clientList = new JLabel("客户端列表");
        lbl_clientList.setBounds(422, 24, 66, 15);
        contentPane.add(lbl_clientList);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(410, 56, 146, 192);
        contentPane.add(scrollPane_2);

        list = new JList<String>();
        list.addListSelectionListener(new ListSelectionChangedListener());
        list.setModel(dlm);
        list.setSelectedIndex(0);
        scrollPane_2.setViewportView(list);
    }

    /**
     * 刷新界面上客户端数量文本框的显示
     */
    public static void flushClientCountShow() {
        textField_count.setText("" + clientNumber);
    }

    /**
     * 传入字符串，显示在服务端的TextArea里
     *
     * @param message Message对象
     */
//	public static void showMsgFromClient(String message) {
//		textArea_showMessage.append("从客户端接收：" + message + "\n");
//	}

    /**
     * 将列表上显示的客户端数量加一个
     *
     * @param name
     */
    public static void addClientShowToList(String name) {
        dlm.addElement(name);
        flushClientCountShow();
//		if (list.getSelectedIndex() == -1) {
//			list.setSelectedIndex(0);
//		}
    }

    /**
     * 将列表上显示的客户端数量减一个
     *
     * @param name
     */
    public static void removeClientShowToList(String name) {
        dlm.removeElement(name);
        flushClientCountShow();
    }

    /**
     * 修改客户端列表的显示名称
     *
     * @param oldName
     * @param newName
     */
    protected static void updateClientShowName(String oldName, String newName) {
        // TODO 此处应该加咩找到的处理
        int index = dlm.indexOf(oldName);// 搜索指定元素的位置，如果没有找到，返回-1
        System.out.println("index:" + index);
        dlm.remove(index);
        // TODO 这里不确定是否会覆盖原来index位置的元素
        dlm.add(index, newName);
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
            for (int j = 0; j < names.length; j++) {
                // 选择一个
                if (thread.getName().equals(names[j])) {
                    serverThreads[i++] = thread;// 将要准备发送消息的客户端放入
                }
            }
        }
        currentSendNumber = i;
        System.out.println("准备发送到" + i + "个输出流");
    }

    /**
     * 把 所有的客户端数组名称放入serverThreads数组，实现群聊
     */
    protected static void updateReadyToSendAllClient() {
        String[] allNames = new String[dlm.getSize()];
        for (int i = 0; i < dlm.getSize(); i++) {
            allNames[i] = dlm.get(i);
        }
        MultiServerFrame.updateReadyToSendClient(allNames);
    }

    /**
     * 清空待发送的客户端列表
     */
    protected static void clearServerThread() {
        for (int i = 0; i < serverThreads.length; i++) {
            if (serverThreads[i] != null) {// !!!这里用高级for循环没用！！！
                serverThreads[i] = null;
            }
        }
    }

    /**
     * 将消息发送到所有被选中的客户端的流里，由本类调用
     *
     * @param message 要发送的消息
     */
    private static void sendMessageToClient(String message) {
        Message msg;
        PrintWriter out;
        for (MultiTalkServerThread thread : serverThreads) {
            if (thread != null) {
                out = thread.getOutPut();
                if (out != null) {
                    msg = thread.getMessage();
                    msg.setText(message);
                    out.println(JSON.toJSONString(msg));
                    out.flush();
                }
            }
        }
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

    /**
     * 通知所有客户端，客户端列表选择改变
     */
    protected static void putAllClientChanged() {
        String[] allNames = new String[dlm.getSize()];
        dlm.copyInto(allNames);
        MultiServerFrame.updateReadyToSendClient(allNames);
        msg.setStatus(1);
        msg.setList(getClientListName());
        msg.setOperation("updateList");
        MultiServerFrame.sendMessageToClient(msg);
        msg.setOperation(null);
    }

    /**
     * 客户端列表选择改变的监听器，用于选择发送的客户端
     *
     * @author xuxin
     */
    private class ListSelectionChangedListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            System.out.println("选择改变");

            // 被选中列表位置的索引的数组，里面是被选中客户端的序号（不是名称）
            int currentIndices[] = list.getSelectedIndices();
            String names[] = new String[currentIndices.length];
            // 根据索引查找被选中的客户端名称
            for (int i = 0, j = 0; i < currentIndices.length; i++) {
                names[j++] = dlm.elementAt(currentIndices[i]);
            }

            // 通知所有客户端，客户端列表选择改变
            putAllClientChanged();

            // 将被选中的客户端加入到准备发送列表
            MultiServerFrame.updateReadyToSendClient(names);
        }
    }

    /**
     * 鼠标点击发送按钮的监听器
     *
     * @author xuxin
     */
    private class SendMessage implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO 自动生成的方法存根
            if (e.getSource() == btn_send && localBye == false) {// 如果服务端没有说拜拜，服务端输入文字
                String localMessageLine = textArea_inputMessage.getText();
                textArea_inputMessage.setText("");
                textArea_showMessage.append("服务端输入：" + localMessageLine + "\n");
                MultiServerFrame.sendMessageToClient(localMessageLine);
                textArea_showMessage.append("服务端已发送给" + currentSendNumber + "个客户端\n");
            }
            if (e.getSource() == btn_close) {
                localBye = true;
            }
        }
    }

    protected static void appendShowMessage(String text) {
        textArea_showMessage.append(text);
    }

    /**
     * 更新界面显示的已连接客户端数量
     *
     * @param change 改变值，正值表示增加，负值表示减少
     */
    protected static void updateClientCount(int change) {
        clientNumber += change;
        flushClientCountShow();
        appendShowMessage("已连接客户端数量更新为" + clientNumber + "\n");
    }
}