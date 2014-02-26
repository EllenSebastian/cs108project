package quizWebsite;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletContext;

import com.mysql.jdbc.Connection;

public class Achievements {
	private static Statement stmt;
	protected ServletContext context;

	public Achievements() {
	}

	public void addAchievement(String user, String ach) {
		java.sql.Connection connection = (Connection) context
				.getAttribute("Connection");
		ResultSet rs;
		ArrayList<String> achArray = new ArrayList<String>();
		String achievs = "";
		try {
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + myDBinfo.MYSQL_DATABASE_NAME);
			rs = stmt.executeQuery("SELECT achievements FROM users where user = "
							+ (char) 34 + user + (char) 34);

			achievs = rs.getString("achievements");
			String returnString = "" + achievs + " " + ach;
			stmt.executeQuery("UPDATE users SET achievements = " +
					(char)34 + returnString + (char)34 +
					" WHERE user = " + (char)34 + user + (char)34);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getAchievements(String user) {
		java.sql.Connection connection = (Connection) context
				.getAttribute("Connection");
		ResultSet rs;
		ArrayList<String> achArray = new ArrayList<String>();
		String achievs = "";
		try {
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + myDBinfo.MYSQL_DATABASE_NAME);
			rs = stmt
					.executeQuery("SELECT achievements FROM users where user = "
							+ (char) 34 + user + (char) 34);

			achievs = rs.getString("achievements");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		achArray = (ArrayList<String>) Arrays.asList(achievs.split(" "));
		return achArray;
	}

}
