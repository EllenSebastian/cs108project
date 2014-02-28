package quizWebsite;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class UserManager {

	private static Connection db = myDBinfo.getConnection();

	private static String hashPassword(String text) {
		byte[] hash = new byte[40];
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(text.getBytes("utf-8"), 0, text.length());
			hash = md.digest();
		} catch (Exception ignored) { }
		return hexToString(hash);
	}

	private static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	

	// in login, check whether the user exists and return the user or null(user not existed)
	public static User checkUser(String name,String password){	
		password = hashPassword(password);

		try {			
			PreparedStatement p = db.prepareStatement("SELECT * FROM User WHERE name = ?");
			p.setString(1, name);

			ResultSet result = p.executeQuery();
			if(!result.next()) return null;
			if(!result.getString("passwordHash").equals(password)) return null;
			
			return User.getUser(result.getInt("user_id"));
		} catch (SQLException e) {
			return null;
		}
	}

	// in sign up, insert a new user into database and return it
	public static User addUser(String name,String password,boolean isAdmin){
		password = hashPassword(password);

		try {
			PreparedStatement p = db.prepareStatement("INSERT IGNORE INTO Users (name, passwordHash, isAdmin) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			p.setString(1, name);
			p.setString(2, password);
			p.setInt(3, isAdmin ? 1 : 0);

			int changed = p.executeUpdate();
			if(changed == 0) return null;

			// return the latest inserted user
			ResultSet s = p.getGeneratedKeys();
			s.next();
			return User.getUser(s.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int numAllUsers() {
		try {
			ResultSet result = db.prepareStatement("SELECT COUNT(*) FROM User").executeQuery();	
			int numUsers = 0;			
			if (result.next()) {
				numUsers += result.getInt(1);
			}
			return numUsers;
		} catch (SQLException e) {
			return 0;
		}
	}

	// make a user admin 
	public static void promoteUser(int id) {
		try {
			db.prepareStatement("UPDATE User SET isAdmin = 1 WHERE user_id = " + id).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// make a user non-admin	
	public static void demoteUser(int id){
		try {
			db.prepareStatement("UPDATE User SET isAdmin = 0 WHERE user_id = " + id).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<User> getAdmins() {
		try {
			ArrayList<User> results = new ArrayList<User>();
			PreparedStatement p = db.prepareStatement("SELECT * FROM User WHERE is_admin =1 ");
			ResultSet result = p.executeQuery();
			while(result.next()) {
				User user = User.getUser(result.getInt("user_id"));
				results.add(user);
			}
			return results;
		} catch (SQLException e) {
			return null;
		}
	}

	// search for a user based on its name 
	public static ArrayList<User> search(String name) {
		try {
			ArrayList<User> results = new ArrayList<User>();
			PreparedStatement p = db.prepareStatement("SELECT * FROM User WHERE name LIKE ? ORDER BY user_id");
			p.setString(1, "%" + name + "%");

			ResultSet result = p.executeQuery();
			while(result.next()) {
				User u = User.getUser(result.getInt("user_id"));
				results.add(u);
			}
			return results;
		} catch (SQLException e) {
			return null;
		}		
	}

	public static void removeUser(User user) {
		try {
			db.prepareStatement("DELETE FROM User WHERE user_id = " + user.user_id).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
