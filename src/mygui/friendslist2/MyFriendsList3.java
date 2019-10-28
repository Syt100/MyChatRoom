package mygui.friendslist2;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.tree.TreePath;

import mygui.frameutil.ResizeFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JTree;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.MatteBorder;

public class MyFriendsList3 {

	/** 窗体 */
	private ResizeFrame frame = new ResizeFrame();
	/** 底层面板 */
	private JPanel content;
	/** 最小化和关闭按钮 */
	private JLabel minButton = new JLabel();
	private JLabel exitButton = new JLabel();
	private JLabel skinButton = new JLabel();
	/** 记录鼠标位置，用于skinOption中实现鼠标高亮 */
	private Point skinPoint = new Point();
	/** pLocation记录frame在屏幕上的位置，用于最大化后还原之前的位置 */
	private Point pLocation = new Point();
	/** 窗口默认大小 */
	private static final int DEFAULT_WIDTH = 290;
	private static final int DEFAULT_HEIGHT = 560;

	/**
	 * 记录创建的皮肤对话框数，最多只能创建一个对话框
	 */
	private static int skinDialogCount = 0;

	/**
	 * 默认主题的颜色
	 */
	private Color color = Color.WHITE;

	/**
	 * 默认的窗口背景图片
	 */
	private ImageIcon backGroundImgIcon = new ImageIcon(getClass().getResource("/Images/back11.png"));

	/**
	 * 窗口标题
	 */
	private static String title = new String(" 我的QQ");

	/**
	 * 将contentPane分为三大区域
	 */
	private final JPanel panel_top = new JPanel();
	private final JPanel panel_center = new JPanel();
	private final JPanel panel_down = new JPanel();

	/**
	 * 设置窗口显示的标题
	 */
	private JLabel lbl_title = new JLabel();

	/**
	 * 用于显示右上角的关闭、最小化等按钮
	 */
	private final JPanel panel_close = new JPanel();

	/**
	 * 中间的两部分之一，用于显示头像、昵称、搜索框信息等
	 */
	private final JPanel panel_header = new JPanel();
	/**
	 * 用于显示好友信息、上方的切换标签等
	 */
	private final JPanel panel_friends = new JPanel();

	/**
	 * 头像
	 */
	private final JLabel lbl_header = new JLabel();

	/**
	 * 存放昵称，签名
	 */
	private final JPanel panel_name = new JPanel();
	/**
	 * 存放昵称
	 */
	private final JLabel lbl_name = new JLabel("New label");
	/**
	 * 存放签名
	 */
	private final JTextArea textArea_sign = new JTextArea();
	/**
	 * 天气面板
	 */
	private final JPanel panel_weather = new JPanel();
	/**
	 * 存放天气
	 */
	private final JLabel lbl_weather = new JLabel("New label");

