package mygui.login;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

import mygui.friendslist2.MyFriendsList3;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class Register extends JPanel {
	private JTextField textField_zhanghao = new JTextField();
	private JPasswordField passField_mima = new JPasswordField();
	private JPasswordField passField_querenmima = new JPasswordField();
	private JLabel tips = new JLabel("");
	public JButton btn_queren = new JButton("确定");
	public JButton btn_quxiao = new JButton("取消");
	

	/**
	 * Create the panel.
	 */
	public Register() {
		setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "\u7528\u6237\u6CE8\u518C", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		setLayout(null);
		setSize(430, 206);
		setOpaque(false);
		init();
	}
	
	private void init() {
		JLabel lbl_zhanghao = new JLabel("账号");
		lbl_zhanghao.setForeground(Color.WHITE);
		lbl_zhanghao.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_zhanghao.setFont(new Font("宋体", Font.PLAIN, 16));
		lbl_zhanghao.setSize(50, 30);
		lbl_zhanghao.setLocation(70, 40);
		add(lbl_zhanghao);
		
		textField_zhanghao.setBounds(131, 40, 220, 25);
		add(textField_zhanghao);
		textField_zhanghao.setColumns(10);
		
		JLabel label_mima = new JLabel("密码");
		label_mima.setForeground(Color.WHITE);
		label_mima.setHorizontalAlignment(SwingConstants.CENTER);
		label_mima.setFont(new Font("宋体", Font.PLAIN, 16));
		label_mima.setBounds(70, 80, 50, 30);
		add(label_mima);
		
		
		passField_mima.setLocation(131, 80);
		passField_mima.setSize(220, 25);
		add(passField_mima);
		
		JLabel label = new JLabel("确认密码");
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setBounds(42, 120, 78, 30);
		add(label);
		
		passField_querenmima.setBounds(131, 120, 220, 25);
		add(passField_querenmima);
		
		btn_queren.setLocation(70, 170);
		btn_queren.setSize(60, 25);
		add(btn_queren);
		
		btn_quxiao.setLocation(291, 170);
		btn_quxiao.setSize(60, 25);
		add(btn_quxiao);
		tips.setFont(new Font("宋体", Font.PLAIN, 14));
		
		tips.setHorizontalAlignment(SwingConstants.CENTER);
		tips.setLocation(131, 170);
		tips.setSize(156, 25);
		tips.setForeground(Color.RED);
		add(tips);
		tips.setVisible(false);
	}
	
	/**
	 * 检查注册中是否填入账号、密码、密码和确认密码是否相同
	 * @return
	 */
	public boolean checkEmpty() {
		String zhanghao = textField_zhanghao.getText();
		String mima = String.valueOf(passField_mima.getPassword());
		String querenmima = String.valueOf(passField_querenmima.getPassword());
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
		return true;
	}
	
	/**
	 * 清空三个输入框
	 */
	public void clean() {
		textField_zhanghao.setText("");
		passField_mima.setText("");
		passField_querenmima.setText("");
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
}
