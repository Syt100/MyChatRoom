package mygui.friendslist;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JLabel;
//import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class FriendsList {

	private JFrame frame;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FriendsList window = new FriendsList();
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
	public FriendsList() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u597D\u53CB\u5217\u8868-\u5DF2\u767B\u5F55");// 好友列表-已登录
		frame.setBounds(100, 100, 280, 583);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 菜单
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("文件");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("新建");
		mnNewMenu.add(mntmNewMenuItem);
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("打开");
		mnNewMenu.add(mntmNewMenuItem_2);

		JMenu mnNewMenu_1 = new JMenu("编辑");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("剪切");
		mnNewMenu_1.add(mntmNewMenuItem_3);
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("复制");
		mnNewMenu_1.add(mntmNewMenuItem_4);
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("粘贴");
		mnNewMenu_1.add(mntmNewMenuItem_5);

		JMenu mnNewMenu_2 = new JMenu("帮助");
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("关于");
		mnNewMenu_2.add(mntmNewMenuItem_6);

		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// 好友列表
		JLabel lblNewLabel = new JLabel("\u597D\u53CB\u5217\u8868");
		contentPane.add(lblNewLabel, BorderLayout.NORTH);

		JList<String> list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] { "\u5F20\u4E09", "\u674E\u56DB", "\u738B\u4E94", "*****", "\u597D\u53CB1",
					"\u597D\u53CB2", "\u597D\u53CB3", "\u597D\u53CB4", "\u597D\u53CB5", "\u597D\u53CB6" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectedIndex(0);
		contentPane.add(list);

		// 右键菜单
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(list, popupMenu);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("删除好友");
		popupMenu.add(mntmNewMenuItem_1);

//				Vector vector = new Vector();
//				vector.add("张三");
//				vector.add("李四");
//				list.setListData(vector);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

}
