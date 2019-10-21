package mygui.login;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import mygui.friendslist2.MyFriendsList3;
import mygui.friendslist2.ResizeFrame;
import util.ConstantStatus;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class Login4 extends ResizeFrame {

	private ResizeFrame frame = new ResizeFrame();
	private JPanel content = new JPanel();
	private JLayeredPane layeredPane_main = new JLayeredPane();
	private JPanel panel_operation;
	private JPanel panel_landing;
	private Register panel_register;
	private JPanel panelUpMiniLabel;
	private JLabel lblSetting;
	private JLabel lblMini;
	private JLabel lblClose;

	private JPanel panelUpCloseLabel;
	private JPanel content_1;
	protected ImageIcon backGroundImgIcon = new ImageIcon(getClass().getResource("/Images/source/img_0.png"));
	private JComboBox<String> comboBox_zhanghao;
	private JPasswordField passwordField_mima;
	private JLabel lbl_zhanghao;
	private JLabel lbl_mima;
	private JButton btn_zhaohuimima;
	private JButton btn_zhucezhanghao;
	private JLabel lbl_erweima;
	private JButton btn_denglu;
	private JLabel lbl_tips;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login4 window = new Login4();
					// window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login4() {
		frame.setBounds(100, 100, 430, 330);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initBackGround();
		initialize();
		initMyFocusListener();
		initMyMouseListener();
		frame.setVisible(true);
	}

	private void initBackGround() {
		frame.setUndecorated(true);// 将原始的边框去掉
		frame.setLocationRelativeTo(null);// 设置窗口打开位置居中
		content_1 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// 这一句可以清除之前绘制的图像，在这个例子中看不出来效果，若是java做画图板的程序就能看出来
				super.paintComponent(g);
				// 此方法有很多种，看的眼花缭乱，了解参数了就好多了，这里说一下基本的
				// 1：image对象
				// 2：重绘的起始横坐标
				// 3：重绘的起始纵坐标
				// 4：重绘的宽度
				// 5：重绘的高度
				// 6：一个实现ImageObserver
				// 接口的对象。它将该对象登记为一个图像观察者，因此当图像的任何新信息可见时它被通知。大多组件可以简单的指定this或null
				// 组件可以指定this作为图像观察者的原因是Component类实现了ImageObserver接口。当图像数据被加载时它的实现调用repaint方法
				g.drawImage(backGroundImgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
			}
		};
		frame.setContentPane(content_1);
		content_1.setLayout(new BorderLayout(0, 0));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JPanel panel_up = new JPanel();
		panel_up.setOpaque(false);
		content_1.add(panel_up, BorderLayout.NORTH);
		panel_up.setLayout(new BorderLayout(0, 0));

		JLabel lbl_qqIcon = new JLabel();
		lbl_qqIcon.setBorder(new EmptyBorder(7, 3, 0, 0));
		lbl_qqIcon.setIcon(new ImageIcon(Login4.class.getResource("/Images/qqIcon/logo-QQ.png")));
		panel_up.add(lbl_qqIcon, BorderLayout.WEST);

		JPanel panelUpRightOperation = new JPanel();
		panelUpRightOperation.setOpaque(false);
		panel_up.add(panelUpRightOperation, BorderLayout.EAST);
		panelUpRightOperation.setLayout(new BorderLayout(0, 0));

		JPanel panelUpSettingMiniCloseOperation = new JPanel();
		panelUpSettingMiniCloseOperation.setOpaque(false);
		panelUpRightOperation.add(panelUpSettingMiniCloseOperation, BorderLayout.NORTH);
		panelUpSettingMiniCloseOperation.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		JPanel panelUpSettingLabel = new JPanel();
		panelUpSettingLabel.setOpaque(false);
		panelUpSettingMiniCloseOperation.add(panelUpSettingLabel);
		panelUpSettingLabel.setLayout(new BorderLayout(0, 0));

		lblSetting = new JLabel("");
		lblSetting.setHorizontalAlignment(SwingConstants.CENTER);
		lblSetting.setIcon(new ImageIcon(Login4.class.getResource("/Images/qqIcon/btn_set_normal.png")));
		panelUpSettingLabel.add(lblSetting);

		panelUpMiniLabel = new JPanel();
		panelUpMiniLabel.setOpaque(false);
		panelUpMiniLabel.setBackground(Color.LIGHT_GRAY);
		panelUpSettingMiniCloseOperation.add(panelUpMiniLabel);

		lblMini = new JLabel("－");
		lblMini.setForeground(Color.WHITE);
		panelUpMiniLabel.add(lblMini);
		lblMini.setFont(new Font("宋体", Font.PLAIN, 18));

		panelUpCloseLabel = new JPanel();
		panelUpCloseLabel.setOpaque(false);
		panelUpCloseLabel.setBackground(Color.RED);
		panelUpSettingMiniCloseOperation.add(panelUpCloseLabel);

		lblClose = new JLabel("×");
		lblClose.setForeground(Color.WHITE);
		panelUpCloseLabel.add(lblClose);
		lblClose.setFont(new Font("宋体", Font.PLAIN, 18));

		JPanel panel_main = new JPanel();
		panel_main.setOpaque(false);
		content_1.add(panel_main, BorderLayout.CENTER);
		panel_main.setLayout(null);

		JLabel lbl_header = new JLabel("");
		lbl_header.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_header.setIcon(new ImageIcon(Login4.class.getResource("/Images/3.png")));
		lbl_header.setBounds(166, 25, 98, 98);
		panel_main.add(lbl_header);

		JLabel lbl_light = new JLabel("");
		lbl_light.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_light.setBounds(166, 25, 98, 98);
		panel_main.add(lbl_light);
		lbl_light.setIcon(new ImageIcon(Login4.class.getResource("/Images/qqIcon/head_bkg_shadow.png")));

		// 分层面板
		layeredPane_main.setBounds(0, 75, 430, 206);
		layeredPane_main.setBackground(Color.WHITE);
		panel_main.add(layeredPane_main);
		layeredPane_main.setLayout(null);

		// 主要操作的面板，用于填写账号密码等
		panel_operation = new JPanel();
		layeredPane_main.setLayer(panel_operation, 10);
		panel_operation.setBounds(0, 0, 430, 206);
		panel_operation.setBackground(Color.WHITE);
		layeredPane_main.add(panel_operation);
		panel_operation.setLayout(null);

		// 显示登录中
		/* 登录中面板的按钮 开始 */
		panel_landing = new JPanel();
		panel_landing.setOpaque(false);
		panel_landing.setBackground(Color.WHITE);
		layeredPane_main.setLayer(panel_landing, 9);
		panel_landing.setBounds(0, 0, 430, 206);
		layeredPane_main.add(panel_landing);
		panel_landing.setLayout(null);

		JLabel lbl_landing_denglu = new JLabel("登录中...");
		lbl_landing_denglu.setForeground(Color.WHITE);
		lbl_landing_denglu.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_landing_denglu.setFont(new Font("宋体", Font.PLAIN, 18));
		lbl_landing_denglu.setBounds(135, 64, 160, 35);
		panel_landing.add(lbl_landing_denglu);

		JButton btn_landing_quxiao = new JButton("取消");
		btn_landing_quxiao.setOpaque(false);
		btn_landing_quxiao.setForeground(Color.WHITE);
		btn_landing_quxiao.setBackground(new Color(0f, 0f, 0f, 0f));
		btn_landing_quxiao.setFont(new Font("宋体", Font.PLAIN, 18));
		btn_landing_quxiao.setBounds(158, 137, 113, 31);
		panel_landing.add(btn_landing_quxiao);
		/* 登录中面板的按钮 结束 */
		
		/* 注册账号  开始*/
		/* 注册账号  结束*/

		lbl_zhanghao = new JLabel("");
		lbl_zhanghao.setIcon(new ImageIcon(Login4.class.getResource("/Images/qqIcon/qqnum_normal.png")));
		lbl_zhanghao.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_zhanghao.setFont(new Font("宋体", Font.PLAIN, 16));
		lbl_zhanghao.setBackground(Color.WHITE);
		lbl_zhanghao.setBounds(85, 50, 35, 25);
		panel_operation.add(lbl_zhanghao);

		lbl_mima = new JLabel("");
		lbl_mima.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_mima.setIcon(new ImageIcon(Login4.class.getResource("/Images/qqIcon/psw_normal.png")));
		lbl_mima.setFont(new Font("宋体", Font.PLAIN, 16));
		lbl_mima.setBackground(Color.WHITE);
		lbl_mima.setBounds(85, 85, 35, 25);
		panel_operation.add(lbl_mima);

		// 账号框
		comboBox_zhanghao = new JComboBox<String>();
		comboBox_zhanghao.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		comboBox_zhanghao.setEditable(true);
		comboBox_zhanghao.setBounds(120, 50, 220, 25);
		panel_operation.add(comboBox_zhanghao);

		// 密码框
		passwordField_mima = new JPasswordField();
		passwordField_mima.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 25));
		passwordField_mima.setBounds(120, 85, 220, 25);
		panel_operation.add(passwordField_mima);

		JCheckBox checkbox_autologin = new JCheckBox("自动登录");
		checkbox_autologin.setForeground(Color.GRAY);
		checkbox_autologin.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		checkbox_autologin.setBackground(Color.WHITE);
		checkbox_autologin.setBounds(93, 115, 75, 23);
		panel_operation.add(checkbox_autologin);

		JCheckBox checkbox_remmber = new JCheckBox("记住密码");
		checkbox_remmber.setForeground(Color.GRAY);
		checkbox_remmber.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		checkbox_remmber.setBackground(Color.WHITE);
		checkbox_remmber.setBounds(185, 115, 75, 23);
		panel_operation.add(checkbox_remmber);

		btn_zhaohuimima = new JButton("找回密码");
		btn_zhaohuimima.setForeground(Color.GRAY);
		btn_zhaohuimima.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
		btn_zhaohuimima.setBounds(270, 115, 85, 23);
		btn_zhaohuimima.setContentAreaFilled(false);// 按钮透明
		btn_zhaohuimima.setBorderPainted(false);// 去除边框
		panel_operation.add(btn_zhaohuimima);

		btn_denglu = new JButton("登录");
		btn_denglu.setFont(new Font("黑体", Font.PLAIN, 16));
		btn_denglu.setForeground(Color.WHITE);
		btn_denglu.setBackground(SystemColor.textHighlight);
		btn_denglu.setBounds(93, 147, 247, 35);
		panel_operation.add(btn_denglu);

		// 提示标签
		lbl_tips = new JLabel("我是一个提示标签");
		lbl_tips.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lbl_tips.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_tips.setBounds(115, 185, 207, 15);
		lbl_tips.setVisible(false);
		panel_operation.add(lbl_tips);

		btn_zhucezhanghao = new JButton("注册账号");
		btn_zhucezhanghao.setForeground(Color.GRAY);
		btn_zhucezhanghao.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		btn_zhucezhanghao.setBounds(0, 177, 85, 23);
		// btnNewButton_2.setOpaque(false);
		btn_zhucezhanghao.setContentAreaFilled(false);// 按钮透明
		btn_zhucezhanghao.setBorderPainted(false);// 去除边框
		panel_operation.add(btn_zhucezhanghao);

		lbl_erweima = new JLabel("");// 显示二维码
		lbl_erweima.setIcon(new ImageIcon(Login4.class.getResource("/Images/qqIcon/corner_right_normal_breath.png")));
		lbl_erweima.setBounds(395, 172, 22, 22);
		// btn_erweima.setBorderPainted(false);// 去除边框
		// btn_erweima.setMargin(new Insets(0, 0, 0, 0));// 让按钮随按钮上的图案变化
		panel_operation.add(lbl_erweima);
		panel_register = new Register();
		layeredPane_main.setLayer(panel_register, 8);
		layeredPane_main.add(panel_register);
	}

	// 焦点事件
	private class MyFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {// 得到焦点
			// TODO 自动生成的方法存根
			if (e.getSource() == comboBox_zhanghao.getEditor().getEditorComponent()) {// 如果账号输入框获取焦点
				lbl_zhanghao.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/qqnum_hover.png")));
			}
			if (e.getSource() == passwordField_mima) {
				lbl_mima.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/psw_hover.png")));
			}
		}

		@Override
		public void focusLost(FocusEvent e) {// 失去焦点
			// TODO 自动生成的方法存根
			if (e.getSource() == comboBox_zhanghao.getEditor().getEditorComponent()) {
				lbl_zhanghao.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/qqnum_normal.png")));
			}
			if (e.getSource() == passwordField_mima) {
				lbl_mima.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/psw_normal.png")));
			}

		}
	}

	// 鼠标事件
	private class MyMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO 自动生成的方法存根
			super.mouseClicked(e);
			if (e.getSource() == btn_denglu) {
				int judge = judgeLoginStatus();
				if (judge == 1) {
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
				} else if (judge == 2) {
					layeredPane_main.setLayer(panel_operation, -1);
					panel_operation.setVisible(false);// 将操作面板设为不可见，以显示登录中的透明背景
					
					// 用计时器实现延迟
					Timer timer = new Timer();// 实例化Timer类
					timer.schedule(new TimerTask() {
						public void run() {
							// TODO 此处可显示登录成功
							layeredPane_main.setLayer(panel_operation, 10);
							frame.dispose();// 注销当前登录窗口
							// 拉起好友界面
							MyFriendsList3 window = new MyFriendsList3();
							window.setFrameVisible(true);
							this.cancel();
						}
					}, 1000);// 延迟x毫秒后启动
				} else if (judge == 3) {
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
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {// 鼠标按下
			if (e.getSource() == lbl_erweima) {
				lbl_erweima
						.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/corner_right_normal_down.png")));
				System.out.println("12");
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {// 鼠标松开
			if (e.getSource() == lbl_erweima) {
				lbl_erweima.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/corner_right_hover.png")));
			}
			if (e.getSource() == lblClose) {
				System.exit(0);
			}
			if (e.getSource() == lblMini) {
				frame.setExtendedState(Frame.ICONIFIED);
			}
			if (e.getSource() == btn_zhucezhanghao) {
				layeredPane_main.setLayer(panel_register, 100);
				panel_operation.setVisible(false);
				panel_landing.setVisible(false);
			}
			if (e.getSource() == panel_register.btn_queren) {// 注册页面中的确认按钮
				if(panel_register.checkEmpty() == false) {
					// 注册失败由Register类处理
				}else {
					layeredPane_main.setLayer(panel_register, 8);
				    panel_operation.setVisible(true);
				    panel_landing.setVisible(true);
				}
			}
			if (e.getSource() == panel_register.btn_quxiao) {// 注册页面中的取消按钮
				panel_register.clean();//清空输入框的内容
				layeredPane_main.setLayer(panel_register, 8);
				panel_operation.setVisible(true);
				panel_landing.setVisible(true);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == btn_zhaohuimima) {
				btn_zhaohuimima.setForeground(Color.BLACK);
				btn_zhaohuimima.setCursor(new Cursor(Cursor.HAND_CURSOR));// 设置鼠标为小手形状
			}
			if (e.getSource() == btn_zhucezhanghao) {
				btn_zhucezhanghao.setForeground(Color.BLACK);
				btn_zhucezhanghao.setCursor(new Cursor(Cursor.HAND_CURSOR));// 设置鼠标为小手形状
			}
			if (e.getSource() == lbl_erweima) {
				lbl_erweima.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/corner_right_hover.png")));
			}
			if (e.getSource() == lblClose) {
				panelUpCloseLabel.setOpaque(true);
				panelUpCloseLabel.repaint();
				panelUpCloseLabel.setBackground(Color.RED);
			}
			if (e.getSource() == lblMini) {
				panelUpMiniLabel.setOpaque(true);
				panelUpMiniLabel.repaint();
				panelUpMiniLabel.setBackground(new Color(255, 255, 255, 100));
			}
			if (e.getSource() == lblSetting) {
				lblSetting.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/btn_set_hover.png")));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == btn_zhaohuimima) {
				btn_zhaohuimima.setForeground(Color.GRAY);
			}
			if (e.getSource() == btn_zhucezhanghao) {
				btn_zhucezhanghao.setForeground(Color.GRAY);
			}
			if (e.getSource() == lbl_erweima) {
				lbl_erweima.setIcon(
						new ImageIcon(getClass().getResource("/Images/qqIcon/corner_right_normal_breath.png")));
			}
			if (e.getSource() == lblClose) {
				panelUpCloseLabel.setOpaque(false);
				panelUpCloseLabel.repaint();
				// panel_4.setBackground(Color.RED);
			}
			if (e.getSource() == lblMini) {
				panelUpMiniLabel.setOpaque(false);
				panelUpMiniLabel.repaint();
			}
			if (e.getSource() == lblSetting) {
				lblSetting.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/btn_set_normal.png")));
			}
		}

	}

	private void initMyFocusListener() {
		// 解决java JComBox
		// 可编辑时的Focus事件:https://blog.csdn.net/helloxtayfnje/article/details/5574897
		comboBox_zhanghao.getEditor().getEditorComponent().addFocusListener(new MyFocusListener());
		passwordField_mima.addFocusListener(new MyFocusListener());
	}

	/**
	 * 初始化鼠标事件
	 */
	private void initMyMouseListener() {
		MyMouseListener mos = new MyMouseListener();
		btn_zhaohuimima.addMouseListener(mos);
		btn_zhucezhanghao.addMouseListener(mos);
		lbl_erweima.addMouseListener(mos);
		lblClose.addMouseListener(mos);
		lblMini.addMouseListener(mos);
		lblSetting.addMouseListener(mos);
		btn_denglu.addMouseListener(mos);
		btn_zhucezhanghao.addMouseListener(mos);
		panel_register.addMouseListener(mos);
		panel_register.btn_queren.addMouseListener(mos);
		panel_register.btn_quxiao.addMouseListener(mos);
	}

	private int judgeLoginStatus() {
		String account = new String();// 获取输入的账号
		char[] password = new char[64];// 获取输入的密码（char)格式
		account = comboBox_zhanghao.getEditor().getItem().toString().trim();
		/*
		 * 获取组合框输入的文本； JComboBox有一个getEditor()方法，getEditor()方法返回ComboBoxEditor,
		 * ComboBoxEditor里getItem()
		 */

		password = passwordField_mima.getPassword();// 返回char类型密码
		String password_1 = String.valueOf(password);// 把字符串类型的password转换为String

		String designate_account = new String("xxy");// 默认正确的账号和密码组合
		String designate_password = new String("123");

		if (account.length() == 0 || password.length == 0) {
			return 1;
		} else if (account.equals(designate_account) && password_1.equals(designate_password)) {
			return 2;
		} else {
			return 3;
		}
	}
}
