package mygui.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import bean.Message;
import bean.Users;
import util.VerticalFlowLayout;

/**
 * 颜色设计来自于https://www.runoob.com/tags/html-colorpicker.html
 * 
 * @author xuxin
 *
 */
/**
 * @author xuxin
 *
 */
public class Chat {

	private JFrame frame;
	private JPanel contentPane;
	private JLabel lbl_images = new JLabel();
	private JLabel lbl_information = new JLabel();

	private Icon icon = new ImageIcon(Chat.class.getResource("/Images/223209_3.jpg"));// 图片地址
	private String title = new String("[好友昵称]");
	private String sign = new String("好友信息");
	private JPanel panel_liaotian;
	private JTextArea textArea_shuru;

	private GridBagConstraints gbc_liaotianjilu = new GridBagConstraints();
	private int messageCount = 3;
	private JProgressBar progressBar;
	
	private Users thisUser = null;
	private Users targetUser = null;
	private ChatThread chatThread;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Chat window = new Chat();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 无参构造，仅用作测试启动
	 */
	public Chat() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * @param thisUser
	 * @param targetUser
	 * @param out
	 * @param in
	 */
	public Chat(Users thisUser, Users targetUser, PrintWriter out, BufferedReader in) {
		this.thisUser = thisUser;
		this.targetUser = targetUser;
		initialize();
		frame.setVisible(true);
		chatThread = new ChatThread(this, out, in);
		chatThread.start();
	}

