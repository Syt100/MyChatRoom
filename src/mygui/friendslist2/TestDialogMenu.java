package mygui.friendslist2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import java.awt.Font;

public class TestDialogMenu extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JList list = new JList();
	private DefaultListModel dlm = new DefaultListModel();
	MyListCellRenderer render = new MyListCellRenderer();
	Point p = new Point();
	
	private  Color color = Color.WHITE;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TestDialogMenu dialog = new TestDialogMenu();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("unchecked")
	public TestDialogMenu() {
		setAlwaysOnTop(true);
		setUndecorated(true);// 将原始的边框去掉
		setOpacity(0.8f);//设置透明度为80%
		setBounds(100, 100, 200, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(color);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		list.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		contentPanel.add(list, BorderLayout.CENTER);

		dlm.addElement("打开");
		dlm.addElement("新建");
		dlm.addElement("帮助");
		dlm.addElement("添加好友");
		dlm.addElement("设置");
		list.setModel(dlm);
		
		//render.setDefaultBackGround(Color.BLUE);
		list.setCellRenderer(render);

//		list.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				p = e.getPoint();
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				p = e.getPoint();
//				render.setCurIndex(-1);// 退出时取消高亮
//				list.repaint();
//			}
//		});
//		list.addMouseMotionListener(new MouseMotionAdapter() {
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				int lastIndex = list.locationToIndex(p);
//				Point current = e.getPoint();
//				int currentIndex = list.locationToIndex(current);
//				// if (currentIndex == lastIndex && lastIndex != -1)
//				// return;
//				render.setCurIndex(currentIndex);
//				list.repaint();
//				// highlight
//				p = current;
//			}
//		});
		this.addWindowFocusListener(new WindowAdapter() {
			@Override
            public void windowLostFocus(WindowEvent e) {
                //直接关闭
                dispose();
                //最小化
                //frame.setExtendedState(JFrame.ICONIFIED);
            }
		});
		//pack();
		list.addMouseMotionListener(new HightLightMoveAdapter(this));
		list.addMouseListener(new HightLightAdapter(this));
	}
	
	private class HightLightMoveAdapter extends MouseMotionAdapter {
		private Component c;

		public HightLightMoveAdapter(Component c) {
			this.c = c;
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			int lastIndex = list.locationToIndex(p);
			Point current = e.getPoint();
			int currentIndex = list.locationToIndex(current);
			// if (currentIndex == lastIndex && lastIndex != -1)
			// return;
			render.setCurIndex(currentIndex);
			list.repaint();
			// highlight
			p = current;
		}
	}
	
	private class HightLightAdapter extends MouseAdapter {
		private Component c;

		public HightLightAdapter(Component c) {
			this.c = c;
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			p = e.getPoint();
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			p = e.getPoint();
			render.setCurIndex(-1);// 退出时取消高亮
			list.repaint();
		}
	}
	// DefaultListCellRenderer
}