package quizWebsite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Message{
	
	public static final int Friend_Request = 1;
	public static final int Challenge = 2;
	public static final int GeneralNote = 3;
	
	int message_id;
	Timestamp time;
	boolean read;
	int type;
	String alert;
	String body;
	int quizID;
	int fromUser;
	int toUser;
	
    private static Statement stmt;
	private static Connection connection = myDBinfo.getConnection();
	
	// type can be 1,2 or 3
	public Message(int message_id,int type,Boolean read,String body, 
			int quizID, int fromUser,int toUser,Timestamp time){	
		this.message_id = message_id;
		this.type = type;
		this.read = read;
		this.body = body;
		this.quizID = quizID;
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.time = time;		
		switch (type) {
		case 1:
			alert = "you get a friend request";
			break;
		case 2:
			alert = "you get a chellenge";
			break;
		case 3:
			alert = "you get a note";
			break;
		default:
			alert =" ";
	}
}
	
	public void markasRead() {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `message` SET `unread`= ? WHERE `message_id` = ?");
			ps.setInt(1, 0);
			ps.setInt(2, this.message_id);
			ps.executeUpdate();
		} catch (SQLException e) { }
	}
	
	public static ArrayList<Message> getMessages(int user_id) {
		ArrayList<Message> list = new ArrayList<Message>();		
		ResultSet rs;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Message where toUser = "
					+ (char) 34 + user_id + (char) 34);
			while (rs.next()) {
				int message_id = rs.getInt("message_id");
				int type = rs.getInt("type");
				boolean read = rs.getBoolean("read");
				String body = rs.getString("body");
				int quizID = rs.getInt("quizID");
				int fromUser = rs.getInt("fromUser");
				Timestamp time = rs.getTimestamp("time");
				Message temp = new Message(message_id,type,read,body, 
						quizID,fromUser,user_id,time);
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void sendMessage() {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT IGNORE INTO Message (message_id,type,read,body,quizID,fromUser,toUser,time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, message_id);
			ps.setInt(2, type);
			ps.setBoolean(3, read);
			ps.setString(4,body);
			ps.setInt(5,quizID);
			ps.setInt(6,fromUser);
			ps.setInt(7,toUser);
			ps.setTimestamp(8,time);
			ps.executeUpdate();
		} catch (SQLException e) { }
  }
}
