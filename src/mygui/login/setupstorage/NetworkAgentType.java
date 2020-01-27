package mygui.login.setupstorage;

/**
 * 登录界面网络代理类型
 * 
 * @author xuxin
 *
 */
public enum NetworkAgentType {

	/**
	 * 不使用代理
	 */
	None,
	/**
	 * HTTP代理
	 */
	HTTPAgent,
	/**
	 * SOCKS5代理
	 */
	SOCKS5Agent,
	/**
	 * 使用浏览器设置
	 */
	UseBrowserAgent
}
