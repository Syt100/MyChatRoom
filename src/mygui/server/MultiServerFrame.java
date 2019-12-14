package mygui.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import bean.Message;
import bean.Users;
import exception.AccountInputException;
import util.ConstantStatus;
import util.XMLOperation;

import javax.swing.event.ListSelectionEvent;

public class MultiServerFrame {

	private static JFrame frame;
	private static JPanel contentPane = new JPanel();
	private static JTextField textField_ip;
	private static JTextField textField_port;

	private static int port = 4444;
	private static String host = "127.0.0.1";
	private static JTextArea textArea_showMessage;

	// 结束标记
	private static boolean localBye = false;// 本机说拜拜
	private static boolean clientBye = false;// 客户端输入流说拜拜
	// private static String localMessageLine, clientMessageLine;
	private static JButton btn_send;
	private static JTextArea textArea_inputMessage;
	private static JButton btn_close;
	private static JTextField textField_count;
	private static JList<String> list;
	private static DefaultListModel<String> dlm = new DefaultListModel<String>();
	protected static int clientNumber = 0;

	/** 存放客户端的线程组 */
	private static ThreadGroup threadGroup = new ThreadGroup("客户端线程组");

	/** 存放客户端的线程数组 */
	private static MultiTalkServerThread[] serverThreads = new MultiTalkServerThread[100];

	private static int currentSendNumber = 0;

