package mygui.server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 单线程服务端的界面
 * @author xuxin
 *
 */
public class SingleServerFrame {

	private JFrame frame;
	private JPanel contentPane = new JPanel();
	private JTextField textField_ip;
	private JTextField textField_port;

	private int port = 4444;
	private String host = "127.0.0.1";
	JTextArea textArea_showMessage;

	boolean localBye = false;// 本机说拜拜
	boolean clientBye = false;// 客户端输入流说拜拜
	String localMessageLine, clientMessageLine;
	private JButton btn_send;
	private JTextArea textArea_inputMessage;
	private PrintWriter out;
	private JButton btn_close;
	
	String exitCode = "exit(0):123456";


	public static void main(String[] args) {
		SingleServerFrame window = new SingleServerFrame();
		window.frame.setVisible(true);
		window.initSocket();
	}

	/**
	 * 
	 */
	public SingleServerFrame() {
		initialize();

	}

	/**
	 * 
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
	}

	public void initSocket() {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		final BufferedReader clientIn;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("无法监听端口: " + port + ". ");
			System.exit(1);
		}

		try {
			textArea_showMessage.append("正在等待客户端连接...\n");
			clientSocket = serverSocket.accept(); //
			textArea_showMessage.append("客户端连接成功！\n");
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}

		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			// 由Socket对象得到输入流，并构造相应的BufferedReader对象
			clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			Listen listen = new Listen(clientIn, this);
			listen.setClientIn(clientIn);
			Thread lis = new Thread(listen);
			lis.start();
//			while (true) {// 循环从客户端读入数据
////				if(!clientIn.ready()) {
////					continue;
////				}
//
//				if (clientBye == false) {// 如果客户端没有说拜拜
//					clientMessageLine = clientIn.readLine();// 从客户端读入文字
//					if (clientMessageLine.equals(exitCode)) {// 对方断开
//						clientBye = true;
//						textArea_showMessage.append("客户端已退出" + clientMessageLine + "\n");
//					}else {
//						textArea_showMessage.append("从客户端接收：" + clientMessageLine + "\n");
//					}
//				}
//
//				if (localBye == true && clientBye == true) {
//					break;
//				}
//			}
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	private class SendMessage implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			if (e.getSource() == btn_send && localBye == false) {// 如果服务端没有说拜拜，服务端输入文字
				localMessageLine = textArea_inputMessage.getText();
				textArea_inputMessage.setText("");
				textArea_showMessage.append("服务端输入：" + localMessageLine + "\n");
				out.println(localMessageLine);// 将服务端键盘输入的文字发送到客户端的输入流中
				out.flush();
			}
			if(e.getSource() == btn_close) {
				localBye = true;
				out.println(exitCode);// 将服务端键盘输入的文字发送到客户端的输入流中
				out.flush();
			}
		}
	}
}

class Listen implements Runnable{
	BufferedReader clientIn = null;
	SingleServerFrame sf;

	/**
	 * @param clientIn
	 * @param sf
	 */
	public Listen(BufferedReader clientIn, SingleServerFrame sf) {
		this.clientIn = clientIn;
		this.sf = sf;
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		try {
			while (true) {// 循环从客户端读入数据

				if (sf.clientBye == false) {// 如果客户端没有说拜拜
					sf.clientMessageLine = clientIn.readLine();// 从客户端读入文字
					if (sf.clientMessageLine.equals(sf.exitCode)) {// 对方断开
						sf.clientBye = true;
						sf.textArea_showMessage.append("客户端已退出" + sf.clientMessageLine + "\n");
					}else {
						sf.textArea_showMessage.append("从客户端接收：" + sf.clientMessageLine + "\n");
					}
				}

				if (sf.localBye == true && sf.clientBye == true) {
					break;
				}
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			try {
				clientIn.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param clientIn 要设置的 clientIn
	 */
	public void setClientIn(BufferedReader clientIn) {
		this.clientIn = clientIn;
	}
	
}
