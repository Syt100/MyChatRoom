/**
 * 自定义的TreeNode，重写好友分组节点、好友信息节点
 * @author xuxin
 * @date 2019年10月1日
 */
package mygui.friendslist2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author xuxin
 *
 */
@SuppressWarnings("serial")
public class MyTreeNode extends DefaultMutableTreeNode {

	/** 账号id */
	private String id;
	/** 头像 */
	private Icon icon;
	/** 昵称文字 */
	private String name;
	/** 签名 */
	private String sign;

	/**
	 * 节点类型 0为分组节点，1为好友节点
	 */
	private int type = 0;

	/** 分组面板 */
	private JPanel cateContent;
	/** 好友面板 */
	private JPanel nodeContent;

	public JLabel iconLabel;
	public JLabel nameLabel;
	public JLabel signLabel;
	
	private Dimension dim = new Dimension();
	private int defaultWidth = 260;

	/**
	 * 默认的无参数构造方法
	 */
	public MyTreeNode() {
		// TODO 自动生成的构造函数存根
	}

	/**
	 * 构造方法：初始化好友分组节点。type=0为分组节点，1为好友节点
	 * 
	 * @param name
	 */
	public MyTreeNode(String name) {
		this.name = name;
		this.type = 0;
		// 初始化UI
		initCateGUI();
	}

	/**
	 * 构造方法：初始化好友分组节点。type=0为分组节点，1为好友节点
	 * 
	 * @param icon 头像
	 * @param name 昵称
	 */
	public MyTreeNode(Icon icon, String name) {
		this.icon = icon;
		this.name = name;
		this.type = 0;
		// 初始化UI
		initCateGUI();
	}

	/**
	 * 构造方法：初始化好友信息节点。type=0为分组节点，1为好友节点
	 * 
	 * @param icon 头像
	 * @param nick 昵称
	 * @param sign 签名
	 */
	public MyTreeNode(Icon icon, String nick, String sign) {
		this.icon = icon;
		this.name = nick;
		this.sign = sign;
		this.type = 1;
		// 初始化UI
		initNodeGUI();
	}

	/**
	 * 获取当前节点类型，0为分组节点，1为好友节点
	 * 
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type 要设置的 type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 初始化好友分组节点（带图片）
	 * 
	 */
	private void initCateGUI() {
		cateContent = new JPanel();
		// cateContent.setLayout(null);
		cateContent.setLayout(new BorderLayout(0, 0));

		cateContent.setOpaque(false);// 设置面板透明
		dim.setSize(defaultWidth, 25);
		cateContent.setPreferredSize(dim);
		cateContent.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));// 设置边框

		iconLabel = new JLabel(icon);//用于显示分组前的箭头图标，在MyTreeCellRenderer中设置前面的箭头图片
		// iconLabel.setBounds(6, 5, 20, 16);
		cateContent.add(iconLabel, BorderLayout.WEST);

		nameLabel = new JLabel(name);
		// nameLabel.setBounds(23, 0, 132, 28);
		cateContent.add(nameLabel, BorderLayout.CENTER);
	}

	/**
	 * 初始化好友信息
	 */
	private void initNodeGUI() {
		nodeContent = new JPanel();
		nodeContent.setLayout(null);
		nodeContent.setOpaque(false);
		dim.setSize(defaultWidth, 50);
		nodeContent.setPreferredSize(dim);
		nodeContent.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		iconLabel = new JLabel(icon);
		iconLabel.setBounds(8, 4, 39, 42);
		nodeContent.add(iconLabel);

		nameLabel = new JLabel(name);
		nameLabel.setBounds(59, 5, 132, 19);
		nodeContent.add(nameLabel);

		signLabel = new JLabel(sign);
		signLabel.setBounds(59, 28, 132, 17);
		nodeContent.add(signLabel);
	}

	/**
	 * 获取设置好友分组面板
	 * 
	 * @return cateContent
	 */
	public JPanel getCateContent() {
		return cateContent;
	}

	/**
	 * 设置好友分组面板
	 * 
	 * @param cateContent 要设置的 cateContent
	 */
	public void setCateContent(JPanel cateContent) {
		this.cateContent = cateContent;
	}

	/**
	 * 获取好友信息面板
	 * 
	 * @return nodeContent
	 */
	public JPanel getNodeContent() {
		return nodeContent;
	}

	/**
	 * 设置好友信息面板
	 * 
	 * @param nodeContent 要设置的 nodeContent
	 */
	public void setNodeContent(JPanel nodeContent) {
		this.nodeContent = nodeContent;
	}

	/**
	 * 将自定义UI返回给渲染器 <br/>
	 * 供渲染器调用，返回的必须是一个Component
	 * 
	 * @return Component 好友分组面板cateContent
	 */
	public Component getCateView() {
		return cateContent;
	}

	/**
	 * 将自定义UI返回给渲染器 <br/>
	 * 供渲染器调用，返回的必须是一个Component
	 * 
	 * @return Component 好友信息节点面板nodeContent
	 */
	public Component getNodeView() {
		return nodeContent;
	}

	/**
	 * @return id
	 */
	protected String getId() {
		return id;
	}

	/**
	 * @param id 要设置的 id
	 */
	protected void setId(String id) {
		this.id = id;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	/**设置被选中后当前节点的颜色，由MyTreeCellRenderer调用
	 * @param sec 是否被选中
	 * @param color 设置被选中节点的颜色
	 */
	public void setSelectColor(boolean sec, Color color) {
		if (sec && type == 0) {
			cateContent.setOpaque(true);
			cateContent.setBackground(color);
		} else if (sec && type == 1) {
			nodeContent.setOpaque(true);
			nodeContent.setBackground(color);
		} else if (!sec && type == 0) {
			cateContent.setOpaque(false);
		} else if (!sec && type == 1) {
			nodeContent.setOpaque(false);
		}
	}
	
	/**重写toString方法，返回当前节点的名称
	 * @return name 当前节点名称
	 */
	public String toString() {
		return name;
	}

	/**
	 * 设置当前节点高亮<br/>
	 * 但不知道为什么，cateContent.setOpaque(true);不起作用
	 */
	public void setHightLight() {
		if(type ==0 && cateContent != null) {
			cateContent.setOpaque(true);//无效
			cateContent.repaint();
			cateContent.setBackground(Color.green);//有效
			cateContent.setVisible(false);//无效
			//调试用
			//System.out.println(cateContent.getBackground().toString());
			//System.out.println("我触发了");
		}else if(type == 1) {
			nodeContent.setOpaque(true);
			nodeContent.setBackground(Color.green);
		}
	}
	
	/**
	 * 取消当前节点高亮<br/>
	 * 这个也不起作用
	 */
	public void setNoHightLight() {
		if(type == 0 && cateContent != null) {
			cateContent.setOpaque(false);
		}else if(type == 1 && nodeContent != null) {
			nodeContent.setOpaque(false);
		}
	}
	
	/**
	 * 设置节点宽度，在MyTreeCellRenderer中调用
	 * @param width 期望的宽度
	 */
	public void setNodeWidth(int width) {
		if(type == 0 && cateContent != null) {
			dim.setSize(width, 25);
			cateContent.setPreferredSize(dim);
		}else if(type == 1 && nodeContent != null) {
			dim.setSize(width, 50);
			nodeContent.setPreferredSize(dim);
		}
	}
}