/**
 * 
 */
package test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author xuxin
 *
 */
public class FrameDemo extends JFrame implements ActionListener {
	JButton jb;

	public FrameDemo() {
		jb = new JButton("   <<静夜思>>   ");
		jb.addActionListener(this);

		JPanel jp = new JPanel();
		jp.add(jb);

		add(jp);
		// 窗口属性的设置
		setTitle("Frame窗口");// 标题
		setSize(256, 100);// 窗口大小
		setLocationRelativeTo(null);// 窗口居中
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new FrameDemo().setVisible(true);// 创建窗口实例, 并让窗口可见
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		// 计算弹出框的位置
		int x = jb.getLocationOnScreen().x + jb.getWidth() / 2 - 100;
		int y = jb.getLocationOnScreen().y + jb.getHeight();
		MyDialog md = new MyDialog(x, y);

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 40; i < 200; i += 2) {
					md.setSize(200, i);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		});
		t.start();// 启动线程
	}

}
class MyDialog extends JDialog {
    public MyDialog(int x, int y) {
        setTitle("静夜思");
        JLabel jl = new JLabel("<html><body>床前明月光,疑是地上霜.<br />举头望明月,低头思故乡.<br /></div></body></html>");
        add(jl);
        setSize(200, 40);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(x, y);
        setVisible(true);
        setAlwaysOnTop(true);
    }
}