package mygui.chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.Icon;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import java.awt.Toolkit;

public class Chat {

	private JFrame frame;
	private JPanel contentPane;
	private JLabel lbl_images = new JLabel();
	private JLabel lbl_information = new JLabel();

	private Icon icon = new ImageIcon(Chat.class.getResource("/Images/223209_3.jpg"));// 图片地址
	private String title = new String("[\u597D\u53CB\u6635\u79F0]");
	private String sign = new String("\u597D\u53CB\u4FE1\u606F");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat window = new Chat();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Chat() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle(title);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Chat.class.getResource("/Images/223209_3.jpg")));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 630, 460);

		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Panel.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// 创建两块面板分别放好友基本信息、 聊天输入记录具体实现
		JPanel friend_information = new JPanel();
		friend_information.setBorder(new EmptyBorder(0, 0, 5, 0));
		// jp1.setBounds(new Rectangle(0, 0, 470, 25));
		friend_information.setSize(470, 25);

		contentPane.add(friend_information, BorderLayout.NORTH);
		friend_information.setLayout(new BorderLayout(0, 0));

		lbl_images.setIcon(icon);// 设置好友头像
		lbl_images.setSize(new Dimension(75, 75));
		friend_information.add(lbl_images, BorderLayout.WEST);

		lbl_information.setText(sign);// 设置签名
		friend_information.add(lbl_information);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		// jp2.add(splitPane);
		splitPane.setDividerLocation(180);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		// 聊天输入框区域
		JPanel jp_right = new JPanel();
		splitPane.setRightComponent(jp_right);
		jp_right.setLayout(new BorderLayout(0, 0));

		JPanel jp_gongneng_shang = new JPanel();
		jp_right.add(jp_gongneng_shang, BorderLayout.NORTH);
		jp_gongneng_shang.setLayout(new BorderLayout(0, 0));

		JPanel panel_gongneng = new JPanel();
		jp_gongneng_shang.add(panel_gongneng, BorderLayout.WEST);
		// 输入框功能上
		JButton btn_biaoqing = new JButton("表情");
		panel_gongneng.add(btn_biaoqing);
		btn_biaoqing.setToolTipText("这是一个功能按钮");

		JButton btn_wenjian = new JButton("文件");
		panel_gongneng.add(btn_wenjian);

		JButton btn_tupian = new JButton("图片");
		panel_gongneng.add(btn_tupian);

		JPanel panel_lishi = new JPanel();
		panel_lishi.setBorder(new EmptyBorder(0, 0, 0, 5));
		jp_gongneng_shang.add(panel_lishi, BorderLayout.EAST);
		panel_lishi.setLayout(new BorderLayout(0, 0));
		
		JLabel btn_lishi = new JLabel();
		btn_lishi.setIcon(new ImageIcon(Chat.class.getResource("/Images/msgmgr.png")));
		
		panel_lishi.add(btn_lishi);
		// 中
		JScrollPane scrollPane_1 = new JScrollPane();
		jp_right.add(scrollPane_1, BorderLayout.CENTER);

		JTextArea textArea_shuru = new JTextArea();
		scrollPane_1.setViewportView(textArea_shuru);

		// 下
		JPanel jp_gongneng_xia = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) jp_gongneng_xia.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		jp_right.add(jp_gongneng_xia, BorderLayout.SOUTH);

		JButton btn_send = new JButton("发送");
		jp_gongneng_xia.add(btn_send);

		// 聊天记录
		JPanel jp_left = new JPanel();
		splitPane.setLeftComponent(jp_left);
		jp_left.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		jp_left.add(scrollPane, BorderLayout.CENTER);

		JTextArea textArea_xiaoxijilu = new JTextArea();
		scrollPane.setViewportView(textArea_xiaoxijilu);
	}

	/**
	 * 设置窗口是否可见
	 * 
	 * @param t
	 */
	public void setFrameVisiable(boolean t) {
		frame.setVisible(t);
	}

	/**
	 * @return icon
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * @param icon 要设置的 icon
	 */
	public void setIcon(Icon icon) {
		this.icon = icon;
		lbl_images.setIcon(icon);// 设置好友头像
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title 要设置的 title
	 */
	public void setTitle(String title) {
		this.title = title;
		frame.setTitle(title);
	}

	/**
	 * @return sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign 要设置的 sign
	 */
	public void setSign(String sign) {
		this.sign = sign;
		lbl_information.setText(sign);// 设置签名
	}

}
