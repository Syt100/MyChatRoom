package mygui.friendslist;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class FriendNode extends DefaultMutableTreeNode {

	protected ImageIcon icon;
	protected long userAccount;
	protected String NickName;
	protected int isOnline;

	public FriendNode() {

	}

	/**
	 * 只包含好友昵称和是否在线的节点构造函数
	 * 
	 * @param NickName
	 * @param isOnline
	 */
	public FriendNode(String NickName, int isOnline) {
		super();
		this.NickName = NickName;
		this.isOnline = isOnline;
	}

	/**
	 * 包含好友头像、昵称、是否在线的节点构造函数
	 * 
	 * @param icon
	 * @param NickName
	 * @param isOnline
	 */
	public FriendNode(ImageIcon icon, String NickName, int isOnline) {
		super();
		this.icon = icon;
		this.NickName = NickName;
		this.isOnline = isOnline;
	}

	/**
	 * 包含好友头像、昵称、是否在线、用户账号的节点构造函数
	 * 
	 * @param icon
	 * @param NickName
	 * @param isOnline
	 * @param userAccount
	 */
	public FriendNode(ImageIcon icon, String NickName, int isOnline, long userAccount) {
		super();
		this.icon = icon;
		this.NickName = NickName;
		this.isOnline = isOnline;
		this.userAccount = userAccount;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public long getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}

	public long getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(long userAccount) {
		this.userAccount = userAccount;
	}
}
