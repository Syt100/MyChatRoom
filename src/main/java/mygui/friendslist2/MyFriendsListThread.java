package mygui.friendslist2;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户端通过此线程与服务端交互
 * 
 * @author xuxin
 *
 */
public class MyFriendsListThread extends Thread {

	/** 好友列表引用 */
	private MyFriendsList3 friendsList;

	// 网络
	/** 接收从好友列表页面传来的socket */
	private Socket socket;

	// 流
	/** 接收从好友列表页面传来的out输出流，向服务器发送消息 */
	private PrintWriter out;
	/** 接收从好友列表页面传来的in输入流，接收服务器消息 */
	private BufferedReader in;

	/**
	 * 
	 */
	public MyFriendsListThread(ThreadGroup group, String name, MyFriendsList3 myFriendsList) {
		super(group, name);
		this.friendsList = myFriendsList;
		this.socket = myFriendsList.getSocket();
		this.out = myFriendsList.getOut();
		this.in = myFriendsList.getIn();
	}

	@Override
	public void run() {
		if (socket == null) {
			return;// TODO 错误处理
		}
		try {
			while (true) {
				String received = in.readLine();
				JSONObject jo = JSONObject.parseObject(received);
				String type = jo.getString("type");

				// 收到服务端的聊天消息
				if (type != null && type.equals("talk")) {
					friendsList.sendToChat(jo);// 由好友列表代理向所有的已经打开的聊天窗口发消息
					System.out.println(getClass() + received);
				}
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
