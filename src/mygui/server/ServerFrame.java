package mygui.server;

import java.awt.EventQueue;
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

public class ServerFrame {

	private JFrame frame;
	private JPanel contentPane = new JPanel();
	private JTextField textField;
	private JTextField textField_1;

	private int port = 4444;
	private String host = "127.0.0.1";
	private JTextArea textArea;

	boolean sysInBye = false;// 本机说拜拜
	boolean inBye = false;// 客户端输入流说拜拜
	String sysInputLine, clientInputLine;
	private JButton btn_send;
	private JTextArea textArea_1;
	private PrintWriter serverOut;
	private JButton btn_close;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
ServerFrame window = new ServerFrame();
					window.frame.setVisible(true);
		 window.initSocket();
	}

	/**
	 * Create the application.
	 */
	public ServerFrame() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("服务端");
		frame.setBounds(100, 100, 580, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("服务端IP");
		lblNewLabel.setBounds(32, 21, 48, 15);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(90, 18, 66, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText(host);

		JLabel label = new JLabel("端口");
		label.setBounds(181, 21, 30, 15);
		contentPane.add(label);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(221, 18, 66, 21);
		textField_1.setText("" + port);
		contentPane.add(textField_1);

		btn_send = new JButton("发送");
		btn_send.setBounds(480, 310, 76, 23);
		contentPane.add(btn_send);
		btn_send.addActionListener(new SendMessage());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 56, 368, 192);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setLineWrap(true);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(32, 258, 368, 75);
		contentPane.add(scrollPane_1);

		textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		textArea_1.setLineWrap(true);
		
		btn_close = new JButton("关闭连接");
		btn_close.setBounds(463, 277, 93, 23);
		contentPane.add(btn_close);
		btn_close.addActionListener(new SendMessage());
	}

	private void initSocket() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("无法监听端口: " + port + ". ");
			System.exit(1);
		}

		Socket clientSocket = null;
		try {
			textArea.append("正在等待客户端连接...\n");
			clientSocket = serverSocket.accept(); //
			textArea.append("客户端连接成功！\n");
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}
		System.out.println("Accept OK!");

		try {
			serverOut = new PrintWriter(clientSocket.getOutputStream(), true);
			// 由Socket对象得到输入流，并构造相应的BufferedReader对象
			BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			// 由系统标准输入设备构造BufferedReader对象
			BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));

//			clientInputLine = clientIn.readLine();// 从客户端读入
//			textArea.setText(textArea.getText() + "\n从客户端接收：" + clientInputLine);
//
//			textArea.setText(textArea.getText() + "\n服务端输入：");
//
//			// sysInputLine = sysIn.readLine();// 从服务端输入
//			sysInputLine = textArea_1.getText();
//			textArea_1.setText("");

			while (true) {// 循环从客户端读入数据
				if(!clientIn.ready()) {
					continue;
				}
//				if (sysInBye == false) {// 如果服务端没有说拜拜
//					serverOut.println(sysInputLine);// 将服务端键盘输入的文字发送到客户端的输入流中
//					serverOut.flush();
//					if (sysInputLine.equals("bye")) {
//						sysInBye = true;
//					}
//				}

				if (inBye == false) {// 如果客户端没有说拜拜
					clientInputLine = clientIn.readLine();// 从客户端读入文字
					textArea.append("从客户端接收：" + clientInputLine + "\n");
					if (clientInputLine.equals("bye")) {
						inBye = true;
					}
				}

				if (sysInBye == true && inBye == true) {
					break;
				}
			}
			serverOut.close();
			clientIn.close();
			sysIn.close();

			clientSocket.close();
			serverSocket.close();

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	private class SendMessage implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			if (e.getSource() == btn_send && sysInBye == false) {// 如果服务端没有说拜拜，服务端输入文字
				sysInputLine = textArea_1.getText();
				textArea_1.setText("");
				textArea.append("服务端输入：" + sysInputLine + "\n");
				serverOut.println(sysInputLine);// 将服务端键盘输入的文字发送到客户端的输入流中
				serverOut.flush();
			}
			if(e.getSource() == btn_close) {
				sysInBye = true;
			}
		}
	}
}
