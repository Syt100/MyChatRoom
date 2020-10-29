/**
 * 
 */
package test;

import java.sql.*;

/**
 * @author xuxin
 *
 */
public class MySQLTest {
	// MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/MYQQ?useSSL=false&serverTimezone=UTC";

	// 数据库的用户名与密码，需要根据自己的设置
	private static final String USER = "root";
	private static final String PASS = "123";

	/**
	 * 
	 */
	public MySQLTest() {
		// TODO 自动生成的构造函数存根
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;

		try {
			// 注册 JDBC 驱动
			Class.forName(JDBC_DRIVER);

			// 打开链接
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// 执行查询
			System.out.println("实例化Statement对象...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM user";
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				int id = rs.getInt("id");
				String name = rs.getString("user_nick");
				String url = rs.getString("user_real_name");

				// 输出数据
				System.out.print("id: " + id);
				System.out.print(", user_nick: " + name);
				System.out.print(", user_real_name: " + url);
				System.out.print("\n");
			}
			
			// 增加
//			String sql_up = "INSERT INTO USER(user_id,user_nick,user_password) "
//					+ "VALUES(8899,'嘿嘿','111'),"
//					+ "(1234,'时跃天','111'),"
//					+ "(2009,'木木夕','111')";
//			stmt.execute(sql_up);
			
			// 删除
			//String sql_del = "DELETE FROM USER";
			//stmt.execute(sql_del);
			
			// 完成后关闭
			rs.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

}
