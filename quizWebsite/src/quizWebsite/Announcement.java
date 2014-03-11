package quizWebsite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


public class Announcement {
	
	public final int announcement_id;
	public int user_id;
	public Timestamp time;
	public String subject;
	public String body;
	
	private static Connection connection = myDBinfo.getConnection();
	
	public static void sendAnnouncement(int user_id, String subject, String body) {
		try {
			PreparedStatement p = connection.prepareStatement("INSERT INTO Announcement (user_id, time, subject,body) VALUES (?, ?, ?, ?)");
			p.setInt(1, user_id);
			p.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			p.setString(3, subject);
			p.setString(4, body);
			p.executeUpdate();
		} catch (SQLException e) { }
	}
	

	public Announcement(int announcement_id,int user_id,Timestamp time,String subject,String body) {
		this.announcement_id = announcement_id;
		this.user_id = user_id;
		this.time = time;
		this.subject = subject;
		this.body = body;		
	}
	
	public static void deleteAnnouncement(int announcement_id) {
		try {
			connection.prepareStatement("DELETE FROM Announcement WHERE announcement_id = " + announcement_id).executeUpdate();
		} catch (SQLException e) { }
	}
	
	public static void main(String args[]){
		getAnnouncements();
	}
	
	public static ArrayList<Announcement> getAnnouncements() {
		try {
			ResultSet r = connection.prepareStatement("SELECT * FROM Announcement ORDER BY time DESC").executeQuery();		
			ArrayList<Announcement> result = new ArrayList<Announcement>();
			while(r.next()){
				int announcement_id = r.getInt("announcement_id");
				int user_id = r.getInt("user_id");
				Timestamp time = r.getTimestamp("time");
				String subject = r.getString("subject");
				String body = r.getString("body");
				Announcement ancmt = new Announcement(announcement_id,user_id,time,subject,body);
				result.add(ancmt);
			}
			System.out.println("size:"+result.size());
			return result;
		} catch (SQLException e) { return null; }
	}

}
