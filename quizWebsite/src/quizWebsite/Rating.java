package quizWebsite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Rating {

	private static Connection connection = myDBinfo.getConnection();
	private int quiz_id;
	private int score; 
	public Rating (int quiz_id, int score){
		this.quiz_id = quiz_id;
		this.score = score; 
	}
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
			e.printStackTrace();
			return null;
		}		
	}
	/* return 1 on changed, 0 on problem.*/
	public static Integer RateQuiz (int quiz_id, int rating, int user_id){
		try{
			PreparedStatement p = connection.prepareStatement("insert into Rating values( ?, ? , ?)");
			p.setInt(1, user_id);
			p.setInt(2, quiz_id);
			p.setInt(3, rating);

			Integer changed = p.executeUpdate();
			return changed; 
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}		
	}
	
}
