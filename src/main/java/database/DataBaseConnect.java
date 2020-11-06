/**
 *
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接操作
 *
 * @author xuxin
 *
 */
public class DataBaseConnect {
	private static Connection connect;

	private DataBaseConnect() {

	}

	public static Connection getConnect() {
		try {
			if (null == connect) {
				String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
				String DB_URL = "jdbc:mysql://localhost:3306/MyQQ?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";

				Class.forName(JDBC_DRIVER);
				connect = DriverManager.getConnection(DB_URL, "root", "123456");
			}
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return connect;
	}

	public static void close() {
		if (null != connect) {
			try {
				connect.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

}
