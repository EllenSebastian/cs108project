package quizWebsite;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Connection;
import javax.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;


public class Achievement{

	private static Connection connection = myDBinfo.getConnection();
	
	public static final int AMATEUR_AUTHOR = 1;
	public static final int PROLIFIC_AUTHOR = 2;
	public static final int PRODIGIOUS_AUTHOR = 3;
	public static final int QUIZ_MACHINE = 4;
	public static final int GREATEST = 5;
	public static final int PRACTICE_MODE = 6;
	public final int user_id;
	public final int achievement_id;
	public final String description;
	public final String title;
	public Timestamp time;

	public Achievement(int user_id, int achievement_id, Timestamp time) {
		this.user_id = user_id;
		this.achievement_id = achievement_id;
		this.time = time;
		switch (achievement_id) {
			case 1:
				description = " created one quiz.";
				title = "Amateur Author";
				break;
			case 2:
				description = " created five quizzes.";
				title = "Prolific Author";
				break;
			case 3:
				description = " created ten quizzes.";
				title = "Prodigious Author";
				break;
			case 4:
				description = " took ten quizzes.";
				title = "Quiz Machine";
				break;
			case 5:
				description = " got the highest score on a quiz.";
				title = "You're the Greatest";
				break;
			case 6:
				description = " took a practice quiz.";
				title = "Practice Makes Perfect";
				break;
			default:
				description = "";
				title = "";
		}
	}
	public void addAchievement(int user_id, int achievement_id) {
		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			String stmt = "INSERT INTO Achievement (user_id, achievement_id, timestamp) VALUES (" + user_id + ", " + achievement_id + ", '" + time + "')";
			PreparedStatement p = connection.prepareStatement(stmt);
			p.executeUpdate();
		} catch (SQLException ignored) {  
		}
	}
	public static ArrayList<Achievement> getAchievement(int user_id) {
		ResultSet r;
		ArrayList<Achievement> list = new ArrayList<Achievement>();
		try {
			r = connection.prepareStatement("SELECT * FROM Achievement WHERE user_id = " + user_id).executeQuery();
			while (r.next()) {
				Achievement a = new Achievement(r.getInt("user_id"), r.getInt("achievement_id"), r.getTimestamp("timestamp"));
				list.add(a);
			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}
	
	
}
