package mygui.login;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.SystemColor;

public class Login0 {

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
					Login0 window = new Login0();
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
	public Login0() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("登录");
		frame.setResizable(false);// 设置窗口不可调整大小
		/*
		 * 若此处设为可调整大小，可进一步完善绝对布局
		 */
		//frame.setBounds(100, 100, 450, 300);
		frame.setSize(415, 350);
		frame.setLocationRelativeTo(null);// 打开窗口居中
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		
		JPanel panel_logo = new JPanel();
		FlowLayout fl_panel_logo = (FlowLayout) panel_logo.getLayout();
		fl_panel_logo.setVgap(0);
		fl_panel_logo.setHgap(0);
		panel_logo.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(panel_logo, BorderLayout.NORTH);

		JLabel label = new JLabel("");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBackground(Color.WHITE);
		label.setIcon(new ImageIcon(Login0.class.getResource("/Images/4f321ae9fa813abf6522eccf72d0095f_5.jpg")));
		panel_logo.add(label);

		JPanel panel_main = new JPanel();
		panel_main.setBackground(Color.WHITE);
		contentPane.add(panel_main, BorderLayout.CENTER);
		panel_main.setLayout(new BorderLayout(0, 0));

		JPanel panel_header = new JPanel();
		panel_header.setBackground(Color.WHITE);
		panel_main.add(panel_header, BorderLayout.NORTH);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Login0.class.getResource("/Images/avatar_users_48px_1108447_easyicon.net.png")));
		panel_header.add(label_1);

		JPanel panel_operation = new JPanel();
		panel_operation.setBackground(Color.WHITE);
		panel_main.add(panel_operation, BorderLayout.CENTER);
		panel_operation.setLayout(null);

		JLabel lbl_zhanghao = new JLabel("账号");
		lbl_zhanghao.setFont(new Font("宋体", Font.PLAIN, 16));
		lbl_zhanghao.setBackground(Color.WHITE);
		lbl_zhanghao.setBounds(100, 10, 35, 22);
		panel_operation.add(lbl_zhanghao);

		JLabel lbl_mima = new JLabel("密码");
		lbl_mima.setFont(new Font("宋体", Font.PLAIN, 16));
		lbl_mima.setBackground(Color.WHITE);
		lbl_mima.setBounds(100, 42, 35, 22);

		panel_operation.add(lbl_mima);

		textField_zhanghao = new JTextField();
		textField_zhanghao.setBounds(148, 11, 159, 22);
		panel_operation.add(textField_zhanghao);
		textField_zhanghao.setColumns(10);

		passwordField_mima = new JPasswordField();
		passwordField_mima.setBounds(148, 43, 159, 22);
		panel_operation.add(passwordField_mima);

		JCheckBox checkbox_autologin = new JCheckBox("自动登录");
		checkbox_autologin.setBackground(Color.WHITE);
		checkbox_autologin.setBounds(100, 70, 75, 23);
		panel_operation.add(checkbox_autologin);

		JCheckBox checkbox_remmber = new JCheckBox("记住密码");
		checkbox_remmber.setBackground(Color.WHITE);
		checkbox_remmber.setBounds(177, 70, 75, 23);
		panel_operation.add(checkbox_remmber);

		JButton btn_denglu = new JButton("登录");
		btn_denglu.setFont(new Font("新宋体", Font.PLAIN, 14));
		btn_denglu.setForeground(Color.WHITE);
		btn_denglu.setBackground(SystemColor.textHighlight);
		btn_denglu.setBounds(100, 99, 207, 23);
		panel_operation.add(btn_denglu);
		btn_denglu.addActionListener( new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				if (textField_zhanghao.getText().trim().length() == 0 || new String(passwordField_mima.getPassword()).trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "账号或密码不允许为空！");
					return;
				}
			}
		});

		JButton btn_zhaohuimima = new JButton("找回密码");
		btn_zhaohuimima.setHorizontalAlignment(SwingConstants.LEFT);
		btn_zhaohuimima.setBounds(241, 70, 85, 23);
		btn_zhaohuimima.setContentAreaFilled(false);// 按钮透明
		btn_zhaohuimima.setBorderPainted(false);// 去除边框
		panel_operation.add(btn_zhaohuimima);

		JButton btn_erweima = new JButton("");// 显示二维码
		btn_erweima.setIcon(new ImageIcon(Login0.class.getResource("/Images/QRcode.png")));
		btn_erweima.setBounds(343, 110, 40, 40);
		btn_erweima.setBorderPainted(false);// 去除边框
		btn_erweima.setMargin(new Insets(0, 0, 0, 0));//让按钮随按钮上的图案变化
		panel_operation.add(btn_erweima);

		JButton btn_zhucezhanghao = new JButton("注册账号");
		btn_zhucezhanghao.setBounds(0, 127, 85, 23);
		// btnNewButton_2.setOpaque(false);
		btn_zhucezhanghao.setContentAreaFilled(false);// 按钮透明
		btn_zhucezhanghao.setBorderPainted(false);// 去除边框
		panel_operation.add(btn_zhucezhanghao);
	
	}

}
