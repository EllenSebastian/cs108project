package quizWebsite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Timestamp;

import java.util.Comparator;
import java.util.List;
import java.util.Collections;

import javax.servlet.ServletContext;

import java.sql.Connection;

public class Activity {

	Timestamp time;
	String type;
	double score;
	int quizId;
	String name;

	private static Statement stmt;
	private static Connection connection = myDBinfo.getConnection();

	// type: "created a quiz" and "took a quiz"
	public Activity(String name,Timestamp time, String type, 
			double score, int quizId){
		this.time = time;
		this.type = type;
		this.score = score;
		this.quizId = quizId;
		this.name = name;
	}

	public static class activityComparator implements Comparator<Activity> {
	    @Override
	    public int compare(Activity o1, Activity o2) {
	        return o1.time.compareTo(o2.time);
	    }
	}

	public static void sortByTime(List<Activity> act) {
		Collections.sort(act, new activityComparator());
	}

	public void addAcitivity() {
		try {
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + myDBinfo.MYSQL_DATABASE_NAME);
			stmt.executeQuery("INSERT into Activity VALUES (" 
					+ (char) 34 + name + (char) 34 + "," + (char) 34
					+ time + (char) 34 + "," + (char) 34 + type
					+ (char) 34 + "," + score + "," + 
					(char) 34 + quizId + (char) 34 + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Activity> getActivity(int user_id) {
		ArrayList<Activity> list = new ArrayList<Activity>();
		ResultSet rs;
		try {
			Statement stmt = connection.createStatement();
			stmt.executeQuery("USE " + myDBinfo.MYSQL_DATABASE_NAME);
			rs = stmt.executeQuery("SELECT * FROM Activity where user_id = "
					+ (char) 34 + user_id + (char) 34);

			while (rs.next()) {
				String name = rs.getString("name");
				Timestamp time = rs.getTimestamp("time");
				String type = rs.getString("type");
				double score = rs.getDouble("score");
				int quizId = rs.getInt("quizID");
				Activity temp = new Activity(name, time, type, score,
						quizId);
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}