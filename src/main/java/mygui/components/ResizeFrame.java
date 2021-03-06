package mygui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 继承JFrame类，实现去掉窗口标题栏后实现拖动窗体来移动位置、拖动鼠标调整窗口大小。
 * 
 * @author xuxin
 *
 */
public class ResizeFrame extends JFrame {

	/** 已生成的串行版本标识 */
	private static final long serialVersionUID = -270775752895603856L;

	private boolean isTopLeft;// 是否处于左上角调整窗口状态
	private boolean isTop;// 是否处于上边界调整窗口状态
	private boolean isTopRight;// 是否处于右上角调整窗口状态
	private boolean isRight;// 是否处于右边界调整窗口状态
	private boolean isBottomRight;// 是否处于右下角调整窗口状态
	private boolean isBottom;// 是否处于下边界调整窗口状态
	private boolean isBottomLeft;// 是否处于左下角调整窗口状态
	private boolean isLeft;// 是否处于左边界调整窗口状态
	private Point point = new Point();// 记录鼠标位置，用于拖动窗体
	private int RESIZE_WIDTH = 2;// 判定是否为调整窗口状态的范围与边界距离
	private final static int MIN_WIDTH = 20;// 窗口最小宽度
	private final static int MIN_HEIGHT = 20;// 窗口最小高度

	public ResizeFrame() {
		ResizeAdapter listener = new ResizeAdapter(this);
		addMouseMotionListener(listener);
		addMouseListener(listener);
	}

	private class ResizeAdapter extends MouseAdapter {
		private Component c;

		public ResizeAdapter(Component c) {
			this.c = c;
		}

		@Override
		public void mouseMoved(MouseEvent event) {// 鼠标移动
			// 如果不能改变大小，直接返回
			if (isResizable() == false) {
				return;
			}
			
			int x = event.getX();
			int y = event.getY();
			int width = c.getWidth();
			int height = c.getHeight();
			int cursorType = Cursor.DEFAULT_CURSOR;// 鼠标光标初始为默认类型，若未进入调整窗口状态，保持默认类型
			// 先将所有调整窗口状态重置
			isTopLeft = isTop = isTopRight = isRight = isBottomRight = isBottom = isBottomLeft = isLeft = false;
			if (y <= RESIZE_WIDTH) {
				if (x <= RESIZE_WIDTH) {// 左上角调整窗口状态
					isTopLeft = true;
					cursorType = Cursor.NW_RESIZE_CURSOR;
				} else if (x >= width - RESIZE_WIDTH) {// 右上角调整窗口状态
					isTopRight = true;
					cursorType = Cursor.NE_RESIZE_CURSOR;
				} else {// 上边界调整窗口状态
					isTop = true;
					cursorType = Cursor.N_RESIZE_CURSOR;
				}
			} else if (y >= height - RESIZE_WIDTH) {
				if (x <= RESIZE_WIDTH) {// 左下角调整窗口状态
					isBottomLeft = true;
					cursorType = Cursor.SW_RESIZE_CURSOR;
				} else if (x >= width - RESIZE_WIDTH) {// 右下角调整窗口状态
					isBottomRight = true;
					cursorType = Cursor.SE_RESIZE_CURSOR;
				} else {// 下边界调整窗口状态
					isBottom = true;
					cursorType = Cursor.S_RESIZE_CURSOR;
				}
			} else if (x <= RESIZE_WIDTH) {// 左边界调整窗口状态
				isLeft = true;
				cursorType = Cursor.W_RESIZE_CURSOR;
			} else if (x >= width - RESIZE_WIDTH) {// 右边界调整窗口状态
				isRight = true;
				cursorType = Cursor.E_RESIZE_CURSOR;
			}
			// 最后改变鼠标光标
			c.setCursor(new Cursor(cursorType));
		}

		@Override
		public void mouseDragged(MouseEvent event) {// 鼠标拖动
			// 控制窗体拖动 
			Point p = c.getLocation();
			if (!isTopLeft && !isTop && !isTopRight && !isRight && !isBottomRight && !isBottom && !isBottomLeft
					&& !isLeft) {
				c.setLocation(p.x + event.getX() - point.x, p.y + event.getY() - point.y);
			}

			// 判断是否可以调整，如果不能，直接返回
			if (isResizable() == false) {
				return;
			}

			// 调整边框
			int x = event.getX();
			int y = event.getY();
			int width = c.getWidth();
			int height = c.getHeight();
			// 保存窗口改变后的x、y坐标和宽度、高度，用于预判是否会小于最小宽度、最小高度
			int nextX = c.getX();
			int nextY = c.getY();
			int nextWidth = width;
			int nextHeight = height;
			if (isTopLeft || isLeft || isBottomLeft) {// 所有左边调整窗口状态
				nextX += x;
				nextWidth -= x;
			}
			if (isTopLeft || isTop || isTopRight) {// 所有上边调整窗口状态
				nextY += y;
				nextHeight -= y;
			}
			if (isTopRight || isRight || isBottomRight) {// 所有右边调整窗口状态
				nextWidth = x;
			}
			if (isBottomLeft || isBottom || isBottomRight) {// 所有下边调整窗口状态
				nextHeight = y;
			}
			if (nextWidth <= MIN_WIDTH) {// 如果窗口改变后的宽度小于最小宽度，则宽度调整到最小宽度
				nextWidth = MIN_WIDTH;
				if (isTopLeft || isLeft || isBottomLeft) {// 如果是从左边缩小的窗口，x坐标也要调整
					nextX = c.getX() + width - nextWidth;
				}
			}
			if (nextHeight <= MIN_HEIGHT) {// 如果窗口改变后的高度小于最小高度，则高度调整到最小高度
				nextHeight = MIN_HEIGHT;
				if (isTopLeft || isTop || isTopRight) {// 如果是从上边缩小的窗口，y坐标也要调整
					nextY = c.getY() + height - nextHeight;
				}
			}
			// 最后统一改变窗口的x、y坐标和宽度、高度，可以防止刷新频繁出现的屏闪情况
			setBounds(nextX, nextY, nextWidth, nextHeight);
		}

		@Override
		public void mousePressed(MouseEvent e) {// 获取鼠标按下时的坐标
			point.x = e.getX();
			point.y = e.getY();
		}
	}

	/**
	 * 获取调整范围
	 * 
	 * @return rESIZE_WIDTH
	 */
	public int getRESIZE_WIDTH() {
		return RESIZE_WIDTH;
	}

	/**
	 * 设置调整范围
	 * 
	 * @param rESIZE_WIDTH 要设置的 rESIZE_WIDTH
	 */
	public void setRESIZE_WIDTH(int rESIZE_WIDTH) {
		RESIZE_WIDTH = rESIZE_WIDTH;
	}

	/**
	 * 测试用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 一个简单的演示小例子
		JFrame frame = new ResizeFrame();
		frame.setSize(400, 300);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
		frame.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() > 1) {
					System.exit(0);
				}
			}
		});
		frame.setUndecorated(true);
		frame.setVisible(true);
	}
}
