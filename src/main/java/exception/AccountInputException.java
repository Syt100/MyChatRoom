package exception;

import util.ConstantStatus;

/**
 * 自定义异常。可以根据状态返回字符串的错误信息
 * 
 * @author xuxin
 *
 */
public class AccountInputException extends RuntimeException {

	/**
	 * 已生成的串行版本标识
	 */
	private static final long serialVersionUID = 2899278398708610647L;

	/**
	 * 登录、注册等的账号状态
	 */
	private int status;

	/**
	 * 错误信息
	 */
	private String message;

	/**
	 * @param st 状态
	 */
	public AccountInputException(int st) {
		this.status = st;
		judgeStatus();
	}

	/**
	 * @param message
	 */
	public AccountInputException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * @param cause
	 */
	public AccountInputException(Throwable cause) {
		super(cause);
		// TODO 自动生成的构造函数存根
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AccountInputException(String message, Throwable cause) {
		super(message, cause);
		// TODO 自动生成的构造函数存根
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public AccountInputException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

	/**
	 * 根据状态参数回报文字说明
	 */
	private void judgeStatus() {
		if (status == ConstantStatus.LOGIN_STATUS_SUCCESS) {
			message = "登陆成功";
		} else if (status == ConstantStatus.LOGIN_STATUS_ERROR_PASSWORD) {
			message = "密码错误";
		} else if (status == ConstantStatus.LOGIN_STATUS_ACCOUNT_NOT_EXIST) {
			message = "账号不存在";
		} else if (status == ConstantStatus.LOGIN_STATUS_EMPTY_INPUT) {
			message = "账号或密码为空";
		}
	}
}
