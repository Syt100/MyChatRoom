package mygui.login.setupstorage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 存储登录界面设置的相关项
 * 
 * @author xuxin
 *
 */
public class SetUpStorage {

	/** 登录界面背景图片地址 */
	public String backgroundImagePath;
	
	/** 登录界面网络代理类型 */
	public NetworkAgentType networkAgentType = NetworkAgentType.None;
	/** 登录界面网络代理地址 */
	public String networkAgentAddress;
	/** 登录界面网络代理端口 */
	public int networkAgentPort;
	/** 登录界面网络代理用户名 */
	public String networkAgentUserName;
	/** 登录界面网络代理密码 */
	public String networkAgentPassword;
	/** 登录界面网络代理域 */
	public String networkAgentField;
	
	/** 登录界面登录服务器类型 */
	public LogonServerType logonServerType= LogonServerType.Default;
	/** 登录界面登录服务器地址 */
	public String loginAddress;
	/** 登录界面登录服务器端口 */
	public int loginPort;
	
	private static SetUpStorage setStorage;
	
	
	public static void main(String[] args) {
		SetUpStorage set = SetUpStorage.getStorage();
		set.backgroundImagePath = "123";
		set.networkAgentType = NetworkAgentType.HTTPAgent;
		set.networkAgentAddress = "123.234";
		set.writeToFile();
		try {
			SetUpStorage.loadSetting();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	private SetUpStorage() {
		
	}
	
	public static SetUpStorage getStorage() {
		if (setStorage == null) {
			setStorage = new SetUpStorage();
			return setStorage;
		} else {
			return setStorage;
		}
	}
	
	/**
	 * 将设置写入到文件
	 */
	public void writeToFile() {
		String set = JSONObject.toJSONString(setStorage);
		//System.out.println(getClass()+set);
		File file = new File("./data/setting.txt");
		
		try {
			if(!file.exists()) {
				file.getParentFile().mkdir();
			}
			FileWriter fo = new FileWriter(file);
			fo.write(set);
			fo.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * 从文件读取设置，会覆盖原有的设置
	 * @throws IOException
	 * @throws Exception
	 */
	public static void loadSetting() throws IOException,Exception {
		File file = new File("./data/setting.txt");
		if(!file.exists()) {
			//file.getParentFile().mkdir();
			throw new Exception("加载设置失败！找不到设置文件");
		}
		
		FileReader fi = new FileReader(file);
		BufferedReader bfi = new BufferedReader(fi);
		String read = null;
		String jsonString = "";
		while ((read = bfi.readLine())!=null) {
			jsonString = jsonString + read;
		}
		bfi.close();
		setStorage = JSON.parseObject(jsonString, SetUpStorage.class);
	}

	@Override
	public String toString() {
		return "SetUpStorage [backgroundImagePath=" + backgroundImagePath + ", networkAgentType=" + networkAgentType
				+ ", networkAgentAddress=" + networkAgentAddress + ", networkAgentPort=" + networkAgentPort
				+ ", networkAgentUserName=" + networkAgentUserName + ", networkAgentPassword=" + networkAgentPassword
				+ ", networkAgentField=" + networkAgentField + ", logonServerType=" + logonServerType
				+ ", loginAddress=" + loginAddress + ", loginPort=" + loginPort + "]";
	}
}
