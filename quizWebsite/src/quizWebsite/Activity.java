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
	public int quizId;
	public int user_id;
	public String description;
	public Integer duration; 
	private static Statement stmt;
	private static Connection connection = myDBinfo.getConnection();

	// type can be 1 or 2
	public Activity(int user_id,Timestamp time,int type, 
			double score, int quizId, int duration){
		this.time = time;
		this.type = type;
		this.score = score;
		this.quizId = quizId;
		this.user_id = user_id;
		this.duration = duration; 
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
			String query = "Insert into Activity values (" + user_id + ",\"" + time 
					+ "\"," + type + "," + score + "," + quizId + "," + duration + ");";
			System.out.println(" adding activity : " + query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<Activity> checkAch = getActivity(user_id);
		int takenCt = 0;
		int createCt = 0;
		for(Activity a:checkAch){
			if(type == 1 && a.type == 1)
				createCt++;
			else if(type == 0 && a.type == 0)
				takenCt++;
		}
		if (createCt == 1){
			Achievement ach = new Achievement(user_id, 1, new Timestamp(System.currentTimeMillis()));
			ach.addAchievement();
		}
		if (createCt == 5){
			Achievement ach = new Achievement(user_id, 2, new Timestamp(System.currentTimeMillis()));
			ach.addAchievement();
		}
		if (createCt == 10){
			Achievement ach = new Achievement(user_id, 3, new Timestamp(System.currentTimeMillis()));
			ach.addAchievement();
		}
		if (takenCt == 10){
			Achievement ach = new Achievement(user_id, 4, new Timestamp(System.currentTimeMillis()));
			ach.addAchievement();
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
				int duration = rs.getInt("durationMS");
				Activity temp = new Activity(user_id,time,type,score,
						quizId,duration);
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<Activity> getActivityType(int type) {
		ArrayList<Activity> list = new ArrayList<Activity>();
		ResultSet rs;
		try {
			rs = connection.prepareStatement("SELECT * FROM Activity WHERE type = " + type + " ORDER BY time DESC").executeQuery();	
			while (rs.next()) {
				Timestamp time = rs.getTimestamp("time");
				int user_id = rs.getInt("user_id");
				double score = rs.getDouble("score");
				int quizId = rs.getInt("pKey");
				int duration = rs.getInt("durationMS");
				Activity temp = new Activity(user_id,time,type,score,
							quizId,duration);
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static int numQuizzesTaken() {
		try {
			ResultSet result = connection.prepareStatement("SELECT COUNT(*) FROM User Where type = 2").executeQuery();	
			int numTaken = 0;			
			if (result.next()) {
				numTaken += result.getInt(1);
			}
			//System.out.println(numUsers);
			return numTaken;
		} catch (SQLException e) {
			return 0;
		}
	}
	

}