	/**
	 * 初始化界面
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle(title);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Chat.class.getResource("/Images/223209_3.jpg")));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 630, 460);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		splitPane.setDividerSize(3);
		splitPane.setDividerLocation(280);
		splitPane.setOneTouchExpandable(true);
		splitPane.setBackground(Color.WHITE);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		// 聊天输入框区域
		JPanel jp_right = new JPanel();
		jp_right.setBackground(Color.WHITE);
		splitPane.setRightComponent(jp_right);
		jp_right.setLayout(new BorderLayout(0, 0));

		JPanel jp_gongneng_shang = new JPanel();
		jp_gongneng_shang.setBackground(Color.WHITE);
		jp_right.add(jp_gongneng_shang, BorderLayout.NORTH);
		jp_gongneng_shang.setLayout(new BorderLayout(0, 0));

		JPanel panel_gongneng = new JPanel();
		panel_gongneng.setBackground(Color.WHITE);
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
		panel_lishi.setBackground(Color.WHITE);
		panel_lishi.setBorder(new EmptyBorder(0, 0, 0, 5));
		jp_gongneng_shang.add(panel_lishi, BorderLayout.EAST);
		panel_lishi.setLayout(new BorderLayout(0, 0));

		JLabel btn_lishi = new JLabel();
		btn_lishi.setIcon(new ImageIcon(Chat.class.getResource("/Images/msgmgr.png")));
		panel_lishi.add(btn_lishi);

		// 中
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		jp_right.add(scrollPane_1, BorderLayout.CENTER);

		textArea_shuru = new JTextArea();
		textArea_shuru.setBorder(new EmptyBorder(5, 5, 5, 5));
		textArea_shuru.setLineWrap(true);
		textArea_shuru.setWrapStyleWord(true);
		scrollPane_1.setViewportView(textArea_shuru);

		// 下
		JPanel jp_gongneng_xia = new JPanel();
		jp_gongneng_xia.setBorder(null);
		jp_gongneng_xia.setBackground(Color.WHITE);
		FlowLayout flowLayout_1 = (FlowLayout) jp_gongneng_xia.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		jp_right.add(jp_gongneng_xia, BorderLayout.SOUTH);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		jp_gongneng_xia.add(progressBar);

		JButton btn_send = new JButton("发送");
		btn_send.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		btn_send.setForeground(Color.WHITE);
		btn_send.setBackground(new Color(51, 153, 255));
		jp_gongneng_xia.add(btn_send);
		btn_send.addActionListener(new SendMessage());

		// 聊天记录
		JPanel jp_left = new JPanel();
		splitPane.setLeftComponent(jp_left);
		jp_left.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		jp_left.add(scrollPane, BorderLayout.CENTER);

		panel_liaotian = new JPanel();
		panel_liaotian.setBackground(Color.WHITE);
		scrollPane.setViewportView(panel_liaotian);
		GridBagLayout gbl_panel_liaotian = new GridBagLayout();
		gbl_panel_liaotian.columnWidths = new int[] { 0 };
		gbl_panel_liaotian.rowHeights = new int[] { 0 };
		gbl_panel_liaotian.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panel_liaotian.rowWeights = new double[] { Double.MIN_VALUE };
		panel_liaotian.setLayout(gbl_panel_liaotian);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 0, 1, 0, (Color) Color.GRAY));
		panel_1.setBackground(Color.WHITE);
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);
		scrollPane.setColumnHeaderView(panel_1);

		JLabel lblNewLabel_1 = new JLabel("聊天");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("公告");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("相册");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("文件");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("活动");
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("设置");
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_6);

		JComboBox comboBox = new JComboBox();
		comboBox.setBorder(new EmptyBorder(0, 0, 0, 0));
		comboBox.setFocusable(false);
		comboBox.setBackground(Color.WHITE);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "设置", "查看群资料", "更新群信息" }));
		comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_1.add(comboBox);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setPreferredSize(new Dimension(110, contentPane.getHeight()));
		scrollPane_2.setBorder(new MatteBorder(1, 0, 0, 1, (Color) Color.GRAY));
		contentPane.add(scrollPane_2, BorderLayout.WEST);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 153, 255));
		scrollPane_2.setColumnHeaderView(panel);

		JLabel lblNewLabel = new JLabel("消息列表");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel.add(lblNewLabel);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(51, 153, 255));
		scrollPane_2.setViewportView(panel_2);

		VerticalFlowLayout verticalFlowLayout = new VerticalFlowLayout();
		verticalFlowLayout.setHfill(true);
		verticalFlowLayout.setAlignment(VerticalFlowLayout.TOP);
		verticalFlowLayout.setHgap(0);
		panel_2.setLayout(verticalFlowLayout);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(217, 217, 217, 130));
		panel_2.add(panel_3);

		JLabel lblNewLabel_7 = new JLabel("New label");
		lblNewLabel_7.setForeground(Color.WHITE);
		panel_3.add(lblNewLabel_7);

		// 创建两块面板分别放好友基本信息、 聊天输入记录具体实现
		JPanel friend_information = new JPanel();
		panel_2.add(friend_information);
		friend_information.setBackground(new Color(217, 217, 217, 130));
		friend_information.setBorder(new EmptyBorder(0, 0, 5, 0));
		// jp1.setBounds(new Rectangle(0, 0, 470, 25));
		friend_information.setSize(470, 25);
		friend_information.setLayout(new BorderLayout(0, 0));

		ImageIcon image = new ImageIcon(getClass().getResource("/Images/1.png"));
		image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		lbl_images.setIcon(image);// 设置好友头像
		lbl_images.setPreferredSize(new Dimension(30, 30));
		friend_information.add(lbl_images, BorderLayout.WEST);

		lbl_information.setForeground(Color.WHITE);
		lbl_information.setText(sign);// 设置签名
		friend_information.add(lbl_information);
	}

	private class SendMessage implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			String text = textArea_shuru.getText();
			addMessagetoPanel(true, thisUser.getName(), text);
			Message msg = new Message();
			msg.setType("talk");
			msg.setSelfUser(thisUser);
			msg.setTargetUser(targetUser);
			msg.setSelfId(thisUser.getId());
			msg.setSelfName(thisUser.getName());
			msg.setTargetId(targetUser.getId());
			msg.setTargetName(targetUser.getName());
			msg.setText(text);
			chatThread.sendMessage(msg);
		}
	}

	protected void addMessagetoPanel(boolean isSelf, String name ,String text) {
		gbc_liaotianjilu.gridx = 0;
		gbc_liaotianjilu.gridy = messageCount++;
		gbc_liaotianjilu.fill = GridBagConstraints.BOTH;

		BubblePanelBorder bub = new BubblePanelBorder(isSelf, name ,text);

		panel_liaotian.add(bub, gbc_liaotianjilu);

		bub.setPreferredSize(new Dimension(0, bub.getPanelHight(panel_liaotian.getWidth())));

		panel_liaotian.updateUI();
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
