package quizWebsite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class Announcement {
	
	public final int announcement_id;
	public int user_id;
	public Timestamp time;
	public String subject;
	public String body;
	
	private static Connection connection = myDBinfo.getConnection();
	
	public static void newAnnouncement(User u, String subject, String body) {
		try {
			PreparedStatement p = connection.prepareStatement("INSERT INTO Announcement (user_id, time, subject,body) VALUES (?, ?, ?, ?)");
			p.setInt(1, u.user_id);
			p.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			p.setString(3, subject);
			p.setString(4, body);
			p.executeUpdate();
		} catch (SQLException e) { }
	}
	
	public Announcement(int announcement_id) {
		this.announcement_id = announcement_id;
		
		try {
			ResultSet r = connection.prepareStatement("SELECT * FROM Announcement WHERE announcement_id = " + announcement_id).executeQuery();
			if(!r.next()) throw new RuntimeException("Invalid announcement id!");
			
			user_id = r.getInt("user_id");
			time = r.getTimestamp("posted");
			subject = r.getString("subject");
			body = r.getString("body");
		} catch (SQLException e) { }
	}
	
	public static void deleteAnnouncement(int announcement_id) {
		try {
			connection.prepareStatement("DELETE FROM Announcement WHERE announcement_id = " + announcement_id).executeUpdate();
		} catch (SQLException e) { }
	}
	
	public static Announcement[] getAnnouncements() {
		try {
			ResultSet r = connection.prepareStatement("SELECT announcement_id FROM Announcement ORDER BY time DESC").executeQuery();
			
			r.last();
			int size = r.getRow();
			r.beforeFirst();
			
			Announcement[] result = new Announcement[size];
			for(int i = 0; i < size; i++) {
				r.next();
				result[i] = new Announcement(r.getInt("announcement_id"));
			}
			
			return result;
		} catch (SQLException e) { return null; }
	}

}
