/**
 * 
 */
package test;

import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * @author xuxin
 *
 */
public class ShadowLightBorder {
	private static final Color clrHi = new Color(255, 229, 63);
	private static final Color clrLo = new Color(255, 105, 0);
	private static final Color clrGlowInnerHi = new Color(253, 239, 175, 148);
	private static final Color clrGlowInnerLo = new Color(255, 209, 0);
	private static final Color clrGlowOuterHi = new Color(253, 239, 175, 124);
	private static final Color clrGlowOuterLo = new Color(255, 179, 0);
	
	int width = 12;
	int height = 20;
	Graphics g;
	private Shape createClipShape() {
		float border = 20.0f;
		float x1 = border;
		float y1 = border;
		float x2 = width - border;
		float y2 = height - border;
		float adj = 3.0f; //帮助圆化类锐的拐角
		float arc = 8.0f;
		float dcx = 0.18f * width;
		float cx1 = x1-dcx;
		float cy1 = 0.40f * height;
		float cx2 = x1+dcx;
		float cy2 = 0.50f * height;
		GeneralPath gp = new GeneralPath();
		gp.moveTo(x1-adj, y1+adj);
		gp.quadTo(x1, y1, x1+adj, y1);
		gp.lineTo(x2-arc, y1);
		gp.quadTo(x2, y1, x2, y1+arc);
		gp.lineTo(x2, y2-arc);
		gp.quadTo(x2, y2, x2-arc, y2);
		gp.lineTo(x1+adj, y2);
		gp.quadTo(x1, y2, x1, y2-adj);
		gp.curveTo(cx2, cy2, cx1, cy1, x1-adj, y1+adj);
		gp.closePath();
		return gp;
	}
	/*private BufferedImage createClipImage(Shape s) {
		//创建一半透明的中间图像，我们可以使用它来实现软修剪效果
		GraphicsConfiguration gc = g.getDeviceConfiguration();
		BufferedImage img = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		Graphics2D g2 = img.createGraphics();
		//清除图像，这样所有的像素都具有零alpha
		g2.setComposite(AlphaComposite.Clear);
		g2.fillRect(0, 0, width, height);
		// 把我们的修剪形状生成到图像上。注意，我们启动了
		// 反走样功能以实现软修剪效果。你可以
		//尝试注释掉启动反走样的这一行，那么
		//你会看到通常的生硬的修剪效果.
		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fill(s);
		g2.dispose();
		return img;
	}
	private static Color getMixedColor(Color c1, float pct1, Color c2, float pct2) {
		float[] clr1 = c1.getComponents(null);
		float[] clr2 = c2.getComponents(null);
		for (int i = 0; i < clr1.length; i++) {
			clr1[i] = (clr1[i] * pct1) + (clr2[i] * pct2);
		}
		return new Color(clr1[0], clr1[1], clr1[2], clr1[3]);
	}
//下面是实现技巧：为了实现发光效果，我们开始使用一种"内部"颜色粗笔
//和笔划需要的形状。然后，我们不断地把笔变细，
//并且不断地移向"外部"颜色，
//并且不断地提高颜色的不透明度以便使其朝向形状的内部看上去暗淡。
//我们使用已经生成到我们的目的图像上的"修剪形状"，这样以来，
//SRC_ATOP规则就会修剪在我们的形状外部的笔划部分。
	private void paintBorderGlow(Graphics2D g2, int glowWidth) {
		int gw = glowWidth*2;
		for (int i=gw; i >= 2; i-=2) {
			float pct = (float)(gw - i) / (gw - 1);
			Color mixHi = getMixedColor(clrGlowInnerHi, pct,clrGlowOuterHi, 1.0f - pct);
			Color mixLo = getMixedColor(clrGlowInnerLo, pct,clrGlowOuterLo, 1.0f - pct);
			g2.setPaint(new GradientPaint(0.0f, height*0.25f, mixHi,0.0f, height, mixLo));
			//g2.setColor(Color.WHITE);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, pct));
			g2.setStroke(new BasicStroke(i));
			g2.draw(clipShape);
		}
	}
	Shape clipShape = createClipShape();
	//Shape clipShape = new Ellipse2D.Float(width/4, height/4, width/2, height/2);
	//把背景清除为白色
	g.setColor(Color.WHITE);
	g.fillRect(0, 0, width, height);
	//设置修剪形状
	BufferedImage clipImage = createClipImage(clipShape);
	Graphics2D g2 = clipImage.createGraphics();
	//使用渐变填充形状
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setComposite(AlphaComposite.SrcAtop);g2.setPaint(new GradientPaint(0, 0, clrHi, 0, height, clrLo));
	g2.fill(clipShape);
	//应用边界发光效果
	paintBorderGlow(g2,8);
	g2.dispose();
	g.drawImage(clipImage, 0, 0, null);*/
}
