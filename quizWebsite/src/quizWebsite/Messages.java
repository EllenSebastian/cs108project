package quizWebsite;

import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import com.mysql.jdbc.Connection;

public class Messages {
	private static Statement stmt;
	protected ServletContext context;
	
	public Messages(){
		
	}
	
	public ArrayList<Message> getUserMessages(String user){
		ArrayList<Message> list = new ArrayList<Message>();
		java.sql.Connection connection= (Connection) context.getAttribute("Connection");
		ResultSet rs;
		try {
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + myDBinfo.MYSQL_DATABASE_NAME);
			rs = stmt.executeQuery("SELECT * FROM messages where userTo = "
					+(char)34 + user + (char)34);

			while (rs.next()) {
				boolean read = rs.getBoolean("read");
				String type = rs.getString("type");
				String body = rs.getString("body");
				String quizId = rs.getString("quizId");
				String fromUser = rs.getString("fromUser");
				Message temp = new Message(read, type, body, quizId, fromUser, user);
				list.add(temp);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
