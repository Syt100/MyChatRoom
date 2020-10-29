package mygui.chat;

import javax.swing.*;
import java.awt.*;

/**
 * 测试类，测试单个聊天气泡的效果
 * @author xuxin
 *
 */
public class SingleChatBubbleTest extends JPanel{
	
	//public JPanel contentPane = new JPanel();
	public JTextArea textArea;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2993719788312584989L;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(430, 150);
		frame.setLocationRelativeTo(null);
		SingleChatBubbleTest bubble = new SingleChatBubbleTest();
		frame.setContentPane(bubble);
		frame.setVisible(true);
	}

	/**
	 * Create the panel.
	 */
	public SingleChatBubbleTest() {
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		this.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setHgap(20);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_4.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("这是我的昵称");
		panel.add(lblNewLabel);
		
		ChatBubble panel_2 = new ChatBubble(true, "时跃天", "qwefcerggggggggggg");
		panel_4.add(panel_2);
//		panel_2.setOpaque(true);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setText("123");
		textArea.setLineWrap(true);
		//panel_2.add(textArea);
		
		JPanel panel_3 = new JPanel();
		this.add(panel_3, BorderLayout.EAST);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_1 = new JLabel("");
		//Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(getClass().getResource("/Images/qqIcon/head_bkg_shadow.png"));
		image = new ImageIcon(image.getImage().getScaledInstance(49, 49, Image.SCALE_SMOOTH));
		lblNewLabel_1.setIcon(image);
		panel_3.add(lblNewLabel_1);

	}

}
