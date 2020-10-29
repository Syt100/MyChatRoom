package mygui.friendslist2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * 当JTextField没有输入时，显示提示文字<br>
 * 原文链接：https://blog.csdn.net/yanjingtp/article/details/79282365<br>
 * 时间 2019年10月4日16:22:51 <br>
 * 用法：<br>
 * <code>JTextField jTextField = new JTextField();
 * jTextField.addFocusListener(new JTextFieldHintListener(jTextField,
 * "提示内容"));</code>
 * 
 * @author xuxin
 *
 */
public class JTextFieldHintListener implements FocusListener {

	private String hintText;
	private JTextField textField;

	public JTextFieldHintListener(JTextField jTextField, String hintText) {
		this.textField = jTextField;
		this.hintText = hintText;
		jTextField.setText(hintText); // 默认直接显示
		jTextField.setForeground(Color.GRAY);
	}

	@Override
	public void focusGained(FocusEvent e) {
		// 获取焦点时，清空提示内容
		String temp = textField.getText();
		if (temp.equals(hintText)) {
			textField.setText("");
			textField.setForeground(Color.BLACK);
		}

	}

	@Override
	public void focusLost(FocusEvent e) {
		// 失去焦点时，没有输入内容，显示提示内容
		String temp = textField.getText();
		if (temp.equals("")) {
			textField.setForeground(Color.GRAY);
			textField.setText(hintText);
		}
	}

}
