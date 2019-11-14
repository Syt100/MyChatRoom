package mygui.server;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

import mygui.client.ClientFrame;

public class MultiServerFrame {

	private JFrame frame;
	private JPanel contentPane = new JPanel();
	private JTextField textField_ip;
	private JTextField textField_port;

	private int port = 4444;
	private String host = "127.0.0.1";
	private JTextArea textArea_showMessage;

	boolean localBye = false;// 本机说拜拜
	boolean clientBye = false;// 客户端输入流说拜拜
	String localMessageLine, clientMessageLine;
	private JButton btn_send;
	private JTextArea textArea_inputMessage;
	private PrintWriter out;
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
		MultiServerFrame window = new MultiServerFrame();
		window.frame.setVisible(true);
		//window.initSocket();
	}

	/**
	 * Create the application.
	 */
	public MultiServerFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
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
	}

	public static void initSocket() {
		ServerSocket serverSocket = null;
		boolean listening = true;
		int clientNumber = 0;

		try {
			serverSocket = new ServerSocket(4444);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
			System.exit(-1);
		}
		
		try {
			while (listening)
			{
				Socket socket;
				//textArea_showMessage.append("等待客户端连接\n");
				socket = serverSocket.accept();  //锟斤拷锟斤拷锟节此等猴拷突锟斤拷说锟斤拷锟斤拷锟�
				clientNumber++;
				//textArea_showMessage.append("有" + clientNumber + "个客户端已连接\n");
				new MultiTalkServerThread(socket, clientNumber).start();
			}
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
			if (e.getSource() == btn_send && localBye == false) {// 如果服务端没有说拜拜，服务端输入文字
				localMessageLine = textArea_inputMessage.getText();
				textArea_inputMessage.setText("");
				textArea_showMessage.append("服务端输入：" + localMessageLine + "\n");
				out.println(localMessageLine);// 将服务端键盘输入的文字发送到客户端的输入流中
				out.flush();
			}
			if (e.getSource() == btn_close) {
				localBye = true;
			}
		}
	}

}

class MultiTalkServerThread extends Thread {
	private Socket socket = null;
	private int clientNumber;

	public MultiTalkServerThread(Socket socket, int clientNumber) {
		super("MultiTalkServerThread");
		this.socket = socket;
		this.clientNumber = clientNumber;
		System.out.println("Accept Client" + clientNumber);
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
//		SingleServerFrame singleServer = new SingleServerFrame();
//		singleServer.initSocket();
		MultiServerFrame.initSocket();
	}
}
