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
	
	public Achievements getAchievements(){
		
	}
	
	public Activity getActivity(){
		
	}
	
	public Friends getFriends(){
		
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
