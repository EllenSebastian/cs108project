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
				int rs= st.executeUpdate("insert into Questions values(" + q.pKey + "," + q.quizKey + ",\"" + q.type + "\",\"" + q.data + "\"");
				} catch (SQLException e) {
				e.printStackTrace();
			} 
	}
	
	public static void addToDatabase(Quiz q, java.sql.Connection connection){
		try {
			java.sql.Statement st = connection.createStatement();
			String query = "insert into Quizzes values(" + q.pKey + ",\"name\",\"quizIntro.jsp?id=" + q.pKey + "\",\""
					+ q.creator + "\"," + q.immediateFeedbackString + "," +q.multiplePagesString + "," + q.practiceModeString+
					"," + q.randomOrderString + ",\"" + q.whenCreated + "\");";
			int rs= st.executeUpdate(query ); 
			} catch (SQLException e) {
				e.printStackTrace();
			} 
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
					q.name = rs.getString(2);
					q.url = rs.getString(3);
					q.creator = rs.getString(4);
					q.immediateFeedback = rs.getBoolean(5);
					q.multiplePages = rs.getBoolean(6);
					q.practiceMode = rs.getBoolean(7);
					q.randomOrder = rs.getBoolean(8);
					q.whenCreated = rs.getTime(9).toString();
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
		System.out.println("1");
		try {
			java.sql.Statement 		st = connection.createStatement();
			System.out.println("2");

			String query = "select * from Questions where quizKey = " + pKey + ";";
			System.out.println("3");

			System.out.println("Quiz.getQuestions query : " + query);
			ResultSet rs= st.executeQuery(query);
			System.out.println("4");

			while (rs.next()) {
				System.out.println("5");

				vec.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vec; 
	}
}
