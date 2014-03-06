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

	
	public static final int quiz_Created = 1;
	public static final int quiz_Taken = 2;
	

	public Timestamp time;
	public int type;
	public double score;
	int quizId;
	int user_id;
	public String description;

	private static Statement stmt;
	private static Connection connection = myDBinfo.getConnection();

	// type can be 1 or 2
	public Activity(int user_id,Timestamp time,int type, 
			double score, int quizId){
		this.time = time;
		this.type = type;
		this.score = score;
		this.quizId = quizId;
		this.user_id = user_id;
		switch (type) {
			case 1:
				description = "created a quiz";
				break;
			case 2:
				description = "took a quiz";
				break;
			default:
				description = "";	
		}
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

	public void addActivity() {
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate("INSERT into Activity VALUES (" 
					+ (char) 34 + user_id + (char) 34 + "," + (char) 34
					+ time + (char) 34 + "," + (char) 34 + type
					+ (char) 34 + "," + score + "," + 
					(char) 34 + quizId + (char) 34 + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    
	// get activity and order the result by time. the latest activity will be the first entry.
	public static ArrayList<Activity> getActivity(int user_id) {
		ArrayList<Activity> list = new ArrayList<Activity>();
		ResultSet rs;
		try {
			rs = connection.prepareStatement("SELECT * FROM Activity WHERE user_id = " + user_id + " ORDER BY time DESC").executeQuery();	
			while (rs.next()) {
				Timestamp time = rs.getTimestamp("time");
				int type = rs.getInt("type");
				double score = rs.getDouble("score");
				int quizId = rs.getInt("pKey");
				Activity temp = new Activity(user_id,time,type,score,
						quizId);
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


}
