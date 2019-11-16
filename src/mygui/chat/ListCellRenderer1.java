package mygui.chat;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class ListCellRenderer1 extends JPanel implements ListCellRenderer<Object>{

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		// TODO 自动生成的方法存根
		BubbleNode bubble = (BubbleNode)value;
		bubble.setSize(getWidth(), 80);
		bubble.textArea.setText("wewhwoiuc美术馆从特产没喝过了没合同了满城桃李没吃过她回来了");
		return bubble;
	}

}
