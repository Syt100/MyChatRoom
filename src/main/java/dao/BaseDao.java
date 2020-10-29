package dao;

import database.DataBaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xuxin
 */
public class BaseDao {

	private Connection connect;
	private ResultSet resultSet;

	/**
	 *
	 */
	public BaseDao() {
		// TODO 自动生成的构造函数存根
	}

	public ResultSet select(String sql) {
		System.out.println(sql);
		connect = DataBaseConnect.getConnect();
		try {
			PreparedStatement statement = connect.prepareStatement(sql);
			resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	public int operate(String sql) {
		System.out.println(sql);
		connect = DataBaseConnect.getConnect();
		try {
			PreparedStatement statement = connect.prepareStatement(sql);
			return statement.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return 0;
	}
}
