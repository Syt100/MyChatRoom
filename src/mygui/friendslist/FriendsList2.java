package mygui.friendslist;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.JFrame;

public class FriendsList2 {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FriendsList2 window = new FriendsList2();
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
	public FriendsList2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("聊天");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 300, 560);
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnNewMenu = new JMenu("文件");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("新建");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_2 = new JMenu("操作");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("添加好友");
		mnNewMenu_2.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("设置");
		mnNewMenu_2.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_1 = new JMenu("帮助");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("关于");
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(FriendsList2.class.getResource("/Images/223209_3.jpg")));
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("昵称：");
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("我的昵称");
		panel_2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("简介：");
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_6 = new JLabel("我的简介");
		panel_2.add(lblNewLabel_6);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, BorderLayout.CENTER);
		
//		JTree tree = new JTree();
//		tree.setModel(new DefaultTreeModel(
//			new DefaultMutableTreeNode("JTree") {
//				{
//					DefaultMutableTreeNode node_1;
//					node_1 = new DefaultMutableTreeNode("我的好友");
//						node_1.add(new DefaultMutableTreeNode("blue"));
//						node_1.add(new DefaultMutableTreeNode("violet"));
//						node_1.add(new DefaultMutableTreeNode("red"));
//						node_1.add(new DefaultMutableTreeNode("yellow"));
//					add(node_1);
//					node_1 = new DefaultMutableTreeNode("同学");
//						node_1.add(new DefaultMutableTreeNode("basketball"));
//						node_1.add(new DefaultMutableTreeNode("soccer"));
//						node_1.add(new DefaultMutableTreeNode("football"));
//						node_1.add(new DefaultMutableTreeNode("hockey"));
//					add(node_1);
//					node_1 = new DefaultMutableTreeNode("同事");
//						node_1.add(new DefaultMutableTreeNode("hot dogs"));
//						node_1.add(new DefaultMutableTreeNode("pizza"));
//						node_1.add(new DefaultMutableTreeNode("ravioli"));
//						node_1.add(new DefaultMutableTreeNode("bananas"));
//						node_1.add(new DefaultMutableTreeNode("new"));
//						node_1.add(new DefaultMutableTreeNode("\u65B0\u7684"));
//					add(node_1);
//				}
//			}
//		));
		
//		tree.setModel(new DefaultTreeModel(new FriTreeNode("JTree") {
//			{
//				FriTreeNode node1;
//				node1 = new FriTreeNode("我的好友");
//				node1.addchild(new FriTreeNode("blue"));
//				node1.addchild(new FriTreeNode("violet"));
//				node1.addchild(new FriTreeNode("red"));
//				node1.addchild(new FriTreeNode("yellow"));
//				addchild(node1);
//			}
//		}));
		
		FriTreeNode root = new FriTreeNode("根节点");
		FriTreeNode node = new FriTreeNode("1000","节点1",new ImageIcon(FriendsList2.class.getResource("/Images/1.png")));
				root.addchild(node);	
				root.addchild(new FriTreeNode("1003","叶子节点3",new ImageIcon(FriendsList2.class.getResource("/Images/3.png"))));
				node.addchild(new FriTreeNode("1001","叶子节点1",new ImageIcon(FriendsList2.class.getResource("/Images/1.png")),"我是一段文本"));
				node.addchild(new FriTreeNode("1002","叶子节点2",new ImageIcon(FriendsList2.class.getResource("/Images/2.png"))));
		DefaultTreeModel jMode = new DefaultTreeModel(root);
			JTree tree = new JTree(jMode);
		
			
		tree.setCellRenderer(new FriTreeRender());
		tree.setRootVisible(false);//设置根节点不可见
		tabbedPane.addTab("好友列表", null, tree, null);
//		tree.putClientProperty("JTree.lineStyle", "Horizontal");// 将树设为水平分隔风格
//		tree.setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 16));//字体的大小，样式
//		tree.setToggleClickCount(1); //设置展开节点之前的鼠标单击数为1
//		tree.setRowHeight(20);//树节点的高度
//		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);//设置一次只能选中一个节点	
		
		//设置未展开图标，设置tree非叶子节折叠时的图片
		//FriTreeRender render = (FriTreeRender) tree.getCellRenderer();		
		//DefaultTreeCellRenderer render=(DefaultTreeCellRenderer )tree.getCellRenderer();
		String arrow_right = new String();
		arrow_right = "/Images/arrow_forward_navigation_next_right_16px_1225491_easyicon.net.png";
		//render.setClosedIcon(new ImageIcon(this.getClass().getResource(arrow_right)));
		//设置tree非叶子节点展开时的图片
		String arrow_down = new String();
		arrow_down = "/Images/arrow_bottom_down_downward_navigation_16px_1225446_easyicon.net.png";
		//render.setOpenIcon(new ImageIcon(this.getClass().getResource(arrow_down)));
		//设置tree叶子节点的图片
		String header = new String();
		header = "/Images/at_sign_16px_1237030_easyicon.net.png";
        //render.setLeafIcon(new ImageIcon(this.getClass().getResource(header)));
		
		JLabel lblNewLabel_4 = new JLabel();
		tabbedPane.addTab("消息", null, lblNewLabel_4, null);
		
		JLabel lblNewLabel_5 = new JLabel();
		tabbedPane.addTab("群聊", null, lblNewLabel_5, null);
		
		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		panel_3.add(panel_4, BorderLayout.EAST);
		
		JButton btnNewButton = new JButton("设置");
		panel_4.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("退出");
		panel_4.add(btnNewButton_1);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.WEST);
		
		JButton btnNewButton_2 = new JButton("消息记录");
		panel_5.add(btnNewButton_2);
	}

}
