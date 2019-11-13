package mygui.login;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 设置面板。在登录界面中显示
 * @author xuxin
 *
 */
public class Setting extends JPanel {
	private static final long serialVersionUID = 3900196068302664939L;

	/** 背景图片 */
	private ImageIcon backgroundImage = null;
	/** 浏览按钮 */
	private JButton btn_browse;
	/** 确定按钮 */
	public JButton btn_confirm;
	/** 返回按钮 */
	public JButton btn_return;
	/** 显示预览图片的标签 */
	private JLabel lbl_showPreview;
	/** 用于显示背景图片路径的文本框 */
	private JTextField textField;
	/** 恢复默认按钮 */
	private JButton btn_reset;

	/**
	 * Create the panel.
	 */
	public Setting() {
		setSize(430, 281);
		setLayout(null);

		JLabel lbl_backgroundImage = new JLabel("背景图片");
		lbl_backgroundImage.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lbl_backgroundImage.setBounds(10, 9, 48, 15);
		add(lbl_backgroundImage);

		textField = new JTextField();
		textField.setBounds(68, 6, 285, 21);
		textField.setColumns(10);
		add(textField);

		btn_browse = new JButton("浏览");
		btn_browse.setMargin(new Insets(2, 10, 2, 10));
		btn_browse.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		btn_browse.setBounds(363, 5, 57, 23);
		btn_browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 从当前路径构造文件选择器对象
				JFileChooser fd = new JFileChooser("./");

				// 设置文件过滤器，只列出JPG或GIF格式的图片
				FileFilter filter = new FileNameExtensionFilter("图像文件（JPG/GIF）", "JPG", "JPEG", "GIF");
				fd.setFileFilter(filter);
				// 显示窗口
				fd.showOpenDialog(null);
				File f = fd.getSelectedFile();
				if (f != null) {
					System.out.println(f.getPath());
					textField.setText(f.getPath());
					backgroundImage = new ImageIcon(f.getPath());
					lbl_showPreview.setIcon(backgroundImage);
				}
			}
		});
		add(btn_browse);

		btn_return = new JButton("返回");
		btn_return.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		btn_return.setMargin(new Insets(2, 10, 2, 10));
		btn_return.setBounds(363, 248, 57, 23);
		add(btn_return);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setBorder(new TitledBorder(null, "\u9884\u89C8", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(10, 37, 343, 234);
		add(scrollPane);

		lbl_showPreview = new JLabel("");
		scrollPane.setViewportView(lbl_showPreview);

		JLabel lbl_preview = new JLabel("预览");
		lbl_preview.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_preview.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lbl_preview.setBounds(363, 38, 57, 23);
		add(lbl_preview);

		btn_confirm = new JButton("确定");
		btn_confirm.setMargin(new Insets(2, 10, 2, 10));
		btn_confirm.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		btn_confirm.setBounds(363, 215, 57, 23);
		add(btn_confirm);
		
		btn_reset = new JButton("恢复默认");
		btn_reset.setMargin(new Insets(2, 1, 2, 1));
		btn_reset.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		btn_reset.setBounds(363, 182, 57, 23);
		add(btn_reset);
		btn_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundImage = new ImageIcon(getClass().getResource("/Images/source/img_0.png"));
				lbl_showPreview.setIcon(backgroundImage);
			}
		});
	}

	/**
	 * 返回背景图片
	 * 
	 * @return backgroundImage
	 */
	public ImageIcon getBackgroundImage() {
		return backgroundImage;
	}
}
