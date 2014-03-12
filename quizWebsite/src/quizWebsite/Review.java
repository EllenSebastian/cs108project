package quizWebsite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

public class Review {
	public int quiz_id;
	public int user_id;
	public String review;
	public Timestamp time;
	
	private static Connection connection = myDBinfo.getConnection();
	
	public Review(int user_id, int quiz_id, String review, Timestamp time) {
		this.quiz_id = quiz_id;
		this.user_id = user_id;
		this.review = review;
		this.time = time;
	}
	
	public static ArrayList<Review> getReivew(int quiz_id){
		ArrayList<Review> results = new ArrayList<Review>();
		try{
			PreparedStatement p = connection.prepareStatement("select * from Review where pKey = ? order by time DESC");
			p.setInt(1, quiz_id);
			ResultSet r = p.executeQuery();
			while(r.next()){
				int user_id = r.getInt("user_id");
				String review = r.getString("review");
				Timestamp time = r.getTimestamp("time");
				Review currentReview = new Review(user_id,quiz_id,review,time);
				results.add(currentReview);
			}
			return results;
		}
		catch(SQLException e){
			return null;
		}		
	}

}
