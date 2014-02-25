package quizWebsite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

public class multipleChoiceQuestion extends Question {
	ArrayList<String> choices;
	Integer correctChoice; 
	public multipleChoiceQuestion(){	}


	// call createQuestionServlet
	public String newQuestionForm(){
		// set session attributes 
		return "";
	}

	// require to set quizKey and pKey
	// when creating new quiz, must set quizKey attribute.
	public int parseNewQuestion(HttpServletRequest request) {
		String choices = request.getParameter("choices");
		Integer quizKey = (Integer) request.getSession().getAttribute("quizKey"); 
		String correctChoice = request.getParameter("correctChoice"); 
		String question = request.getParameter("question"); 
		// set ivars 
		pKey = getNextQuestionpKey(request); 
		this.type = "multipleChoiceQuestion";
		// move this to servlet mysqlManager.addToDatabase(this);
		return 0;
	}
	

	@Override
	int parseData(String data) {
		this.type = "multipleChoiceQuestion";
		// TODO Auto-generated method stub
		return 1; 
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
