package mygui.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import bean.Message;

/**
 * 聊天界面的网络、流处理线程，读取从服务端收到的消息，并显示在聊天界面上。
 * @author xuxin
 *
 */
public class ChatThread extends Thread {

	/** chat对象的引用 */
	private Chat chat;
	
	/** 接收从好友列表页面传来的out输出流 */
	private PrintWriter out;
	/** 接收从好友列表页面传来的in输入流 */
	private BufferedReader in;
	
	/** 是否已经关闭窗口 */
	private boolean isCloseed = true;
	
	public ChatThread() {

	}

	/**
	 * @param chat
	 * @param out
	 * @param in
	 */
	public ChatThread(Chat chat, PrintWriter out, BufferedReader in) {
		this.chat = chat;
		this.out = out;
		this.in = in;
	}

	@Override
	public void run() {
		try {
			while (isCloseed) {
				String received = in.readLine();
				System.out.println(getClass() + received);
				
				JSONObject jo = JSONObject.parseObject(received);
				String type = jo.getString("type");
				String selfId = jo.getString("selfId");
				String selfName = jo.getString("selfName");
				String text = jo.getString("text");
				
				if (type != null && type.equals("talk") && !chat.getThisUser().getId().equals(selfId)) {
					chat.addMessagetoPanel(false, selfName, text);
				}
				
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	protected void sendMessage(Message msg) {
		out.println(JSON.toJSONString(msg));
		out.flush();
	}
	
	protected void close() {
		isCloseed = false;
		System.out.println("聊天窗口已关闭");
	}

}
