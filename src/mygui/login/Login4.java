package mygui.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import bean.Users;
import exception.AccountInputException;
import mygui.frameutil.BackgroundJPanel;
import mygui.frameutil.ResizeFrame;
import mygui.friendslist2.MyFriendsList3;
import util.ConstantStatus;

public class Login4 {

	/** 可调大小窗体 */
	private ResizeFrame frame = new ResizeFrame();
	/** 内容面板，并可以设置背景图片 */
	private BackgroundJPanel content;
	/** 内容面板的背景图片 */
	private ImageIcon backGroundImgIcon = new ImageIcon(getClass().getResource("/Images/source/img_0.png"));
	/** 设置图标 */
	private JLabel lblSetting;
	/** 最小化图标 */
	private JLabel lblMini;
	/** 关闭窗口图标 */
	private JLabel lblClose;
	/** 放置最小化图标的面板，用来调节透明度 */
	private JPanel panelUpMiniLabel;
	/** 放置关闭图标的面板，用来调节透明度 */
	private JPanel panelUpCloseLabel;
	/** 放置头像的面板 */
	private JPanel panel_header;
	/** 分层面板，用于组织登录面板、注册面板、登录中面板、二维码登录面板、找回密码面板等显示的前后关系 */
	private JLayeredPane layeredPane_main = new JLayeredPane();
	/** 输入账号密码、点击登录等 */
	private JPanel panel_operation;
	/** 显示登录中 */
	private JPanel panel_landing;
	/** 注册面板 */
	private Register panel_register;
	/** 二维码面板 */
	private QRcode panel_qrcode = new QRcode();
	/** 找回密码面板 */
	private RetrievePassword panel_retrievePassword;
	/** 设置面板 */
	private Setting panel_setting;

	/** 账号图标 */
	private JLabel lbl_zhanghao;
	/** 密码图标 */
	private JLabel lbl_mima;
	/** 账号输入框 */
	private JComboBox<String> comboBox_zhanghao;
	/** 密码输入框 */
	private JPasswordField passwordField_mima;
	/** 找回密码按钮 */
	private JButton btn_zhaohuimima;
	/** 注册账号按钮 */
	private JButton btn_zhucezhanghao;
	/** 登录按钮 */
	private JButton btn_denglu;
	/** 二维码图标 */
	private JLabel lbl_erweima;
	/** 显示提示信息，默认隐藏，按需显示 */
	private JLabel lbl_tips;
	
