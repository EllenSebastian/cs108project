package quizWebsite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import com.mysql.jdbc.Connection;

public class Activity {

	private static Statement stmt;
	protected ServletContext context;

	public ArrayList<HistoryItem> getUserActivity(String user) {
		ArrayList<HistoryItem> list = new ArrayList<HistoryItem>();
		java.sql.Connection connection = (Connection) context
				.getAttribute("Connection");
		ResultSet rs;
		try {
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + myDBinfo.MYSQL_DATABASE_NAME);
			rs = stmt.executeQuery("SELECT * FROM activity where user = "
					+ (char) 34 + user + (char) 34);

			while (rs.next()) {
				String date = rs.getString("date");
				String type = rs.getString("date");
				;
				double score = rs.getDouble("score");
				String quizId = rs.getString("date");
				;
				HistoryItem temp = new HistoryItem(user, date, type, score,
						quizId);
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void addHistory(HistoryItem hi) {
		java.sql.Connection connection = (Connection) context
				.getAttribute("Connection");
		ResultSet rs;
		try {
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + myDBinfo.MYSQL_DATABASE_NAME);
			stmt.executeQuery("INSERT into activity VALUES (" 
					+ (char) 34 + hi.user + (char) 34 + "," + (char) 34
					+ hi.date + (char) 34 + "," + (char) 34 + hi.type
					+ (char) 34 + "," + hi.score + "," + 
					(char) 34 + hi.quizId + (char) 34 + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
