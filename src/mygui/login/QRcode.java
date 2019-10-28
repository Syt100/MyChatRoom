package mygui.login;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.QRCodeUtil;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class QRcode extends JPanel {
	
	public final JButton btn_return = new JButton("返回");
	private final JLabel lbl_qrcode = new JLabel("");
	private final JLabel lbl_tips = new JLabel("");

	/**
	 * Create the panel.
	 */
	public QRcode() {
		setOpaque(false);
		setSize(430, 281);
		createQRCode();
		setLayout(null);
		lbl_qrcode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO 刷新图片
			}
		});
		lbl_qrcode.setIcon(new ImageIcon(QRcode.class.getResource("/Images/verificationcode/jam.jpg")));
		lbl_qrcode.setBounds(140, 20, 150, 150);
		add(lbl_qrcode);
		
		btn_return.setBounds(95, 221, 240, 35);
		add(btn_return);
		
		lbl_tips.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_tips.setText("点击二维码即可刷新");
		lbl_tips.setLocation(65, 186);
		lbl_tips.setSize(300, 25);
		add(lbl_tips);
	}

	private void createQRCode() {
		try {
			// 存放在二维码中的内容
			String text = "我是小铭";
			// 嵌入二维码的图片路径
			//String imgPath = "./rec/dog.jpg";
			// 生成的二维码的路径及名称
			String destPath = "./rec/Images/verificationcode/jam.jpg";
			// 生成二维码
			QRCodeUtil.encode(text, null, destPath, true);
			// 解析二维码
			String str = QRCodeUtil.decode(destPath);
			// 打印出解析出的内容
			System.out.println(str);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

}
