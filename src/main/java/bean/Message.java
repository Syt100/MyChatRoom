/**
 * 
 */
package bean;

import java.util.ArrayList;

/**
 * 此类对象用于表示一条具体的消息
 * @author xuxin
 *
 */
public class Message {

	/** 消息类型，有test,login,register,online,tallk等 */
	private String type;
	/** 状态，是否注册成功 */
	private int status;
	/** 要发送给的目标用户，一般省略，注册的时候用 */
	private Users targetUser;
	/** 要发送给的目标用户ID */
	private String targetId;
	/** 要发送给的目标用户名 */
	private String targetName;
	
	/** 来源用户，一般省略，注册的时候用 */
	private Users selfUser;
	/** 来源Id，自身的 */
	private String selfId;
	/** 自身的name */
	private String selfName;
	
	/** 消息的文本 */
	private String text;
	/** 请求操作的指令 */
	private String operation;
	
	/** 列表，表示一系列指令 */
	private ArrayList<String> list;
	
	/**
	 * 默认无参构造方法
	 */
	public Message() {
		
	}

	/**
	 * @param targetId
	 * @param text
	 */
	public Message(String targetId, String text) {
		this.targetId = targetId;
		this.text = text;
	}
	
	/**
	 * 除status外其余字段全部置为空
	 */
	public void clearIgnoreStatus() {
		this.type = null;
		this.targetUser = null;
		this.targetId = null;
		this.targetName = null;
		this.selfUser = null;
		this.selfId = null;
		this.selfName = null;
		this.text = null;
		this.operation = null;
		this.list = null;
	}
	
	/**
	 * 将所有字段置为空
	 */
	public void clearAll() {
		this.status = 0;
		clearIgnoreStatus();
	}

	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type 要设置的 type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status 要设置的 status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return targetUser
	 */
	public Users getTargetUser() {
		return targetUser;
	}

	/**
	 * @param targetUser 要设置的 targetUser
	 */
	public void setTargetUser(Users targetUser) {
		this.targetUser = targetUser;
	}

	/**
	 * @return targetId
	 */
	public String getTargetId() {
		return targetId;
	}

	/**
	 * @param targetId 要设置的 targetId
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	/**
	 * @return targetName
	 */
	public String getTargetName() {
		return targetName;
	}

	/**
	 * @param targetName 要设置的 targetName
	 */
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	/**
	 * @return selfUser
	 */
	public Users getSelfUser() {
		return selfUser;
	}

	/**
	 * @param selfUser 要设置的 selfUser
	 */
	public void setSelfUser(Users selfUser) {
		this.selfUser = selfUser;
	}

	/**
	 * @return selfId
	 */
	public String getSelfId() {
		return selfId;
	}

	/**
	 * @param selfId 要设置的 selfId
	 */
	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}

	/**
	 * @return selfName
	 */
	public String getSelfName() {
		return selfName;
	}

	/**
	 * @param selfName 要设置的 selfName
	 */
	public void setSelfName(String selfName) {
		this.selfName = selfName;
	}

	/**
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text 要设置的 text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation 要设置的 operation
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return list
	 */
	public ArrayList<String> getList() {
		return list;
	}

	/**
	 * @param list 要设置的 list
	 */
	public void setList(ArrayList<String> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Message{" +
				"type='" + type + '\'' +
				", status=" + status +
				", targetUser=" + targetUser +
				", targetId='" + targetId + '\'' +
				", targetName='" + targetName + '\'' +
				", selfUser=" + selfUser +
				", selfId='" + selfId + '\'' +
				", selfName='" + selfName + '\'' +
				", text='" + text + '\'' +
				", operation='" + operation + '\'' +
				", list=" + list +
				'}';
	}
}
