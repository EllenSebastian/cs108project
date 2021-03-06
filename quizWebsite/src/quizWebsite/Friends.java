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
	
	public static ArrayList<Integer> getFriends(int user_id){	
		ResultSet rs;
		ArrayList<Integer> friends = new ArrayList<Integer>();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Friend where user1 = "
					+ (char) 34 + user_id + (char) 34);
			while (rs.next()) {
				Integer friend = rs.getInt("user2");
				System.out.println(rs.getInt("user2"));
				friends.add(friend);
			}
		}catch (SQLException e) {
		}
		return friends;

	}

	public static void addFriend(int user1,int user2){
		try{
			String stmt = "INSERT INTO Friend (user1, user2) VALUES (" + user1 + ", " + user2 + ")";
			PreparedStatement p = connection.prepareStatement(stmt);
			p.executeUpdate();
			String stmt1 = "INSERT INTO Friend (user1, user2) VALUES (" + user2 + ", " + user1 + ")";
			PreparedStatement p1 = connection.prepareStatement(stmt1);
			p1.executeUpdate();
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
			connection.prepareStatement("DELETE FROM Friend WHERE user1 =" + user1 + " AND user2 =" + user2).executeUpdate();
			connection.prepareStatement("DELETE FROM Friend WHERE user1 =" + user2 + " AND user2 =" + user1).executeUpdate();
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
			System.out.println(changed);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// accept or remove the request
	public static void removeFriendRequest(int requestor_id, int requestee_id) {
		try {
			connection.prepareStatement("DELETE FROM Friend_request WHERE requestor_id =" + requestor_id + " AND requestee_id =" + requestee_id).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
