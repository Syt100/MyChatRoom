package mygui.login;

import bean.Users;
import util.MakeCertPic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Register extends JPanel {
	/**
	 * 已生成的串行化版本标识
	 */
	private static final long serialVersionUID = -4248204657599601398L;

	/** 账号框 */
	private JTextField textField_zhanghao = new JTextField();
	/** 密码框 */
	private JPasswordField passField_mima = new JPasswordField();
	/** 确认密码输入框 */
	private JPasswordField passField_querenmima = new JPasswordField();

	/** 确定按钮 */
	public JButton btn_queren = new JButton("确定");
	/** 取消按钮 */
	public JButton btn_quxiao = new JButton("取消");

	/** 提示标签 */
	private JLabel tips = new JLabel("");
	/** 验证码输入框 */
	private JTextField textField_yanzheng;
	/** 显示验证码图片的标签 */
	private JLabel lbl_tupian = new JLabel("");
	/** 生成验证码 */
	private MakeCertPic yanzhengma = new MakeCertPic();// 产生一个验证码
	/** 存放验证码，用于显示在标签上 */
	private Icon icon;
	/** 刷新按钮 */
	private JLabel lbl_shuaxin = new JLabel("刷新");

	/** 通过输入的账号密码构造Users类 */
	private Users user;

	/** 昵称输入框 */
	private JTextField textField_nicheng;

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
		lbl_zhanghao.setLocation(70, 80);
		add(lbl_zhanghao);

		textField_zhanghao.setBounds(130, 80, 220, 25);
		add(textField_zhanghao);
		textField_zhanghao.setColumns(10);

		JLabel label_mima = new JLabel("密码");
		label_mima.setHorizontalAlignment(SwingConstants.CENTER);
		label_mima.setFont(new Font("宋体", Font.PLAIN, 14));
		label_mima.setBounds(70, 123, 50, 25);
		add(label_mima);


		passField_mima.setLocation(130, 123);
		passField_mima.setSize(220, 25);
		add(passField_mima);

		JLabel label = new JLabel("确认密码");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("宋体", Font.PLAIN, 14));
		label.setBounds(42, 166, 78, 25);
		add(label);

		passField_querenmima.setBounds(130, 166, 220, 25);
		add(passField_querenmima);

		btn_queren.setLocation(70, 246);
		btn_queren.setSize(60, 25);
		add(btn_queren);

		btn_quxiao.setLocation(300, 246);
		btn_quxiao.setSize(60, 25);
		add(btn_quxiao);
		tips.setFont(new Font("宋体", Font.PLAIN, 14));

		tips.setHorizontalAlignment(SwingConstants.CENTER);
		tips.setLocation(140, 246);
		tips.setSize(156, 25);
		tips.setForeground(Color.RED);
		add(tips);

		JLabel label_yanzheng = new JLabel("验证码");
		label_yanzheng.setHorizontalAlignment(SwingConstants.CENTER);
		label_yanzheng.setFont(new Font("宋体", Font.PLAIN, 14));
		label_yanzheng.setBounds(55, 209, 65, 25);
		add(label_yanzheng);

		textField_yanzheng = new JTextField();
		textField_yanzheng.setColumns(10);
		textField_yanzheng.setBounds(130, 209, 110, 25);
		add(textField_yanzheng);
		tips.setVisible(false);

		lbl_tupian.setBounds(250, 210, 60, 25);
		refresh();// 刷新验证码
		add(lbl_tupian);

		lbl_shuaxin.setForeground(Color.GRAY);
		lbl_shuaxin.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_shuaxin.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));

		lbl_shuaxin.setBounds(320, 214, 30, 20);
		add(lbl_shuaxin);

		JLabel lbl_nicheng = new JLabel("昵称");
		lbl_nicheng.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_nicheng.setFont(new Font("宋体", Font.PLAIN, 14));
		lbl_nicheng.setBounds(70, 34, 50, 25);
		add(lbl_nicheng);

		textField_nicheng = new JTextField();
		textField_nicheng.setColumns(10);
		textField_nicheng.setBounds(130, 34, 220, 25);
		add(textField_nicheng);
		// 点击后刷新验证码
		lbl_shuaxin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO 自动生成的方法存根
				super.mouseReleased(e);
				refresh();
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
	 * 检查注册中是否填入账号、密码、密码和确认密码是否相同，并构造Users类
	 * @return
	 */
	public boolean checkPass() {
		String zhanghao = textField_zhanghao.getText();
		String mima = String.valueOf(passField_mima.getPassword());
		String querenmima = String.valueOf(passField_querenmima.getPassword());
		String yanzheng = textField_yanzheng.getText();
		String nicheng = textField_nicheng.getText();
		if(zhanghao == null || zhanghao.equals("")) {
			showTips("账号不能为空！");
			return false;
		}
		if(mima.equals("")) {
			showTips("密码不能为空！");
			return false;
		}
		if(querenmima.equals("")) {
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
		if (!checkVerificationCode()) {
			showTips("验证码不正确！");
			return false;
		}
		if (nicheng == null || nicheng.equals("")) {
			showTips("昵称不能为空！");
		}
		user = new Users(zhanghao, nicheng, mima);
		return true;
	}

	/**
	 * 清空五个输入框
	 */
	public void clean() {
		textField_zhanghao.setText("");
		passField_mima.setText("");
		passField_querenmima.setText("");
		textField_yanzheng.setText("");
		textField_nicheng.setText("");
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
		return str.equals(textField_yanzheng.getText());
	}

	/**
	 * 刷新验证码
	 */
	private void refresh() {
		icon = yanzhengma.getCertPic(lbl_tupian.getWidth(), lbl_tupian.getHeight());
		lbl_tupian.setIcon(icon);
		System.out.println(yanzhengma.getStr());
	}

	/**
	 * 返回构造好的Users类
	 * @return user
	 */
	public Users getUser() {
		if(checkPass()) {
			return user;
		}
		return null;
	}
}
