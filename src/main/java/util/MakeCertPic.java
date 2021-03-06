package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
 
/**
 * 产生验证码图片，并保存在./rec/Images/Verification Code/验证码.jpg
 * @author 
 * 版权声明：本文为CSDN博主「xinweimingtian」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/whm114336793/article/details/52381843
 *
 */
public class MakeCertPic {
 
	/**存储随机产生的验证码*/
	private String str;
	
	/** 验证码图片中可以出现的字符集*/
	private char mapTable[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9' };
	
	/**
	 * 生产彩色验证码图片
	 * @param 	width 生产图片宽度
	 * @param 	height 生产图片高度
	 * @param 	os 页面输出流
	 * @return	image 随机生产的验证码BufferedImage
	 */
	public BufferedImage getCertPic(int width, int height, OutputStream os){
		//设定高度宽度默认值
		if(width <= 0){
			width = 60;
		}
		if(height <= 0){
			height = 20;
		}
		//创建一个特定样式的BufferedImage
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//获取图形上下文
		Graphics g = image.getGraphics();
		//设定背景色
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);
		//画边框
		g.setColor(Color.black);
		g.drawRect(0, 0, width-1, height-1);
		//取随机产生的认证码
		String strEnsure = "";
		//4代表4位验证码，如果要生产更多位的认证码，则加大数值
		for(int i = 0; i < 4; i++){
			strEnsure += mapTable[(int) (mapTable.length * Math.random())];
		}
		//将验证码显示到图像中，如果要生产更多位的验证码，增加drawString语句
		g.setColor(Color.black);
		g.setFont(new Font("Atlantic Inline", Font.PLAIN, 18));
		String str = strEnsure.substring(0, 1);
		g.drawString(str, 8, 17);
		str = strEnsure.substring(1, 2);
		g.drawString(str, 20, 15);
		str = strEnsure.substring(2, 3);
		g.drawString(str, 35, 18);
		str = strEnsure.substring(3, 4);
		g.drawString(str, 45, 15);
		//随机产生10个干扰点,产生多个修改数量10
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			g.drawOval(x, y, 1, 1);
		}
		//释放图形上下文
		g.dispose();
		if (null != os) {
			// 写入图片
			try {
				ImageIO.write(image, "JPEG", os);
			} catch (IOException e) {
				return null;
			} 
		}
		this.str = strEnsure;
		return image;
	}
	
	/**
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @return ImageIcon 图片
	 */
	public ImageIcon getCertPic(int width, int height) {
		BufferedImage image = getCertPic(width, height, null);
		ImageIcon icon = new ImageIcon(image);
		return icon;
	}
	
	public MakeCertPic() {
		
	}
	
	public String getStr() {
		return str;
	}
	
	/**
	 * 重新产生一个验证码
	 * @return 产生的随机验证码
	 */
	public String reMake() {
		try {
			FileOutputStream fos = new FileOutputStream("./rec/Images/verificationcode/验证码.jpg");
			getCertPic(60, 25, fos);
			System.out.println(str);
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static void main(String[] arg) {
		new MakeCertPic();
	}
}

