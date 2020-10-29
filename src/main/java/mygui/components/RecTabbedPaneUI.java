package mygui.components;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

/**
 * 自定义TabbedPaneUI，显示效果为圆角
 * @author https://blog.csdn.net/xzquan/article/details/83263243
 *
 */
public class RecTabbedPaneUI extends BasicTabbedPaneUI {

	/**
	 * by: QQ: 725137 Name:whole
	 */
	private Color defaultColor = new Color(192, 192, 192);

	private Color selectedColor = new Color(155, 159, 232);

	public static ComponentUI createUI(JComponent c) {
		return new RecTabbedPaneUI();
	}

	@Override
	protected LayoutManager createLayoutManager() {
		return new TabbedPaneLayout();
	}// 设置Layout

	@Override
	protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
		int width = tabPane.getWidth();
		int height = tabPane.getHeight();
		Insets insets = tabPane.getInsets();
		Insets tabAreaInsets = getTabAreaInsets(tabPlacement);

		int x = insets.left;
		int y = insets.top;
		int w = width - insets.right - insets.left;
		int h = height - insets.top - insets.bottom;

		switch (tabPlacement) {
		case LEFT:
			x += calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);

			w -= (x - insets.left);
			break;
		case RIGHT:
			w -= calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);

			w += tabAreaInsets.left;
			break;
		case BOTTOM:
			h -= calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);

			h += tabAreaInsets.top;
			break;
		case TOP:

		default:
			y += calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
			y -= tabAreaInsets.bottom;
			h -= (y - insets.top);
		}

		// Fill region behind content area
		Color color = UIManager.getColor("TabbedPane.contentAreaColor");
		// Color color = new Color(240, 240, 240);
		if (color != null) {
			g.setColor(color);
		} else if (selectedColor == null) {
			g.setColor(tabPane.getBackground());
		} else {
			g.setColor(selectedColor);
		}
		g.fillRect(x, y, w, h);
		g.setColor(selectedColor);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(10.0f));

//		g.drawLine(x + 6, y + 5, w, y + 5);
		// g.drawLine(x , y , w, y );
		g2d.setStroke(new BasicStroke(1.0f));
	}

	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
			boolean isSelected) {
		g.setColor(!isSelected || selectedColor == null ? defaultColor : selectedColor);

		switch (tabPlacement) {
		case LEFT:
			g.fillRect(x + 1, y + 1, w - 1, h - 3);
			break;
		case RIGHT:
			g.fillRect(x, y + 1, w - 2, h - 3);
			break;
		case BOTTOM:
			g.fillRect(x + 1, y, w - 3, h - 1);
			break;
		case TOP:

		default:
			g.fillRect(x + 1, y + 1, w - 3, h - 1);
		}
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

	public class TabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout {
		@Override
		protected void calculateTabRects(int tabPlacement, int tabCount) {
			super.calculateTabRects(tabPlacement, tabCount);
			setRec(10);
			// 设置间距
			tabInsets.bottom = 10;// 设置选项卡高度
		}

		public void setRec(int rec) {
			for (int i = 0; i < rects.length; i++) {
				rects[i].x = rects[i].x + rec * i;
			}
		}
	}
}
