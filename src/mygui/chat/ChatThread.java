package mygui.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import bean.Message;

/**
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
	
	/**
	 * 
	 */
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
			while (true) {
				String received = in.readLine();
				System.out.println(getClass() + received);
				
				JSONObject jo = JSON.parseObject(received);
				String type = jo.getString("type");
				String targetName = jo.getString("targetName");
				String selfName = jo.getString("selfName");
				String text = jo.getString("text");
				
				if (type != null && type.equals("talk")) {
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

}
