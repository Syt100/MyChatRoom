package mygui.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import bean.Message;
import bean.Users;
import util.VerticalFlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 聊天窗口
 * 颜色设计来自于https://www.runoob.com/tags/html-colorpicker.html
 * 
 * @author xuxin
 *
 */
public class Chat {
	
	/** 窗口的框架 */
	private JFrame frame;
	/** 框架的内容面板 */
	private JPanel contentPane;
	/** 放置左侧的消息列表中的好友头像 */
	private JLabel lbl_images = new JLabel();
	/** 左侧的消息列表中的好友信息 */
	private JLabel lbl_information = new JLabel();
	/** 默认的左侧的消息列表中的好友头像 */
	private Icon icon = new ImageIcon(Chat.class.getResource("/Images/223209_3.jpg"));
	
	/** 窗口的标题 */
	private String title = new String("[好友昵称]");
	/** 默认的左侧的消息列表中的好友信息 */
	private String sign = new String("好友信息");
	
	/** 聊天消息记录的滚动面板 */
	private JScrollPane scrollPane;
	/** 显示聊天消息记录的面板 */
	private JPanel panel_liaotian;
	/** 输入消息的输入框 */
	private JTextArea textArea_shuru;

	/** 布局约束，用于聊天记录面板上显示单个聊天气泡 */
	private GridBagConstraints gbc_liaotianjilu = new GridBagConstraints();
	/** 确保聊天气泡是从下面开始显示，而不是上面 */
	private int messageCount = 3;
	/** 进度条，为发送文件预留的 */
	private JProgressBar progressBar;
	
	/** 当前用户 */
	private Users thisUser = null;
	/** 与当前用户聊天的用户 */
	private Users targetUser = null;
	
	/** 聊天窗口的接收服务端消息的线程 */
	private ChatThread chatThread;
	
