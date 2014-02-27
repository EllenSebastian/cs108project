package quizWebsite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Message{
	
	private static Connection connection = myDBinfo.getConnection();
	
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
}
