package mygui.login;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import mygui.friendslist2.MyFriendsList3;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class Login3 {

	private JFrame frame;
	private JPanel contentPane;
	private JTextField textField_zhanghao;
	private JPasswordField passwordField_mima;
	private JLayeredPane layeredPane_operation;
	private JPanel panel_operation;
	private JPanel panel_landing;
	private static int WindowStatus = 0;
	private static final int DEFAULT_WIDTH=410;
    private static final int DEFAULT_HEIGHT=330;
    //private static final int DEFAULT_HEIGHT_EXTEND=500;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login3 window = new Login3();
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
	public Login3() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("登录");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				Login3.class.getResource("/Images/chat_circle_love_29.611940298507px_1228617_easyicon.net.png")));
		frame.setResizable(false);// 设置窗口不可调整大小
		frame.setBounds(100, 100, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_logo = new JPanel();
		panel_logo.setBackground(Color.WHITE);
		panel_logo.setBounds(0, 0, 401, 141);
		panel_logo.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(panel_logo);
		panel_logo.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1
				.setIcon(new ImageIcon(Login3.class.getResource("/Images/avatar_users_64px_1108447_easyicon.net.png")));
		lblNewLabel_1.setBounds(168, 60, 64, 64);
		panel_logo.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(0, 0, 400, 141);
		lblNewLabel.setIcon(new ImageIcon(Login3.class.getResource("/Images/f988052c0de226d39930a64855d10355_3.jpg")));
		panel_logo.add(lblNewLabel);

		JPanel panel_main = new JPanel();
		panel_main.setBounds(0, 140, 401, 163);
		panel_main.setBackground(Color.WHITE);
		contentPane.add(panel_main);
		panel_main.setLayout(null);

		layeredPane_operation = new JLayeredPane();
		layeredPane_operation.setBackground(Color.WHITE);
		layeredPane_operation.setBounds(0, 0, 401, 153);
		panel_main.add(layeredPane_operation);

		JPanel panel_operation = new JPanel();
		layeredPane_operation.setLayer(panel_operation, 10);
		panel_operation.setBounds(0, 0, 401, 153);
		layeredPane_operation.add(panel_operation);
		panel_operation.setBackground(Color.WHITE);
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

		// 提示标签
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
		// 鼠标点击登录以后的事件
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

					// 用多线程实现让lbl_tips一段时间后消失
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(3000);// 该线程睡眠3秒
							} catch (InterruptedException ex) {
							}
							lbl_tips.setVisible(false);
						}
					});
					t.start();// 启动线程
					return;
				}
				if (account.equals(designate_account) && password_1.equals(designate_password)) {// 登录成功
					//JOptionPane.showMessageDialog(null, "登录成功！");
					layeredPane_operation.setLayer(panel_operation, -1);
					
					// 用计时器实现延迟
					Timer timer = new Timer();// 实例化Timer类
					timer.schedule(new TimerTask() {
						public void run() {
							//TODO 此处可显示登录成功
							layeredPane_operation.setLayer(panel_operation, 10);
							frame.dispose();//注销当前登录窗口
							//拉起好友界面
							new MyFriendsList3();
							this.cancel();
						}
					}, 1000);// 延迟x毫秒后启动
					
				} else {
					//JOptionPane.showMessageDialog(null, "账号或密码错误，请重试！");
					lbl_tips.setText("账号或密码错误，请重试！");
					lbl_tips.setVisible(true);
					// 用计时器实现延迟
					Timer timer = new Timer();// 实例化Timer类
					timer.schedule(new TimerTask() {
						public void run() {
							lbl_tips.setVisible(false);
							this.cancel();
						}
					}, 3000);// 延迟x毫秒后启动
				}
				//System.out.println(account);
				//System.out.println(password_1);
			}
		});

		JButton btn_zhaohuimima = new JButton("找回密码");
		btn_zhaohuimima.setHorizontalAlignment(SwingConstants.LEFT);
		btn_zhaohuimima.setBounds(238, 70, 85, 23);
		btn_zhaohuimima.setContentAreaFilled(false);// 按钮透明
		btn_zhaohuimima.setBorderPainted(false);// 去除边框
		panel_operation.add(btn_zhaohuimima);
		btn_zhaohuimima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 点击找回密码的事件
				if(WindowStatus == 0) {
					setSizeExtend();
					contentPane.add(zhaoHui());
				}
			}
		});
		

		JButton btn_erweima = new JButton("");// 显示二维码
		btn_erweima.setIcon(new ImageIcon(Login3.class.getResource("/Images/QRcode.png")));
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
		btn_zhucezhanghao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//注册账号事件
				if(frame.getHeight()==DEFAULT_HEIGHT) {
					contentPane.add(zhuCe());//绘制注册面板，并将其添加到contentPane中
					setSizeExtend();//设置窗口变大，setSizeExtend()在下面具体实现
				}else {
					setSizeDefault();
					contentPane.remove(zhuCe());
				}			
			}
		});
		
		
		JPanel panel_landing = new JPanel();
		panel_landing.setBackground(Color.WHITE);
		layeredPane_operation.setLayer(panel_landing, 9);
		panel_landing.setBounds(0, 0, 401, 153);
		layeredPane_operation.add(panel_landing);
		panel_landing.setLayout(null);
		
		JLabel lbl_landing_denglu = new JLabel("登录中...");
		lbl_landing_denglu.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_landing_denglu.setFont(new Font("宋体", Font.PLAIN, 16));
		lbl_landing_denglu.setBounds(119, 21, 162, 31);
		panel_landing.add(lbl_landing_denglu);
		
		JButton btn_landing_quxiao = new JButton("取消");
		btn_landing_quxiao.setFont(new Font("宋体", Font.PLAIN, 16));
		btn_landing_quxiao.setBounds(144, 89, 113, 31);
		panel_landing.add(btn_landing_quxiao);
		btn_landing_quxiao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//取消登录操作
				layeredPane_operation.setLayer(panel_operation, 10);
			}
		});
	}

	private JPanel zhuCe() {
		JPanel panel_zhuce = new JPanel();
		panel_zhuce.setBounds(0, 303, 401, 170);
		panel_zhuce.setBackground(Color.WHITE);
		//contentPane.add(panel_zhuce);
		panel_zhuce.setVisible(true);
		panel_zhuce.setLayout(null);
		panel_zhuce.setBorder(new TitledBorder(null, "用户注册", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblNewLabel = new JLabel("用户名");
		lblNewLabel.setBounds(66, 22, 70, 26);
		panel_zhuce.add(lblNewLabel);
		
		JTextField textField = new JTextField();
		textField.setBounds(146, 25, 163, 21);
		panel_zhuce.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("密码");
		lblNewLabel_1.setBounds(66, 54, 70, 26);
		panel_zhuce.add(lblNewLabel_1);
		
		JTextField textField_1 = new JTextField();
		textField_1.setBounds(146, 56, 163, 21);
		panel_zhuce.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("确认密码");
		lblNewLabel_2.setBounds(66, 84, 70, 26);
		panel_zhuce.add(lblNewLabel_2);
		
		JTextField textField_2 = new JTextField();
		textField_2.setBounds(146, 87, 163, 21);
		panel_zhuce.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("取消");
		btnNewButton.setBounds(66, 120, 93, 23);
		panel_zhuce.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSizeDefault();
			}
		});
		
		JButton btnNewButton_1 = new JButton("确认");
		btnNewButton_1.setBounds(216, 120, 93, 23);
		panel_zhuce.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setSizeDefault();
			}
		});
		return panel_zhuce;
	}

	private JPanel zhaoHui() {//找回密码面板
		JPanel panel_zhaohui = new JPanel();
		panel_zhaohui.setBounds(0, 303, 401, 170);
		panel_zhaohui.setBackground(Color.WHITE);
		panel_zhaohui.setBorder(new TitledBorder(null, "\u627E\u56DE\u5BC6\u7801", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_zhaohui.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("账号");
		lblNewLabel.setBounds(80, 25, 54, 15);
		panel_zhaohui.add(lblNewLabel);
		
		JTextField textField = new JTextField();
		textField.setBounds(157, 25, 155, 21);
		panel_zhaohui.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("电子邮件");
		lblNewLabel_1.setBounds(80, 56, 54, 15);
		panel_zhaohui.add(lblNewLabel_1);
		
		JTextField textField_1 = new JTextField();
		textField_1.setBounds(157, 56, 155, 21);
		panel_zhaohui.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("取消");
		btnNewButton.setBounds(80, 87, 93, 23);
		panel_zhaohui.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton btnNewButton_1 = new JButton("找回");
		btnNewButton_1.setBounds(219, 87, 93, 23);
		panel_zhaohui.add(btnNewButton_1);
		
		return panel_zhaohui;
	}

	private void setSizeDefault() {
		frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	private void setSizeExtend() {
		//frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT + 170);
		for(int i = 0; i<= 170;i++) {
			frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT + i);
		}
	}
}
