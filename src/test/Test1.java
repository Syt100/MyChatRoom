package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;

public class Test1 extends JFrame {

	private JPanel contentPane;

	private Point point = new Point(0,0);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test1 frame = new Test1();
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
	public Test1() {
		setBackground(Color.WHITE);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		addMouseListener(new MyMouseAdapter(this));
		addMouseMotionListener(new MyMouseAdapter(this));
	}
	
	private class MyMouseAdapter extends MouseAdapter {
		private Component c;

		public MyMouseAdapter(Component c) {
			this.c = c;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO 自动生成的方法存根
			Point p = c.getLocation();
			//System.out.println(p.x);
			c.setLocation(e.getX() - point.x + p.x, e.getY() - point.y + p.y);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO 自动生成的方法存根
			point.x = e.getX();
			point.y = e.getY();
			//System.out.println(point.x);
		}

	}

}
