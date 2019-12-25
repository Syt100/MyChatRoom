package test;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**本类测试去掉窗口标题后自己添加最小化、最大化、关闭按钮
 * @author xuxin
 *
 */
@SuppressWarnings("serial")
public class FrameTest extends JFrame {

	private JPanel content;
	private ImageIcon backGroundImgIcon = new ImageIcon(getClass().getResource("/Images/f988052c0de226d39930a64855d10355_1.jpg"));

	private JLabel minButton = new JLabel();
	private JLabel maxButton = new JLabel();
	private JLabel exitButton = new JLabel();
	private Point point = new Point();
	/** pLocation记录frame在屏幕上的位置，用于最大化后还原之前的位置 */
	private Point pLocation = new Point();
	private static final int DEFAULT_WIDTH=410;
    private static final int DEFAULT_HEIGHT=330;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameTest frame = new FrameTest();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameTest() {
		initBackGroundGUI();
		//initGUIwithNoBackGround();
		initListener();
	}

	/**
	 * 用图片初始化背景
	 * @param imgIcon
	 */
	public FrameTest(ImageIcon imgIcon) {
		this.backGroundImgIcon = imgIcon;
	}

	@SuppressWarnings({ "serial", "unused" })
	private void initBackGroundGUI() {
		setBounds(100, 100, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setUndecorated(true);// 将原始的边框去掉

		content = new JPanel()
		{
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
		content.setBorder(new EmptyBorder(0, 0, 0, 0));
		content.setLayout(null);
		
		backGroundImgIcon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);

		minButton.setBounds(getWidth() - (24*3+5*3), 5, 16, 15);
		System.out.println(getWidth() + " " + getHeight());
		minButton.setIcon(produceImage("/Images/min2.png"));
		content.add(minButton);

		maxButton.setBounds(getWidth() - (24*2+5*2), 5, 16, 16);
		maxButton.setIcon(produceImage("/Images/max2.png"));
		content.add(maxButton);

		exitButton.setBounds(getWidth() - (24*1+5*1), 5, 16, 16);
		exitButton.setIcon(produceImage("/Images/close2.png"));
		content.add(exitButton);
		setContentPane(content);
	}
	
	private void initGUIwithNoBackGround() {
		setBounds(100, 100, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setUndecorated(true);// 将原始的边框去掉

		content = new JPanel();
		content.setBorder(new EmptyBorder(0, 0, 0, 0));
		content.setLayout(null);
		
		backGroundImgIcon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);

		minButton.setBounds(getWidth() - (24*3+5*3), 5, 16, 15);
		System.out.println(getWidth() + " " + getHeight());
		minButton.setIcon(produceImage("/Images/min2.png"));
		content.add(minButton);

		maxButton.setBounds(getWidth() - (24*2+5*2), 5, 16, 16);
		maxButton.setIcon(produceImage("/Images/max2.png"));
		content.add(maxButton);

		exitButton.setBounds(getWidth() - (24*1+5*1), 5, 16, 16);
		exitButton.setIcon(produceImage("/Images/close2.png"));
		content.add(exitButton);
		setContentPane(content);
	}

	private void initListener() {
		// 窗体事件，用于拖动窗体位置
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				point.x = e.getX();
				point.y = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
			}
		});
		// 最小化按钮事件
		minButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {//鼠标退出组件时
				minButton.setIcon(produceImage("/Images/min2.png"));
			}

			@Override
			public void mouseEntered(MouseEvent e) {//鼠标进入组件时
				minButton.setIcon(produceImage("/Images/min_active2.png"));
			}

			@Override
			public void mouseReleased(MouseEvent e) {//鼠标松开时
				setExtendedState(Frame.ICONIFIED);
			}
		});
		// 最大化按钮事件
		maxButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {//鼠标退出组件时
				if (getExtendedState() != Frame.MAXIMIZED_BOTH) {
					maxButton.setIcon(produceImage("/Images/max2.png"));
				} else {
					maxButton.setIcon(produceImage("/Images/restore2.png"));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {//鼠标进入组件时
				if (getExtendedState() != Frame.MAXIMIZED_BOTH) {
					maxButton.setIcon(produceImage("/Images/max_active2.png"));
				} else {
					maxButton.setIcon(produceImage("/Images/restore_active2.png"));
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {//鼠标松开时
				// 若是最大化过了，就直接还原大小
				if (getExtendedState() == Frame.MAXIMIZED_BOTH) {
					setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);// 还原大小
					setLocation(pLocation);//重新设置坐标
					//setLocationRelativeTo(null);// 这里需要重新设置坐标
					maxButton.setIcon(produceImage("/Images/max2.png"));
				} else {
					pLocation = getLocationOnScreen();//获取frame在屏幕上的位置
					setExtendedState(Frame.MAXIMIZED_BOTH);
					maxButton.setIcon(produceImage("/Images/restore2.png"));
				}
				// 按比例放置按钮
				fixedButtonPoint();
			}
		});
		// 退出按钮事件
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(produceImage("/Images/close2.png"));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(produceImage("/Images/close_active2.png"));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * 按比例放置按钮，不管放大还是缩小窗体，按钮永远在右上角
	 */
	private void fixedButtonPoint() {
		// 重新调整按钮位置
		minButton.setBounds(getWidth() - (24*3+5*3), 5, 16, 16);
		maxButton.setBounds(getWidth() - (24*2+5*2), 5, 16, 16);
		exitButton.setBounds(getWidth() - (24*1+5*1), 5, 16, 16);
	}

	/**
	 * 获取图片
	 * @param name 图片名称
	 * @return backImage
	 */
	private ImageIcon produceImage(String name) {
		ImageIcon backImage = new ImageIcon(getClass().getResource(name));
		return backImage;
	}
}
