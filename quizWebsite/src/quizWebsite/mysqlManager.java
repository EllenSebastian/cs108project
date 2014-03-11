package quizWebsite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class mysqlManager {
	
	public static final String MYSQL_USERNAME = "ccs108ellens2";
	public static final String MYSQL_PASSWORD = "eekohgoo";
	public static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	public static final String MYSQL_DATABASE_NAME = "c_cs108_ellens2";

	// TODO
	public static ArrayList<String> quizList(){
		return new ArrayList<String>(); 
	}
	public static ArrayList<Quiz>  allQuizzes( Connection con){
		try {
			ArrayList<Quiz> results = new ArrayList<Quiz>();
			PreparedStatement p = con.prepareStatement("SELECT * FROM Quizzes");

			ResultSet result = p.executeQuery();
			while(result.next()) {
				Quiz q = retreiveQuiz(result.getInt("pKey"),con);
				//System.out.println(result.getInt("user_id"));
				results.add(q);
			}
			return results;
		} catch (SQLException e) {
			return null;
		}	
	}

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
			String query = "insert into Quizzes values(" + q.pKey + ",\"" + q.name + "\",\"" 
					+ q.description + "\",\"quizIntro.jsp?id=" + q.pKey + "\",\""
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
					q.pKey = pKey; 
					q.name = rs.getString("name");
					q.url = rs.getString("url");
					q.creator = rs.getInt("creator");
					q.immediateFeedback = rs.getBoolean("immediateFeedback");
					q.multiplePages = rs.getBoolean("multiplePages");
					q.practiceMode = rs.getBoolean("practiceMode");
					q.randomOrder = rs.getBoolean("randomOrder");
					q.whenCreated = rs.getTime("whenCreated").toString();
					q.description = rs.getString("description");
					return q; 
				} catch (SQLException e) {
					e.printStackTrace();
				} 	
		  return null; 
		}

	
	public static User retreiveUser(int user_id, java.sql.Connection connection){
		
		String query = "select * from User where user_id =" + user_id + ";";
		try{
			java.sql.Statement st = connection.createStatement();
			System.out.println("Query : " + query);
			ResultSet rs= st.executeQuery(query);
			rs.next();
			User user = new User(
			          rs.getInt("user_id"),
			          rs.getString("name"), 					           	
			          rs.getString("passwordHash"),
					  rs.getInt("isAdmin") == 1 ? true : false
					 );
			return user; 
		}catch (SQLException e) {
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
	
	public static Integer timesQuizTaken(Integer qid, Connection con){
		String query = "select count(*) from Activity where type = " + Activity.quiz_Taken
				+ " and pKey = " + qid + ";";
		try {
			java.sql.Statement 		st = con.createStatement();
			ResultSet rs= st.executeQuery(query);
			while (rs.next()) {
				return (rs.getInt("count(*)"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Float quizAverage(Integer qid,Connection con){
		String query = "select avg(score) from Activity where type = " + Activity.quiz_Taken
				+ " and pKey = " + qid + ";";
		try {
			java.sql.Statement 		st = con.createStatement();
			ResultSet rs= st.executeQuery(query);
			while (rs.next()) {
				return (rs.getFloat("avg(score)"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Integer quizMin(Integer qid,Connection con){
		String query = "select min(score) from Activity where type = " + Activity.quiz_Taken
				+ " and pKey = " + qid + ";";
		try {
			java.sql.Statement 		st = con.createStatement();
			ResultSet rs= st.executeQuery(query);
			while (rs.next()) {
				return (rs.getInt("min(score)"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	public static Integer quizMax(Integer qid,Connection con){
		String query = "select max(score) from Activity where type = " + Activity.quiz_Taken
				+ " and pKey = " + qid + ";";
		try {
			java.sql.Statement 		st = con.createStatement();
			ResultSet rs= st.executeQuery(query);
			while (rs.next()) {
				return (rs.getInt("max(score)"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	// return html. any args can be null if you don't want to select for them. 
	public static String quizHistory(Integer user_id,Integer quizKey, Integer hoursEarlier, Integer n , Connection con ){
		String query = "select * from Activity where type = "+ Activity.quiz_Taken + " " ; 
		if (user_id != null) query = query + "and user_id = " + user_id + " " ;
		if (quizKey != null) query = query + "and pKey = " + quizKey + " " ;
		if (hoursEarlier != null){
			DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");                                                                                         
            Calendar cal = Calendar.getInstance();     
            Date d = cal.getTime();
            Date earlyDate = new Date(d.getTime() - hoursEarlier * 3600 * 1000);
            String datetime = dateFormat.format(earlyDate); 
            query = query + " and time > \"" + datetime + "\"";
		}
		
		query += " order by score desc, durationMS "; 
		if (n != null) query = query +  " limit " + n;
		query = query + ";";
		System.out.println(" query : " + query);
		String result = "";
		if (quizKey != null) result += "quiz     "; 
		if (user_id != null) result += "user     ";
		
			
		result += "score   duration    <br>";
		try {
			java.sql.Statement 		st = con.createStatement();
			ResultSet rs= st.executeQuery(query);
			while (rs.next()) {
				if (quizKey != null){
					Integer qid = rs.getInt("pKey");
					Quiz q = mysqlManager.retreiveQuiz(qid, con);
					result += q.name + "    ";
				}
				if (user_id == null){
					Integer uid = rs.getInt("user_id");
					User u = mysqlManager.retreiveUser(uid, con);
					result += u.name() + "   ";
				}
				
				Integer score = rs.getInt("score");
				Integer duration = rs.getInt("durationMS");
				result += score + "   ";
				result += duration + "    " + "<br>";
			}
		return result; 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void clearQuizHistory(int qid, Connection con){
		try{
			java.sql.Statement 	st = con.createStatement();
			ResultSet rs= st.executeQuery("DELETE FROM Activity WHERE quizId = " +qid
					+" AND type = 2 ");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
