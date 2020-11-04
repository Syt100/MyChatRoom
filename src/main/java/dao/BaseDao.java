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

    private final Connection connect;
    private ResultSet resultSet;

    /**
     *
     */
    public BaseDao() {
        connect = DataBaseConnect.getConnect();
    }

    public ResultSet select(String sql) {
        System.out.println(sql);
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public int operate(String sql) {
        System.out.println(sql);
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            return statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return 0;
    }

    public void commit() {
        try {
            connect.commit();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public Connection getConnect() {
        return connect;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }
}
