package test;

import mygui.components.ResizeFrame;
import mygui.friendslist2.TestDialogMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Yanshi {

	private JFrame frame = new JFrame();
	TestDialogMenu dialog = new TestDialogMenu();
	ResizeFrame jf = new ResizeFrame();
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Yanshi yan = new Yanshi();
		
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	Yanshi(){
		//frame.setUndecorated(true);
		//frame.setOpacity(0.9f);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("延时");
		frame.setBounds(100, 100, 450, 142);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btn_dongHua = new JButton("新动画");
		frame.getContentPane().add(btn_dongHua);
		frame.setVisible(true);
		btn_dongHua.addActionListener(new MyActionListener(btn_dongHua));
		
		JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
		frame.getContentPane().add(internalFrame);
		
		JDesktopPane desktopPane = new JDesktopPane();
		frame.getContentPane().add(desktopPane);
		internalFrame.setVisible(true);
		
		jf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				super.mouseClicked(e);
				if(e.getClickCount() == 2) {
					jf.dispose();
				}
			}
		});
	}
	
	private class MyActionListener implements ActionListener {
		int i = 0;
		int wei = 70;
		int t = 0;
		private Component c;
		
		public MyActionListener(Component c) {
			this.c = c;
		}

		@Override
	    public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
			if(e.getSource() == c) {
				wei = 70;
				t = 0;
				jf.setUndecorated(true);
				jf.setOpacity(0.9f);
				jf.setBounds(frame.getWidth()+150, frame.getHeight()+150, 450, 0);
				//jf.setUndecorated(true);
				
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				Timer timer = new Timer();// 实例化Timer类\
				TimerTask tm = new TimerTask() {
					@Override
					public void run() {
						// TODO 自动生成的方法存根
					}
				};
				timer.schedule(new TimerTask() {
					public void run() {
						//System.out.println("退出");
						jf.setSize(450, wei);
						jf.setBackground(new Color(255,0,0,t));
						jf.repaint();
						wei+=3;
						t+=3;
						if(wei>300 & t>=255) {
							this.cancel();
						}
					}
				}, 0, 3);// 这里百毫秒

				 
			    System.out.println("本程序存在5秒后自动退出");
				jf.setVisible(true);
			}
	    }
	}

	
}
