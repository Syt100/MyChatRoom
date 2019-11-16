package mygui.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
	private static String localMessageLine, clientMessageLine;
	private static JButton btn_send;
	private static JTextArea textArea_inputMessage;
	private static PrintWriter out;
	private static JButton btn_close;
	private static JTextField textField_count;
	private static JList<String> list;
	private static DefaultListModel<String> dlm = new DefaultListModel<String>();
	protected static int clientNumber = 0;

	private static ThreadGroup threadGroup = new ThreadGroup("客户端线程组");
	private static PrintWriter[] outs = new PrintWriter[100];
	private static int currentOutsNumber = 0;

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
		//addClientShowToList();
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
	 * 从客户端的流中读取字符串，并显示
	 * @param clientIn 客户端输入流
	 * @throws IOException
	 */
	public static void showMsgFromClient(BufferedReader clientIn) throws IOException {
		clientMessageLine = clientIn.readLine();// 从客户端读入文字
		textArea_showMessage.append("从客户端接收：" + clientMessageLine + "\n");
	}

	/**
	 * 将列表上显示的客户端数量加一个
	 * @param name
	 */
	public static void addClientShowToList(String name) {
		dlm.addElement(name);
		flushClientCountShow();
		if (list.getSelectedIndex() == -1) {
			list.setSelectedIndex(0);
		}
	}
	
	public static void removeClientShowToList(String name) {
		dlm.removeElement(name);
		flushClientCountShow();
	}

	/**
	 * 客户端列表选择改变的监听器，用于选择发送的客户端
	 * @author xuxin
	 *
	 */
	private class ListSelectionChangedListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			System.out.println("选择改变");
			// ThreadGroup currentGroup =Thread.currentThread().getThreadGroup();//获取当前线程组
			int noThreads = threadGroup.activeCount();// 获取存放客户端的线程组中活跃线程数量，即客户端的数量
			System.out.println("线程数：" + noThreads);

			// 客户端线程数组，
			MultiTalkServerThread[] lstThreads = new MultiTalkServerThread[noThreads];
			// 把客户端线程组中的所有活动子组的引用复制到数组中。
			threadGroup.enumerate(lstThreads);

			int i = 0;
			// 当前被选中的列表的索引，因为客户端编号从1开始，所以加一
			//String currentName = list.getSelectedValue();
			// 被选中列表位置的索引的数组，里面是被选中客户端的序号（不是名称）
			int currentNames[] = list.getSelectedIndices();
			
			for (MultiTalkServerThread thread : lstThreads) {
				System.out.println("线程数量：" + noThreads + " 线程id：" + thread.getId() + " 线程名称：" + thread.getName()
						+ " 线程状态：" + thread.getState());
				// 遍历这个客户端序号的数组，根据序号从dlm里查找对应的元素，即客户端的名称，再与客户端线程名称对比
				for (int j = 0; j < currentNames.length; j++) {
					// 选择一个
					if (thread.getName().equals(dlm.elementAt(currentNames[j]))) {
						outs[i] = thread.getOutPut();// 将客户端线程的输出流放到outs输出流数组中
						i++;
					} 
				}
			}
			currentOutsNumber = i;
			System.out.println("准备发送到" + i + "个输出流");
			System.out.println("列表当前被选中项：" + list.getSelectedIndex());
		}
	}

	/**
	 * 鼠标点击发送按钮的监听器
	 * @author xuxin
	 *
	 */
	private class SendMessage implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			if (e.getSource() == btn_send && localBye == false) {// 如果服务端没有说拜拜，服务端输入文字
				localMessageLine = textArea_inputMessage.getText();
				textArea_inputMessage.setText("");
				textArea_showMessage.append("服务端输入：" + localMessageLine + "\n");
				for (PrintWriter pw : outs) {
					if (pw != null) {
						pw.println(localMessageLine);
						pw.flush();
					}
				}
				textArea_showMessage.append("服务端已发送给" + currentOutsNumber + "个客户端\n");
				// out.println(localMessageLine);// 将服务端键盘输入的文字发送到客户端的输入流中
				// out.flush();
			}
			if (e.getSource() == btn_close) {
				localBye = true;
			}
		}
	}
}

class MultiTalkServerThread extends Thread {
	private Socket socket = null;
	@SuppressWarnings("unused")
	private int clientNumber;// 第几个客户端，暂时没用
	private String name = null;
	private static boolean localBye = false;// 本机说拜拜
	private static boolean clientBye = false;// 客户端输入流说拜拜
	private PrintWriter out;
	private BufferedReader clientIn;

	public MultiTalkServerThread(ThreadGroup tg, Socket socket, int clientNumber) {
		super(tg, "" + clientNumber);
		this.socket = socket;
		this.clientNumber = clientNumber;
		System.out.println("Accept Client" + clientNumber);
	}

	@Override
	public void run() {
		// MultiServerFrame.connetToClient(socket);
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			clientIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			name = clientIn.readLine();
			this.setName(name);
			MultiServerFrame.addClientShowToList(name);

			while (true) {// 循环从客户端读入数据
				if (localBye == true && clientBye == true) {
					break;
				}
				if (clientBye == false) {// 如果客户端没有说拜拜
					MultiServerFrame.showMsgFromClient(clientIn);
				}
			}
			out.close();
			clientIn.close();
			socket.close();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("客户端" + name +  "断开了连接");
			MultiServerFrame.clientNumber--;
			MultiServerFrame.flushClientCountShow();
			MultiServerFrame.removeClientShowToList(name);
		}
	}

	public PrintWriter getOutPut() {
		return out;
	}
}
