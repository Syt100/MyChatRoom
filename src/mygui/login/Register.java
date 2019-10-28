package mygui.login;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

import mygui.friendslist2.MyFriendsList3;
import util.MakeCertPic;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.border.EtchedBorder;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Register extends JPanel {
	private JTextField textField_zhanghao = new JTextField();
	private JPasswordField passField_mima = new JPasswordField();
	private JPasswordField passField_querenmima = new JPasswordField();
	private JLabel tips = new JLabel("");
	public JButton btn_queren = new JButton("确定");
	public JButton btn_quxiao = new JButton("取消");
	private JTextField textField_yanzheng;
	private JLabel lbl_tupian = new JLabel("");
	private MakeCertPic yanzhengma = new MakeCertPic();// 产生一个验证码
	private Icon icon;
	private JLabel lbl_shuaxin = new JLabel("刷新");

	/**
	 * Create the panel.
	 */
	public Register() {
		setOpaque(false);
		setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "\u7528\u6237\u6CE8\u518C", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		setLayout(null);
		setSize(430, 281);
		init();
	}
	
	private void init() {
		JLabel lbl_zhanghao = new JLabel("账号");
		lbl_zhanghao.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_zhanghao.setFont(new Font("宋体", Font.PLAIN, 14));
		lbl_zhanghao.setSize(50, 25);
		lbl_zhanghao.setLocation(70, 40);
		add(lbl_zhanghao);
		
		textField_zhanghao.setBounds(130, 40, 220, 25);
		add(textField_zhanghao);
		textField_zhanghao.setColumns(10);
		
		JLabel label_mima = new JLabel("密码");
		label_mima.setHorizontalAlignment(SwingConstants.CENTER);
		label_mima.setFont(new Font("宋体", Font.PLAIN, 14));
		label_mima.setBounds(70, 83, 50, 25);
		add(label_mima);
		
		
		passField_mima.setLocation(130, 83);
		passField_mima.setSize(220, 25);
		add(passField_mima);
		
		JLabel label = new JLabel("确认密码");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("宋体", Font.PLAIN, 14));
		label.setBounds(42, 126, 78, 25);
		add(label);
		
		passField_querenmima.setBounds(130, 126, 220, 25);
		add(passField_querenmima);
		
		btn_queren.setLocation(60, 225);
		btn_queren.setSize(60, 25);
		add(btn_queren);
		
		btn_quxiao.setLocation(290, 225);
		btn_quxiao.setSize(60, 25);
		add(btn_quxiao);
		tips.setFont(new Font("宋体", Font.PLAIN, 14));
		
		tips.setHorizontalAlignment(SwingConstants.CENTER);
		tips.setLocation(130, 225);
		tips.setSize(156, 25);
		tips.setForeground(Color.RED);
		add(tips);
		
		JLabel label_yanzheng = new JLabel("验证码");
		label_yanzheng.setHorizontalAlignment(SwingConstants.CENTER);
		label_yanzheng.setFont(new Font("宋体", Font.PLAIN, 14));
		label_yanzheng.setBounds(55, 169, 65, 25);
		add(label_yanzheng);
		
		textField_yanzheng = new JTextField();
		textField_yanzheng.setColumns(10);
		textField_yanzheng.setBounds(130, 169, 110, 25);
		add(textField_yanzheng);
		tips.setVisible(false);
		
		// 显示验证码图片
		try {
			icon = new ImageIcon(ImageIO.read(new File("./rec/Images/verificationcode/验证码.jpg")));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//lbl_tupian.setIcon(new ImageIcon(Register.class.getResource("/Images/verificationcode/验证码.jpg")));
		lbl_tupian.setIcon(icon);
		lbl_tupian.setLocation(250, 170);
		lbl_tupian.setSize(60, 25);
		add(lbl_tupian);
		lbl_shuaxin.setForeground(Color.GRAY);
		lbl_shuaxin.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_shuaxin.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		
		lbl_shuaxin.setBounds(320, 174, 30, 20);
		add(lbl_shuaxin);
		// 点击后刷新验证码
		lbl_shuaxin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO 自动生成的方法存根
				super.mouseReleased(e);
				yanzhengma.reMake();
				try {
					icon = new ImageIcon(ImageIO.read(new File("./rec/Images/verificationcode/验证码.jpg")));
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				lbl_tupian.setIcon(icon);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
				super.mouseEntered(e);
				lbl_shuaxin.setForeground(Color.BLACK);
				lbl_shuaxin.setCursor(new Cursor(Cursor.HAND_CURSOR));// 设置鼠标为小手
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
				super.mouseExited(e);
				lbl_shuaxin.setForeground(Color.GRAY);
			}
			
		});
	}
	
	/**
	 * 检查注册中是否填入账号、密码、密码和确认密码是否相同
	 * @return
	 */
	public boolean checkPass() {
		String zhanghao = textField_zhanghao.getText();
		String mima = String.valueOf(passField_mima.getPassword());
		String querenmima = String.valueOf(passField_querenmima.getPassword());
		String yanzheng = textField_yanzheng.getText();
		if(zhanghao == null || zhanghao.equals("")) {
			showTips("账号不能为空！");
			return false;
		}
		if(mima == null || mima.equals("")) {
			showTips("密码不能为空！");
			return false;
		}
		if(querenmima == null || querenmima.equals("")) {
			showTips("确认密码不能为空！");
			return false;
		}
		if(!mima.equals(querenmima)) {
			showTips("两次输入的密码不一致！");
			return false;
		}
		if(yanzheng == null || yanzheng.equals("")) {
			showTips("验证码不能为空！");
			return false;
		}
		if(!checkVerificationCode()) {
			showTips("验证码不正确！");
			return false;
		}
		return true;
	}
	
	/**
	 * 清空四个输入框
	 */
	public void clean() {
		textField_zhanghao.setText("");
		passField_mima.setText("");
		passField_querenmima.setText("");
		textField_yanzheng.setText("");
	}
	
	/**
	 * 显示面板上的提示，一段时间后消失
	 * @param tip 要显示的文本
	 */
	private void showTips(String tip) {
		tips.setText(tip);
		tips.setVisible(true);
		
		// 用计时器实现延迟
		Timer timer = new Timer();// 实例化Timer类
		timer.schedule(new TimerTask() {
			public void run() {
				// TODO 此处可显示登录成功
				tips.setVisible(false);
				this.cancel();
			}
		}, 2000);// 延迟x毫秒后启动
	}
	
	/**
	 * 检查验证码是否填写正确
	 * @return 是否填写正确
	 */
	private boolean checkVerificationCode() {
		String str = yanzhengma.getStr();
		if(str.equals(textField_yanzheng.getText())) {
			return true;
		}
		return false;
	}
	
}
