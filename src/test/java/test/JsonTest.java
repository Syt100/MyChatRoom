/**
 * 
 */
package test;

import bean.Message;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 阿里巴巴的FastJson的测试类
 * @author xuxin
 *
 */
public class JsonTest {

	/**
	 * 
	 */
	public JsonTest() {
		// TODO 自动生成的构造函数存根
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Message msg = new Message();
		msg.setStatus(1);
		msg.setTargetId("kh123456");
		msg.setText("你好");
		
		String jsonString = JSON.toJSONString(msg);
		System.out.println(jsonString);
		
		JSONObject jsonObject = JSON.parseObject(jsonString);
		int a = (int) jsonObject.get("status");
		int b = jsonObject.getInteger("status");
		System.out.println(a);
		System.out.println(b);
	}

}
