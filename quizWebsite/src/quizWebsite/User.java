package quizWebsite;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;


public class User {

	public int user_id;
	private String name;
	private String password;
	private boolean isAdmin;
	private String cookieKey;

	private static Connection connection = myDBinfo.getConnection();
		
	// id refer to the key of this particular user in database
	// id refer to the key of this particular user in database
		public User(int user_id,String name,String password,boolean isAdmin
				,String cookieKey){
			this.user_id = user_id;
			this.name = name;
			this.password = password;
			this.isAdmin = isAdmin;
			this.cookieKey = cookieKey;
		}
	
	public Boolean equals(User u){
		return (u.uid() == this.user_id && this.name == u.name() && this.password == u.password() && this.isAdmin == u.isAdmin());
	}
	public int uid(){
		return user_id;
	}
	public String password(){
		return password; 
	}
	public String name(){
		return name;
	}
	
	public Boolean isAdmin(){
		return isAdmin;
	}
	
	public ArrayList<Announcement> getAnnouncements(){
		return Announcement.getAnnouncements();
	}

	
	public ArrayList<Achievement> getUserAchievements() {
		return Achievement.getAchievement(user_id);
	}
	
	
	public ArrayList<Activity> getUserActivities() {
		return Activity.getActivity(user_id);
	}
	
	public ArrayList<Integer> getUserFriends(){
		return Friends.getFriends(user_id);
	}
	
	public void addFriend(int to_id){
		Friends.addFriend(user_id, to_id);
	}
	
	public void removeFriend(int to_id){
		Friends.removeFriend(user_id, to_id);
	}
	
	public List<Activity> getFriendsActivity() {
		ArrayList<Activity> act = new ArrayList<Activity>();
		ArrayList<Integer> friends = getUserFriends();
		for (int i = 0; i < friends.size(); i++) {
			act.addAll(Activity.getActivity(friends.get(i)));
		}
		Activity.sortByTime(act);
		return act;
	}
	
	public ArrayList<Message> getUserMessages(){
		return Message.getMessages(user_id);
	}
	public void rateQuiz(int quiz_id,int rating){
		try {
			PreparedStatement p = connection.prepareStatement("DELETE from Rating WHERE user_id = ? AND pKey = ?");
			p.setInt(1, user_id);
			p.setInt(2,  quiz_id);
			p.executeUpdate();
			PreparedStatement p2 = connection.prepareStatement("INSERT into Rating VALUES (?, ?, ?)");
			p2.setInt(1,  user_id);
			p2.setInt(2,  quiz_id);
			p2.setInt(3, rating);
			p2.executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	public int getRating(int quiz_id){
		try {
			PreparedStatement p = connection.prepareStatement("SELECT * from Rating WHERE user_id = ? and pKey = ?");
			p.setInt(1, user_id);
			p.setInt(2, quiz_id);
			ResultSet r = p.executeQuery();
			if (!r.next()) {
				return 0;
			} else {		   
				int currentRating = r.getInt("rating");
				return currentRating;
			}
		} catch (SQLException e) {
			return 0;
		}
	}
	
	public void reviewQuiz(int quiz_id,String review){
		try {
			PreparedStatement p = connection.prepareStatement("INSERT into Review VALUES (?, ?, ?, ?)");
			p.setInt(1,user_id);
			p.setInt(2,quiz_id);
			p.setString(3,review);
			p.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
			p.executeUpdate();
		} catch (SQLException e) {
		}		
	}
	
	public void setTag(int quiz_id, String tag){
		try {
			PreparedStatement p = connection.prepareStatement("INSERT into Tag VALUES (?, ?, ?)");
			p.setInt(1,user_id);
			p.setInt(2,quiz_id);
			p.setString(3,tag);
			p.executeUpdate();
		} catch (SQLException e) {
		}		
	}
	
	// only creator of the quiz can set the category; only category that already be specified can be set; 
	// one quiz can only falls into one category, so the old category will be replaced
	// if the operation is successful, return true, otherwise return false
	public boolean setCategory(int quiz_id,String category){		
		if(Category.contains(category)){
		try {
			PreparedStatement p = connection.prepareStatement("DELETE from Category WHERE pKey = ?");
			p.setInt(1, quiz_id);
			p.executeUpdate();
			PreparedStatement p2 = connection.prepareStatement("INSERT into Category VALUES (?, ?)");
			p2.setInt(1,quiz_id);
			p2.setString(2, category);
			p2.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		   }
		}
		else return false;
	}
	
	public void addCategory(String category){
		if(this.isAdmin){
			try {
				PreparedStatement p = connection.prepareStatement("INSERT into CategoryList VALUES (?)");
				p.setString(1,category);
				p.executeUpdate();
			} catch (SQLException e) {
			}
		}	
	} 
	
	public static void main(String args[]){
		//UserManager.addUser("hai","1234",false);
		//User hai = UserManager.getUser(1);		
		//String key = hai.setCookieKey();
		//System.out.println(key);
		//hai.rateQuiz(29, 10);
		//ArrayList<Integer> ratings = Rating.getRating(29);
		//System.out.println(ratings.get(0));
		//hai.reviewQuiz(29,"interesting");
		//ArrayList<Review> reviews = Review.getReivew(29);
		//System.out.println(reviews.get(0).user_id);
		//hai.setTag(29,"science");
		//ArrayList<String> tags = Tag.getTags(29);
		//System.out.println(tags.get(0));
		//ArrayList<Integer> quizes = Tag.getQuizByTag("science");
		//System.out.println(quizes.get(0));
		//UserManager.addUser("huanghai","1234", true);
		//UserManager.promoteUser(3);
		//User huang = UserManager.getUser(3);
		//huang.addCategory("science");
		//huang.addCategory("humanity");
		//System.out.println(huang.setCategory(29,"science"));
		//System.out.println(huang.setCategory(29,"humanity"));
		//ArrayList<Integer> quiz0 = Category.getQuizByCategory("science");
		//ArrayList<Integer> quiz1 = Category.getQuizByCategory("humanity");
		//System.out.println(quiz0.isEmpty());
		//System.out.println(quiz1.get(0));
	}
	
	public String setCookieKey() {
		String key = new BigInteger(140, new SecureRandom()).toString();
		
		try {
			PreparedStatement p = connection.prepareStatement("UPDATE User SET cookieKey = ? WHERE user_id = ?");
			p.setString(1, key);
			p.setInt(2, user_id);
			p.executeUpdate();
		} catch(SQLException ignored) { 
			return null; 
		}		
		this.cookieKey = key;
		return key;
	}
	
	// sample front end code for reference. if "remember me" is selected, next 
	// time no need to log in again.
	
	//Cookie c;
	//if(request.getParameter("remember me") != null) {
	//	String key = user.setCookieKey();
	//	c = new Cookie("user_key", key);
	//	c.setMaxAge(365 * 86400);
	//} else {
	//	c = new Cookie("user_key", "");
	//	c.setMaxAge(0);
	//}
	//c.setHttpOnly(true);
	//c.setPath("/");
	//response.addCookie(c);
	// redirect to userPage if already logged in 
}
