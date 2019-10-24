/**
 * 
 */
package bean;

import util.XMLOperation;

/**
 * @author xuxin
 *
 */
public class Users {
	private String name;
	private String password;
	
	/**
	 * 
	 */
	public Users() {
		// TODO 自动生成的构造函数存根
	}

	/**
	 * @param name
	 * @param password
	 */
	public Users(String name, String password) {
		this.name = name;
		this.password = password;
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

	public boolean isAccountExit(Users users) {
		XMLOperation xml = new XMLOperation();
		boolean flag = xml.isElementNameExit(users.getName());
		return flag;
	}
}
