package bean;

import domain.UserInfo;
import util.XMLOperation;

/**
 * @author xuxin
 */
public class Users {
	private String id;
	private String name;
	private String signature;
	private String password;
	private String friends;

	/**
	 * 默认的无参构造方法
	 */
	public Users() {

	}

	/**
	 * @param id
	 * @param password
	 */
	public Users(String id, String password) {
		this.id = id;
		this.password = password;
	}

	/**
	 * @param id
	 * @param name
	 * @param password
	 * @param friends
	 */
	public Users(String id, String name, String password, String friends) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.friends = friends;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id 要设置的 id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return friends
	 */
	public String getFriends() {
		return friends;
	}

	/**
	 * @param friends 要设置的 friends
	 */
	public void setFriends(String friends) {
		this.friends = friends;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password 要设置的 password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public boolean isAccountExitById(Users users) {
		XMLOperation xml = new XMLOperation();
		boolean flag = xml.isElementIdExit(users.getId());
		return flag;
	}

	public String getPassWordById(String id) {
		XMLOperation xml = new XMLOperation();
		if (!xml.isElementIdExit(id)) {
			return null;
		}
		Users user = xml.getUsersById(id);
		return user.getPassword();
	}

	/**
	 * 将userInfo类转换为Users类
	 *
	 * @param userInfo UserInfo类对象
	 * @return Users类对象
	 */
	public static Users valueOf(UserInfo userInfo) {
		if (userInfo != null) {
			Users users = new Users();
			users.setId(String.valueOf(userInfo.getId()));
			users.setName(userInfo.getUser_name());
			users.setSignature(userInfo.getUser_signature());
			return users;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Users{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", signature='" + signature + '\'' +
				", password='" + password + '\'' +
				", friends='" + friends + '\'' +
				'}';
	}
}
