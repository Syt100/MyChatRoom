package mygui.friendslist;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;


public class FriTreeRender extends DefaultTreeCellRenderer implements TreeCellRenderer {

	String a = new String("/Images/arrow_forward_navigation_next_right_16px_1225491_easyicon.net.png");
	String b = new String("/Images/arrow_bottom_down_downward_navigation_16px_1225446_easyicon.net.png");
	ImageIcon Arrow_right = new ImageIcon(this.getClass().getResource(a));// 节点折叠时的图片
	ImageIcon Arrow_down = new ImageIcon(this.getClass().getResource(b));// 节点展开式的图片

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		FriTreeNode f = (FriTreeNode) value;// 把value转换为节点

		// 选中
		updateUI();

		if (selected) {
			//setForeground(getTextSelectionColor());
			this.setBackground(Color.green);
		} else {
			setForeground(getTextNonSelectionColor());
		}

		if (leaf && f.getParent() != tree.getModel().getRoot()) {// 节点需要不为根节点，和根节点的孩子节点

			/***************** 设置JLable（好友标签）的文字 ****************************/
			String hyName = f.getuName();// 第一行显示好友名字
			String hyQianMing = f.getText();// 第二行显示好友的个性签名
			if (hyName == null) {
				hyName = "";
			}
			if (hyQianMing == null) {
				hyQianMing = "";
			}
			String text = "<html>" + hyName + "<br/>" + hyQianMing + " <html/>";
			setText(text);// 设置JLable的文字

			/**************************** 设置JLable的图片 *****************/
			// 得到此图标的 Image,然后创建此图像的缩放版本。

			if (f.getImg() != null) {// 若节点中没有初始化图片对象，不加此判断没有图像时会报错
				Image img = f.getImg().getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);// 缩放到50x50
				setIcon(new ImageIcon(img));// 设置JLable的图片
				setIconTextGap(15);// 设置JLable的图片与文字之间的距离
			}

		} else { // 非叶子节点的文字为节点的uName
			setText(f.getuName());// 设置JLable的文字
			if (expanded) {// 节点展开
				setIcon(Arrow_down);
				// setBackground(Color.BLUE);
			} else {
				setIcon(Arrow_right);// 设置JLable的图片
			}
		}

		// Dimension d;
//		setSize(0, 30);

		tree.setToggleClickCount(1); // 设置展开节点之前的鼠标单击数为1
		tree.putClientProperty("JTree.lineStyle", "Horizontal");// 将树设为水平分隔风格
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);// 设置一次自能选中一个节点
		tree.setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 14));//字体的大小，样式
		
		return this;
	}
}
