package mygui.login;

import bean.Message;
import bean.Users;
import exception.AccountInputException;
import mygui.components.BackgroundJPanel;
import mygui.components.ResizeFrame;
import mygui.components.RolloverBackgroundButton;
import mygui.friendslist2.MyFriendsList3;
import mygui.login.setupstorage.SetUpStorage;
import util.ConstantStatus;
import util.FileLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 登录界面。为什么Login后面还有个4，因为前面做了好几个，都被抛弃了。
 * @author xuxin
 *
 */
public class Login4 {

	/**
	 * 可调大小窗体
	 */
	private ResizeFrame frame;
	/**
	 * 背景图片面板
	 */
	private BackgroundJPanel borderBackgroundJPanel;
	/** 内容面板 */
	private JPanel content;
	/** 内容面板的背景图片 */
	private ImageIcon backGroundImgIcon;
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
	private JLayeredPane layeredPane_main;
	/** 输入账号密码、点击登录等 */
	private JPanel panel_operation;
	/** 显示登录中 */
	private JPanel panel_landing;
	/** 注册面板 */
	private Register panel_register;
	/** 二维码面板 */
	private QRcode panel_qrcode;
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
	/** 自动登录单选框 */
	private JCheckBox checkbox_autologin;
	/** 记住密码单选框 */
	private JCheckBox checkbox_remmber;
	/** 找回密码按钮 */
	private JButton btn_zhaohuimima;
	/** 注册账号按钮 */
	private JButton btn_zhucezhanghao;
	/** 登录按钮 */
	private RolloverBackgroundButton btn_denglu;
	/** 二维码图标 */
	private JLabel lbl_erweima;
	/** 显示提示信息，默认隐藏，按需显示 */
	private JLabel lbl_tips;

	/** 登录界面的后台线程，处理接收消息、初始化socket */
	private LoginTread loginThread;
	private ThreadGroup loginThreadGroup;



	public static void main(String[] args) {
		Login4 login = new Login4();
		login.startDemo();
	}

	/**
	 * 构造方法，初始化界面及事件
	 */
	public Login4() {
		initBackGround();
		initialize();
		initMyFocusListener();
		initMyMouseListener();
		initMyItemListener();
		setTop(panel_operation);// 将主面板置顶
		frame.setVisible(true);
	}

	/**
	 * 启动后台线程，加载设置
	 */
	private void startDemo() {
		try {
			// 加载设置面板的设置
			panel_setting.readSetting();
			// 加载设置
			SetUpStorage set = SetUpStorage.getStorage();

			if (set.remmberPassword) {
				loadPassword();
			}
			checkbox_autologin.setSelected(set.autoLogin);
			checkbox_remmber.setSelected(set.remmberPassword);

		} catch (Exception e) {
			// TODO 错误处理
			e.printStackTrace();
		}

		loginThreadGroup = new ThreadGroup("登录线程组");
		loginThread = new LoginTread(loginThreadGroup, this, "Login");
		loginThread.start();
		if (checkbox_autologin.isSelected()) {
			// 用计时器实现延迟
			Timer timer = new Timer();// 实例化Timer类
			timer.schedule(new TimerTask() {
				public void run() {
					login();
					this.cancel();
				}
			}, 1500);// 延迟x毫秒后启动
		}
	}

