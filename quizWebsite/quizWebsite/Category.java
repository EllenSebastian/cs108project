package quizWebsite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Category {
	
	private static Connection connection = myDBinfo.getConnection();
	
	public static boolean contains(String category){
		try {
			PreparedStatement p = connection.prepareStatement("select * from CategoryList WHERE category = ?");
			p.setString(1, category);
			ResultSet r = p.executeQuery();
			if(r.next()){
				return true;
			}
			else return false;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public static ArrayList<Integer> getQuizByCategory(String category){
		ArrayList<Integer> results = new ArrayList<Integer>();
		try{
			PreparedStatement p = connection.prepareStatement("select * from Category where category = ?");
			p.setString(1, category);
			ResultSet r = p.executeQuery();
			while(r.next()){
				Integer quiz_id = r.getInt("pKey");
				results.add(quiz_id);
			}
			return results;
		}
		catch(SQLException e){
			return null;
		}
	}

}
