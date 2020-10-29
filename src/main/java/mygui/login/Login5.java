package mygui.login;

import mygui.components.BackgroundJPanel;
import mygui.components.ResizeFrame;
import mygui.components.RolloverBackgroundButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login5 {

	/** 可调大小窗体 */
	private ResizeFrame frame = new ResizeFrame();
	/** 内容面板，并可以设置背景图片 */
	private JPanel content;
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
	private JPasswordField passwordField;

	public static void main(String[] args) {
		new Login5();
	}

	public Login5() {
		initBackGround();
		initialize();
		frame.setVisible(true);
	}

	/**
	 * 初始化窗体frame和contentPane，设置背景
	 */
	private void initBackGround() {
		int inset = 5;// 边框阴影大小
		frame.setBounds(100, 100, 430 + 2 * inset, 330 + 2 * inset);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 在这里无效，因为去除了窗口装饰
		frame.setUndecorated(true);// 将原始的边框去掉
		frame.setLocationRelativeTo(null);// 设置窗口打开位置居中
		frame.setBackground(new Color(0, 0, 0, 0));
//		frame.setResizable(false);

		// 初始化边框、背景
		BackgroundJPanel borderBackgroundJPanel = new BackgroundJPanel(backGroundImgIcon, inset);
		frame.setContentPane(borderBackgroundJPanel);
		borderBackgroundJPanel.setLayout(new BorderLayout(0, 0));

		content = new JPanel();
		content.setOpaque(false);
		content.setLayout(new BorderLayout(0, 0));
		content.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		borderBackgroundJPanel.add(content, BorderLayout.CENTER);
	}

	private void initialize() {
		// 将内容面板分为两部分，这是上半部分
//		JPanel panel_up = new JPanel();
		Box panel_up = Box.createHorizontalBox();
		panel_up.setOpaque(false);
//		panel_up.setLayout(new BorderLayout(0, 0));
		content.add(panel_up, BorderLayout.NORTH);
		
		JLabel lbl_qqIcon = new JLabel(new ImageIcon(Login4.class.getResource("/Images/qqIcon/logo-QQ.png")));
		lbl_qqIcon.setBorder(new EmptyBorder(7, 3, 0, 0));
		panel_up.add(lbl_qqIcon, BorderLayout.WEST);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_up.add(horizontalGlue);
		
		Box verticalBox = Box.createVerticalBox();
		panel_up.add(verticalBox);
		
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		
		JLabel lblNewLabel = new JLabel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7752233327819173066L;

			@Override
			protected void paintComponent(Graphics g) {
				// TODO 自动生成的方法存根
				super.paintComponent(g);
				
			}
			
		};
		horizontalBox.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("－");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 18));
		horizontalBox.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("×");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_2.setOpaque(true);
				lblNewLabel_2.setBackground(Color.RED);
				lblNewLabel_2.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_2.setOpaque(false);
				lblNewLabel_2.repaint();
			}
		});
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 18));
		horizontalBox.add(lblNewLabel_2);
		
		Component verticalGlue = Box.createVerticalGlue();
		verticalBox.add(verticalGlue);
		
		JLayeredPane layeredPane = new JLayeredPane();
		content.add(layeredPane, BorderLayout.CENTER);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		Box verticalBox_main = Box.createVerticalBox();
		layeredPane.add(verticalBox_main, "name_41814196907100");
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox_main.add(horizontalBox_1);
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(98, 98));
		panel.setPreferredSize(new Dimension(98, 98));
		horizontalBox_1.add(panel);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		panel.add(lblNewLabel_3);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		verticalBox_main.add(horizontalStrut_2);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		horizontalBox_2.setPreferredSize(new Dimension(0, 25));
		verticalBox_main.add(horizontalBox_2);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		horizontalBox_2.add(lblNewLabel_4);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setMinimumSize(new Dimension(220, 25));
		comboBox.setPreferredSize(new Dimension(220, 25));
		comboBox.setMaximumSize(new Dimension(220, 25));
		horizontalBox_2.add(comboBox);
		
		Component verticalStrut = Box.createVerticalStrut(5);
		verticalBox_main.add(verticalStrut);
		
		Box horizontalBox_3 = Box.createHorizontalBox();
		horizontalBox_3.setPreferredSize(new Dimension(0, 25));
		verticalBox_main.add(horizontalBox_3);
		
		Component horizontalGlue_5 = Box.createHorizontalGlue();
		horizontalBox_3.add(horizontalGlue_5);
		
		JLabel label = new JLabel("New label");
		horizontalBox_3.add(label);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox_3.add(horizontalStrut_1);
		
		passwordField = new JPasswordField();
		passwordField.setMinimumSize(new Dimension(220, 25));
		passwordField.setPreferredSize(new Dimension(220, 25));
		passwordField.setMaximumSize(new Dimension(220, 25));
		horizontalBox_3.add(passwordField);
		
		Component horizontalGlue_6 = Box.createHorizontalGlue();
		horizontalBox_3.add(horizontalGlue_6);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalBox_main.add(verticalStrut_2);
		
		Box horizontalBox_4 = Box.createHorizontalBox();
		verticalBox_main.add(horizontalBox_4);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		horizontalBox_4.add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("New check box");
		horizontalBox_4.add(chckbxNewCheckBox_1);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		horizontalBox_4.add(lblNewLabel_5);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalBox_main.add(verticalStrut_1);
		
		Box horizontalBox_5 = Box.createHorizontalBox();
		verticalBox_main.add(horizontalBox_5);
		
		JButton btnNewButton = new JButton("New button");
		horizontalBox_5.add(btnNewButton);
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		verticalBox_main.add(lblNewLabel_6);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		verticalBox_main.add(verticalGlue_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		layeredPane.setLayer(panel_1, 8);
		layeredPane.add(panel_1, "name_41814214111400");
	}
}
