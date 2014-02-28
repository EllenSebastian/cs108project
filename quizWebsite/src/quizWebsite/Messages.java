package quizWebsite;

import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletContext;


public class Messages {
	private static Statement stmt;

	private static Connection connection = myDBinfo.getConnection();
	

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

	public void sendMessage(Message msg) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT IGNORE INTO Message (message_id,type,read,body,quizID,fromUser,toUser,time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, msg.message_id);
			ps.setInt(2, msg.type);
			ps.setBoolean(3, msg.read);
			ps.setString(4,msg.body);
			ps.setInt(5, msg.quizID);
			ps.setInt(6, msg.fromUser);
			ps.setInt(7,msg.toUser);
			ps.setTimestamp(8,msg.time);
			ps.executeUpdate();
		} catch (SQLException e) { }
  }

}
