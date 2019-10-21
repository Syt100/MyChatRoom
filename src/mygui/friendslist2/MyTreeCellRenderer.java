/**
 * 自定义CellRenderer 实现鼠标悬浮高亮
 */
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
 * @author xuxin
 * @date 2019年10月1日14:00:04
 */
@SuppressWarnings("serial")
public class MyTreeCellRenderer extends DefaultTreeCellRenderer {

	private Icon ico1 = new ImageIcon(MyFriendsList.class.getResource("/Images/arrow_bottom_down_downward_navigation_16px_1225446_easyicon.net.png"));
	private Icon ico2 = new ImageIcon(MyFriendsList.class.getResource("/Images/arrow_forward_navigation_next_right_16px_1225491_easyicon.net.png"));
	private int width = 250;//默认的节点宽度
	private Color color = new Color(240, 240, 240);//默认节点被选中的颜色

	/**
	 * 标识鼠标当前所在行，以实现鼠标悬浮在节点上使节点高亮<br/>
	 * 默认为-1，即不选中任意一行<br/>
	 * 在鼠标事件监听器中这样设置mouseRow：<code>MyTreeCellRenderer.mouseRow = 1;</code>
	 */
	public static int mouseRow = -1;
	
	/**
	 * 要聊天的好友
	 */
	public static int selRow = -1;
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		// 大家这里注意，我为了节省时间，所以就没有写两个node类
		// 所有的代码写在了同一个类中，然后根据不同的节点来调用相应的方法
		MyTreeNode node = (MyTreeNode) value;
		//tree.putClientProperty("JTree.lineStyle", "None"); //去掉节点前的线条
		tree.setToggleClickCount(1); //设置展开节点之前的鼠标单击数为1
		node.setSelectColor(sel, color);//设置节点被选中颜色
		node.setNodeWidth(width);//设置节点宽度
		
		//通过mouseRow判断鼠标是否悬停在当前行
        if (mouseRow == row) {
            node.setHightLight();
        } else {
            //node.setNoHightLight();
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
				Chat window = new Chat();
				window.setIcon(node.getIcon());
				window.setTitle(node.getName());
				window.setSign(node.getSign());
				selRow = -1;
			}
			return node.getNodeView();
		}
		return this;
	}
	
	/**
	 * 设置节点宽度，若在渲染器对象中调用，可设置所有节点统一宽度
	 * @param width
	 */
	public void setMyNodeWidth(int width) {
		this.width = width;
	}
	
	public void setMyNodeSelectedColor(Color c) {
		this.color = c;
	}
}
