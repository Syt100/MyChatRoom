package mygui.friendslist2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.tree.TreePath;

import com.alibaba.fastjson.JSONObject;

import bean.Users;
import mygui.chat.Chat;
import mygui.components.BackgroundJPanel;
import mygui.components.ResizeFrame;
import util.XMLOperation;

/**
 * 好友列表
 * @author xuxin
 *
 */
public class MyFriendsList3 {

	/** 窗体 */
	private ResizeFrame frame = new ResizeFrame();
	/** 底层面板 */
	private JPanel content;
	/** 最小化按钮 */
	private JLabel minButton = new JLabel();
	/** 退出按钮 */
	private JLabel exitButton = new JLabel();
	/** 皮肤按钮 */
	private JLabel skinButton = new JLabel();
	
	
	/** 窗口默认宽度 */
	private static final int DEFAULT_WIDTH = 260;
	/** 窗口默认高度 */
	private static final int DEFAULT_HEIGHT = 560;

	// 颜色部分
	/** 默认主题的颜色 */
	private Color themeColor = Color.WHITE;
	/** 默认的好友、消息、群聊三个标签没有被选中时的前景色 */
	private Color COLOR_TABLE_NOT_SELECTED = Color.GRAY;
	/** 默认的好友、消息、群聊三个标签被选中时的前景色 */
	private Color COLOR_TABLE_SELECTED = Color.BLACK;

	/** 默认的窗口背景图片 */
	private ImageIcon backGroundImgIcon = new ImageIcon(getClass().getResource("/Images/back03.jpg"));

	/** 窗口标题 */
	private static String title = new String(" 我的QQ");

	//将contentPane分为三大区域
	/** contentPane的上 */
	private final JPanel panel_top = new JPanel();
	/** contentPane的中 */
	private final JPanel panel_center = new JPanel();
	/** contentPane的下 */
	private final JPanel panel_down = new JPanel();

	/** 设置窗口显示的标题 */
	private final JLabel lbl_title = new JLabel();

	/** 用于显示右上角的关闭、最小化等按钮 */
	private final JPanel panel_close = new JPanel();

	/** 中间的两部分之一，用于显示头像、昵称、搜索框信息等 */
	private final JPanel panel_header = new JPanel();
	/** 用于显示好友信息、上方的切换标签等 */
	private final JPanel panel_friends = new JPanel();

	/** 头像 */
	private final JLabel lbl_header = new JLabel();

	/** 存放昵称，签名 */
	private final JPanel panel_name = new JPanel();
	/** 存放昵称 */
	private final JLabel lbl_name = new JLabel("");
	/** 存放签名 */
	private final JTextArea textArea_sign = new JTextArea();
	/** 天气面板 */
	private final JPanel panel_weather = new JPanel();
	/** 存放天气 */
	private final JLabel lbl_weather = new JLabel("");

	/** 搜索面板 */
	private final JPanel panel_search = new JPanel();
	/** 搜索图标 */
	private final JLabel lbl_search = new JLabel();
	/** 搜索输入框 */
	private final JTextField txtField = new JTextField();
	
	/** 用于切换最近聊天、好友列表、群聊 */
	private final JPanel panel_table = new JPanel();
	/** 存放最近聊天 */
	private final JPanel panel_tab1 = new JPanel();
	/** 存放好友列表 */
	private final JPanel panel_tab2 = new JPanel();
	/** 存放群聊列表 */
	private final JPanel panel_tab3 = new JPanel();
	
	/** 分层面板，用于切换上面的三个tab，用来显示最近聊天、好友列表、群聊 */
	private final JLayeredPane layeredPane = new JLayeredPane();
	
	private final JLabel lbl_tab1 = new JLabel("消息");
	private final JLabel lbl_tab2 = new JLabel("好友");
	private final JLabel lbl_tab3 = new JLabel("群聊");
	
