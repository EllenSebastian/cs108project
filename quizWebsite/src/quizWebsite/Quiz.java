package quizWebsite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletContext;

import com.mysql.jdbc.Connection;

public class Quiz {
// session data has list of questions
// when question is answered store answers and score as session attribute
// when last question is answered send to feedback
	public int pKey; 
	public String name; 
	public String url; 
	public String creator; 
	public boolean immediateFeedback;
	public boolean multiplePages; 
	public boolean practiceMode;
	public boolean randomOrder;
	public String whenCreated; 
	public Vector<Integer> Questions;
	private Connection connection;
	public Quiz(String name, String url, String creator, boolean immediateFeedback,boolean multiplePages,
			boolean practiceMode,boolean randomOrder, String whenCreated, Vector<Integer> Questions, ServletContext context){
		// insert into db
		pKey = (Integer) context.getAttribute("maxQuizKey");
		context.setAttribute("maxQuizKey", pKey+1);
		this.pKey = pKey;
		this.name = name;
		this.url = url;
		this.creator = creator;
		this.immediateFeedback = immediateFeedback;
		this.multiplePages = multiplePages;
		this.practiceMode = practiceMode;
		this.randomOrder = randomOrder;
		this.whenCreated = whenCreated;
		this.connection= (Connection) context.getAttribute("Connection");
		
		String immediateFeedbackString = "FALSE";
		String multiplePagesString = "FALSE";
		String practiceModeString = "FALSE";
		String randomOrderString = "FALSE";
		if (immediateFeedback) immediateFeedbackString = "TRUE";
		if (multiplePages) multiplePagesString = "TRUE";
		if (practiceMode) practiceModeString = "TRUE";
		if (randomOrder) randomOrderString = "TRUE";
		try {
			java.sql.Statement st = connection.createStatement();
			String query = "insert into Quizzes values(" + pKey + ",\"name\",\"quizIntro.jsp?id=" + pKey + "\",\""
					+ creator + "\"," + immediateFeedbackString + "," +multiplePagesString + "," + practiceModeString+
					"," + randomOrderString + ",\"" + whenCreated + "\");";
			int rs= st.executeUpdate(query ); 
			} catch (SQLException e) {
				e.printStackTrace();
			} 
	}
	public Quiz (int pKey,ServletContext context){
		  this.connection= (Connection) context.getAttribute("Connection");
			try {
				java.sql.Statement st = connection.createStatement();
				String query = "select * from Quizzes where pKey = " + pKey + ";";
				System.out.println("Query : " + query);
				ResultSet rs= st.executeQuery(query);
				rs.next();
				this.name = rs.getString(2);
				this.url = rs.getString(3);
				this.creator = rs.getString(4);
				this.immediateFeedback = rs.getBoolean(5);
				this.multiplePages = rs.getBoolean(6);
				this.practiceMode = rs.getBoolean(7);
				this.randomOrder = rs.getBoolean(8);
				this.whenCreated = rs.getTime(9).toString();
			} catch (SQLException e) {
				e.printStackTrace();
			} 	
			}
	
	public Vector<Integer> getQuestions(){
		Vector<Integer> vec = new Vector<Integer>(); 
		try {
			java.sql.Statement st = connection.createStatement();
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

