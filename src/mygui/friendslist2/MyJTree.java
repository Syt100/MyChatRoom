/**
 * 
 */
package mygui.friendslist2;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * @author xuxin
 *
 */
public class MyJTree extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6388274944148278143L;
	private MyTreeCellRenderer renderer = new MyTreeCellRenderer();

	public MyJTree() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	public MyJTree(MyTreeNode root) {
		super(root);
		setRootVisible(false);// 设置根节点不可见
		setUI(new MyTreeUI());
		renderer.setMyNodeWidth(260);
		setCellRenderer(renderer);
		
		addMouseListener(new MyMouseAdapter(this));
		addMouseMotionListener(new MyMouseAdapter(this));
	}
	
	private class MyMouseAdapter extends MouseAdapter {
		private Component c;

		public MyMouseAdapter(Component c) {
			this.c = c;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO 自动生成的方法存根
			super.mouseClicked(e);
			if (e.getSource() == c && e.getClickCount() == 1) {//如果点击的是这棵树并且鼠标点击次数为1
				//从鼠标点击位置获取x y坐标，再通过坐标获取这个坐标对应的节点的路径
				//注意TreePath对象，通过它可以获得该路径对应的节点对象
				TreePath selPath = getPathForLocation(e.getX(), e.getY());
				if (selPath != null) {//防止空指针错误，当鼠标在树中没有节点的地方，会返回空指针
					System.out.println(selPath);//输出当前的路径，返回从根节点一直到当前节点的名称字符串
					//获取tree中最近一次被选中的节点，并返回一个Object对象，要把它显式转换为MyTreeNode对象
					MyTreeNode clicNode = (MyTreeNode) getLastSelectedPathComponent();
					if (clicNode != null) {//防止空指针错误
						System.out.println(clicNode.toString());//toString在MyTreeNode中已定义，返回输出节点名称
					}
				}
				renderer.setMyNodeWidth(c.getWidth());
				c.repaint();
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO 自动生成的方法存根
			super.mouseExited(e);
			MyTreeCellRenderer.mouseRow = -1;// 鼠标退出这棵树，取消高亮
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO 自动生成的方法存根
			super.mouseDragged(e);
			renderer.setMyNodeWidth(c.getWidth());
			c.repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO 自动生成的方法存根
			super.mouseMoved(e);
			int x = e.getX();
			int y = e.getY();
			if (e.getSource() == c) {
				TreePath selPath = getPathForLocation(e.getX(), e.getY());
				if (selPath != null) {
					//MyTreeNode clicNode = (MyTreeNode) tree.getLastSelectedPathComponent();
					//MyTreeNode currentNode = (MyTreeNode) selPath.getLastPathComponent();
					
					//实现鼠标悬浮高亮
					MyTreeCellRenderer.mouseRow = getRowForLocation(x, y);
					repaint();
				}else {//鼠标在节点之外，取消高亮
					MyTreeCellRenderer.mouseRow = -1;
					repaint();
				}
			}
		}
	}
	
}
