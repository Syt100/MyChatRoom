/**
 * 
 */
package mygui.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import util.*;
/**
 * @author xuxin
 *
 */
public class RegisterEvent implements ActionListener{
	JComponent jc1,jc2;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		System.out.println("11");
		ConstantStatus.CONFIRM_BUTTON_STATUS = 1;
	}

}
