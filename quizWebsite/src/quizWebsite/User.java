package quizWebsite;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class User {
	
	private String name;
	public int id;
	private String password;
	private boolean isAdmin;
		
	// need to implement the connection to Database
	private static Connection db = myDBinfo.getConnection();
    
	// id refer to the key of this particular user in database
	public User(int id,String name,String password,boolean isAdmin){
		this.name = name;
		this.password = password;
		this.id = id;
		this.isAdmin = isAdmin;
	}
	
	// retrieve the user information from db and create a user object
	public static User getUser(int id){
		ResultSet r;
		try {
			r = db.prepareStatement("SELECT * FROM user WHERE user_id = " + id).executeQuery();
			if(!r.next()) return null;
			User user = new User(r.getInt("user_id"),
					          r.getString("name"), 					           	
					          r.getString("password"),
							  r.getInt("isAdmin") == 1 ? true : false
							 );
			return user;
		} catch (SQLException e) {
			return null;
		}
	}
	
	public ArrayList<String> getAchievements(){
		Achievements achievement = new Achievements();
		return achievement.getAchievements(name);
	}
	
	public void addAchievement(String ach){
		Achievements achievement = new Achievements();
		achievement.addAchievement(name, ach);
	}
	
	public ArrayList<HistoryItem> getActivity(){
		Activity activity = new Activity();
		return activity.getUserActivity(name);
	}
	
	public void addActivity(String user,String date, String type, 
			double score, String quizId){
		HistoryItem hi = new HistoryItem(user, date, type, score, quizId);
		Activity activity = new Activity();
		activity.addHistory(hi);
	}
	
	public ArrayList<String> getFriends(){
		Friends friends = new Friends();
		return friends.friendsOf(name);
	}
	
	public void addFriend(String name, String friend){
		Friends friends = new Friends();
		friends.addFriend(name, friend);
	}
	
	public ArrayList<Message> getMessages(){
		Messages messages = new Messages();
		return messages.getUserMessages(name);
	}
	
	public void sendMessage(String type, String body, String quizId, String toUser){
		Message msg = new Message(false, type, body, quizId, name, toUser);
		Messages messages = new Messages();
		messages.sendMessage(msg);
	}
	
}
