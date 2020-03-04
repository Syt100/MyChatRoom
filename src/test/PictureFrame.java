package test;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import util.ModifiedFlowLayout;

public class PictureFrame {

	private JFrame frame;

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	private JTextField textField;
	private JLabel lbl_totalHits_show;
	private JLabel lbl_total_show;
	private JLabel lbl_time_show;
	private JPanel panel_1;
	private JLabel lbl_status;

	private int page = 1;
	private JButton btn_nextPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PictureFrame window = new PictureFrame();
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
	public PictureFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("网络图片");
		frame.setBounds(100, 100, 810, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);

		JPanel panel_top = new JPanel();
		panel_top.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPane.add(panel_top, BorderLayout.NORTH);

		lblNewLabel = new JLabel("关键词");
		panel_top.add(lblNewLabel);

		textField = new JTextField("yellow+flowers");
		textField.setColumns(10);
		panel_top.add(textField);

		btnNewButton = new JButton("搜索");
		btnNewButton.addActionListener(new MyActionListener());
		panel_top.add(btnNewButton);

		btn_nextPage = new JButton("下一页");
		btn_nextPage.addActionListener(new MyActionListener());
		panel_top.add(btn_nextPage);

		JPanel panel_center = new JPanel();
		contentPane.add(panel_center, BorderLayout.CENTER);
		panel_center.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_center.add(panel, BorderLayout.NORTH);

		JLabel lbl_totalHits = new JLabel("单次查询个数");
		panel.add(lbl_totalHits);

		lbl_totalHits_show = new JLabel("0");
		panel.add(lbl_totalHits_show);

		JLabel lbl_total = new JLabel("总点击数：");
		panel.add(lbl_total);

		lbl_total_show = new JLabel("0");
		panel.add(lbl_total_show);

		JLabel lbl_time = new JLabel("用时：");
		panel.add(lbl_time);

		lbl_time_show = new JLabel("0");
		panel.add(lbl_time_show);

		panel_1 = new JPanel();
		ModifiedFlowLayout mfl_panel_1 = new ModifiedFlowLayout();
		mfl_panel_1.setAlignment(FlowLayout.LEFT);
		panel_1.setLayout(mfl_panel_1);

		JScrollPane scrollPane = new JScrollPane(panel_1);
		scrollPane.getVerticalScrollBar().setUnitIncrement(9);// 设置拖曳滚动轴时，滚动轴刻度一次的变化量。
		panel_center.add(scrollPane, BorderLayout.CENTER);

		JPanel panel_status = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_status.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_center.add(panel_status, BorderLayout.SOUTH);

		lbl_status = new JLabel("就绪");
		panel_status.add(lbl_status);

	}

	/**
	 * 
	 */
	private void getImage(int page) {
		String key = "15160484-c08e29a75f97d928613c854b3";
		String q = textField.getText();
		String image_type = "all";
		int per_page = 20;
		String requset = "https://pixabay.com/api/?key=" + key + "&q=" + q + "&image_type=" + image_type + "&page="
				+ page + "&per_page=" + per_page;
//		page++;

		// 计时
		final long curTime = System.currentTimeMillis();

		new Thread(new Runnable() {
			public void run() {
				try {
					lbl_status.setText("正在加载JSON字符串...");
					URL url = new URL(requset);
					BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

					String line;
					StringBuffer json = new StringBuffer();
					while ((line = in.readLine()) != null) {
						// System.out.println(line);
						json = json.append(line);
					}
					in.close();

					lbl_status.setText("正在加载预览图...");
					long curTime1 = System.currentTimeMillis();
					long time = curTime1 - curTime;
					lbl_time_show.setText("" + time + "毫秒");

					System.out.println("===========================================");

					JSONObject jo = JSONObject.parseObject(json.toString());

					// 复制到剪贴板
					StringSelection stsel = new StringSelection(json.toString());
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel, null);
					System.out.println("已复制到系统剪贴板");
					//
					int totalHits = jo.getIntValue("totalHits");
					lbl_totalHits_show.setText("" + totalHits);
					System.out.println(totalHits);
					int total = jo.getIntValue("total");
					lbl_total_show.setText("" + total);

					JSONArray jsonArray = jo.getJSONArray("hits");
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject tmp = jsonArray.getJSONObject(i);
						String imgUrl = tmp.getString("previewURL");
						System.out.println(imgUrl);
						ImageIcon imgIcon = new ImageIcon(new URL(imgUrl));
						JLabel lbl = new JLabel(imgIcon);
						MyMouseAdapter mouseListener = new MyMouseAdapter(lbl, tmp);
						lbl.addMouseListener(mouseListener);
						lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
						panel_1.add(lbl);
						panel_1.updateUI();

					}
					System.out.println("加载完成");
					lbl_status.setText("加载完成");

				} catch (MalformedURLException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} catch (UnknownHostException e1){
					System.out.println("UnknownHostException");
				} catch(ConnectException e1) {
					System.err.println(e1.toString());
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		}).start();
	}

	private class MyMouseAdapter extends MouseAdapter {
		private JComponent jc;
		private JSONObject jo;

		protected MyMouseAdapter(JComponent jc, JSONObject jo) {
			this.jc = jc;
			this.jo = jo;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			JDialog dia = new JDialog();
			if (jc instanceof JLabel) {
				JLabel lbl = (JLabel) jc;
				ImageIcon ic = (ImageIcon) lbl.getIcon();
				dia.setIconImage(ic.getImage());
			}
			dia.setLocationRelativeTo(jc);
			dia.setSize(500, 400);
			dia.setVisible(true);
			String imgUrl = jo.getString("largeImageURL");
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						ImageIcon imgIcon = new ImageIcon(new URL(imgUrl));
						JLabel lbl = new JLabel(imgIcon);
						dia.getContentPane().add(lbl);
						lbl.updateUI();
					} catch (MalformedURLException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}).start();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO 自动生成的方法存根
			super.mouseExited(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO 自动生成的方法存根
			super.mouseMoved(e);
		}

	}

	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnNewButton) {
				panel_1.removeAll();
				page = 1;
				getImage(page++);
			}
			if (e.getSource() == btn_nextPage) {
				getImage(page++);
			}

		}

	}
}