	/**
	 * 初始化窗体frame和contentPane，设置背景
	 */
	private void initBackGround() {
		int inset = 5;// 边框阴影大小
		frame = new ResizeFrame();
		frame.setBounds(100, 100, 430 + 2 * inset, 330 + 2 * inset);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 在这里无效，因为去除了窗口装饰
		frame.setUndecorated(true);// 将原始的边框去掉
		frame.setLocationRelativeTo(null);// 设置窗口打开位置居中
		frame.setBackground(new Color(0, 0, 0, 0));// 设置背景颜色为透明

		// 初始化边框、背景
		backGroundImgIcon = new ImageIcon(getClass().getResource("/Images/source/img_0.png"));
		borderBackgroundJPanel = new BackgroundJPanel(backGroundImgIcon, inset);
		borderBackgroundJPanel.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(borderBackgroundJPanel);

		content = new JPanel();
		content.setOpaque(false);
		content.setLayout(new BorderLayout(0, 0));
//		content.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		borderBackgroundJPanel.add(content, BorderLayout.CENTER);
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
		layeredPane_main = new JLayeredPane();
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
		panel_qrcode = new QRcode();
		layeredPane_main.setLayer(panel_qrcode, 7);
		layeredPane_main.add(panel_qrcode);
		/* 二维码登录面板  结束*/

		/* 找回密码面板 开始*/
		panel_retrievePassword = new RetrievePassword();
		layeredPane_main.setLayer(panel_retrievePassword, 6);
		layeredPane_main.add(panel_retrievePassword);
		/* 找回密码面板 结束*/

		/* 设置面板 开始*/
		panel_setting = new Setting(this);
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

		checkbox_autologin = new JCheckBox("自动登录");
		checkbox_autologin.setForeground(Color.GRAY);
		checkbox_autologin.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		checkbox_autologin.setBackground(Color.WHITE);
		checkbox_autologin.setBounds(93, 115, 75, 23);
		panel_operation.add(checkbox_autologin);

		checkbox_remmber = new JCheckBox("记住密码");
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
		btn_zhaohuimima.setCursor(new Cursor(Cursor.HAND_CURSOR));// 设置鼠标为小手形状
		panel_operation.add(btn_zhaohuimima);

//		btn_denglu = new JButton("登录");
		btn_denglu = new RolloverBackgroundButton("登录");
		btn_denglu.setFont(new Font("黑体", Font.PLAIN, 16));
		btn_denglu.setBounds(93, 147, 247, 35);
		btn_denglu.setNormalBackground(new Color(7,188,252));
		btn_denglu.setNormalBorderColor(new Color(179,229,155));
		btn_denglu.setNormalForeground(Color.WHITE);
		btn_denglu.setRolloverBackground(new Color(30,198,252));
		btn_denglu.setPressedBackground(new Color(34, 174, 250));
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
		btn_zhucezhanghao.setCursor(new Cursor(Cursor.HAND_CURSOR));// 设置鼠标为小手形状
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
			if (e.getSource() == comboBox_zhanghao.getEditor().getEditorComponent()) {// 如果账号输入框获取焦点
				lbl_zhanghao.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/qqnum_hover.png")));
			}
			if (e.getSource() == passwordField_mima) {
				lbl_mima.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/psw_hover.png")));
			}
		}

