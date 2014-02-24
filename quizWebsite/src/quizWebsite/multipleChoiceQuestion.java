package quizWebsite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.mysql.jdbc.Connection;

public class multipleChoiceQuestion extends Question {
	private Connection connection;
	private int quizKey; 
	public multipleChoiceQuestion(HttpServletRequest request, ArrayList<String> choices, int quizKey, int correctChoice) {
		this.quizKey = quizKey; 
		pKey = (Integer) context.getAttribute("maxQuestionKey");

		storeData(compressData(),quizKey,pKey,"multipleChoiceQuestion"); 
		context=request.getServletContext();
		context.setAttribute("maxQuestionKey",pKey+1);
		this.type = "multipleChoiceQuestion";
		// TODO Auto-generated method stub
	}
	public multipleChoiceQuestion(Integer pKey, ServletContext context){
		  this.connection= (Connection) context.getAttribute("Connection");
			try {
				java.sql.Statement st = connection.createStatement();
				ResultSet rs= st.executeQuery("select * from Quizzes where pKey = " + pKey + ";");
				
			} catch (SQLException e) {
				e.printStackTrace();
			} 	
		this.type = "multipleChoiceQuestion";

	}
	
	public String displayQuestion() {
		// TODO Auto-generated method stub
		return null;
	}

	public int scoreAnswer() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String displayQuestionForm() {
		// TODO Auto-generated method stub
		return "question form here";
	}

	@Override
	void parseData(String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	int scoreAnswer(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	String className() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	String compressData() {
		// TODO Auto-generated method stub
		return null;
	}



}
