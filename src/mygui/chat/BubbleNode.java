package mygui.chat;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

public class BubbleNode extends JPanel{
	
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
		BubbleNode bubble = new BubbleNode();
		frame.setContentPane(bubble);
		frame.setVisible(true);
	}

	/**
	 * Create the panel.
	 */
	public BubbleNode() {
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
		
		BubblePanelBorder panel_2 = new BubblePanelBorder(true, "时跃天", "qwefcerggggggggggg");
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
