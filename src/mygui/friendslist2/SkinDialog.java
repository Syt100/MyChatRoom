package mygui.friendslist2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JList;
import javax.swing.DefaultListModel;

/**
 * 皮肤、颜色选择对话框类
 * @author xuxin
 *
 */
public class SkinDialog {

	private JDialog dialog;

	/** 好友列表对象的引用，以便调用其方法 */
	private MyFriendsList3 myFri;

	/** 记录鼠标位置，用于skinOption中实现鼠标高亮 */
	private Point skinPoint = new Point();

	/** 记录创建的皮肤对话框数，最多只能创建一个对话框 */
	private static int skinDialogCount = 0;

	/** 记录窗口位置 */
	private Point curPoint;
	private int width = 126;// 对话框宽度
	private int height = 170;// 对话框高度

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			new SkinDialog();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试用，由本类的main方法调用
	 */
	private SkinDialog() {
		curPoint = new Point(20, 30);
		initDialog();
	}

	/**
	 * @param myFri
	 * @param curPoint
	 */
	public SkinDialog(MyFriendsList3 myFri, Point curPoint) {
		this.myFri = myFri;
		this.curPoint = curPoint;
		initDialog();
	}

	/**
	 * 初始化对话框
	 */
	private void initDialog() {
		if (skinDialogCount >= 1) {
			return;
		}

		dialog = new JDialog();
		final JPanel contentPanel = new JPanel();
		JList<String> list = new JList<String>();
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		MyListCellRenderer render = new MyListCellRenderer();
		// DefaultListCellRenderer

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
					myFri.setThemeColor(c);
					myFri.initColor();
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
//					color = Color.RED;
					myFri.setThemeColor(Color.RED);
				} else if (selected == 1) {
//					color = Color.GREEN;
					myFri.setThemeColor(Color.GREEN);
				}
				myFri.initColor();// 调用颜色设置
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
		// JColorChooser.showDialog(null, "选择", null);
		skinDialogCount++;// 数量自增一
	}

	/**
	 * @return skinDialogCount
	 */
	public static int getSkinDialogCount() {
		return skinDialogCount;
	}

	/**
	 * @param skinDialogCount 要设置的 skinDialogCount
	 */
	public static void setSkinDialogCount(int skinDialogCount) {
		SkinDialog.skinDialogCount = skinDialogCount;
	}

}
