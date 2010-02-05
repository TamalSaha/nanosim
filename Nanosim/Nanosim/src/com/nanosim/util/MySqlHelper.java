package com.nanosim.util;

public class MySqlHelper extends SqlHelperBase implements ISqlHelper {

	public MySqlHelper() {

		// this.DRIVER_CLASS = "com.mysql.jdbc.Driver";
		// this.URL = "jdbc:mysql://dbm2.itc.virginia.edu/nanosim";
		// this.USERNAME = "ypa5q";
		// this.PASSWORD = "nanosim";

		this.DRIVER_CLASS = "com.mysql.jdbc.Driver";
		this.URL = "jdbc:mysql://localhost/nanosim2010";
		this.USERNAME = "root";
		this.PASSWORD = "3366";
	}
}