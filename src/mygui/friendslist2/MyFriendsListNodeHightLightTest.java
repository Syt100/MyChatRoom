package mygui.friendslist2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreePath;

/**
 * 本类测试鼠标悬浮在树节点上高亮效果
 * @author xuxin
 *
 */
@SuppressWarnings("serial")
public class MyFriendsListNodeHightLightTest extends JDialog {

	private final JPanel contentPanel = new JPanel();
	MyJTree tree;
	MyTreeCellRenderer renderer = new MyTreeCellRenderer();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MyFriendsListNodeHightLightTest dialog = new MyFriendsListNodeHightLightTest();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MyFriendsListNodeHightLightTest() {
		setBounds(100, 100, 300, 409);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		Icon ico1 = new ImageIcon(MyFriendsListNodeHightLightTest.class.getResource("/Images/1.png"));
		Icon ico2 = new ImageIcon(MyFriendsListNodeHightLightTest.class.getResource("/Images/2.png"));
		Icon ico3 = new ImageIcon(MyFriendsListNodeHightLightTest.class.getResource("/Images/3.png"));
		Icon ico4 = new ImageIcon(MyFriendsListNodeHightLightTest.class.getResource("/Images/4.png"));

		MyTreeNode root = new MyTreeNode();
		MyTreeNode node1 = new MyTreeNode("我的好友");
		MyTreeNode node2 = new MyTreeNode("自定义分组1");
		MyTreeNode node3 = new MyTreeNode("自定义分组2");

		MyTreeNode node1_1 = new MyTreeNode(ico2, "好友1", "人生若只如初见");
		MyTreeNode node1_2 = new MyTreeNode(ico2, "好友2", "人生若只如初见");
		MyTreeNode node1_3 = new MyTreeNode(ico2, "好友3", "人生若只如初见");
		MyTreeNode node1_4 = new MyTreeNode(ico2, "好友4", "人生若只如初见");

		MyTreeNode node2_1 = new MyTreeNode(ico3, "好友1", "人生若只如初见");
		MyTreeNode node2_2 = new MyTreeNode(ico3, "好友2", "人生若只如初见");
		MyTreeNode node2_3 = new MyTreeNode(ico3, "好友3", "人生若只如初见");

		MyTreeNode node3_1 = new MyTreeNode(ico4, "好友1", "人生若只如初见");
		MyTreeNode node3_2 = new MyTreeNode(ico4, "好友2", "人生若只如初见");
		MyTreeNode node3_3 = new MyTreeNode(ico4, "好友3", "人生若只如初见");

		root.add(node1);
		root.add(node2);
		root.add(node3);
		node1.add(node1_1);
		node1.add(node1_2);
		node1.add(node1_3);
		node1.add(node1_4);
		node2.add(node2_1);
		node2.add(node2_2);
		node2.add(node2_3);
		node3.add(node3_1);
		node3.add(node3_2);
		node3.add(node3_3);
		contentPanel.setLayout(new BorderLayout(0, 0));
		

		tree = new MyJTree(root);
//		tree.setRootVisible(false);// 设置根节点不可见
//		tree.setUI(new MyTreeUI());
//		
//		renderer.setMyNodeWidth(260);
//		tree.setCellRenderer(renderer);
		

//		tree.addMouseListener(new MouseAdapter() {//添加鼠标点击监听器
//			@Override
//			public void mouseClicked(MouseEvent e) {//当鼠标点击
//				if (e.getSource() == tree && e.getClickCount() == 1) {//如果点击的是这棵树并且鼠标点击次数为1
//					//从鼠标点击位置获取x y坐标，再通过坐标获取这个坐标对应的节点的路径
//					//注意TreePath对象，通过它可以获得该路径对应的节点对象
//					TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
//					if (selPath != null) {//防止空指针错误，当鼠标在树中没有节点的地方，会返回空指针
//						System.out.println(selPath);//输出当前的路径，返回从根节点一直到当前节点的名称字符串
//						//获取tree中最近一次被选中的节点，并返回一个Object对象，要把它显式转换为MyTreeNode对象
//						MyTreeNode clicNode = (MyTreeNode) tree.getLastSelectedPathComponent();
//						if (clicNode != null) {//防止空指针错误
//							System.out.println(clicNode.toString());//toString在MyTreeNode中已定义，返回输出节点名称
//						}
//					}
//				}
//			}
//			public void mouseEntered(MouseEvent e) {
//				//TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
//			}
//			@Override
//			public void mouseExited(MouseEvent e) {
//				//TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
//				MyTreeCellRenderer.mouseRow = -1;// 鼠标退出这棵树，取消高亮
//				tree.repaint();
//			}
//		});
//		
//		tree.addMouseMotionListener(new MouseAdapter() {//添加鼠标移动监听器
//			@Override
//			public void mouseMoved(MouseEvent e) {//鼠标移动	
//				int x = e.getX();
//				int y = e.getY();
//				if (e.getSource() == tree) {
//					TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
//					if (selPath != null) {
//						//MyTreeNode clicNode = (MyTreeNode) tree.getLastSelectedPathComponent();
//						//MyTreeNode currentNode = (MyTreeNode) selPath.getLastPathComponent();
//						//实现鼠标悬浮高亮
//						MyTreeCellRenderer.mouseRow = tree.getRowForLocation(x, y);
//						tree.repaint();
//					}else {//鼠标在节点之外，取消高亮
//						MyTreeCellRenderer.mouseRow = -1;
//						tree.repaint();
//					}
//				}
//			}
//		});

		// 将tree添加到JScrollPane 中
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tree);
		//scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 

		// 将scrollPane添加到JPanel中用于显示
		contentPanel.add(scrollPane);
	}

}
