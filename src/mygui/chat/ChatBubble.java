package mygui.chat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import mygui.friendslist2.MyListCellRenderer;

import javax.swing.JList;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;


public class ChatBubble extends JPanel {

	JList<BubbleNode> list = new JList<BubbleNode>();
	// private DefaultListModel<BubbleNode> dlm = new
	// DefaultListModel<BubbleNode>();
	private BubbleListModel dlm = new BubbleListModel();
	ListCellRenderer1 render = new ListCellRenderer1();
	private JTextField textField;
	JTextPane textPane = new JTextPane();
	
	private int count = 3;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		ChatBubble chatBubble = new ChatBubble();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		frame.setContentPane(chatBubble);
		// frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Create the panel.
	 */
	public ChatBubble() {

		JScrollPane scrollPane = new JScrollPane();

		JPanel panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);

		JLabel lblNewLabel = new JLabel("消息列表");
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		scrollPane.setRowHeaderView(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent e) {
				System.out.println("componentAdded");
			}
		});
		scrollPane.setViewportView(panel_2);
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] {0, 0};
		gbl.rowHeights = new int[] {0, 0};
		gbl.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl.rowWeights = new double[]{1.0};
		panel_2.setLayout(gbl);
		
		

		// panel_2.add(list);

		JPanel panel_3[] = new JPanel[20];
		JLabel lbl[] = new JLabel[20];
		
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 81, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 23, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblNewLabel_1 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);

		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		JButton btnNewButton = new JButton("添加消息");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gbc_lblNewLabel_2.gridx = 0;
				gbc_lblNewLabel_2.gridy = count++;
				gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
				
				
				String text = textField.getText();
//				StyledDocument doc = textPane.getStyledDocument();
//				SimpleAttributeSet attrset = new SimpleAttributeSet();
				BubblePanelBorder bub = new BubblePanelBorder("我是一条消息：" + text + e.getWhen());
				// bub.setMinimumSize(new Dimension(textPane.getWidth(), 70));
				
				panel_2.add(bub, gbc_lblNewLabel_2);
				bub.setPreferredSize(new Dimension(0, bub.getPanelHight(panel_2.getWidth())));
				JScrollBar sbar = scrollPane.getVerticalScrollBar();
//				panel_2.repaint();
//				panel_2.revalidate();
				panel_2.updateUI();
//				bub.validate();
//				bub.repaint();
//				bub.revalidate();
//				bub.updateUI();
//				bub.invalidate();
				if(sbar != null) {
					sbar.setValue(sbar.getMaximum());
				}
//				try {
//					textPane.insertComponent(bub);
//					doc.insertString(1, "\n", attrset);
//					// textPane.setCaretPosition(i);
//				} catch (BadLocationException e1) {
//					// TODO 自动生成的 catch 块
//					e1.printStackTrace();
//				}

			}
		});
		setLayout(new BorderLayout(0, 0));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		panel_1.add(btnNewButton, gbc_btnNewButton);
		add(scrollPane);

		// panel_2.add(textPane);

		// StyleConstants.setFontSize(attrset,24);
//		try {
//			for (int i = 0; i < 15; i++) {
//				StyledDocument doc = textPane.getStyledDocument();
//				SimpleAttributeSet attrset = new SimpleAttributeSet();
//				BubblePanelBorder bub = new BubblePanelBorder();
//				// bub.setPreferredSize(new Dimension(textPane.getWidth(), 60));
//				textPane.insertComponent(bub);
//
//				doc.insertString(1, "\n", attrset);
//
//				// textPane.setCaretPosition(i);
//			}
//		} catch (BadLocationException e1) {
//			// TODO 自动生成的 catch 块
//			e1.printStackTrace();
//		}
//		bn1.contentPane.setPreferredSize(new Dimension(panel_2.getWidth(), 22));
//		
//		panel_2.add(bn1.contentPane);
//		panel_2.add(bn2.contentPane);

		// 创建 弹性布局管理器 和 容器，容器 使用 弹性布局

//		dlm.addElement(bn1);
//		dlm.addElement(bn2);
//		
//		list.setModel(dlm);
//		list.setCellRenderer(render);

		//scrollPane.setViewportView(textPane);

	}

}
