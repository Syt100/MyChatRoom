package mygui.friendslist2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
  * 好友列表的类中，换肤对话框的自定义列表渲染器
 * 
 * @author 原文链接：https://bbs.csdn.net/topics/350012532<br/>
 *         https://bbs.csdn.net/topics/380094912<br/>
  *         日期：2019年10月4日20:35:19<br/>
 * @apiNote 用法：<br/>
 *          <code>
 * JList list = new JList();
 * DefaultListModel dlm = new DefaultListModel();
 * list.setModel(dlm);
 * list.setCellRenderer(render);
 * MyListCellRenderer render = new MyListCellRenderer();
 * render.setCurIndex(ci);// ci为索引，表示第几个单元格（从0开始）
 * list.repaint();//用来刷新显示
 * </code>
 */
public class MyListCellRenderer extends JLabel implements ListCellRenderer<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 这个索引标识哪个节点被高亮，在外部调用<code>setCurIndex(ci)</code>来实现设置高亮<br/>
	 */
	private int curIndex = -1;
	/** 默认的单元格的字体 */
	private Font font = new Font("SimSun", Font.PLAIN, 16);
	/** 当前默认的背景色 */
	Color backGround;
	/** 当前的前景色 */
	Color foreGround;
	/** 默认的背景色 */
	Color defaultBackGround = Color.WHITE;
	/** 默认的前景色 */
	Color defaultForeGround = Color.BLACK;
	/** 默认高亮的背景色 */
	Color highBackGround = Color.RED;
	/** 默认高亮的前景色 */
	Color highForeGround = Color.WHITE;
	/** 默认的被选中的背景色 */
	Color selectBackGround = Color.GREEN;
	/** 默认的被选中的前景色 */
	Color selectForeGround = Color.RED;
	
	/**
	 * 默认的无参构造方法，将单元格设为不透明
	 */
	public MyListCellRenderer() {
		this.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		setText(value.toString());
		setFont(font);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		backGround = defaultBackGround;//将背景色和前景色重置为默认。表示未选中时的颜色
		foreGround = defaultForeGround;

		if (index == curIndex) {// 如果当前节点是被标识为高亮节点
			backGround = highBackGround;//设置高亮新的背景色和前景色
			foreGround = highForeGround;
		} else if (isSelected) {// 检查下是否被选中，被标识为高亮的节点优先级高
			backGround = selectBackGround;// 设置被选中的背景色和前景色
			foreGround = selectForeGround;
		}

		setBackground(backGround);
		setForeground(foreGround);
		return this;
	}

	/**
	 * 设置高亮节点的索引
	 * @param ci 设置要被高亮的节点的索引
	 */
	public void setCurIndex(int ci) {
		this.curIndex = ci;
	}

	/**
	 * 设置单元格显示的字体
	 * @param font 要设置的 font
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * @return defaultBackGround
	 */
	public Color getDefaultBackGround() {
		return defaultBackGround;
	}

	/**
	 * @param defaultBackGround 要设置的 defaultBackGround
	 */
	public void setDefaultBackGround(Color defaultBackGround) {
		this.defaultBackGround = defaultBackGround;
	}

	/**
	 * @return defaultForeGround
	 */
	public Color getDefaultForeGround() {
		return defaultForeGround;
	}

	/**
	 * @param defaultForeGround 要设置的 defaultForeGround
	 */
	public void setDefaultForeGround(Color defaultForeGround) {
		this.defaultForeGround = defaultForeGround;
	}

	/**
	 * @return highBackGround
	 */
	public Color getHighBackGround() {
		return highBackGround;
	}

	/**
	 * @param highBackGround 要设置的 highBackGround
	 */
	public void setHighBackGround(Color highBackGround) {
		this.highBackGround = highBackGround;
	}

	/**
	 * @return highForeGround
	 */
	public Color getHighForeGround() {
		return highForeGround;
	}

	/**
	 * @param highForeGround 要设置的 highForeGround
	 */
	public void setHighForeGround(Color highForeGround) {
		this.highForeGround = highForeGround;
	}

	/**
	 * @return selectBackGround
	 */
	public Color getSelectBackGround() {
		return selectBackGround;
	}

	/**
	 * @param selectBackGround 要设置的 selectBackGround
	 */
	public void setSelectBackGround(Color selectBackGround) {
		this.selectBackGround = selectBackGround;
	}

	/**
	 * @return selectForeGround
	 */
	public Color getSelectForeGround() {
		return selectForeGround;
	}

	/**
	 * @param selectForeGround 要设置的 selectForeGround
	 */
	public void setSelectForeGround(Color selectForeGround) {
		this.selectForeGround = selectForeGround;
	}
	
}