	// 网络部分
	/** 客户端套接字 */
	private Socket socket = null;
	/** 客户端输出流 */
	private PrintWriter out = null;
	/** 客户端输入流 */
	private BufferedReader in = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Login4();
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
		initBackGround();
		initialize();
		initMyFocusListener();
		initMyMouseListener();
		setTop(panel_operation);// 将主面板置顶
		frame.setVisible(true);// 一般在构造方法最后一步
	}

	/**
	 * 初始化窗体frame和contentPane，设置背景
	 */
	private void initBackGround() {
		frame.setBounds(100, 100, 430, 330);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 在这里无效，因为去除了窗口装饰
		frame.setUndecorated(true);// 将原始的边框去掉
		frame.setLocationRelativeTo(null);// 设置窗口打开位置居中
		content = new BackgroundJPanel(backGroundImgIcon);// 初始化内容面板，设置背景
		frame.setContentPane(content);
		content.setLayout(new BorderLayout(0, 0));
	}

	/**
	 * 初始化界面组件
	 */
	private void initialize() {
		// 将内容面板分为两部分，这是上半部分
		JPanel panel_up = new JPanel();
		panel_up.setOpaque(false);
		panel_up.setLayout(new BorderLayout(0, 0));
		content.add(panel_up, BorderLayout.NORTH);

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
		lblMini.setFont(new Font("宋体", Font.PLAIN, 18));
		panelUpMiniLabel.add(lblMini);

		panelUpCloseLabel = new JPanel();
		panelUpCloseLabel.setOpaque(false);
		panelUpCloseLabel.setBackground(Color.RED);
		panelUpSettingMiniCloseOperation.add(panelUpCloseLabel);

		lblClose = new JLabel("×");
		lblClose.setForeground(Color.WHITE);
		lblClose.setFont(new Font("宋体", Font.PLAIN, 18));
		panelUpCloseLabel.add(lblClose);

		JPanel panel_main = new JPanel();
		panel_main.setOpaque(false);
		panel_main.setLayout(null);
		content.add(panel_main, BorderLayout.CENTER);
		
		// 头像面板，放头像
		panel_header = new JPanel();
		panel_header.setOpaque(false);
		panel_header.setLayout(null);
		panel_header.setBounds(166, 25, 98, 98);
		panel_main.add(panel_header);

		// 头像标签，用于存放用户头像
		JLabel lbl_header = new JLabel("");
		lbl_header.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_header.setIcon(new ImageIcon(Login4.class.getResource("/Images/3.png")));
		lbl_header.setBounds(0, 0, 98, 98);
		panel_header.add(lbl_header);

		// 头像周围的边框
		JLabel lbl_light = new JLabel("");
		lbl_light.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_light.setBounds(0, 0, 98, 98);
		lbl_light.setIcon(new ImageIcon(Login4.class.getResource("/Images/qqIcon/head_bkg_shadow.png")));
		panel_header.add(lbl_light);

		// 分层面板
		layeredPane_main.setBounds(0, 0, 430, 281);
		layeredPane_main.setBackground(Color.WHITE);
		layeredPane_main.setLayout(null);
		panel_main.add(layeredPane_main);

		// 主要操作的面板，用于填写账号密码等
		panel_operation = new JPanel();
		layeredPane_main.setLayer(panel_operation, 10);
		panel_operation.setBounds(0, 75, 430, 206);
		panel_operation.setBackground(Color.WHITE);
		panel_operation.setLayout(null);
		layeredPane_main.add(panel_operation);

		// 显示登录中
		/* 登录中面板的按钮 开始 */
		panel_landing = new JPanel();
		panel_landing.setOpaque(false);
		panel_landing.setBackground(Color.WHITE);
		layeredPane_main.setLayer(panel_landing, 9);
		panel_landing.setBounds(0, 75, 430, 206);
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
		
		/* 注册面板  开始*/
		panel_register = new Register();
		panel_register.setLocation(0, 0);
		layeredPane_main.setLayer(panel_register, 8);
		layeredPane_main.add(panel_register);
		/* 注册面板  结束*/
		
		/* 二维码登录面板  开始*/
		layeredPane_main.setLayer(panel_qrcode, 7);
		layeredPane_main.add(panel_qrcode);
		/* 二维码登录面板  结束*/
		
		/* 找回密码面板 开始*/
		panel_retrievePassword = new RetrievePassword();
		layeredPane_main.setLayer(panel_retrievePassword, 6);
		layeredPane_main.add(panel_retrievePassword);
		/* 找回密码面板 结束*/
		
		/* 设置面板 开始*/
		panel_setting = new Setting();
		layeredPane_main.setLayer(panel_setting, 5);
		layeredPane_main.add(panel_setting);
		/* 设置面板 结束*/

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
		btn_zhucezhanghao.setContentAreaFilled(false);// 按钮透明
		btn_zhucezhanghao.setBorderPainted(false);// 去除边框
		panel_operation.add(btn_zhucezhanghao);

		lbl_erweima = new JLabel("");// 显示二维码
		lbl_erweima.setIcon(new ImageIcon(Login4.class.getResource("/Images/qqIcon/corner_right_normal_breath.png")));
		lbl_erweima.setBounds(395, 172, 22, 22);
		panel_operation.add(lbl_erweima);
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
		public void mouseClicked(MouseEvent e) {// 鼠标点击
			if (e.getSource() == btn_denglu) {// 点击登录按钮
				verificationAccountFromServer();
//				int judge = judgeLoginStatus();
//				if (judge == 1) {
//					showTipsByThread("账号或密码不能为空！");
//				} else if (judge == 2) {
//					skipToFriendList();
//				} else if (judge == 3) {
//					showTipsByTimer("账号或密码错误，请重试！");
//				}
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
			if (e.getSource() == lbl_erweima) {// 二维码按钮
				lbl_erweima.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/corner_right_hover.png")));
				setTop(panel_qrcode);
				panel_header.setVisible(false);// 将头像面板设为不可见
			}
			if (e.getSource() == lblClose) {
				System.exit(0);
			}
			if (e.getSource() == lblMini) {
				frame.setExtendedState(Frame.ICONIFIED);
			}
			if (e.getSource() == btn_zhucezhanghao) {// 注册按钮
				setTop(panel_register);// 将注册页面显示出来，同时将其他页面设为不可见
				panel_header.setVisible(false);
			}
			if (e.getSource() == panel_register.btn_queren) {// 注册页面中的确认按钮
				if(panel_register.checkPass() == false) {
					// 注册失败由Register类处理
				}else {// 注册成功
					setTop(panel_operation);
					panel_header.setVisible(true);
				}
			}
			if (e.getSource() == panel_register.btn_quxiao) {// 注册页面中的取消按钮
				panel_register.clean();//清空输入框的内容
				setTop(panel_operation);
				panel_header.setVisible(true);
			}
			if (e.getSource() == panel_qrcode.btn_return) {// 二维码页面的返回按钮
				setTop(panel_operation);
				panel_header.setVisible(true);// 将头像面板设为可见
			}
			if (e.getSource() == btn_zhaohuimima) {// 找回密码按钮
				setTop(panel_retrievePassword);
				panel_header.setVisible(false);
			}
			if (e.getSource() == panel_retrievePassword.btn_return) {// 找回密码面板中的返回按钮
				setTop(panel_operation);
				panel_header.setVisible(true);// 将头像面板设为可见
			}
			if (e.getSource() == lblSetting) {// 设置按钮事件，点击进入设置面板
				setTop(panel_setting);
				panel_header.setVisible(false);
			}
			if (e.getSource() == panel_setting.btn_return) {// 设置面板的返回事件
				setTop(panel_operation);
				panel_header.setVisible(true);// 将头像面板设为可见
			}
			if (e.getSource() == panel_setting.btn_confirm) {// 设置面板的确定
				if (panel_setting.getBackgroundImage() != null) {
					setBackgroundImage(panel_setting.getBackgroundImage());
					setTop(panel_operation);
					panel_header.setVisible(true);// 将头像面板设为可见
				}
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
				panelUpMiniLabel.setBackground(new Color(255, 255, 255, 50));
				panelUpMiniLabel.updateUI();
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
		panel_qrcode.btn_return.addMouseListener(mos);
		panel_retrievePassword.btn_return.addMouseListener(mos);
		lblSetting.addMouseListener(mos);
		panel_setting.btn_return.addMouseListener(mos);
		panel_setting.btn_confirm.addMouseListener(mos);
	}
	
	/**
	 * 初始化网络相关
	 * @throws Exception 
	 */
	private void initSocket() throws Exception {
		// 初始化Socket
		try {
			socket = new Socket("127.0.0.1", 4444);
		} catch (IOException e) {
			System.out.println("无法连接到服务器");
			showTipsByTimer("无法连接到服务器");
			throw new Exception("无法连接到服务器");
		}
		showTipsByTimer("已连接到服务器:127.0.0.1");
		System.out.println("已连接到服务器:127.0.0.1");
		// 初始化输入输出流
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("I/O流出错");
			throw new Exception("I/O流出错");
		}
	}
	
	/**
	 * 关闭套接字和流
	 */
	private void closeSocketAndStream() {
		try {
			if(out != null) {
				out.close();
			}
			if(in != null) {
				in.close();
			}
			if(socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置要显示在顶端的组件
	 * @param jc 要设置可见为真的组件（面板）
	 */
	private void setTop(JComponent jc) {
		if(jc instanceof JPanel) {
			JPanel panel =(JPanel) jc;
			panel.setVisible(true);
			layeredPane_main.setLayer(panel, 101);
			System.out.println("jc instanceof JPanel");
			if(jc != panel_operation) {
				panel_operation.setVisible(false);
				layeredPane_main.setLayer(panel_operation, 10);
				System.out.println("panel_operation");
			}
			if(jc != panel_landing) {
				panel_landing.setVisible(false);
				layeredPane_main.setLayer(panel_landing, 9);
				System.out.println("panel_landing");
			}
			if(jc != panel_register) {
				panel_register.setVisible(false);
				layeredPane_main.setLayer(panel_register, 8);
				System.out.println("panel_register");
			}
			if(jc != panel_qrcode) {
				panel_qrcode.setVisible(false);
				layeredPane_main.setLayer(panel_qrcode,7);
				System.out.println("panel_qrcode");
			}
			if(jc != panel_retrievePassword) {
				panel_retrievePassword.setVisible(false);
				layeredPane_main.setLayer(panel_retrievePassword,6);
				System.out.println("panel_retrievePassword");
			}
			if(jc != panel_setting) {
				panel_setting.setVisible(false);
				layeredPane_main.setLayer(panel_setting,6);
				System.out.println("panel_setting");
			}
		}
	}
	
	/**
	 * 从界面输入构造一个Users对象
	 * @return users
	 * @throws AccountInputException 
	 */
	private Users getUserFromInput() throws AccountInputException {
		String id = comboBox_zhanghao.getEditor().getItem().toString().trim();// 获取输入的账号
		char[] password0 = passwordField_mima.getPassword();// 获取输入的密码（char)格式
		/* 获取组合框输入的文本； JComboBox有一个getEditor()方法，getEditor()方法返回ComboBoxEditor,
		 * ComboBoxEditor里getItem()
		 */
		String password = String.valueOf(password0);
		if(id.length() == 0 || password.length() == 0) {
			throw new AccountInputException(ConstantStatus.LOGIN_STATUS_EMPTY_INPUT);
		}
		Users user = new Users(id, password);
		return user;
	}
	
	/**
	 * 从服务器验证账号密码是否匹配
	 */
	private void verificationAccountFromServer() {
		try {
			initSocket();
		} catch (Exception e1) {
			System.out.println(e1.toString());
			return;
		}
		Users user = null;
		String comfirm = null;
		try {
			user = getUserFromInput();
		} catch (AccountInputException e) {
			showTipsByTimer(e.getMessage());
			return;
		}
		out.println("login." + user.getId() + "." + user.getPassword());
		out.flush();
		try {
			comfirm = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(comfirm.equals("success")) {
			System.out.println("success");
			skipToFriendList();// 跳转到好友列表界面
		}else {
			System.out.println(comfirm);
			showTipsByTimer(comfirm);
		}
	}
	
	/**
	 * 设置登录界面的背景图片
	 * @param backgroundImageIcon
	 */
	public void setBackgroundImage(ImageIcon backgroundImageIcon) {
		content.setBackGroundImg(backgroundImageIcon.getImage());
		content.repaint();
	}

	/**
	 * 更改提示标签的内容，通过多线程实现
	 * @param tip 要显示的提示内容
	 */
	@SuppressWarnings("unused")
	private void showTipsByThread(String tip) {
		lbl_tips.setText(tip);
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
	}

	/**
	 * 更改提示标签的内容，通过计时器
	 * @param tip 要显示的提示内容
	 */
	private void showTipsByTimer(String tip) {
		lbl_tips.setText(tip);
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

	/**
	 * 登录成功后，跳转到好友列表
	 */
	public void skipToFriendList() {
		setTop(panel_landing);
		
		// 用计时器实现延迟
		Timer timer = new Timer();// 实例化Timer类
		timer.schedule(new TimerTask() {
			public void run() {
				// TODO 此处可显示登录成功
				//layeredPane_main.setLayer(panel_operation, 10);
				closeSocketAndStream();
				frame.dispose();// 注销当前登录窗口
				// 拉起好友界面
				MyFriendsList3 window = new MyFriendsList3();
				window.setFrameVisible(true);
				this.cancel();
			}
		}, 1000);// 延迟x毫秒后启动
	}
}
