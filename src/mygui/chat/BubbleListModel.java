package mygui.chat;



import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

public class BubbleListModel extends DefaultListModel<BubbleNode>{
	private List<BubbleNode> imageFile = new ArrayList<BubbleNode>();
	public void addElement(BubbleNode file){
		this.imageFile.add(file);
	}
	public int getSize(){
    	return imageFile.size();
    }
    public BubbleNode getElementAt(int index){
		return imageFile.get(index);
	}
//————————————————
//版权声明：本文为CSDN博主「Silly-77」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/qq_40064948/article/details/81273790

}
