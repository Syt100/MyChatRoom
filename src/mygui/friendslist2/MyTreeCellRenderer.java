package mygui.friendslist2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import mygui.chat.Chat;

/**
 * 自定义CellRenderer 实现鼠标悬浮高亮
 * 
 * @author xuxin
 * @date 2019年10月1日14:00:04
 */
public class MyTreeCellRenderer extends DefaultTreeCellRenderer {
	/** 已生成的串行版本标识 */
	private static final long serialVersionUID = 26783771907719948L;

	/** 分组节点展开图标，左边的小箭头 */
	private Icon ico1 = new ImageIcon(getClass().getResource("/Images/arrow_bottom_down_downward_navigation_16px_1225446_easyicon.net.png"));
	/** 分组节点折叠图标，左边的小箭头 */
	private Icon ico2 = new ImageIcon(getClass().getResource("/Images/arrow_forward_navigation_next_right_16px_1225491_easyicon.net.png"));

	/** 默认的节点宽度 */
	private int width = 250;
	/** 默认节点被选中的颜色 */
	private Color color = new Color(240, 240, 240);

	/**
	 * 标识鼠标当前所在行，以实现鼠标悬浮在节点上使节点高亮<br/>
	 * 默认为-1，即不选中任意一行<br/>
	 * 在鼠标事件监听器中这样设置mouseRow：<code>MyTreeCellRenderer.mouseRow = 1;</code>
	 */
	public static int mouseRow = -1;

	/** 选择要聊天的好友 */
	public static int selRow = -1;

	/** 选染的节点对象 */
	private MyTreeNode node;
	
	/** 初始化本类时传入引用，以便调用其中的方法 */
	private MyFriendsList3 friend;

	/**
	 * 默认的无参数构造方法
	 */
	public MyTreeCellRenderer() {
		super();
	}

	/**
	 * @param friend
	 */
	public MyTreeCellRenderer(MyFriendsList3 friend) {
		this.friend = friend;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {

		node = (MyTreeNode) value;
		// tree.putClientProperty("JTree.lineStyle", "None"); //去掉节点前的线条
		tree.setToggleClickCount(1); // 设置展开节点之前的鼠标单击数为1
		node.setSelectColor(sel, color);// 设置节点被选中颜色
		node.setNodeWidth(width);// 设置节点宽度

		// 通过mouseRow判断鼠标是否悬停在当前行
		if (mouseRow == row) {
			node.setHightLight();
		} else {
			// node.setNoHightLight();
		}

		if (node.getLevel() == 1) {// 如果是树节点（分组）
			if (expanded) {// 如果节点展开
				node.iconLabel.setIcon(ico1);
			} else {
				node.iconLabel.setIcon(ico2);
			}
			return node.getCateView();
		}
		if (node.getLevel() == 2) {// 如果是叶子节点（好友）
			// 要聊天的好友
			if (selRow == row) {
				// 发起聊天
				friend.chatWithFriend(node.getId());
				selRow = -1;
			}
			return node.getNodeView();
		}
		return this;
	}

	/**
	 * 设置节点宽度，若在渲染器对象中调用，可设置所有节点统一宽度
	 * 
	 * @param width
	 */
	public void setMyNodeWidth(int width) {
		this.width = width;
	}

	public void setMyNodeSelectedColor(Color c) {
		this.color = c;
	}

	/**
	 * @return node
	 */
	protected MyTreeNode getNode() {
		return node;
	}
}