	private static Message msg = new Message();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new MultiServerFrame();
		MultiServerFrame.listenToClientConnect();
	}

	/**
	 * Create the application.
	 */
	public MultiServerFrame() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * 初始化图形界面和事件
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("服务端-多线程");
		frame.setBounds(100, 100, 580, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("服务端IP");
		lblNewLabel.setBounds(32, 21, 48, 15);
		contentPane.add(lblNewLabel);

		textField_ip = new JTextField();
		textField_ip.setBounds(90, 18, 66, 21);
		contentPane.add(textField_ip);
		textField_ip.setColumns(10);
		textField_ip.setText(host);

		JLabel label = new JLabel("端口");
		label.setBounds(181, 21, 30, 15);
		contentPane.add(label);

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

		JLabel label_count = new JLabel("客户端数量");
		label_count.setBounds(297, 21, 66, 15);
		contentPane.add(label_count);

		textField_count = new JTextField();
		textField_count.setColumns(10);
		textField_count.setBounds(373, 18, 27, 21);
		flushClientCountShow();
		contentPane.add(textField_count);

		JLabel label_1 = new JLabel("客户端列表");
		label_1.setBounds(422, 24, 66, 15);
		contentPane.add(label_1);

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
				textArea_showMessage.append("等待客户端连接\n");
				Socket socket = serverSocket.accept(); // 阻塞等待客户端的连接
				clientNumber++;// 当有客户端连接进来后，客户端数量加一
				textArea_showMessage.append("有" + clientNumber + "个客户端已连接\n");
				new MultiTalkServerThread(threadGroup, socket, clientNumber).start();// 启动一个线程处理客户端请求
			}
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param clientSocket
	 */
	public static void connetToClient(Socket clientSocket) {
		// addClientShowToList();
		try {
			// 由Socket对象得到输出流，并构造相应的PrintWriter对象
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			// 由Socket对象得到输入流，并构造相应的BufferedReader对象
			BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			while (true) {// 循环从客户端读入数据
				if (localBye == true && clientBye == true) {
					break;
				}
				if (clientBye == false) {// 如果客户端没有说拜拜
					showMsgFromClient(clientIn);
				}
			}
			out.close();
			clientIn.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("客户端断开了连接");
			clientNumber--;
			flushClientCountShow();
		}
	}

	/**
	 * 刷新界面上客户端数量文本框的显示
	 */
	public static void flushClientCountShow() {
		textField_count.setText("" + clientNumber);
	}

	/**
	 * 从客户端的流中读取字符串，并显示在服务端的TextArea里
	 * 
	 * @param clientIn 客户端输入流
	 * @throws IOException
	 */
	public static void showMsgFromClient(BufferedReader clientIn) throws IOException {
		textArea_showMessage.append("从客户端接收：" + clientIn.readLine() + "\n");
	}

	/**
	 * 传入字符串，显示在服务端的TextArea里
	 * 
	 * @param message
	 */
	public static void showMsgFromClient(String message) {
		textArea_showMessage.append("从客户端接收：" + message + "\n");
	}

	/**
	 * 将列表上显示的客户端数量加一个
	 * 
	 * @param name
	 */
	public static void addClientShowToList(String name) {
		dlm.addElement(name);
		flushClientCountShow();
		if (list.getSelectedIndex() == -1) {
			list.setSelectedIndex(0);
		}
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
	public static void updateClientShowName(String oldName, String newName) {
		// TODO 此处应该加咩找到的处理
		int index = dlm.indexOf(oldName);// 搜索指定元素的位置，如果没有找到，返回-1
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
	 * @return name,所有已连接的客户端线程名
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
	 * 清空待发送的客户端列表
	 */
	protected static void clearServerThread() {
//		for(MultiTalkServerThread thread : serverThreads) {
//			if(thread != null) {
//				thread = null;
//			}
//		}
		for (int i = 0; i < serverThreads.length; i++) {
			if (serverThreads[i] != null) {// !!!这里用高级for循环没用！！！
				serverThreads[i] = null;
			}
		}
	}

	/**
	 * 将消息发送到所有被选中的客户端的流里，由本类调用
	 * 
	 * @param msg 要发送的消息
	 */
	protected static void sendMessageToClient(String message) {
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
	 * @param message
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
	 * 用于服务端向客户端发消息
	 * 
	 * @param type
	 * @param selfName
	 * @param targetName
	 * @param text
	 */
	protected static void sendMessageToClient(String type, String selfName, String targetName, String text) {
		msg.setStatus(1);
		msg.setType(type);
		msg.setSelfName(selfName);
		msg.setTargetName(targetName);
		msg.setText(text);
		for (MultiTalkServerThread thread : serverThreads) {
			if (thread != null && thread.getOutPut() != null) {
				thread.getOutPut().println(JSON.toJSONString(msg));
				thread.getOutPut().flush();
			}
		}
	}

	/**
	 * 通知所有客户端，客户端列表选择改变
	 */
	protected static void putAllClientChanged() {
		String[] allNames = new String[dlm.getSize()];
		for (int i = 0; i < dlm.getSize(); i++) {
			allNames[i] = dlm.get(i);
		}
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
	 *
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
	 *
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
}

class MultiTalkServerThread extends Thread {
	/** 用于和客户端通信的Socket */
	private Socket socket = null;
	@SuppressWarnings("unused")
	private int clientNumber;// 第几个客户端，暂时没用

	/** 线程名 */
	private String name = null;
	/**
	 * 每当有新客户端练上来，就要在客户端列表上加一个客户端的名字。 此操作只执行一次，用于在循环里判断是否已经添加
	 */
	private boolean isAddToList = false;
	private static boolean localBye = false;// 本机说拜拜
	private static boolean clientBye = false;// 客户端输入流说拜拜
	private PrintWriter out;
	private BufferedReader clientIn;

	/** 用于发送给客户端的Message对象消息 */
	private Message msg = new Message();
	/** 用于解析从客户端收到的JSON字符串 */
	private JSONObject jsonObject = null;

	public MultiTalkServerThread(ThreadGroup tg, Socket socket, int clientNumber) {
		super(tg, "" + clientNumber);
		this.socket = socket;
		this.clientNumber = clientNumber;
		msg.setStatus(1);
		msg.setSelfName("server");
	}

	@Override
	public void run() {
		// MultiServerFrame.connetToClient(socket);
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while (true) {// 循环从客户端读入数据
				if (localBye == true && clientBye == true) {
					break;
				}
				if (clientBye == false) {// 如果客户端没有说拜拜
					String received = clientIn.readLine();
					jsonObject = JSONObject.parseObject(received);

					// 消息来源为测试客户端
					if (jsonObject.getString("type") != null && jsonObject.getString("type").equals("test")) {
						processTest();
						// 客户端请求刷新所有客户端列表
						if (jsonObject.getString("operation") != null && jsonObject.getString("operation").equals("getClientList")) {
							MultiServerFrame.putAllClientChanged();
						}
						if (jsonObject.getString("targetName") != null) {
							String name[] = {jsonObject.getString("targetName")};
							MultiServerFrame.updateReadyToSendClient(name);
							msg.setSelfName(this.name);
							msg.setTargetName(name[0]);
							if (jsonObject.getString("text") != null) {
								msg.setText(jsonObject.getString("text"));
							}
							MultiServerFrame.sendMessageToClient(msg);
						}
					} else if (received.startsWith("login")) {
						processLogin(received);
					} else if (received.startsWith("register")) {
						processRegister(received);
					} else if (received.startsWith("online")) {

					} else if (received.startsWith("talk")) {
						processTalk(received);
					} else {
						MultiServerFrame.showMsgFromClient(received);
					}

					if (isAddToList == false) {
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
			// e.printStackTrace();
			System.out.println("客户端" + name + "断开了连接");
			MultiServerFrame.clientNumber--;
			MultiServerFrame.flushClientCountShow();
			MultiServerFrame.removeClientShowToList(name);
		}
	}

	/**
	 * 处理测试客户端
	 */
	private void processTest() {
		name = jsonObject.getString("selfName");
		msg.setTargetName(name);
		MultiServerFrame.showMsgFromClient(jsonObject.toJSONString());// jsonObject.getString("text")
	}

	/**
	 * 处理登录请求
	 * 
	 * @param received
	 */
	private void processLogin(String received) {
		String[] rec = received.split("\\.");
		name = rec[0] + "登录中";
		String id = rec[1];
		String password = rec[2];
		try {
			checkLogin(id, password);
			Users user = getUsersInformationById(id);
			out.println("success" + "." + user.getId() + "." + user.getName() + "." + user.getFriends());
			out.flush();
		} catch (AccountInputException e) {
			out.println(e.getMessage());
			out.flush();
		}
	}

	/**
	 * 处理注册请求
	 * 
	 * @param received
	 */
	private void processRegister(String received) {
		// 特殊字符作为分隔符时需要使用\\进行转义(比如使用\\作为分隔符的话，则转义为\\\\)
		// 特殊字符有 .$|()[{^?*+\\
		String[] rec = received.split("\\.");
		name = rec[0] + "注册中";
		String id = rec[1];
		String name = rec[2];
		String password = rec[3];
		String friends = rec[4];
		Users user = new Users(id, name, password, friends);
		XMLOperation xml = new XMLOperation();
		xml.addUser(user);
		out.println("success");
		out.flush();
	}

	private void processTalk(String received) {
		String[] rec = received.split("\\.");
		name = rec[0] + "聊天中";

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
	public Message getMessage() {
		return msg;
	}

	/**
	 * 是否允许登录
	 * 
	 * @param id
	 * @param password
	 * @return
	 * @throws AccountInputException
	 */
	private boolean checkLogin(String id, String password) throws AccountInputException {
		Users users = new Users(id, password);
		if (!users.isAccountExitById(users)) {
			throw new AccountInputException(ConstantStatus.LOGIN_STATUS_ACCOUNT_NOT_EXIST);
		}
		if (password.equals(users.getPassWordById(id))) {
			return true;
		} else {
			throw new AccountInputException(ConstantStatus.LOGIN_STATUS_ERROR_PASSWORD);
		}
	}

	/**
	 * 返回用户的全部信息
	 * 
	 * @param id
	 * @return
	 */
	private Users getUsersInformationById(String id) {
		XMLOperation xml = new XMLOperation();
		return xml.getUsersById(id);
	}
}
