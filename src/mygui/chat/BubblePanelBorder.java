package mygui.chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * 绘制单个消息气泡
 * @author xuxin
 *
 */
public class BubblePanelBorder extends JPanel {
	
	private static final long serialVersionUID = 7060923833121261620L;

	/** 消息发送方是否为自己，默认为自己 */
	private boolean isSelf = true;
	/** 是否显示昵称，默认为显示 */
	private boolean isShowName = true;
	
	/** 头像 */
	private BufferedImage image = null;

	/** 昵称 */
	private String name = "蜜城mimiob";
	/** 消息文本 */
	private String message = "注意，这是一段小字.charAt() 方法用于返回指定索引处的字符。索引范围为从 0 到 length() - 1。";

	/** 位置 */
	private Point nameSize = new Point();
	private Point bubbleSize = new Point();
	private Point imageSize = new Point(49, 49);

	private Point namePoint = new Point();
	private Point bubblePoint = new Point();
	private Point imagePoint = new Point();

	// 绘制消息气泡旁边的小箭头的坐标点
	int xPoints[] = new int[3];
	int yPoints[] = new int[3];
	/** 小箭头的位置 */
	private Point arrowPoint;

	/** 用于计算面板高度 */
	private int panelHeight = 30;
	/** 单行字符串的高度 */
	private int singleStrHeight;
	/** 默认显示的字体 */
	private Font font;
	/** 默认的字体属性、测量方法 */
	private FontMetrics fontMetrics;

	/** 名字的字符串总宽度 */
	private int nameStrWidth;
	/** message消息字符串的总宽度 */
	private int strWidth;
	/** 用于显示message消息字符串的行宽度 */
	private int rowWidth;
	/** message消息字符串的行数 */
	private int strRow;

	/** 消息字符串的位置 */
	private int loc_X;
	private int loc_Y;

	/**
	 * 构造方法
	 */
	public BubblePanelBorder() {
		super();
		init();
	}

	/**
	 * 构造方法，用消息文本构造
	 * 
	 * @param message
	 */
	public BubblePanelBorder(String message) {
		this.message = message;
		init();
	}
	
	/**
	 * @param isSelf
	 * @param message
	 */
	public BubblePanelBorder(boolean isSelf, String message) {
		this.isSelf = isSelf;
		this.message = message;
	}

