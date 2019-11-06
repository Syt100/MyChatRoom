package mygui.client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientFrame {

	private JFrame frame;
	private JPanel contentPane = new JPanel();
	private JTextField textField;
	private JTextField textField_1;

	private int port = 4444;
	private String host = "127.0.0.1";
	private JTextArea textArea;

	boolean ubye = false;// 本机说拜拜
	boolean sbye = false;// 服务端 输入流说拜拜
	String fromServer, fromUser;
	private JButton btn_send;
	private JTextArea textArea_1;
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
		ClientFrame window = new ClientFrame();
					window.frame.setVisible(true);
					window.initSocket();
	}

	/**
	 * Create the application.
	 */
	public ClientFrame() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("客户端");
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
		Socket client = null;
		out = null;
		BufferedReader in = null;

		try {
			client = new Socket(host, port);
			out = new PrintWriter(client.getOutputStream(), true); // auto flush
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: 127.0.0.1.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: 127.0.0.1.");
			System.exit(1);
		}
		try {
			while (true) {
				if(!in.ready()) {
					continue;
				}
//				if (ubye == false && !(fromUser.equals("") || fromUser == null)) {
//					out.println(fromUser);
//					out.flush();
//					fromUser = "";
//					// System.out.println("Client: " + fromUser);
//					if (fromUser.equals("Bye."))
//						ubye = true;
//				}

				if (sbye == false) {
					fromServer = in.readLine();
					textArea.append("从服务端接收：" + fromServer + "\n");
					if (fromServer.equals("Bye."))
						sbye = true;
				}

				if (ubye == true && sbye == true)
					break;
			}

			out.close();
			in.close();
			client.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	private class SendMessage implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			if (e.getSource() == btn_send && ubye == false) {// 如果客户端没有说拜拜，客户端输入文字
				fromUser = textArea_1.getText();
				textArea_1.setText("");
				textArea.append("客户端输入：" + fromUser + "\n");
				out.println(fromUser);
				out.flush();
			}
			if(e.getSource() == btn_close) {
				ubye = true;
			}
		}
	}

}
