package test;


import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * 提示窗口
 *
 */
public class RemindDialog extends JDialog {
	private static final int TITLE_HEIGHT = 20;
	private JButton exit;
	private JCheckBox set;
	private JTextArea text;
	
	public static void main(String[] arg) {
		RemindDialog a = new RemindDialog("1");
	}
	
	public RemindDialog(String t) {
		this.setTitle("提示");
		//设置窗体大小及显示位置
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
		int width=250,height=150;
		
		this.setBounds(dim.width-width-5,dim.height-height-35 ,width, height);
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		this.setFocusable(false);
		
		
		exit=new JButton("确定");
		set=new JCheckBox("今天不再提醒!",false);
		text=new JTextArea();
		text.setLineWrap(true);
		JScrollPane scrollPane=new JScrollPane(text);
		
		exit.setBounds(130, 125, 60, 22);
		set.setBounds(20, 125, 105, 22);
		scrollPane.setBounds(5,20,240,100);
		text.setEditable(false);
		text.setText(t);
		
		exit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				//set.isSelected();
				dispose();
			}
			
		});
		MouseHandler ml = new MouseHandler();
		addMouseListener(ml);
		addMouseMotionListener(ml);
		
		
		this.setLayout(null);
		this.add(scrollPane);
		this.add(exit);
		this.add(set);
		this.setVisible(true);
		
		
	}

	
	
	public RemindDialog() {
		// TODO 自动生成的构造函数存根
	}



	private class MouseHandler extends MouseInputAdapter {
		private Point point;

		public void mousePressed(MouseEvent e) {
			if (e.getY() <= TITLE_HEIGHT) {
				this.point = e.getPoint();
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (point != null) {
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

				Point p = e.getPoint();
				int dx = p.x - point.x;
				int dy = p.y - point.y;

				int x = getX();
				int y = getY();
				setLocation(x + dx, y + dy);
			}
		}

		public void mouseReleased(MouseEvent e) {
			point = null;
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(0, 0, 128));
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		g.fillRect(0, 0, getWidth(), TITLE_HEIGHT);

		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.white);
		g.drawString(getTitle(), 2, (TITLE_HEIGHT - fm.getHeight()) / 2
				+ fm.getAscent());
	}
	
}


