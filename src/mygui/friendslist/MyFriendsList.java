package mygui.friendslist;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;

public class MyFriendsList {

	private JFrame frame;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFriendsList window = new MyFriendsList();
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
	public MyFriendsList() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);

		// 用刚才自定义的节点类来创建树节点，并且添加自定义的属性
		FriendNode root = new FriendNode("我的好友", 3);
		FriendNode node1 = new FriendNode(new ImageIcon(MyFriendsList.class.getResource("/Images/1.png")), "好友1", 1);
		FriendNode node2 = new FriendNode(new ImageIcon("Images//1.png"), "好友1", 0);
		FriendNode node3 = new FriendNode(new ImageIcon("Images//1.png"), "好友1", 0);
		FriendNode node4 = new FriendNode(new ImageIcon("Images//1.png"), "好友1", 0);
		FriendNode node5 = new FriendNode(new ImageIcon("Images//1.png"), "好友1", 0);

		// 将所有的子节点加入到根节点中，当然也可以一个节点再次添加
		// 下一个节点，这样的话就可以呈现出多级的树形结果
		root.add(node1);
		root.add(node2);
		root.add(node3);
		root.add(node4);
		root.add(node5);

		// 将刚才的根节点添加到JTree中
		JTree tree = new JTree(root);

		// 将树的前面连接去掉
		tree.putClientProperty("JTree.lineStyle", "Horizontal");
		// 设置树的字体大小，样式
		tree.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		// 设置树节点的高度
		tree.setRowHeight(50);
		// 设置单元描述
		tree.setCellRenderer(new FriendNodeRenderer());
		// 添加树的双击触发事件
		// tree.addMouseListener(new MouseListener());

		// 将tree添加到JScrollPane 中
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tree);

		// 将scrollPane添加到JPanel中用于显示
		contentPane.add(scrollPane);
	}

}
