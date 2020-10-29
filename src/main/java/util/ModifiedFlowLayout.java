package util;

import java.awt.*;

/**
 * 类似于文档流的界面布局器：改进版的FlowLayout。</br>
 * 放在滚动面板中，在横向上自动换行，避免水平滚动轴出现
 * 
 * @author https://blog.csdn.net/u011121525/article/details/9865853
 *
 */
public class ModifiedFlowLayout extends FlowLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3306789701887265753L;

	public ModifiedFlowLayout() {
		super();
	}

	public ModifiedFlowLayout(int align) {
		super(align);
	}

	public ModifiedFlowLayout(int align, int hgap, int vgap) {
		super(align, hgap, vgap);
	}

	public Dimension minimumLayoutSize(Container target) {
		return computeSize(target, true);
	}

	public Dimension preferredLayoutSize(Container target) {
		return computeSize(target, false);
	}

	private Dimension computeSize(Container target, boolean minimum) {
		synchronized (target.getTreeLock()) {
			int hgap = getHgap();
			int vgap = getVgap();
			int w = target.getWidth();

			if (w == 0) {
				w = Integer.MAX_VALUE;
			}

			Insets insets = target.getInsets();
			if (insets == null) {
				insets = new Insets(0, 0, 0, 0);
			}
			int reqdWidth = 0;

			int maxwidth = w - (insets.left + insets.right + hgap * 2);
			int n = target.getComponentCount();
			int x = 0;
			int y = insets.top;
			int rowHeight = 0;

			for (int i = 0; i < n; i++) {
				Component c = target.getComponent(i);
				if (c.isVisible()) {
					Dimension d = minimum ? c.getMinimumSize() : c.getPreferredSize();
					if ((x == 0) || ((x + d.width) <= maxwidth)) {
						if (x > 0) {
							x += hgap;
						}
						x += d.width;
						rowHeight = Math.max(rowHeight, d.height);
					} else {
						x = d.width;
						y += vgap + rowHeight;
						rowHeight = d.height;
					}
					reqdWidth = Math.max(reqdWidth, x);
				}
			}
			y += rowHeight;
			return new Dimension(reqdWidth + insets.left + insets.right, y);
		}
	}
}
