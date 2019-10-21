package mygui.friendslist2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JList;
import javax.swing.AbstractListModel;

public class TestDialog extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TestDialog dialog = new TestDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("serial")
	public TestDialog() {
		setAlwaysOnTop(true);
		final JPanel contentPanel = new JPanel();
		JList<String> list = new JList<String>();
		setBounds(100, 100, 126, 170);
		setUndecorated(true);// 将原始的边框去掉
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			
			list.setModel(new AbstractListModel<String>() {
				String[] values = new String[] {"红（Color.red）", "绿（Color.green）"};
				public int getSize() {
					return values.length;
				}
				public String getElementAt(int index) {
					return values[index];
				}
			});
			contentPanel.add(list);
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			{
				JButton diyButton = new JButton("自定义");
				diyButton.setMargin(new Insets(0, 0, 0, 0));
				diyButton.setFont(new Font("宋体", Font.PLAIN, 12));
				diyButton.setPreferredSize(new Dimension(42, 20));
				diyButton.setActionCommand("diy");
				buttonPane.add(diyButton);
				diyButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Color c = JColorChooser.showDialog(null, "选择", null);
						if(c!=null) {
							System.out.println(c.toString());
						}
					}
				});
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.setMargin(new Insets(0, 0, 0, 0));
				cancelButton.setFont(new Font("宋体", Font.PLAIN, 12));
				cancelButton.setPreferredSize(new Dimension(32, 20));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();//退出当前窗口
					}
				});
			}
			{
				JButton okButton = new JButton("确定");
				okButton.setMargin(new Insets(0,0,0,0));
				okButton.setFont(new Font("宋体", Font.PLAIN, 12));
				okButton.setPreferredSize(new Dimension(32, 20));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int a =list.getSelectedIndex();
						System.out.println(a);
					}
				});
			}
			
		}
		this.addWindowFocusListener(new WindowAdapter() {
			@Override
            public void windowLostFocus(WindowEvent e) {
                //直接关闭
                dispose();
                //最小化
                //frame.setExtendedState(JFrame.ICONIFIED);
            }
		});
		
		//JColorChooser.showDialog(null, "选择", null);
	}

}
