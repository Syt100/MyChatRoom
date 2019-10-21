package test;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class ListDrivers {
	// 列出当前所有的数据库驱动程序
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Enumeration e = DriverManager.getDrivers();
		while(e.hasMoreElements()) {
			Driver d = (Driver)e.nextElement();
			System.out.println(d);
		}
	}

}
