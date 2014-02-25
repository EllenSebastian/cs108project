package quizWebsite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import com.mysql.jdbc.Connection;

public class Friends {
	private static Statement stmt;
	protected ServletContext context;
	
	public Friends(){
		
	}
	
	public ArrayList<String> friendsOf(String user){
		java.sql.Connection connection = (Connection) context.getAttribute("Connection");
		ResultSet rs;
		ArrayList<String> friends = new ArrayList<String>();
		try {
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + myDBinfo.MYSQL_DATABASE_NAME);
			rs = stmt.executeQuery("SELECT * FROM friends where user1 = "
					+ (char) 34 + user + (char) 34);
			while (rs.next()) {
				String friend = rs.getString("user2");
				friends.add(friend);
			}
		}catch (SQLException e) {
		}
		return friends;
		
	}
	
	public void addFriend(String user1, String user2){
		java.sql.Connection connection = (Connection) context.getAttribute("Connection");
		ResultSet rs;
		try{
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + myDBinfo.MYSQL_DATABASE_NAME);
			stmt.executeQuery("INSERT into friends VALUES ("+ (char)34 + user1
					+ (char)34 + "," + (char)34 + user2 + (char)34 + ")");
			stmt.executeQuery("INSERT into friends VALUES ("+ (char)34 + user2
					+ (char)34 + "," + (char)34 + user1 + (char)34 + ")");
		}catch (SQLException e) {
			
		}
	}
}
