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
import java.sql.Statement;


public class User {

	int user_id;
	private String name;
	private String password;
	private boolean isAdmin;

	private static Connection connection = myDBinfo.getConnection();
		
    
	// id refer to the key of this particular user in database
	public User(int user_id,String name,String password,boolean isAdmin){
		this.user_id = user_id;
		this.name = name;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public String name(){
		return name;
	}
	
	public Boolean isAdmin(){
		return isAdmin;
	}
	
	public Announcement[] getAnnouncements(){
		return Announcement.getAnnouncements();
	}

	
	public ArrayList<Achievement> getUserAchievements() {
		return Achievement.getAchievement(user_id);
	}
	
	
	public ArrayList<Activity> getUserActivities() {
		return Activity.getActivity(user_id);
	}
	
	public ArrayList<Integer> getUserFriends(){
		return Friends.getFriends(user_id);
	}
	
	public List<Activity> getFriendsActivity() {
		ArrayList<Activity> act = new ArrayList<Activity>();
		ArrayList<Integer> friends = getUserFriends();
		for (int i = 0; i < friends.size(); i++) {
			act.addAll(Activity.getActivity(friends.get(i)));
		}
		Activity.sortByTime(act);
		return act;
	}
	
	public ArrayList<Message> getUserMessages(){
		return Message.getMessages(user_id);
	}

}
