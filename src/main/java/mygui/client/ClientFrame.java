package mygui.client;

import bean.Message;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import util.RandomID;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientFrame {

	private JFrame frame;
	private JPanel contentPane = new JPanel();
	private JTextField textField_ip;
	private JTextField textField_port;

	private int port = 4444;
	private String host = "127.0.0.1";
	
	/** 客户端数量 */
	public static int clientNum = 0;
	/** 客户端名称，设置窗口标题，用于标识不同的客户端 */
	private String clientName = "kh" + RandomID.nextID();
	
	private JTextArea textArea_showMessage;

	boolean localBye = false;// 本机说拜拜
	boolean serverBye = false;// 服务端 输入流说拜拜
	String fromServer, fromLocal;
	private JButton btn_send;
	private JTextArea textArea_inputMessage;
	private PrintWriter out;
	private JButton btn_close;
	private JTextField textField_count;
	
	private JList<String> list;
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	
	private String exitCode = "exit(0):123456";
	private Message msg = new Message();
	private JSONObject jsonObject = null;
	private JButton btn_flush;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ClientFrame window = new ClientFrame();
		window.frame.setVisible(true);
		window.initSocket();
	}

	/**
	 * Create the application.
	 */
	public ClientFrame() {
		ClientFrame.clientNum += 1;
		initialize();
		System.out.println(ClientFrame.clientNum);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("客户端" + clientName);
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
		
		JLabel label_count = new JLabel("客户端数量");
		label_count.setBounds(297, 21, 66, 15);
		contentPane.add(label_count);
		
		textField_count = new JTextField();
		textField_count.setColumns(10);
		textField_count.setBounds(373, 18, 27, 21);
		textField_count.setText("" + ClientFrame.clientNum);
		contentPane.add(textField_count);
		btn_close.addActionListener(new SendMessage());
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(410, 56, 146, 192);
		contentPane.add(scrollPane_2);
		
		list = new JList<String>();
		list.addListSelectionListener(new ListSelectionChangedListener());
		list.setModel(dlm);
		list.setSelectedIndex(0);
		scrollPane_2.setViewportView(list);
		
		JLabel label_1 = new JLabel("所有在线客户端");
		label_1.setBounds(410, 21, 93, 15);
		contentPane.add(label_1);
		
		btn_flush = new JButton("刷新");
		btn_flush.setMargin(new Insets(2, 4, 2, 4));
		btn_flush.setBounds(508, 17, 48, 23);
		btn_flush.addActionListener(new SendMessage());
		contentPane.add(btn_flush);
	}

	public void initSocket() {
		Socket client = null;
		out = null;
		BufferedReader in = null;

		try {
			textArea_showMessage.append("正在连接服务端\n");
			client = new Socket(host, port);
			textArea_showMessage.append("连接服务端成功！\n");
			out = new PrintWriter(client.getOutputStream(), true); // auto flush
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			msg.setStatus(1);// 1表示正常链接
			msg.setType("test");
			msg.setSelfName(clientName);
			msg.setOperation("getClientList");
			String jsonString = JSON.toJSONString(msg);
			out.println(jsonString);
			out.flush();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: 127.0.0.1.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: 127.0.0.1.");
			System.exit(1);
		}
		try {
			while (true) {
				if (localBye == true && serverBye == true) {
					break;
				}

				if (serverBye == false) {
					fromServer = in.readLine();
					if(!isJsonObject(fromServer)){
						textArea_showMessage.append("从服务端接收：" + fromServer + "\n");
						continue;
					}
					
					jsonObject = JSON.parseObject(fromServer);
					
					if (jsonObject.getInteger("status") == 0) {// 服务端（对方）退出
						serverBye = true;
						textArea_showMessage.append("服务端已退出" + "\n");
					} else if(jsonObject.getString("operation") != null && jsonObject.getString("operation").equals("updateList")) {
						JSONArray jsonArray = jsonObject.getJSONArray("list");
						String[] name = toStringList(jsonArray);
						flushClientListShow(name);
						System.out.println("updateList");
					} else if(jsonObject.getString("selfName") != null){// 来自其他客户端
						textArea_showMessage.append("从" + jsonObject.getString("selfName") + "接收：" + jsonObject.getString("text") + "\n");
					} else {//fromServer
						textArea_showMessage.append("从服务端接收：" + fromServer + "\n");
					}
				}
			}

			out.close();
			in.close();
			client.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	/**
	 * 将JSONArray对象转换为String[]
	 * @param jsonArray
	 * @return
	 */
	private String[] toStringList(JSONArray jsonArray) {
		String name[] = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			name[i] = jsonArray.getString(i);
		}
		return name;
	}
	
	
	/**
	 * 更新客户端列表、客户端数量的显示
	 * @param name
	 */
	private void flushClientListShow(String[] name) {
		dlm.clear();
		for (int i = 0; i < name.length; i++) {
			dlm.addElement(name[i]);
		}
		textField_count.setText("" + name.length);
	}
	
	
	private class ListSelectionChangedListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int indices[] = list.getSelectedIndices();
			if (indices.length >= 1) {
				String names[] = new String[indices.length];
				for (int i = 0, j = 0; i < indices.length; i++) {
					names[j++] = dlm.get(indices[i]);
				}
				if (names[0] != null) {
					msg.setTargetName(names[0]);
				} 
			}
		}
	}

	private class SendMessage implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			if (e.getSource() == btn_send && localBye == false) {// 如果客户端没有说拜拜，客户端输入文字
				fromLocal = textArea_inputMessage.getText();
				textArea_inputMessage.setText("");
				textArea_showMessage.append("客户端输入：" + fromLocal + "\n");
				
				// msg.clearIgnoreStatus();
				msg.setText(fromLocal);
				out.println(JSON.toJSONString(msg));
				out.flush();
			}
			if (e.getSource() == btn_close) {
				localBye = true;
				out.println(exitCode);
				out.flush();
			}
			if (e.getSource() == btn_flush) {// 刷新按钮
				msg.setOperation("getClientList");
				out.println(JSON.toJSONString(msg));
				out.flush();
				msg.setOperation(null);
			}
		}
	}
	
	/**
	* 判断字符串是否可以转化为json对象
	* @param content
	* @return
	*/
	private static boolean isJsonObject(String content) {
	    // 此处应该注意，不要使用StringUtils.isEmpty(),因为当content为"  "空格字符串时，JSONObject.parseObject可以解析成功，
	    // 实际上，这是没有什么意义的。所以content应该是非空白字符串且不为空，判断是否是JSON数组也是相同的情况。
	    try {
	        JSONObject jsonStr = JSONObject.parseObject(content);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
}