	/**
	 * @param isSelf
	 * @param image
	 * @param name
	 * @param message
	 */
	public BubblePanelBorder(boolean isSelf, BufferedImage image, String name, String message) {
		this.isSelf = isSelf;
		this.image = image;
		this.name = name;
		this.message = message;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 根据本类中message字符串计算出面板高度
	 * 
	 * @param width 面板宽度
	 * @return int 高度
	 */
	public int getPanelHight(int width) {
		System.out.println("先行计算的宽度：" + width);
		rowWidth = width - 20 - imageSize.x - 35;
		strRow = getStringRows(strWidth, rowWidth);
		panelHeight = singleStrHeight * strRow + singleStrHeight + 20;
		System.out.println("先行计算出的字符串高度：" + panelHeight);
		return panelHeight;
	}

	/**
	 * 获取单行的字符数
	 * 
	 * @param strnum   字符串的总长度
	 * @param rowWidth 单行的宽度
	 * @param strWidth 字符串的总宽度
	 * @return int 单行的字符数
	 */
	private int getRowStrNum(int strnum, int rowWidth, int strWidth) {
		int rowstrnum = 0;
		if (strWidth == 0) {
			return 0;
		}
		if (getStringRows(strWidth, rowWidth) == 1) {// 如果只有一行，则单行字符数等于字符串总长度
			rowstrnum = strnum;
		} else {
			rowstrnum = (rowWidth * strnum) / strWidth;
		}
		System.out.println("每行的字符数:" + rowstrnum);
		return rowstrnum;
	}

	/**
	 * 获取字符串的行数。
	 * 
	 * @param strWidth 字符串的总宽度
	 * @param rowWidth 单行的宽度
	 * @return int 行数
	 */
	private int getStringRows(int strWidth, int rowWidth) {
		int rows = 0;
		if (strWidth % rowWidth > 0) {
			rows = strWidth / rowWidth + 1;
		} else {
			rows = strWidth / rowWidth;
		}
		System.out.println("行数:" + rows);
		return rows;
	}

	/**
	 * 获取字符串的总宽度 另一种方法：char[] strcha = str.toCharArray(); int strWidth =
	 * g.getFontMetrics().charsWidth(strcha, 0, str.length());
	 * 
	 * @param g   Graphics2D绘图对象
	 * @param str 要计算宽度的字符串
	 * @return int 字符串的总宽度
	 */
	private int getStringWidth(Graphics2D g, String str) {
		int strWidth = 0;
		if (g == null) {
			strWidth = fontMetrics.stringWidth(str);// 获取整个字符串的宽度
			System.out.println("g为空时字符总宽度:" + strWidth);
		} else {
			strWidth = g.getFontMetrics().stringWidth(str);
		}
//		for (int i = 0; i < str.length(); i++) {
//			char c = str.charAt(i);
//			if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
//				strWidth += 2;
//			}
//		}
		System.out.println(str + "的字符总宽度:" + strWidth);
		return strWidth;
	}

	/**
	 * 初始化参数
	 */
	private void init() {
		// 初始化字体
		font = new Font("微软雅黑", Font.PLAIN, 12);
		fontMetrics = getFontMetrics(font);// 初始化字体属性
		singleStrHeight = fontMetrics.getHeight();// 初始化单行字体宽度
		// 初始化字符串宽度
		nameStrWidth = getStringWidth(null, name);
		strWidth = getStringWidth(null, message);
		// 默认的头像
		try {
			image = ImageIO.read(getClass().getResource("/Images/223209_3.jpg"));
			image.getScaledInstance(imageSize.x, imageSize.x, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		setBackground(Color.WHITE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO 自动生成的方法存根
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 反锯齿平滑绘制
		super.paintComponent(g2D);

		g2D.setFont(font);

		singleStrHeight = g2D.getFontMetrics().getHeight();
		System.out.println("字符串高度:" + singleStrHeight);

		if (isSelf) {// 自己发的消息
			// 确定头像位置
			imagePoint.x = getWidth() - imageSize.x;
			imagePoint.y = 0;
			g2D.drawImage(image, imagePoint.x, imagePoint.y, imageSize.x, imageSize.y, null);
			if (isShowName) {
				// 确定昵称位置
				namePoint.x = getWidth() - nameStrWidth - 20 - imageSize.x;
				namePoint.y = singleStrHeight;
				nameSize.x = nameStrWidth;
				nameSize.y = singleStrHeight;
				g2D.drawString(name, namePoint.x, namePoint.y);
			}
			// 确定小箭头位置
			arrowPoint = new Point(getWidth() - imageSize.x, namePoint.y + 10);
			xPoints[0] = arrowPoint.x - 15;
			yPoints[0] = arrowPoint.y;
			xPoints[1] = xPoints[0] + 8;
			yPoints[1] = arrowPoint.y;
			xPoints[2] = xPoints[0];
			yPoints[2] = arrowPoint.y + 8;
			// 确定气泡位置
			bubblePoint.x = 5;
			bubblePoint.y = namePoint.y + 5;
			bubbleSize.x = getWidth() - 20 - imageSize.x;
			bubbleSize.y = getHeight() - namePoint.y - 10;
			// 确定消息字符串位置
			loc_X = bubblePoint.x + 5;
			loc_Y = bubblePoint.x + nameSize.y + singleStrHeight;
			// 画气泡
			if (strRow <= 1) {// 单行，则气泡适应文字
				// Y坐标不变。位置为原本的X坐标加上气泡宽度减去字符串宽度，再减10为微调。新的气泡宽度加10也是微调
				g2D.drawRoundRect(bubblePoint.x + bubbleSize.x - strWidth - 10, bubblePoint.y, strWidth + 10,
						bubbleSize.y, 10, 10);
			} else {// 多行文字，气泡大小不变
				g2D.drawRoundRect(bubblePoint.x, bubblePoint.y, bubbleSize.x, bubbleSize.y, 10, 10);
			}
		} else {// 对方发的消息
			// 确定头像位置
			imagePoint.x = 0;
			imagePoint.y = 0;
			g2D.drawImage(image, imagePoint.x, imagePoint.y, imageSize.x, imageSize.y, null);
			if (isShowName) {
				// 确定昵称位置
				namePoint.x = 8 + imageSize.x;
				namePoint.y = singleStrHeight;
				nameSize.x = nameStrWidth;
				nameSize.y = singleStrHeight;
				g2D.drawString(name, namePoint.x, namePoint.y);
			}
			// 确定小箭头位置
			arrowPoint = new Point(imageSize.x, namePoint.y + 10);
			xPoints[0] = arrowPoint.x;
			yPoints[0] = arrowPoint.y;
			xPoints[1] = xPoints[0] + 8;
			yPoints[1] = arrowPoint.y;
			xPoints[2] = xPoints[1];
			yPoints[2] = arrowPoint.y + 8;
			// 确定气泡位置
			bubblePoint.x = imageSize.x + 8;
			bubblePoint.y = namePoint.y + 5;
			bubbleSize.x = getWidth() - 20 - imageSize.x;
			bubbleSize.y = getHeight() - namePoint.y - 10;
			// 确定消息字符串位置
			loc_X = bubblePoint.x + 5;
			loc_Y = bubblePoint.y + singleStrHeight;
			// 画气泡
			if (strRow <= 1) {// 单行，则气泡适应文字
				// Y坐标不变。位置为原本的X坐标加上气泡宽度减去字符串宽度，再减10为微调。新的气泡宽度加10也是微调
				g2D.drawRoundRect(bubblePoint.x, bubblePoint.y, strWidth + 10, bubbleSize.y, 10, 10);
			} else {// 多行文字，气泡大小不变
				g2D.drawRoundRect(bubblePoint.x, bubblePoint.y, bubbleSize.x, bubbleSize.y, 10, 10);
			}
		}

		// 画小箭头
		g2D.setColor(Color.BLUE);
		// g2D.fillPolygon(xPoints, yPoints, 3);
		g2D.drawPolyline(xPoints, yPoints, 3);
		g2D.drawLine(xPoints[0], yPoints[0] + 1, xPoints[2], yPoints[2] - 1);

		this.setPreferredSize(new Dimension(0, getPanelHight(getWidth())));
		System.out.println("计算高度为：" + panelHeight + "; 实际高度为：" + getHeight() + "\n当前宽度：" + getWidth());

		// 画消息
		String temp = "";
		int rowstrnum = getRowStrNum(message.length(), rowWidth, strWidth);
		if (strWidth > rowWidth) {// 如果不止一行
			for (int i = 0; i < strRow; i++) {
				if (i == strRow - 1) {
					// 最后一行
					temp = message.substring(i * rowstrnum, message.length());
				} else {
					temp = message.substring(i * rowstrnum, (i + 1) * rowstrnum);
				}
				if (i > 0) {
					// 第一行不需要增加字符高度，以后的每一行在换行的时候都需要增加字符高度
					loc_Y = loc_Y + singleStrHeight;
				}
				g2D.drawString(temp, loc_X, loc_Y);
			}
		} else {// 只有一行
			// 同单行气泡位置
			if (isSelf) {
				g2D.drawString(message, loc_X + bubbleSize.x - strWidth - 10, loc_Y);
			} else {
				g2D.drawString(message, loc_X, loc_Y);
			}
		}

	}

	/**
	 * @param message 要设置的 message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