	/** 是否启用群聊模式 */
	private boolean isGroupChat = false;
	
	
	public static void main(String[] args) {
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Chat();
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
	public Chat(Users thisUser, Users targetUser, PrintWriter out, boolean isGroupChat) {
		this.thisUser = thisUser;
		this.targetUser = targetUser;
		this.isGroupChat = isGroupChat;
		initialize();
		frame.setVisible(true);
		chatThread = new ChatThread(this, out);
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
		frame.addWindowListener(new CloseWindow());// 窗口被关闭，通知线程停止接收消息

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

		scrollPane = new JScrollPane();
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

		JPanel panel_dinglan = new JPanel();
		panel_dinglan.setBorder(new MatteBorder(1, 0, 1, 0, (Color) Color.GRAY));
		panel_dinglan.setBackground(Color.WHITE);
		FlowLayout fl_panel_dinglan = (FlowLayout) panel_dinglan.getLayout();
		fl_panel_dinglan.setHgap(10);
		fl_panel_dinglan.setAlignment(FlowLayout.LEFT);
		scrollPane.setColumnHeaderView(panel_dinglan);

		JLabel lblNewLabel_1 = new JLabel("聊天");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_dinglan.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("公告");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_dinglan.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("相册");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_dinglan.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("文件");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_dinglan.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("活动");
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_dinglan.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("设置");
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_dinglan.add(lblNewLabel_6);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBorder(new EmptyBorder(0, 0, 0, 0));
		comboBox.setFocusable(false);
		comboBox.setBackground(Color.WHITE);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "设置", "查看群资料", "更新群信息" }));
		comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_dinglan.add(comboBox);

		JScrollPane scrollPane_xiaoxi = new JScrollPane();
		scrollPane_xiaoxi.setPreferredSize(new Dimension(110, contentPane.getHeight()));
		scrollPane_xiaoxi.setBorder(new MatteBorder(1, 0, 0, 1, (Color) Color.GRAY));
		contentPane.add(scrollPane_xiaoxi, BorderLayout.WEST);

		JPanel panel_xiaoxiliebiao = new JPanel();
		panel_xiaoxiliebiao.setBackground(new Color(51, 153, 255));
		scrollPane_xiaoxi.setColumnHeaderView(panel_xiaoxiliebiao);

		JLabel lblNewLabel = new JLabel("消息列表");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		panel_xiaoxiliebiao.add(lblNewLabel);

		JPanel panel_flow = new JPanel();
		panel_flow.setBackground(new Color(51, 153, 255));
		scrollPane_xiaoxi.setViewportView(panel_flow);

		VerticalFlowLayout vfl_panel_flow = new VerticalFlowLayout();
		vfl_panel_flow.setHfill(true);
		vfl_panel_flow.setAlignment(VerticalFlowLayout.TOP);
		vfl_panel_flow.setHgap(0);
		panel_flow.setLayout(vfl_panel_flow);

		JPanel panel_test = new JPanel();
		panel_test.setBackground(new Color(217, 217, 217, 130));
		panel_flow.add(panel_test);

		JLabel lblNewLabel_7 = new JLabel("测试多个好友");
		lblNewLabel_7.setForeground(Color.WHITE);
		panel_test.add(lblNewLabel_7);

		// 创建两块面板分别放好友基本信息、 聊天输入记录具体实现
		JPanel friend_information = new JPanel();
		panel_flow.add(friend_information);
		friend_information.setBackground(new Color(217, 217, 217, 130));
		friend_information.setBorder(new EmptyBorder(0, 0, 5, 0));
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
	
	/**
	 * 窗口关闭事件监听
	 * @author xuxin
	 *
	 */
	private class CloseWindow extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			if (chatThread != null) {
				// 窗口关闭，断开连接
				chatThread.close();
			}
		}
	}

	/**
	 * 点击发送按钮的监听事件
	 * @author xuxin
	 *
	 */
	private class SendMessage implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String text = textArea_shuru.getText();
			addMessagetoPanel(true, thisUser.getName(), text);
			Message msg;
			if (isGroupChat == false) {
				msg = new Message();
				msg.setType("talk");
				msg.setSelfId(thisUser.getId());
				msg.setSelfName(thisUser.getName());
				msg.setTargetId(targetUser.getId());
				msg.setTargetName(targetUser.getName());
				msg.setText(text);
			} else {
				msg = new Message();
				msg.setType("GroupChat");
				msg.setSelfId(thisUser.getId());
				msg.setSelfName(thisUser.getName());
				msg.setTargetId(targetUser.getId());
				msg.setTargetName(targetUser.getName());
				msg.setText(text);
			}
			chatThread.sendMessage(msg);
		}
	}

	protected void addMessagetoPanel(boolean isSelf, String name ,String text) {
		gbc_liaotianjilu.gridx = 0;
		gbc_liaotianjilu.gridy = messageCount++;
		gbc_liaotianjilu.fill = GridBagConstraints.BOTH;

		ChatBubble bub = new ChatBubble(isSelf, name ,text);

		panel_liaotian.add(bub, gbc_liaotianjilu);

		bub.setPreferredSize(new Dimension(0, bub.getPanelHight(panel_liaotian.getWidth())));

		panel_liaotian.updateUI();
		JScrollBar sbar = scrollPane.getVerticalScrollBar();
		sbar.setValue(sbar.getMaximum() + bub.getPanelHight(panel_liaotian.getWidth()));

	}
	
	/**
	 * 聊天界面的网络、流处理线程，读取从服务端收到的消息，并显示在聊天界面上。
	 * @author xuxin
	 *
	 */
	private class ChatThread extends Thread {

		/** chat对象的引用 */
		private Chat chat;
		
		/** 接收从好友列表页面传来的out输出流 */
		private PrintWriter out;
//		/** 接收从好友列表页面传来的in输入流 */
//		private BufferedReader in;
		
		private PipedReader pipIn = new PipedReader();
		
		/** 是否已经关闭窗口 */
		private boolean isCloseed = true;
		

		/**
		 * @param chat
		 * @param out
		 * @param in
		 */
		public ChatThread(Chat chat, PrintWriter out) {
			this.chat = chat;
			this.out = out;
//			this.in = in;
//			this.pipIn = pipin;
		}

		@Override
		public void run() {
			try {
				while (isCloseed) {
					char ch[] = new char[1024];
					pipIn.read(ch);
					String received = new String(ch);
					System.out.println(getClass() + received);
					
					JSONObject jo = JSONObject.parseObject(received);
					String type = jo.getString("type");
					String selfId = jo.getString("selfId");
					String selfName = jo.getString("selfName");
					String text = jo.getString("text");
					
					if (type != null && type.equals("talk") && !chat.getThisUser().getId().equals(selfId)) {
						chat.addMessagetoPanel(false, selfName, text);
					}
					
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				return;
			}
		}
		
		protected void sendMessage(Message msg) {
			out.println(JSON.toJSONString(msg));
			out.flush();
		}
		
		protected void close() {
			isCloseed = false;
			try {
				pipIn.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			System.out.println("聊天窗口已关闭");
		}

		/**
		 * @return pipIn
		 */
		public PipedReader getPipIn() {
			return pipIn;
		}

	}

	public PipedReader getPipeIn() {
		return chatThread.getPipIn();
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
		if (isGroupChat == true) {
			frame.setTitle(title + "群聊模式");
		} else {
			frame.setTitle(title);
		}
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

	/**
	 * @return thisUser
	 */
	protected Users getThisUser() {
		return thisUser;
	}

}
