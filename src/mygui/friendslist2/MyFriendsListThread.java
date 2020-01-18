package mygui.friendslist2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.alibaba.fastjson.JSONObject;

/**
 * @author xuxin
 *
 */
public class MyFriendsListThread extends Thread {
	// 好友列表引用
	private MyFriendsList3 friendsList;
	
	// 网络
	/** 接收从好友列表页面传来的socket */
	private Socket socket = null;
	/** 管道输出流，向聊天线程发消息 */
	private PipedWriter pipOut = null;
	/** 接收从好友列表页面传来的in输入流，接收服务器消息 */
	private BufferedReader in = null;

	/**
	 * 
	 */
	public MyFriendsListThread(ThreadGroup group, String name, MyFriendsList3 myFriendsList) {
		super(group, name);
		this.friendsList = myFriendsList;
		this.socket = myFriendsList.getSocket();
		pipOut = new PipedWriter();
		this.in = myFriendsList.getIn();
	}

	@Override
	public void run() {
		if (socket == null) {
			return;// TODO 错误处理
		}
		try {
			while(true) {
				String received = in.readLine();
				JSONObject jo = JSONObject.parseObject(received);
				String type = jo.getString("type");
				
				if(type != null && type.equals("talk") && pipOut != null) {
					pipOut.write(received);
					pipOut.flush();
					System.out.println(getClass() + received);
				}
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * @return pipOut
	 */
	public PipedWriter getPipOut() {
		return pipOut;
	}

}
