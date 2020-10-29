package mygui.login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login2 {

	private JFrame frame;
	private JPanel contentPane;
	private JTextField textField_zhanghao;
	private JPasswordField passwordField_mima;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login2 window = new Login2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("登录");
		frame.setResizable(false);// 设置窗口不可调整大小
		frame.setBounds(100, 100, 410, 330);
		frame.setLocationRelativeTo(null);// 打开窗口居中
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				Login2.class.getResource("/Images/chat_circle_love_29.611940298507px_1228617_easyicon.net.png")));
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);
		
		JPanel panel_logo = new JPanel();
		panel_logo.setBackground(Color.WHITE);
		panel_logo.setBounds(0, 0, 401, 141);
		panel_logo.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(panel_logo);
		panel_logo.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1
				.setIcon(new ImageIcon(Login2.class.getResource("/Images/avatar_users_64px_1108447_easyicon.net.png")));
		lblNewLabel_1.setBounds(168, 60, 64, 64);
		panel_logo.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(0, 0, 400, 141);
		lblNewLabel.setIcon(new ImageIcon(Login2.class.getResource("/Images/f988052c0de226d39930a64855d10355_3.jpg")));
		panel_logo.add(lblNewLabel);

		JPanel panel_main = new JPanel();
		panel_main.setBounds(0, 140, 401, 153);
		panel_main.setBackground(Color.WHITE);
		contentPane.add(panel_main);
		panel_main.setLayout(null);

		JPanel panel_operation = new JPanel();
		panel_operation.setBounds(0, 0, 401, 153);
		panel_operation.setBackground(Color.WHITE);
		//panel_operation.setVisible(false);
		panel_main.add(panel_operation);
		panel_operation.setLayout(null);

		JLabel lbl_zhanghao = new JLabel("账号");
		lbl_zhanghao.setFont(new Font("宋体", Font.PLAIN, 16));
		lbl_zhanghao.setBackground(Color.WHITE);
		lbl_zhanghao.setBounds(97, 10, 35, 22);
		panel_operation.add(lbl_zhanghao);

		JLabel lbl_mima = new JLabel("密码");
		lbl_mima.setFont(new Font("宋体", Font.PLAIN, 16));
		lbl_mima.setBackground(Color.WHITE);
		lbl_mima.setBounds(97, 42, 35, 22);

		panel_operation.add(lbl_mima);

//		textField_zhanghao = new JTextField();
//		textField_zhanghao.setBounds(148, 11, 159, 22);
//		panel_operation.add(textField_zhanghao);
//		textField_zhanghao.setColumns(10);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setEditable(true);
		comboBox.setBounds(145, 12, 159, 22);
		panel_operation.add(comboBox);

		passwordField_mima = new JPasswordField();
		passwordField_mima.setBounds(145, 43, 159, 22);
		panel_operation.add(passwordField_mima);

		JCheckBox checkbox_autologin = new JCheckBox("自动登录");
		checkbox_autologin.setBackground(Color.WHITE);
		checkbox_autologin.setBounds(97, 70, 75, 23);
		panel_operation.add(checkbox_autologin);

		JCheckBox checkbox_remmber = new JCheckBox("记住密码");
		checkbox_remmber.setBackground(Color.WHITE);
		checkbox_remmber.setBounds(174, 70, 75, 23);
		panel_operation.add(checkbox_remmber);

		//提示标签
		JLabel lbl_tips = new JLabel("New label");
		lbl_tips.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_tips.setBounds(97, 132, 207, 15);
		lbl_tips.setVisible(false);
		panel_operation.add(lbl_tips);

		JButton btn_denglu = new JButton("登录");
		btn_denglu.setFont(new Font("新宋体", Font.PLAIN, 14));
		btn_denglu.setForeground(Color.WHITE);
		btn_denglu.setBackground(SystemColor.textHighlight);
		btn_denglu.setBounds(97, 99, 207, 23);
		panel_operation.add(btn_denglu);
		//鼠标点击登录以后的事件
		btn_denglu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// JComboBox<String> cb = (JComboBox<String>)
				// e.getSource();//.getEditorComponent().toString();
				String account = new String();// 获取输入的账号
				char[] password = new char[64];// 获取输入的密码（char)格式
				account = comboBox.getEditor().getItem().toString().trim();// 获取组合框输入的文本；
				/*
				 * JComboBox有一个getEditor()方法，getEditor()方法返回ComboBoxEditor,
				 * ComboBoxEditor里getItem()
				 */

				password = passwordField_mima.getPassword();// 返回char类型密码
				String password_1 = String.valueOf(password);// 把字符串类型的password转换为String

				String designate_account = new String("xxy");// 默认正确的账号和密码组合
				String designate_password = new String("123");

				if (account.length() == 0 || password.length == 0) {
					// JOptionPane.showMessageDialog(null, "账号或密码不能允许为空！");
					
					lbl_tips.setText("账号或密码不能为空！");
					lbl_tips.setVisible(true);

					//用多线程实现让lbl_tips一段时间后消失
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							// jl.setText("呈现文字.5秒后消失");
							try {
								Thread.sleep(3000);// 该线程睡眠3秒
							} catch (InterruptedException ex) {
							}
							// jl.setText("");//清空文字
							lbl_tips.setVisible(false);
						}
					});
					t.start();// 启动线程

					//用计时器实现让lbl_tips一段时间后消失
//					long start = System.currentTimeMillis();
//					long end = start + 3000;
//					while(start>0) {
//						try {
//						Thread.sleep(1);
//						if(System.currentTimeMillis() >= end) {
//							lbl_tips.setVisible(false);
//							break;
//							}
//						} catch (InterruptedException e1){
//							e1.printStackTrace();
//						}
//					}
					return;
				}
				if (account.equals(designate_account) && password_1.equals(designate_password)) {
					JOptionPane.showMessageDialog(null, "登录成功！");
				} else {
					JOptionPane.showMessageDialog(null, "账号或密码错误，请重试！");
				}
				System.out.println(account);
				System.out.println(password_1);
			}
		});

		JButton btn_zhaohuimima = new JButton("找回密码");
		btn_zhaohuimima.setHorizontalAlignment(SwingConstants.LEFT);
		btn_zhaohuimima.setBounds(238, 70, 85, 23);
		btn_zhaohuimima.setContentAreaFilled(false);// 按钮透明
		btn_zhaohuimima.setBorderPainted(false);// 去除边框
		panel_operation.add(btn_zhaohuimima);

		JButton btn_erweima = new JButton("");// 显示二维码
		btn_erweima.setIcon(new ImageIcon(Login2.class.getResource("/Images/QRcode.png")));
		btn_erweima.setBounds(343, 110, 40, 40);
		btn_erweima.setBorderPainted(false);// 去除边框
		btn_erweima.setMargin(new Insets(0, 0, 0, 0));// 让按钮随按钮上的图案变化
		panel_operation.add(btn_erweima);

		JButton btn_zhucezhanghao = new JButton("注册账号");
		btn_zhucezhanghao.setBounds(0, 127, 85, 23);
		// btnNewButton_2.setOpaque(false);
		btn_zhucezhanghao.setContentAreaFilled(false);// 按钮透明
		btn_zhucezhanghao.setBorderPainted(false);// 去除边框
		panel_operation.add(btn_zhucezhanghao);
	}

}
