package mygui.components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 实现面板上的背景图片<br/>
 * 面板默认的边框为0，即Insets(0, 0, 0, 0);，此时不会显示任何边框。<br/>
 * 可以通过含有Insets对象参数的构造方法指定四周的边距值，会在四周绘制出阴影效果
 * 
 * @apiNote 阴影为从透明到浅灰色的渐变色，需要设置本类的面板背景色为透明(默认已设置)，且需设置放置本类的容器的背景色为透明，如
 *          设置JFram的背景色为透明，然后调用方法frame.setContentPane(new BackgroundJPanel());<br/>
 *          使用本类时，与ResizeFrame类结合使用，将BackgroundJPanel设为ResizeFrame的contentPane。例如：
 *          <code><pre>
 *          public class ShadowFrame extends ResizeFrame {
 *				//上下文面板 
 *				private BackgroundJPanel contentPane;
 *				public ShadowFrame() {
 *					this.setRESIZE_WIDTH(7);
 *					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 *					setBounds(100, 100, 450, 300);
 *					this.setUndecorated(true);
 *					this.setBackground(new Color(0,0,0,0));
 *					contentPane = new BackgroundJPanel(new ImageIcon(ShadowFrame.class.getResource("/Images/back02.jpg")), 10);
 *			
 *					contentPane.setLayout(new BorderLayout(0, 0));
 *					setContentPane(contentPane);
 *				}
 *			}</pre>
 *          </code>
 * 
 * @author xuxin
 * @see {@link GradientPaint
 *      绘制渐变效果}(https://blog.csdn.net/qingmuluoyang/article/details/75151747)
 *      {@link https://www.php.cn/manual/view/22577.html}
 *      {@link https://blog.csdn.net/monitor1394/article/details/6222209?utm_source=blogxgwz9}
 *
 */
public class BackgroundJPanel extends JPanel {
	/** 已生成的串行版本ID */
	private static final long serialVersionUID = 8949644952601819627L;

	/** 填充整个面板的图片 */
	private Image backGroundImg;

	/** 绘制背景面板的左右上下边距，默认都为0 */
	private Insets insets;

	/** 统一绘制背景面板的左右上下边距，默认为0。若为0，则不绘制阴影 */
	private int inset;

	{// 统一初始化
		insets = new Insets(0, 0, 0, 0);
		inset = -1;// 默认不绘制阴影
		this.setBackground(new Color(0, 0, 0, 0));
		// this.setBorder(new EmptyBorder(insets));
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new ResizeFrame();
		BackgroundJPanel panel = new BackgroundJPanel(
				new ImageIcon(BackgroundJPanel.class.getResource("/Images/back02.jpg")), 20);
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 500, 400);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
	}

	/**
	 * 默认的无参构造方法
	 */
	public BackgroundJPanel() {
		super();
	}

	/**
	 * 用Image对象构造
	 * 
	 * @param backGroundImg
	 */
	public BackgroundJPanel(Image backGroundImg) {
		this.backGroundImg = backGroundImg;
	}

	/**
	 * 用ImageIcon对象构造
	 * 
	 * @param backGroundImgIcon
	 */
	public BackgroundJPanel(ImageIcon backGroundImgIcon) {
		this.backGroundImg = backGroundImgIcon.getImage();
	}

	/**
	 * 用ImageIcon对象和边距构造
	 * 
	 * @param backGroundImg
	 * @param insets
	 */
	public BackgroundJPanel(ImageIcon backGroundImg, Insets insets) {
		this.backGroundImg = backGroundImg.getImage();
		if (insets.left < 0 || insets.top < 0 || insets.right < 0 || insets.bottom < 0) {
			throw new NullPointerException("边距不能小于0");
		}
		this.insets = insets;
		inset = 1;
		this.setBorder(new EmptyBorder(insets));// 设置边框
	}

	/**
	 * 用ImageIcon对象和统一的边距
	 * 
	 * @param backGroundImg
	 * @param inset
	 */
	public BackgroundJPanel(ImageIcon backGroundImg, int inset) {
		super();
		this.backGroundImg = backGroundImg.getImage();
		if (inset < 0) {
			throw new NullPointerException("边距不能小于0");
		}
		this.inset = inset;
		this.insets = new Insets(inset, inset, inset, inset);
		this.setBorder(new EmptyBorder(insets));
	}

	/**
	 * 获取背景
	 * 
	 * @return backGroundImg Image对象
	 */
	public Image getBackGroundImg() {
		return backGroundImg;
	}

	/**
	 * 设置背景
	 * 
	 * @param backGroundImg 要设置的 backGroundImg
	 */
	public void setBackGroundImg(Image backGroundImg) {
		this.backGroundImg = backGroundImg;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// 这一句可以清除之前绘制的图像，在这个例子中看不出来效果，若是java做画图板的程序就能看出来
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		// 此方法有很多种，下面是方法说明
		// 1：image对象
		// 2：重绘的起始横坐标
		// 3：重绘的起始纵坐标
		// 4：重绘的宽度
		// 5：重绘的高度
		// 6：一个实现ImageObserver
		// 接口的对象。它将该对象登记为一个图像观察者，因此当图像的任何新信息可见时它被通知。大多组件可以简单的指定this或null
		// 组件可以指定this作为图像观察者的原因是Component类实现了ImageObserver接口。当图像数据被加载时它的实现调用repaint方法

		// 绘制背景图像
		if (backGroundImg != null) {
			g2d.drawImage(backGroundImg, insets.left, insets.top, getWidth() - insets.right - insets.left,
					getHeight() - insets.bottom - insets.top, null);
		}
		if (inset < 0) {// 若没有设置insets，则不绘制阴影
			return;
		}
		// 开始绘制阴影
		// 阴影的两种渐变颜色
		Color color1 = new Color(0, 0, 0, 0);
		Color color2 = new Color(0, 0, 0, 60);

		// 左边界
		// GradientPaint类能够绘制渐变颜色
		GradientPaint paint = new GradientPaint(0, 0, color1, insets.right, 0, color2);
		g2d.setPaint(paint);
		g2d.fillRect(0, insets.top, insets.left, getHeight() - insets.bottom - insets.top);

		// 上边界
		paint = new GradientPaint(insets.left, 0, color1, insets.left, insets.top, color2);
		g2d.setPaint(paint);
		g2d.fillRect(insets.left, 0, getWidth() - insets.right - insets.left, insets.top);

		// 右边界
		// 这里两个颜色反过来了
		paint = new GradientPaint(getWidth() - insets.right, insets.top, color2, getWidth(), insets.top, color1);
		g2d.setPaint(paint);
		g2d.fillRect(getWidth() - insets.right, insets.top, insets.right, getHeight() - insets.top - insets.bottom);

		// 下边界
		// 这里两个颜色反过来了
		paint = new GradientPaint(insets.left, getHeight() - insets.bottom, color2, insets.left, getHeight(), color1);
		g2d.setPaint(paint);
		g2d.fillRect(insets.left, getHeight() - insets.bottom, getWidth() - insets.left - insets.right, insets.bottom);

		// 左上角
		Point2D center = new Point2D.Float(insets.left, insets.top);
		// 半径是下方矩形的宽和高的最大值
		float radius = Math.max(insets.left, insets.top);
		float[] dist = { 0.0f, 1.0f };
		Color[] colors = { color2, color1 };
		RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
		g2d.setPaint(p);
		g2d.fillRect(0, 0, insets.left, insets.top);

		// 左下角
		center = new Point2D.Float(insets.left, getHeight() - insets.bottom);
		radius = Math.max(insets.left, insets.bottom);
		p = new RadialGradientPaint(center, radius, dist, colors);
		g2d.setPaint(p);
		g2d.fillRect(0, getHeight() - insets.bottom, insets.left, insets.bottom);

		// 右上角
		center = new Point2D.Float(getWidth() - insets.right, insets.top);
		radius = Math.max(insets.right, insets.top);
		p = new RadialGradientPaint(center, radius, dist, colors);
		g2d.setPaint(p);
		g2d.fillRect(getWidth() - insets.right, 0, insets.right, insets.top);

		// 右下角
		center = new Point2D.Float(getWidth() - insets.right, getHeight() - insets.bottom);
		radius = Math.max(insets.right, insets.bottom);
		p = new RadialGradientPaint(center, radius, dist, colors);
		g2d.setPaint(p);
		g2d.fillRect(getWidth() - insets.right, getHeight() - insets.bottom, insets.right, insets.bottom);
	}
}
