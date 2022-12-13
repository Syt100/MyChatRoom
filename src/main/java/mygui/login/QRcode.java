package mygui.login;

import util.QRCodeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class QRcode extends JPanel {

    public final JButton btn_return = new JButton("返回");
    private final JLabel lbl_qrcode = new JLabel("");
    private final JLabel lbl_tips = new JLabel("");
    private ImageIcon img_qrcode = new ImageIcon();

    public static void main(String[] arg) {
        JFrame frame = new JFrame();
        frame.setContentPane(new QRcode());
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Create the panel.
     */
    public QRcode() {

        setOpaque(false);
        setSize(430, 281);
        img_qrcode = createQRCode("https://www.baidu.com/");
        setLayout(null);

        //lbl_qrcode.setIcon(new ImageIcon(QRcode.class.getResource("/Images/verificationcode/jam.jpg")));
        lbl_qrcode.setIcon(img_qrcode);
        lbl_qrcode.setBounds(140, 20, 150, 150);
        add(lbl_qrcode);
        lbl_qrcode.addMouseListener(new FlushQRCodeMouseListener());
        btn_return.setFont(new Font("微软雅黑", Font.PLAIN, 18));

        btn_return.setBounds(95, 221, 240, 35);
        add(btn_return);
        lbl_tips.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        lbl_tips.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_tips.setText("点击二维码即可刷新");
        lbl_tips.setLocation(65, 186);
        lbl_tips.setSize(300, 25);
        add(lbl_tips);
    }

    /**
     * 鼠标点击事件监听，刷新二维码
     *
     * @author xuxin
     */
    private class FlushQRCodeMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            // 刷新图片
            int random = new Random().nextInt(99999999);
            img_qrcode = createQRCode("https://www.baidu.com/s?wd=" + random);
            System.out.println("https://www.baidu.com/s?wd=" + random);
            lbl_qrcode.setIcon(img_qrcode);
        }
    }

    /**
     * 根据传入的字符串产生一个二维码
     *
     * @param content 存放在二维码中的内容
     * @return imageIcon 返回一个这样子的对象
     */
    private ImageIcon createQRCode(String content) {
        try {
            BufferedImage bi = QRCodeUtil.encode(content);
			return new ImageIcon(bi);
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }
}
