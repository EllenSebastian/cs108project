package quizWebsite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Friends {
	private static Statement stmt;
	private static Connection connection = myDBinfo.getConnection();
	
	public static ArrayList<String> friendsOf(int user_id){	
		ResultSet rs;
		ArrayList<String> friends = new ArrayList<String>();
		try {
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + myDBinfo.MYSQL_DATABASE_NAME);
			rs = stmt.executeQuery("SELECT * FROM Friend where user1 = "
					+ (char) 34 + user_id + (char) 34);
			while (rs.next()) {
				String friend = rs.getString("user2");
				friends.add(friend);
			}
		}catch (SQLException e) {
		}
		return friends;
		
	}
	
	public static void addFriend(int user1,int user2){
		try{
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + myDBinfo.MYSQL_DATABASE_NAME);
			stmt.executeQuery("INSERT into Friend VALUES ("+ (char)34 + user1
					+ (char)34 + "," + (char)34 + user2 + (char)34 + ")");
			stmt.executeQuery("INSERT into Friend VALUES ("+ (char)34 + user2
					+ (char)34 + "," + (char)34 + user1 + (char)34 + ")");
		}catch (SQLException e) {
			
		}
	}
	
	public static boolean checkFriend(int user_id1, int user_id2) {
		try {
			PreparedStatement p = connection.prepareStatement("SELECT * FROM Friend WHERE user1 =" + user_id1 + " AND user2 =" + user_id2);
			ResultSet r = p.executeQuery();
			if(!r.next()) return false;
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static void removeFriend(int user1, int user2) {
		try {
			connection.prepareStatement("DELETE FROM Friend WHERE (user1 =" + user1 + " AND user2 =" + user2).executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	// accompanied by the request message 
	public static void addFriendRequest(int requestor_id, int requestee_id) {
		try {
			PreparedStatement p = connection.prepareStatement("INSERT IGNORE INTO Friend_request (requestor_id,requestee_id) VALUES (?, ?)");
			p.setInt(1, requestor_id);
			p.setInt(2, requestee_id);					
			int changed = p.executeUpdate();
			//if(changed == 0) return false;
			//return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// accept or remove the request
	public static void removeFriendRequest(int requestor_id, int requestee_id) {
		try {
			connection.prepareStatement("DELETE FROM Friend_request WHERE requestor_id =" + requestor_id + " AND requestee_user_id =" + requestee_id).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
