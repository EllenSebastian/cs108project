package quizWebsite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.Connection;

public class mysqlManager {
	
	public static final String MYSQL_USERNAME = "ccs108ellens2";
	public static final String MYSQL_PASSWORD = "eekohgoo";
	public static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	public static final String MYSQL_DATABASE_NAME = "c_cs108_ellens2";


	
	public static void addToDatabase(Question q, java.sql.Connection connection){
			try {
				java.sql.Statement st = connection.createStatement();
				String query = "insert into Questions values(" + q.pKey + "," + q.quizKey + ",\"" + q.className() + "\",\"" + q.compressData() + "\");";
				int rs= st.executeUpdate(query);

			} catch (SQLException e) {
				e.printStackTrace();
			} 
	}
	
	public static int addToDatabase(Quiz q, java.sql.Connection connection){
		try {
			java.sql.Statement st = connection.createStatement();
			String query = "insert into Quizzes values(" + q.pKey + ",\"" + q.name + "\",\"quizIntro.jsp?id=" + q.pKey + "\",\""
					+ q.creator + "\"," + q.immediateFeedbackString + "," +q.multiplePagesString + "," + q.practiceModeString+
					"," + q.randomOrderString + ",\"" + q.whenCreated + "\");";
			return st.executeUpdate(query ); 
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		return 0 ; 
	}
	
	// TODO figure out why these return the same thing twice. need to "refresh" the db??
	public static Integer getNextQuizKey(java.sql.Connection connection){
		java.sql.Statement st;
		String query = "select max(pKey) from Quizzes;";

		try {
			st = connection.createStatement();
			ResultSet rs= st.executeQuery(query);
			rs.next();
			return 1+ rs.getInt("max(pKey)"); 

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null; 
	}
	
	public static Integer getNextQuestionKey(java.sql.Connection connection){
		java.sql.Statement st;
		String query = "select max(pKey) from Questions;";

		try {
			st = connection.createStatement();
			ResultSet rs= st.executeQuery(query);
			rs.next();
			return 1+ rs.getInt("max(pKey)"); 

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null; 
	}
	
	
	public static Question getQuestion(HttpServletRequest request, HttpSession session, Integer pKey, java.sql.Connection connection){
		
		try {
			java.sql.Statement st = connection.createStatement();
			String query = "select type from Questions where pKey = " + pKey + ";";
			System.out.println("questionServlet.getQuestion query: " + "select type from Questions where pKey = " + pKey + ";");
			ResultSet rs= st.executeQuery(query);
			rs.next();
			
			String questionClassName = rs.getString("type"); 
			Class<?> clazz = Class.forName("quizWebsite." + questionClassName);
			java.lang.reflect.Constructor<?> ctor = clazz.getConstructor();
			System.out.println(" returning question with pkey" + pKey);
			String data = retreiveQuestionData(pKey, connection);
			Question q = (Question) ctor.newInstance ();
			int ok = q.parseData(data);
			q.pKey = pKey;
			if (ok == 1)	return q; 
			else{
				throw new Exception("invalid " + questionClassName);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null; 
	}
	
	public static Quiz retreiveQuiz(int pKey, java.sql.Connection connection){
		  Quiz q = new Quiz(); 
		  try {
					java.sql.Statement st = connection.createStatement();
					String query = "select * from Quizzes where pKey = " + pKey + ";";
					System.out.println("Query : " + query);
					ResultSet rs= st.executeQuery(query);
					rs.next();
					q.name = rs.getString("name");
					q.url = rs.getString("url");
					q.creator = rs.getInt("creator");
					q.immediateFeedback = rs.getBoolean("immediateFeedback");
					q.multiplePages = rs.getBoolean("multiplePages");
					q.practiceMode = rs.getBoolean("practiceMode");
					q.randomOrder = rs.getBoolean("randomOrder");
					q.whenCreated = rs.getTime("whenCreated").toString();
					return q; 
				} catch (SQLException e) {
					e.printStackTrace();
				} 	
		  return null; 
		}

	
	
	public static String retreiveQuestionData(int pKey, java.sql.Connection connection){
		try {
			java.sql.Statement st = connection.createStatement();
			ResultSet rs= st.executeQuery("select questionData from Questions where pKey = " + pKey + ";");
			rs.next();
			String data = rs.getString(1);
			return data; 
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return ""; 
	}
	
	public static Vector<Integer> getQuestions(Integer pKey, java.sql.Connection connection){
		Vector<Integer> vec = new Vector<Integer>(); 
		try {
			java.sql.Statement 		st = connection.createStatement();
			String query = "select * from Questions where quizKey = " + pKey + ";";
			System.out.println("Quiz.getQuestions query : " + query);
			ResultSet rs= st.executeQuery(query);

			while (rs.next()) {

				vec.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vec; 
	}
}