		@Override
		public void focusLost(FocusEvent e) {// 失去焦点
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
		public void mousePressed(MouseEvent e) {// 鼠标按下
			if (e.getSource() == lbl_erweima) {
				lbl_erweima
						.setIcon(new ImageIcon(getClass().getResource("/Images/qqIcon/corner_right_normal_down.png")));
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {// 鼠标松开
			if (e.getSource() == btn_denglu) {// 点击登录按钮
				login();
			}
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
				if (!panel_register.checkPass()) {
					// 注册失败由Register类处理
				} else {// 注册成功
					setTop(panel_operation);
					panel_header.setVisible(true);
					if (LoginTread.isConnected()) {
						loginThread.registerAccountFromServer(panel_register.getUser());
					} else {
						loginThread = new LoginTread(loginThreadGroup, Login4.this, "register");
						loginThread.start();
						loginThread.registerAccountFromServer(panel_register.getUser());
					}
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
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == btn_zhaohuimima) {
				btn_zhaohuimima.setForeground(Color.BLACK);
			}
			if (e.getSource() == btn_zhucezhanghao) {
				btn_zhucezhanghao.setForeground(Color.BLACK);
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
				//TODO 下面代码有bug,半透明会混杂其他图像
//				panelUpMiniLabel.setOpaque(true);
//				panelUpMiniLabel.setBackground(new Color(255, 255, 255, 50));
//				panelUpMiniLabel.updateUI();
				lblMini.setForeground(Color.LIGHT_GRAY);
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
			}
			if (e.getSource() == lblMini) {
				//TODO 有bug
//				panelUpMiniLabel.setOpaque(false);
//				panelUpMiniLabel.repaint();
				lblMini.setForeground(Color.WHITE);
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
		panel_register.addMouseListener(mos);
		panel_register.btn_queren.addMouseListener(mos);
		panel_register.btn_quxiao.addMouseListener(mos);
		panel_qrcode.btn_return.addMouseListener(mos);
		panel_retrievePassword.btn_return.addMouseListener(mos);
	}

	/**
	 * 项目监听器，监听记住密码和自动登录复选框
	 */
	private void initMyItemListener() {
		checkbox_autologin.addItemListener(e -> {
			SetUpStorage set = SetUpStorage.getStorage();
			set.autoLogin = checkbox_autologin.isSelected();
			set.writeToFile();
		});
		checkbox_remmber.addItemListener(e -> {
			SetUpStorage set = SetUpStorage.getStorage();
			set.remmberPassword = checkbox_remmber.isSelected();
			set.writeToFile();
			if (checkbox_remmber.isSelected()) {
				savePassword();
			}
		});
	}

	/**
	 * 保存密码
	 */
	private void savePassword() {
		new Thread(() -> {
			try {
				File file = FileLoader.loadFile("/data/", "password.dat");

				FileWriter fo = new FileWriter(file);
				String id = comboBox_zhanghao.getEditor().getItem().toString().trim();// 获取输入的账号
				String password = String.valueOf(passwordField_mima.getPassword());// 获取输入的密码

				fo.write(id + "\n" + password);
				fo.flush();
				fo.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}).start();
	}

	/**
	 * 加载密码
	 */
	private void loadPassword() {
		new Thread(() -> {
			try {
				File file = FileLoader.loadFile("/data/", "password.dat");
				BufferedReader bfi = new BufferedReader(new FileReader(file));
				comboBox_zhanghao.setSelectedItem(bfi.readLine());
				passwordField_mima.setText(bfi.readLine());
				bfi.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}).start();
	}

	/**
	 * 设置要显示在顶端的组件，将其他面板隐藏
	 * @param jc 要设置可见为真的组件（面板）
	 */
	private void setTop(JComponent jc) {
		if(jc instanceof JPanel) {
			JPanel panel =(JPanel) jc;
			panel.setVisible(true);
			layeredPane_main.setLayer(panel, 101);
			//System.out.println("jc instanceof JPanel");
			if(jc != panel_operation) {
				panel_operation.setVisible(false);
				layeredPane_main.setLayer(panel_operation, 10);
				//System.out.println("panel_operation");
			}
			if(jc != panel_landing) {
				panel_landing.setVisible(false);
				layeredPane_main.setLayer(panel_landing, 9);
				//System.out.println("panel_landing");
			}
			if(jc != panel_register) {
				panel_register.setVisible(false);
				layeredPane_main.setLayer(panel_register, 8);
				//System.out.println("panel_register");
			}
			if(jc != panel_qrcode) {
				panel_qrcode.setVisible(false);
				layeredPane_main.setLayer(panel_qrcode,7);
				//System.out.println("panel_qrcode");
			}
			if(jc != panel_retrievePassword) {
				panel_retrievePassword.setVisible(false);
				layeredPane_main.setLayer(panel_retrievePassword,6);
				//System.out.println("panel_retrievePassword");
			}
			if(jc != panel_setting) {
				panel_setting.setVisible(false);
				layeredPane_main.setLayer(panel_setting,6);
				//System.out.println("panel_setting");
			}
		}
	}

	/**
	 * 由其他面板调用，从其他面板返回到主面板
	 */
	public void returnToMain() {
		setTop(panel_operation);// 将输入密码等操作面板置顶
		panel_header.setVisible(true);// 将头像面板设为可见
	}

	/**
	 * 从界面输入构造一个包含用户id和密码的Message对象，发往服务端验证
	 *
	 * @return Message
	 * @throws AccountInputException 账户异常
	 */
	protected Message getUserFromInput() throws AccountInputException {
		String id = comboBox_zhanghao.getEditor().getItem().toString().trim();// 获取输入的账号
		char[] password0 = passwordField_mima.getPassword();// 获取输入的密码（char)格式
		/* 获取组合框输入的文本； JComboBox有一个getEditor()方法，getEditor()方法返回ComboBoxEditor,
		 * ComboBoxEditor里getItem()
		 */
		String password = String.valueOf(password0);
		if (id.length() == 0 || password.length() == 0) {
			throw new AccountInputException(ConstantStatus.LOGIN_STATUS_EMPTY_INPUT);
		}
		Message message = new Message();
//		message.setType("login");
		message.setSelfId(id);// id
		message.setText(password);// 消息内容代表密码
		return message;
	}

	/**
	 * 登录事件
	 */
	private void login() {
		if (LoginTread.isConnected()) {
			loginThread.verificationAccountFromServer();
		} else {
			// 如果服务端未打开
			loginThread = new LoginTread(loginThreadGroup, Login4.this, "login");
			loginThread.start();
			loginThread.verificationAccountFromServer();
		}
	}

	/**
	 * 设置登录界面的背景图片
	 * @param backgroundImageIcon 登录界面的背景图片
	 */
	public void setBackgroundImage(ImageIcon backgroundImageIcon) {
		borderBackgroundJPanel.setBackGroundImg(backgroundImageIcon.getImage());
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
		Thread t = new Thread(() -> {
			try {
				Thread.sleep(3000);// 该线程睡眠3秒
			} catch (InterruptedException ignored) {
			}
			lbl_tips.setVisible(false);
		});
		t.start();// 启动线程
	}

	/**
	 * 更改提示标签的内容，通过计时器
	 * @param tip 要显示的提示内容
	 */
	protected void showTipsByTimer(String tip) {
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
	public void skipToFriendList(Users user, Socket socket, PrintWriter out, BufferedReader in) {
		setTop(panel_landing);

		// 用计时器实现延迟
		Timer timer = new Timer();// 实例化Timer类
		timer.schedule(new TimerTask() {
			public void run() {
				// TODO 此处可显示登录成功
				frame.dispose();// 注销当前登录窗口
				// 拉起好友界面
				new MyFriendsList3(user, socket, out, in);
				this.cancel();
			}
		}, 1000);// 延迟x毫秒后启动
	}
}
