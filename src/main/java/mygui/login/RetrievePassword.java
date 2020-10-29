package mygui.login;

import javax.swing.*;

/**
 * 找回密码面板
 * @author xuxin
 *
 */
public class RetrievePassword extends JPanel {

	/**
	 * 已生成的串行版本ID
	 */
	private static final long serialVersionUID = 3696467422433870646L;
	
	private final JLabel lbl_tips = new JLabel("");
	public final JButton btn_return = new JButton("返回");

	/**
	 * 构造方法
	 */
	public RetrievePassword() {
		setSize(430, 281);
		add(lbl_tips);
		
		// JLabel支持简单的html语法，利用<br>标签实现换行
		lbl_tips.setText("<html>这是一个找回密码的面板。<br>额，暂时还没做好。</html>");
		
		add(btn_return);
	}

}
