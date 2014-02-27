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
	
	// retrieve the user information from db and create a user object
	public static User getUser(int id){
		ResultSet r;
		try {
			r = connection.prepareStatement("SELECT * FROM User WHERE user_id = " + id).executeQuery();
			if(!r.next()) return null;
			User user = new User(
					          r.getInt("user_id"),
					          r.getString("name"), 					           	
					          r.getString("password"),
							  r.getInt("isAdmin") == 1 ? true : false
							 );
			return user;
		} catch (SQLException e) {
			return null;
		}
	}
	
	public ArrayList<Achievement> getUserAchievement() {
		return Achievement.getAchievement(user_id);
	}
	
	
	public ArrayList<Activity> getUserActivity() {
		return Activity.getActivity(user_id);
	}
	
	public ArrayList<String> getUserFriends(){
		return Friends.friendsOf(user_id);
	}
	
	public ArrayList<Message> getUserMessages(){
		return Messages.getMessages(user_id);
	}
	
}
