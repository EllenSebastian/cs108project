package quizWebsite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Tag {
	
	private static Connection connection = myDBinfo.getConnection();
	
	public static ArrayList<String> getTags(int quiz_id){
		ArrayList<String> results = new ArrayList<String>();
		try{
			PreparedStatement p = connection.prepareStatement("select * from Tag where quizKey = ?");
			p.setInt(1, quiz_id);
			ResultSet r = p.executeQuery();
			while(r.next()){
				String tag = r.getString("tag");
				results.add(tag);
			}
			return results;
		}
		catch(SQLException e){
			return null;
		}
	}
	
	public static ArrayList<Integer> getQuizByTag(String tag){
		ArrayList<Integer> results = new ArrayList<Integer>();
		try{
			PreparedStatement p = connection.prepareStatement("select * from Tag where tag = ?");
			p.setString(1, tag);
			ResultSet r = p.executeQuery();
			while(r.next()){
				Integer quiz_id = r.getInt("quizKey");
				results.add(quiz_id);
			}
			return results;
		}
		catch(SQLException e){
			return null;
		}
	}
	public static int addTag(int quizKey, String tag){
		try{
			PreparedStatement p = connection.prepareStatement("insert into Tag values( ?, ? )");
			p.setInt(1, quizKey);
			p.setString(2, tag);
			Integer changed = p.executeUpdate();
			return changed; 
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}		
	}
}