	/** 显示好友列表、消息列表、群聊等 */
	private final JPanel panel_main = new JPanel();
	
	/** JTree的根节点 */
	private MyTreeNode root;
	/** JTree的自定义渲染器 */
	private MyTreeCellRenderer renderer = new MyTreeCellRenderer(this);
	/** 滚动面板 */
	private JScrollPane scrollPane = new JScrollPane();
	/** 显示好友列表的树 */
	private JTree tree;
	
	/** 底栏的面板，防置功能按钮，左边 */
	private final JPanel panel_down_left = new JPanel();
	/** 底栏的面板，防置功能按钮，右边 */
	private final JPanel panel_down_right = new JPanel();
	/** 底栏的面板，菜单面板 */
	private final JLabel lbl_menu = new JLabel();
	
	/** 是否群聊的开关 */
	private final JCheckBox chckbx_qunliao = new JCheckBox("开启群聊模式");
	
	// 用户
	/** 接收从客户端接收的Users类 */
	private Users user = null;
	
	// 网络、流
	/** 接收从登陆页面传来的socket */
	private Socket socket = null;
	/** 接收从登陆页面传来的out输出流 */
	private PrintWriter out = null;
	/** 接收从登陆页面传来的in输入流 */
	private BufferedReader in = null;
	
	// private PipedReader pipIn = new PipedReader();
	
	// 线程
	private ThreadGroup threadGroup;
	private MyFriendsListThread friThread;
	
	// 与聊天界面通信
	/** 所有已经打开的聊天窗口的引用 */
	private ArrayList<Chat> chatArray = new ArrayList<Chat>();
	

	public static void main(String[] args) {
		new MyFriendsList3();
	}

	/**
	 * 无参构造，仅用作测试启动
	 */
	public MyFriendsList3() {
		initBackGround();// 初始化窗口及背景
		initComponent();// 初始化窗口内组件
		initSetOpaque();// 设置组件透明
		initialize();// 初始化组件内容
		initJTreeNode();// 初始化JTree的节点，即好友列表
		initJtree();// 初始化JTree样式，使用自定义样式
		initColor();
		initListener();// 初始化窗口事件监听，实现窗口拖动、最小化、关闭
		frame.setVisible(true);
	}

	/**
	 * 由登录界面传入参数启动
	 * @param user
	 * @param socket
	 * @param out
	 * @param in
	 */
	public MyFriendsList3(Users user, Socket socket, PrintWriter out, BufferedReader in) {
		this.user = user;
		this.socket = socket;
		this.out = out;
		this.in = in;
		initBackGround();// 初始化窗口及背景
		initComponent();// 初始化窗口内组件
		initSetOpaque();// 设置组件透明
		initialize(this.user);// 初始化组件内容
		initJTreeNode(this.user);// 初始化JTree的节点，即好友列表
		initJtree();// 初始化JTree样式，使用自定义样式
		initColor();
		initListener();// 初始化窗口事件监听，实现窗口拖动、最小化、关闭
		frame.setVisible(true);
		
		threadGroup = new ThreadGroup("好友列表线程组");
		friThread = new MyFriendsListThread(threadGroup, "friend", this);
		friThread.start();
	}

