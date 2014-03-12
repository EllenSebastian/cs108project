package quizWebsite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Rating {

	private static Connection connection = myDBinfo.getConnection();
		
	public static ArrayList<Integer> getRating(int quiz_id){
		try{
			ArrayList<Integer> ratings = new ArrayList<Integer>();
			PreparedStatement p = connection.prepareStatement("select * from Rating where pKey = ?");
			p.setInt(1, quiz_id);
			ResultSet r = p.executeQuery();
			while(r.next()){
				int rating = r.getInt("rating");
				ratings.add(rating);
			}
			return ratings;
		}
		catch(SQLException e){
			return null;
		}		
	}
	
	
}
