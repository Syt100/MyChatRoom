package util;

/**
 * 尝试将一些常量外部化
 * @author xuxin
 *
 */
public class ConstantStatus {
	/** 默认的注册面板中确认按钮状态，0：未点击，1：已点击 */
	public static int CONFIRM_BUTTON_STATUS = 0;
	
	/** 登录状态：登陆成功 */
	public static final int LOGIN_STATUS_SUCCESS = 0;
	/** 登录状态：账号不存在 */
	public static final int LOGIN_STATUS_ACCOUNT_NOT_EXIST = 1;
	/** 登录状态：密码错误 */
	public static final int LOGIN_STATUS_ERROR_PASSWORD = 2;
	/** 登录状态：账号或密码为空 */
	public static final int LOGIN_STATUS_EMPTY_INPUT = 3;
}