	/**
	 * 初始化窗口及背景图片
	 */
	private void initBackGround() {
		frame.setBounds(100, 100, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		frame.setUndecorated(true);// 将原始的边框去掉
		frame.setLocationRelativeTo(null);// 设置窗口打开位置居中
		
		content = new BackgroundJPanel(backGroundImgIcon);// 初始化内容面板，设置背景
		content.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		content.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(content);
	}

	/**
	 * 初始化窗口内组件
	 */
	private void initComponent() {
		panel_top.setLayout(new BorderLayout(0, 0));
		content.add(panel_top, BorderLayout.NORTH);

		panel_top.add(lbl_title, BorderLayout.WEST);

		FlowLayout fl_panel_close = (FlowLayout) panel_close.getLayout();
		fl_panel_close.setAlignment(FlowLayout.RIGHT);
		fl_panel_close.setVgap(0);
		fl_panel_close.setHgap(0);
		panel_top.add(panel_close, BorderLayout.EAST);
		
		// 最小化、关闭、换肤
		minButton.setIcon(produceImage("minimize.png"));
		exitButton.setIcon(produceImage("close3.png"));
		skinButton.setIcon(produceImage("skin.png"));
		panel_close.add(skinButton);
		panel_close.add(minButton);
		panel_close.add(exitButton);
		
		/** 设置上左边框宽度为5 */
		panel_center.setBorder(new EmptyBorder(5, 0, 0, 0));
		panel_center.setLayout(new BorderLayout(0, 0));
		content.add(panel_center, BorderLayout.CENTER);

		panel_center.add(panel_header, BorderLayout.NORTH);
		panel_header.setLayout(new BorderLayout(10, 10));
		/** 设置头像离左边框的距离为5 */
		lbl_header.setBorder(new EmptyBorder(0, 5, 0, 0));
		panel_header.add(lbl_header, BorderLayout.WEST);

		panel_name.setLayout(new GridLayout(2, 1, 0, 0));
		panel_header.add(panel_name, BorderLayout.CENTER);
		
		lbl_name.setFont(new Font("黑体", Font.BOLD, 15));
		panel_name.add(lbl_name);

		textArea_sign.setEditable(false);
		textArea_sign.setRows(2);
		textArea_sign.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		textArea_sign.setLineWrap(true);
		textArea_sign.setOpaque(false);
		panel_name.add(textArea_sign);
		
		/** 设置天气标签距离右边界距离为15 */
		panel_weather.setBorder(new EmptyBorder(0, 0, 0, 15));
		panel_weather.setLayout(new BorderLayout(10, 10));
		panel_weather.add(lbl_weather);
		panel_header.add(panel_weather, BorderLayout.EAST);

		panel_search.setLayout(new BorderLayout(0, 0));
		panel_search.add(lbl_search, BorderLayout.WEST);
		panel_header.add(panel_search, BorderLayout.SOUTH);

		panel_search.add(txtField);

		panel_friends.setLayout(new BorderLayout(0, 0));
		panel_friends.add(panel_table, BorderLayout.NORTH);
		panel_center.add(panel_friends, BorderLayout.CENTER);

		panel_table.setLayout(new GridLayout(0, 3, 0, 0));
		panel_tab1.setBorder(new MatteBorder(0, 0, 3, 0, themeColor));
		panel_table.add(panel_tab1);
		lbl_tab1.setForeground(COLOR_TABLE_NOT_SELECTED);

		panel_tab1.add(lbl_tab1);

		/** 设置panel_tab2为花色边框，颜色 */
		panel_tab2.setBorder(new MatteBorder(0, 0, 3, 0, themeColor));
		panel_table.add(panel_tab2);
		lbl_tab2.setForeground(COLOR_TABLE_NOT_SELECTED);

		panel_tab2.add(lbl_tab2);

		/** 设置panel_tab3为花色边框，颜色 */
		panel_tab3.setBorder(new MatteBorder(0, 0, 3, 0, themeColor));
		panel_table.add(panel_tab3);
		lbl_tab3.setForeground(COLOR_TABLE_NOT_SELECTED);

		panel_tab3.add(lbl_tab3);
		
		panel_main.setLayout(new BorderLayout(0, 0));
		panel_main.add(layeredPane, BorderLayout.CENTER);
		panel_friends.add(panel_main, BorderLayout.CENTER);
		
		layeredPane.setLayer(panel_main, 1);
		layeredPane.setLayout(new BorderLayout(0, 0));

		layeredPane.add(scrollPane, BorderLayout.CENTER);

		content.add(panel_down, BorderLayout.SOUTH);

		panel_down.setLayout(new BorderLayout(0, 0));
		panel_down.add(panel_down_left, BorderLayout.WEST);
		panel_down.add(panel_down_right, BorderLayout.EAST);
		
		panel_down_right.add(chckbx_qunliao);
		panel_down_left.add(lbl_menu);

	}

	/**
	 * 初始化窗口内组件透明
	 */
	private void initSetOpaque() {
		panel_top.setOpaque(false);
		panel_center.setOpaque(false);
		panel_close.setOpaque(false);
		panel_header.setOpaque(false);
		panel_friends.setOpaque(false);
		panel_name.setOpaque(false);
		panel_weather.setOpaque(false);
		panel_search.setOpaque(false);
	}

	/**
	 * 初始化组件内容（默认头像昵称，调试用）
	 */
	private void initialize() {
		lbl_title.setText(title);
		lbl_header.setIcon(produceImage("223209_3.jpg"));
		lbl_name.setText("木木夕");
		lbl_weather.setText("晴");
		lbl_search.setIcon(toSuitableIcon(20, 20, "search_icon.png"));

		lbl_tab1.setFont(new Font("宋体", Font.PLAIN, 14));
		lbl_tab2.setFont(new Font("宋体", Font.PLAIN, 14));
		lbl_tab3.setFont(new Font("宋体", Font.PLAIN, 14));

		textArea_sign.setText("人生若只如初见，何事秋风悲画扇");

		txtField.addFocusListener(new JTextFieldHintListener(txtField, "搜索好友"));

		lbl_menu.setIcon(produceImage("menu.png"));
		lbl_menu.setPreferredSize(new Dimension(25, 20));
	}
	
	/**
	 * 初始化组件内容
	 */
	private void initialize(Users user) {
		lbl_title.setText(title);
		lbl_header.setIcon(produceImage("223209_3.jpg"));
		lbl_name.setText(user.getName() + "(" + user.getId() + ")");
		lbl_weather.setText("晴");
		lbl_search.setIcon(toSuitableIcon(20, 20, "search_icon.png"));

		lbl_tab1.setFont(new Font("宋体", Font.PLAIN, 14));
		lbl_tab2.setFont(new Font("宋体", Font.PLAIN, 14));
		lbl_tab3.setFont(new Font("宋体", Font.PLAIN, 14));

		textArea_sign.setText("人生若只如初见，何事秋风悲画扇");

		txtField.addFocusListener(new JTextFieldHintListener(txtField, "搜索好友"));

		lbl_menu.setIcon(produceImage("menu.png"));
		lbl_menu.setPreferredSize(new Dimension(25, 20));
	}

	/**
	 * 初始化JTree的节点，即好友列表（默认调试用）
	 */
	private void initJTreeNode() {
		Icon ico1 = new ImageIcon(getClass().getResource("/Images/1.png"));
		Icon ico2 = new ImageIcon(getClass().getResource("/Images/2.png"));
		Icon ico3 = new ImageIcon(getClass().getResource("/Images/3.png"));
		Icon ico4 = new ImageIcon(getClass().getResource("/Images/4.png"));

		root = new MyTreeNode();
		MyTreeNode node1 = new MyTreeNode("我的好友");
		MyTreeNode node2 = new MyTreeNode("自定义分组1");
		MyTreeNode node3 = new MyTreeNode("自定义分组2");

		MyTreeNode node1_1 = new MyTreeNode(ico2, "好友1", "人生若只如初见");
		MyTreeNode node1_2 = new MyTreeNode(ico2, "好友2", "人生若只如初见");
		MyTreeNode node1_3 = new MyTreeNode(ico2, "好友3", "人生若只如初见");
		MyTreeNode node1_4 = new MyTreeNode(ico2, "好友4", "人生若只如初见");

		MyTreeNode node2_1 = new MyTreeNode(ico3, "好友1", "人生若只如初见");
		MyTreeNode node2_2 = new MyTreeNode(ico3, "好友2", "人生若只如初见");
		MyTreeNode node2_3 = new MyTreeNode(ico3, "好友3", "人生若只如初见");

		MyTreeNode node3_1 = new MyTreeNode(ico4, "好友1", "人生若只如初见");
		MyTreeNode node3_2 = new MyTreeNode(ico4, "好友2", "人生若只如初见");
		MyTreeNode node3_3 = new MyTreeNode(ico4, "好友3", "人生若只如初见");

		root.add(node1);
		root.add(node2);
		root.add(node3);
		node1.add(node1_1);
		node1.add(node1_2);
		node1.add(node1_3);
		node1.add(node1_4);
		node2.add(node2_1);
		node2.add(node2_2);
		node2.add(node2_3);
		node3.add(node3_1);
		node3.add(node3_2);
		node3.add(node3_3);
	}
	
	/**
	 * 初始化JTree的节点，即好友列表（默认调试用）
	 */
	private void initJTreeNode(Users user) {
		root = new MyTreeNode();
		String[] friend = user.getFriends().split(",");// XML 那里用.分割会有问题
//		//TODO 此处可处理下没有好友的情况
//		if(friend.length == 0) {
//			return;
//		}
		
		Icon ico1 = new ImageIcon(getClass().getResource("/Images/1.png"));
		Icon ico2 = new ImageIcon(getClass().getResource("/Images/2.png"));
		Icon ico3 = new ImageIcon(getClass().getResource("/Images/3.png"));
		Icon ico4 = new ImageIcon(getClass().getResource("/Images/4.png"));
		
		XMLOperation xml = new XMLOperation();
		MyTreeNode node_main = new MyTreeNode("我的好友");
		root.add(node_main);
		MyTreeNode node[] = new MyTreeNode[friend.length];
		for (int i = 0; i < friend.length; i++) {
			Users friends = xml.getUsersById(friend[i]);
			System.out.println(friends.getName());
			node[i] = new MyTreeNode(ico1, friends.getName(), "人生若只如初见");
			node[i].setId(friends.getId());
			node_main.add(node[i]);
		}
		
	}


	/**
	 * 初始化JTree样式，使用自定义样式
	 */
	private void initJtree() {
		tree = new JTree(root);
		tree.setRootVisible(false);// 设置根节点不可见
		tree.setUI(new MyTreeUI());

		renderer.setMyNodeWidth(frame.getWidth());// 用当前窗口的宽度作为节点的宽度
		tree.setCellRenderer(renderer);// 设置为自定义的渲染器
		scrollPane.setViewportView(tree);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 隐藏横向滚动条
	}

	/**
	 * 初始化组件颜色
	 */
	protected void initColor() {
		panel_table.setBackground(themeColor);
		panel_tab1.setBackground(themeColor);
		//panel_tab1.setBackground(new Color(255,255,0,100));
		
		panel_tab2.setBackground(themeColor);
		panel_tab3.setBackground(themeColor);
		// tree.setBackground(color);
		renderer.setMyNodeSelectedColor(themeColor);
	}

	/**
	 * 初始化窗口事件监听，实现窗口拖动、最小化、关闭
	 */
	private void initListener() {
		MouseAdapter mos = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {// 鼠标移动
				if (e.getSource() == tree) {// tree的事件，实现鼠标悬浮高亮
					TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
					if (selPath != null) {
						//MyTreeNode clicNode = (MyTreeNode) tree.getLastSelectedPathComponent();
						//MyTreeNode currentNode = (MyTreeNode) selPath.getLastPathComponent();
						//实现鼠标悬浮高亮
						MyTreeCellRenderer.mouseRow = tree.getRowForLocation(e.getX(), e.getY());
						tree.repaint();
					}else {//鼠标在节点之外，取消高亮
						MyTreeCellRenderer.mouseRow = -1;
						tree.repaint();
					}
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {// 鼠标拖动
				if (e.getSource() == frame) {
					// Point p = frame.getLocation();
					// frame.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
					renderer.setMyNodeWidth(frame.getWidth());// 貌似做不到实时刷新
					// renderer.updateUI();
					tree.repaint();// 刷新以实现调节宽度
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {// 鼠标点击
				// TODO 自动生成的方法存根
				int x = e.getX(), y = e.getY();
				TreePath selPath = tree.getPathForLocation(x, y);
				if (e.getSource() == tree && e.getClickCount() == 2 && selPath != null) {
					int selRow = tree.getRowForLocation(x, y);
					MyTreeCellRenderer.selRow = selRow;
					tree.repaint();// 让好友聊天界面立即打开，否则移动鼠标才会显示
					System.out.println(tree.getRowForLocation(x, y));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {// 鼠标退出组件
				if (e.getSource() == skinButton) {// skinButton事件
					skinButton.setIcon(produceImage("skin.png"));
				}
				if (e.getSource() == minButton) {// minButton事件
					minButton.setIcon(produceImage("minimize.png"));
				}
				if (e.getSource() == exitButton) {// exitButton事件
					exitButton.setIcon(produceImage("close3.png"));
				}
				if (e.getSource() == lbl_menu) {// lbl_menu事件
					lbl_menu.setIcon(produceImage("menu.png"));
				}
				if (e.getSource() == tree) {// tree的事件，实现鼠标悬浮高亮
					MyTreeCellRenderer.mouseRow = -1;// 鼠标退出这棵树，取消高亮
					tree.repaint();
				}
				if (e.getSource() == panel_tab1) {// 消息
//					panel_tab1.setBackground(color);
//					panel_tab1.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 200, 0)));
//					panel_tab1.repaint();
					lbl_tab1.setForeground(COLOR_TABLE_NOT_SELECTED);
				}
				if (e.getSource() == panel_tab2) {// 好友
//					panel_tab2.setBackground(color);
//					panel_tab2.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 200, 0)));
//					panel_tab2.repaint();
					lbl_tab2.setForeground(COLOR_TABLE_NOT_SELECTED);
				}
				if (e.getSource() == panel_tab3) {// 群聊
//					panel_tab3.setBackground(color);
//					panel_tab3.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 200, 0)));
//					panel_tab3.repaint();
					lbl_tab3.setForeground(COLOR_TABLE_NOT_SELECTED);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {// 鼠标进入组件
				if (e.getSource() == skinButton) {// skinButton事件
					skinButton.setIcon(produceImage("skin_active.png"));
				}
				if (e.getSource() == minButton) {// minButton事件
					minButton.setIcon(produceImage("minimize_active.png"));
				}
				if (e.getSource() == exitButton) {// exitButton事件
					exitButton.setIcon(produceImage("close_active3.png"));
				}
				if (e.getSource() == lbl_menu) {// lbl_menu事件
					lbl_menu.setIcon(produceImage("menu_active2.png"));
				}
				if (e.getSource() == panel_tab1) {// 消息
//					panel_tab1.setBackground(new Color(255,255,0,100));
//					panel_tab1.setBorder(new MatteBorder(0, 0, 3, 0, color));
//					panel_tab1.repaint();
					lbl_tab1.setForeground(COLOR_TABLE_SELECTED);
				}
				if (e.getSource() == panel_tab2) {// 好友
//					panel_tab2.setBackground(new Color(255,255,0,100));
//					panel_tab2.setBorder(new MatteBorder(0, 0, 3, 0, color));
//					panel_tab2.repaint();
//					lbl_tab2.repaint();
					lbl_tab2.setForeground(COLOR_TABLE_SELECTED);
				}
				if (e.getSource() == panel_tab3) {// 群聊
//					panel_tab3.setBackground(new Color(255,255,0,100));
//					panel_tab3.setBorder(new MatteBorder(0, 0, 3, 0, color));
//					panel_tab3.repaint();
//					lbl_tab3.repaint();
					lbl_tab3.setForeground(COLOR_TABLE_SELECTED);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {// 鼠标松开
				Point fp = frame.getLocation();// 获取frame的坐标，Point对象有公共整型成员x y可访问
				Point curP = new Point(e.getX() + fp.x, e.getY() + fp.y);
				if (e.getSource() == skinButton) {// skinButton事件
					new SkinDialog(MyFriendsList3.this, curP);// 创建一个选择颜色的对话框
				}
				if (e.getSource() == minButton) {// minButton事件
					frame.setExtendedState(Frame.ICONIFIED);
				}
				if (e.getSource() == exitButton) {// exitButton事件
					System.exit(0);
				}
				if (e.getSource() == lbl_menu) {// lbl_menu事件
					TestDialogMenu dialog = new TestDialogMenu();
					dialog.setLocation(curP.x, curP.y + frame.getHeight() - dialog.getHeight() - 40);
					dialog.setVisible(true);
				}
			}
		};
		frame.addMouseMotionListener(mos);
		skinButton.addMouseListener(mos);
		minButton.addMouseListener(mos);
		exitButton.addMouseListener(mos);
		lbl_menu.addMouseListener(mos);
		tree.addMouseListener(mos);
		tree.addMouseMotionListener(mos);
		panel_tab1.addMouseListener(mos);
		panel_tab2.addMouseListener(mos);
		panel_tab3.addMouseListener(mos);
	}

	/**
	 * 双击好友节点后，打开好友聊天界面
	 * 
	 * @param targetId 被选中的好友的Id
	 */
	protected void chatWithFriend(String targetId) {
		boolean isSel = chckbx_qunliao.isSelected();
		XMLOperation xml = new XMLOperation();
		Users targetUser = xml.getUsersById(targetId);
		
		Chat window = new Chat(this, user, targetUser, out, isSel);
		chatArray.add(window);
		
		window.setIcon(renderer.getNode().getIcon());
		window.setTitle(renderer.getNode().getName());
		window.setSign(renderer.getNode().getSign());
	}
	
	/**
	 * 删除好友列表中Chat对象的引用，由聊天窗口调用
	 * @param cha
	 */
	public void chatEnd(Chat cha) {
		chatArray.remove(cha);
	}
	
	/**
	 * 向所有的聊天窗口发消息，消息的内容由聊天窗口判断
	 * 
	 * @param jo
	 */
	protected void sendToChat(JSONObject jo) {
		chatArray.forEach(i -> {
			i.receiveMsg(jo);
		});
	}

	/**
	 * 获取图片
	 * 
	 * @param name 图片名称（不需要相对路径）
	 * @return backImage ImageIcon对象
	 */
	private ImageIcon produceImage(String name) {
		ImageIcon backImage = new ImageIcon(getClass().getResource("/Images/" + name));
		return backImage;
	}

	/**
	 * 将图片缩放为合适宽度
	 * 
	 * @param width  期望缩放后的宽度
	 * @param height 期望缩放后的高度
	 * @param name   图片的文件名（不需要相对路径）
	 * @return ImageIcon对象
	 */
	private ImageIcon toSuitableIcon(int width, int height, String name) {
		ImageIcon backImage = produceImage(name);
		backImage.setImage(backImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		return backImage;
	}

	/**
	 * @return themeColor
	 */
	public Color getThemeColor() {
		return themeColor;
	}

	/**
	 * @param themeColor 要设置的 themeColor
	 */
	public void setThemeColor(Color themeColor) {
		this.themeColor = themeColor;
	}

	/**
	 * @return socket
	 */
	protected Socket getSocket() {
		return socket;
	}

	/**
	 * @return out
	 */
	protected PrintWriter getOut() {
		return out;
	}

	/**
	 * @return in
	 */
	protected BufferedReader getIn() {
		return in;
	}
}
