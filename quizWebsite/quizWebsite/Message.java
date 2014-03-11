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
	public Timestamp time;
	public boolean checked;
	int type;
	public String alert;
	public String body;
	int quizID;
	public int fromUser;
	int toUser;
	
    private static Statement stmt;
	private static Connection connection = myDBinfo.getConnection();
	
	// type can be 1,2 or 3
	public Message(int type,Boolean checked,String body, 
			int quizID, int fromUser,int toUser,Timestamp time){	
		this.type = type;
		this.checked = checked;
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
			PreparedStatement ps = connection.prepareStatement("UPDATE Message SET checked = ? WHERE fromUser = ? and toUser = ? and time = ?");
			System.out.println("I came here");
			ps.setBoolean(1,true);
			ps.setInt(2, this.fromUser);
			ps.setInt(3, this.toUser);
			ps.setTimestamp(4, this.time);
			ps.executeUpdate();
			System.out.println("I came here");
		} catch (SQLException e) { }
	}
	
	
	// get messages and order the result by time. the latest comes first.
	public static ArrayList<Message> getMessages(int user_id) {
		ArrayList<Message> list = new ArrayList<Message>();		
		ResultSet rs;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Message where toUser = "
					+ (char) 34 + user_id + (char) 34 + " ORDER BY time DESC");
			while (rs.next()) {
				int type = rs.getInt("type");
				boolean checked = rs.getBoolean("checked");
				String body = rs.getString("body");
				int quizid = rs.getInt("quizid");
				int fromUser = rs.getInt("fromUser");
				Timestamp time = rs.getTimestamp("time");
				System.out.println(type);
				System.out.println(body);
				System.out.println(quizid);
				System.out.println(fromUser);
				Message temp = new Message(type,checked,body, 
						quizid,fromUser,user_id,time);
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void sendMessage() {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT IGNORE INTO Message (type,checked,body,quizID,fromUser,toUser,time) VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, type);
			ps.setBoolean(2, checked);
			ps.setString(3,body);
			ps.setInt(4,quizID);
			ps.setInt(5,fromUser);
			ps.setInt(6,toUser);
			ps.setTimestamp(7,time);
			ps.executeUpdate();
		} catch (SQLException e) { }
  }
}
