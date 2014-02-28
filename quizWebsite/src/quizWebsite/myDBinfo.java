package quizWebsite;

import java.sql.*;


public class myDBinfo {

		public static final String MYSQL_USERNAME = "ccs108ellens2";
		public static final String MYSQL_PASSWORD = "eekohgoo";
		public static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
		public static final String MYSQL_DATABASE_NAME = "c_cs108_ellens2";


		private static Connection con;

		static {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String url = "jdbc:mysql://" + MYSQL_DATABASE_SERVER + "/" + MYSQL_DATABASE_NAME;
				con = DriverManager.getConnection(url, MYSQL_USERNAME, MYSQL_PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("CS108 student: Update the MySQL constants to correct values!");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.err.println("CS108 student: Add the MySQL jar file to your build path!");
			}
		}

		public static Connection getConnection() {
			return con;
		}

		public static void close() {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}