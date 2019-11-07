/**
 * 
 */
package mygui.frameutil;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author xuxin
 *
 */
public class BackgroundJPanel extends JPanel {
	/**
	 * 已生成的串行版本ID
	 */
	private static final long serialVersionUID = 8949644952601819627L;

	/**
	 * 填充整个面板的图片
	 */
	private Image backGroundImg;
	
	/**
	 * 默认的无参构造方法
	 */
	public BackgroundJPanel() {
		// TODO 自动生成的构造函数存根
		super();
	}
	
	/**
	 * 用Image对象构造
	 * @param backGroundImg
	 */
	public BackgroundJPanel(Image backGroundImg) {
		this.backGroundImg = backGroundImg;
	}

	/**
	 * 用ImageIcon对象构造
	 * @param backGroundImgIcon
	 */
	public BackgroundJPanel(ImageIcon backGroundImgIcon) {
		// TODO 自动生成的构造函数存根
		this.backGroundImg = backGroundImgIcon.getImage();
	}

	/**
	 * 获取背景
	 * @return backGroundImg Image对象
	 */
	public Image getBackGroundImg() {
		return backGroundImg;
	}

	/**
	 * 设置背景
	 * @param backGroundImg 要设置的 backGroundImg
	 */
	public void setBackGroundImg(Image backGroundImg) {
		this.backGroundImg = backGroundImg;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// 这一句可以清除之前绘制的图像，在这个例子中看不出来效果，若是java做画图板的程序就能看出来
		super.paintComponent(g);
		// 此方法有很多种，看的眼花缭乱，了解参数了就好多了，这里说一下基本的
		// 1：image对象
		// 2：重绘的起始横坐标
		// 3：重绘的起始纵坐标
		// 4：重绘的宽度
		// 5：重绘的高度
		// 6：一个实现ImageObserver
		// 接口的对象。它将该对象登记为一个图像观察者，因此当图像的任何新信息可见时它被通知。大多组件可以简单的指定this或null
		// 组件可以指定this作为图像观察者的原因是Component类实现了ImageObserver接口。当图像数据被加载时它的实现调用repaint方法
		g.drawImage(backGroundImg, 0, 0, getWidth(), getHeight(), null);
	}
}