	/**
	 * 搜索面板
	 */
	private final JPanel panel_search = new JPanel();
	/**
	 * 搜索图标
	 */
	private final JLabel lbl_search = new JLabel();
	/**
	 * 搜索输入框
	 */
	private final JTextField txtField = new JTextField();
	/**
	 * 用于切换最近聊天、好友列表、群聊
	 */
	private final JPanel panel_table = new JPanel();
	/**
	 * 存放最近聊天
	 */
	private final JPanel panel_tab1 = new JPanel();
	/**
	 * 存放好友列表
	 */
	private final JPanel panel_tab2 = new JPanel();
	/**
	 * 存放群聊列表
	 */
	private final JPanel panel_tab3 = new JPanel();
	/**
	 * 分层面板，用于切换上面的三个tab，用来显示最近聊天、好友列表、群聊
	 */
	private final JLayeredPane layeredPane = new JLayeredPane();
	private final JLabel lbl_tab1 = new JLabel("最近");
	private final JLabel lbl_tab2 = new JLabel("好友");
	private final JLabel lbl_tab3 = new JLabel("群聊");
	private final JPanel panel_friendsList = new JPanel();
	MyTreeNode root;
	MyTreeCellRenderer renderer = new MyTreeCellRenderer();
	private JScrollPane scrollPane = new JScrollPane();
	private JTree tree;
	private final JPanel panel_down_left = new JPanel();
	private final JPanel panel_down_right = new JPanel();
	private final JLabel lbl_menu = new JLabel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFriendsList3 window = new MyFriendsList3();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
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
	}

	/**
	 * 初始化窗口及背景图片
	 */
	@SuppressWarnings("serial")
	private void initBackGround() {
		frame.setBounds(100, 100, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		frame.setUndecorated(true);// 将原始的边框去掉
		frame.setLocationRelativeTo(null);// 设置窗口打开位置居中

		content = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// 这一句可以清除之前绘制的图像，在这个例子中看不出来效果，若是java做画图板的程序就能看出来
				super.paintComponent(g);
				// 此方法有很多种，看的眼花缭乱，了解参数了就好多了，这里说一下基本的
				// 1：image对象
				// 2：重绘的起始横坐标
				// 3：重绘的起始纵坐标
				// 4：重绘的宽度
				// 5：重绘的高度
				// 6：一个实现ImageObserver
				// 接口的对象。它将该对象登记为一个图像观察者，因此当图像的任何新信息可见时它被通知。大多组件可以简单的指定this或null
				// 组件可以指定this作为图像观察者的原因是Component类实现了ImageObserver接口。当图像数据被加载时它的实现调用repaint方法
				g.drawImage(backGroundImgIcon.getImage(), 0, 0, getWidth(), getHeight(), null);
			}
		};
		frame.setContentPane(content);
	}

	/**
	 * 初始化窗口内组件
	 */
	private void initComponent() {
		content.setBorder(new LineBorder(Color.LIGHT_GRAY));
		content.setLayout(new BorderLayout(0, 0));

		content.add(panel_top, BorderLayout.NORTH);
		panel_top.setLayout(new BorderLayout(0, 0));

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

		panel_header.add(panel_name, BorderLayout.CENTER);
		panel_name.setLayout(new GridLayout(2, 1, 0, 0));
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

		panel_header.add(panel_weather, BorderLayout.EAST);

		panel_weather.setLayout(new BorderLayout(10, 10));
		panel_weather.add(lbl_weather);

		panel_header.add(panel_search, BorderLayout.SOUTH);

		panel_search.setLayout(new BorderLayout(0, 0));
		panel_search.add(lbl_search, BorderLayout.WEST);

		panel_search.add(txtField);

		panel_center.add(panel_friends, BorderLayout.CENTER);

		panel_friends.setLayout(new BorderLayout(0, 0));
		panel_friends.add(panel_table, BorderLayout.NORTH);

		panel_table.setLayout(new GridLayout(0, 3, 0, 0));
		/** 设置panel_tab1为花色边框，颜色 */
		panel_tab1.setBorder(new MatteBorder(0, 0, 3, 0, color));
		panel_table.add(panel_tab1);

		panel_tab1.add(lbl_tab1);

		/** 设置panel_tab2为花色边框，颜色 */
		panel_tab2.setBorder(new MatteBorder(0, 0, 3, 0, color));
		panel_table.add(panel_tab2);

		panel_tab2.add(lbl_tab2);

		/** 设置panel_tab3为花色边框，颜色 */
		panel_tab3.setBorder(new MatteBorder(0, 0, 3, 0, color));
		panel_table.add(panel_tab3);

		panel_tab3.add(lbl_tab3);
		panel_friends.add(panel_friendsList, BorderLayout.CENTER);
		panel_friendsList.setLayout(new BorderLayout(0, 0));
		panel_friendsList.add(layeredPane, BorderLayout.CENTER);
		layeredPane.setLayer(panel_friendsList, 1);
		layeredPane.setLayout(new BorderLayout(0, 0));

		layeredPane.add(scrollPane, BorderLayout.CENTER);

		content.add(panel_down, BorderLayout.SOUTH);

		panel_down.setLayout(new BorderLayout(0, 0));
		panel_down.add(panel_down_left, BorderLayout.WEST);
		panel_down.add(panel_down_right, BorderLayout.EAST);
		panel_down_left.add(lbl_menu);

	}

	/**
	 * 初始化窗口内组件透明
	 */
	private void initSetOpaque() {
		panel_top.setOpaque(false);
		panel_center.setOpaque(false);
		panel_down.setOpaque(false);
		panel_close.setOpaque(false);
		panel_header.setOpaque(false);
		panel_friends.setOpaque(false);
		panel_name.setOpaque(false);
		panel_weather.setOpaque(false);
		panel_search.setOpaque(false);
	}

	/**
	 * 初始化组件内容
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

		// txtField.setBackground(Color.MAGENTA);
		// txtField.setText("搜索");
		txtField.addFocusListener(new JTextFieldHintListener(txtField, "搜索好友"));

		lbl_menu.setIcon(produceImage("menu.png"));
		lbl_menu.setPreferredSize(new Dimension(25, 20));
	}

	/**
	 * 初始化JTree的节点，即好友列表
	 */
	private void initJTreeNode() {
		Icon ico1 = new ImageIcon(MyFriendsList.class.getResource("/Images/1.png"));
		Icon ico2 = new ImageIcon(MyFriendsList.class.getResource("/Images/2.png"));
		Icon ico3 = new ImageIcon(MyFriendsList.class.getResource("/Images/3.png"));
		Icon ico4 = new ImageIcon(MyFriendsList.class.getResource("/Images/4.png"));

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
	private void initColor() {
		panel_table.setBackground(color);
		panel_tab1.setBackground(color);
		//panel_tab1.setBackground(new Color(255,255,0,100));
		
		panel_tab2.setBackground(color);
		panel_tab3.setBackground(color);
		// tree.setBackground(color);
		renderer.setMyNodeSelectedColor(color);
	}

	/**
	 * 初始化窗口事件监听，实现窗口拖动、最小化、关闭
	 */
	private void initListener() {
		// 窗体事件，用于拖动窗体位置
//		frame.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				point.x = e.getX();
//				point.y = e.getY();
//			}
//		});
		MouseAdapter mos = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {// 鼠标移动
				if (e.getSource() == tree) {// tree的事件，实现鼠标悬浮高亮
					int x = e.getX(), y = e.getY();
					TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
					if (selPath != null) {
						//MyTreeNode clicNode = (MyTreeNode) tree.getLastSelectedPathComponent();
						//MyTreeNode currentNode = (MyTreeNode) selPath.getLastPathComponent();
						//实现鼠标悬浮高亮
						MyTreeCellRenderer.mouseRow = tree.getRowForLocation(x, y);
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
				TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
				if (e.getSource() == tree && e.getClickCount() == 2 && selPath != null) {
//					System.out.println("12");
					int selRow = tree.getRowForLocation(x, y);
					MyTreeCellRenderer.selRow = selRow;
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
				if (e.getSource() == panel_tab1) {
					panel_tab1.setBackground(color);
					panel_tab1.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 200, 0)));
					panel_tab1.repaint();
				}
				if (e.getSource() == panel_tab2) {
					panel_tab2.setBackground(color);
					panel_tab2.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 200, 0)));
					panel_tab2.repaint();
				}
				if (e.getSource() == panel_tab3) {
					panel_tab3.setBackground(color);
					panel_tab3.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 200, 0)));
					panel_tab3.repaint();
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
				if (e.getSource() == panel_tab1) {
					panel_tab1.setBackground(new Color(255,255,0,100));
					panel_tab1.setBorder(new MatteBorder(0, 0, 3, 0, color));
					panel_tab1.repaint();
					lbl_tab1.repaint();
				}
				if (e.getSource() == panel_tab2) {
					panel_tab2.setBackground(new Color(255,255,0,100));
					panel_tab2.setBorder(new MatteBorder(0, 0, 3, 0, color));
					panel_tab2.repaint();
					lbl_tab2.repaint();
				}
				if (e.getSource() == panel_tab3) {
					panel_tab3.setBackground(new Color(255,255,0,100));
					panel_tab3.setBorder(new MatteBorder(0, 0, 3, 0, color));
					panel_tab3.repaint();
					lbl_tab3.repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {// 鼠标松开
				Point fp = frame.getLocation();// 获取frame的坐标，Point对象有公共整型成员x y可访问
				Point curP = new Point(e.getX() + fp.x, e.getY() + fp.y);
				if (e.getSource() == skinButton) {// skinButton事件
					if (skinDialogCount < 1) {// 如果当前选择皮肤对话框数量为0
						skinDialogCount += 1;
						skinOption(curP);// 创建一个选择颜色的对话框
					}
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
		

		// 皮肤按钮事件
//		skinButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseExited(MouseEvent e) {
//				skinButton.setIcon(produceImage("skin.png"));
//			}
//			
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				skinButton.setIcon(produceImage("skin_active.png"));
//			}
//			
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				Point fp = frame.getLocation();//获取frame的坐标，Point对象有公共整型成员x y可访问
//				Point p = new Point(e.getX() + fp.x, e.getY() + fp.y);
//				if(skinDialogCount < 1) {//如果当前选择皮肤对话框数量为0
//					skinDialogCount += 1;
//					skinOption(p);//创建一个选择颜色的对话框			
//				}
//			}
//		});

		// 最小化按钮事件
//		minButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseExited(MouseEvent e) {// 鼠标退出组件时
//				minButton.setIcon(produceImage("minimize.png"));
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {// 鼠标进入组件时
//				minButton.setIcon(produceImage("minimize_active.png"));
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {// 鼠标松开时
//				frame.setExtendedState(Frame.ICONIFIED);
//			}
//		});

		// 退出按钮事件
//		exitButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseExited(MouseEvent e) {
//				exitButton.setIcon(produceImage("close3.png"));
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				exitButton.setIcon(produceImage("close_active3.png"));
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				System.exit(0);
//			}
//		});

//		lbl_menu.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseExited(MouseEvent e) {
//				lbl_menu.setIcon(produceImage("menu.png"));
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				lbl_menu.setIcon(produceImage("menu_active2.png"));
//			}
//		});
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

	@SuppressWarnings("serial")
	private void skinOption(Point curPoint) {
		int width = 126;// 对话框宽度
		int height = 170;// 对话框高度

		JDialog dialog = new JDialog();
		final JPanel contentPanel = new JPanel();
		JList<String> list = new JList<String>();
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		MyListCellRenderer render = new MyListCellRenderer();
		
		dialog.setAlwaysOnTop(true);// 置顶显示
		dialog.setBounds(((int) curPoint.getX() + width), (int) curPoint.getY(), width, height);
		dialog.setUndecorated(true);// 将原始的边框去掉
		dialog.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		dlm.addElement("红（Color.red）");
		dlm.addElement("绿（Color.green）");
		list.setModel(dlm);
		list.setCellRenderer(render);
//		list.setModel(new AbstractListModel<String>() {
//			String[] values = new String[] { "红（Color.red）", "绿（Color.green）" };
//
//			public int getSize() {
//				return values.length;
//			}
//
//			public String getElementAt(int index) {
//				return values[index];
//			}
//		});
		contentPanel.add(list);
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				skinPoint = e.getPoint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				skinPoint = e.getPoint();
				render.setCurIndex(-1);// 退出时取消高亮
				list.repaint();
			}
		});
		list.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int lastIndex = list.locationToIndex(skinPoint);
				Point current = e.getPoint();
				int currentIndex = list.locationToIndex(current);
				// if (currentIndex == lastIndex && lastIndex != -1)
				// return;
				render.setCurIndex(currentIndex);
				list.repaint();
				// highlight
				skinPoint = current;
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		dialog.getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton diyButton = new JButton("自定义");
		diyButton.setMargin(new Insets(0, 0, 0, 0));
		diyButton.setFont(new Font("宋体", Font.PLAIN, 12));
		diyButton.setPreferredSize(new Dimension(42, 20));
		diyButton.setActionCommand("diy");
		buttonPane.add(diyButton);
		diyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * 颜色选择器用于颜色的选择、编辑等操作
				 * 参考链接：https://blog.csdn.net/chen_z_p/article/details/82749846
				 */
				Color c = JColorChooser.showDialog(null, "选择", null);
				if (c != null) {
					color = c;
					initColor();
				}
			}
		});

		JButton okButton = new JButton("确定");
		okButton.setMargin(new Insets(0, 0, 0, 0));
		okButton.setFont(new Font("宋体", Font.PLAIN, 12));
		okButton.setPreferredSize(new Dimension(32, 20));
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		dialog.getRootPane().setDefaultButton(okButton);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = list.getSelectedIndex();
				if (selected == 0) {
					color = Color.RED;
				} else if (selected == 1) {
					color = Color.GREEN;
				}
				initColor();// 调用颜色设置
				dialog.dispose();// 退出当前窗口
				skinDialogCount -= 1;
				// System.out.println(a);
			}
		});

		JButton cancelButton = new JButton("取消");
		cancelButton.setMargin(new Insets(0, 0, 0, 0));
		cancelButton.setFont(new Font("宋体", Font.PLAIN, 12));
		cancelButton.setPreferredSize(new Dimension(32, 20));
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();// 退出当前窗口
				skinDialogCount -= 1;
			}
		});

		dialog.setVisible(true);
		dialog.addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowLostFocus(WindowEvent e) {// 对话框失去焦点
				dialog.dispose();// 点击窗体之外任意位置直接关闭
				skinDialogCount -= 1;
				// 最小化
				// dialog.setExtendedState(JFrame.ICONIFIED);
			}
		});
	}
	
	/**
	 * 设置窗口可见
	 * @param t 是否可见
	 */
	public void setFrameVisible(boolean t) {
		frame.setVisible(t);
	}
}
