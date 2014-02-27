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
	String question;
	String choicesString; 
	String correctChoiceString; 
	String data; 
	public multipleChoiceQuestion(){	}


	// call createQuestionServlet
	public String newQuestionForm(){
		// set session attributes 
		return "";
	}

	// require to set quizKey and pKey
	// when creating new quiz, must set quizKey attribute.
	public int parseNewQuestion(HttpServletRequest request) {
		// TODO figure why this is not actually parsing. 
		this.choicesString = request.getParameter("choice0");
		this.quizKey = (Integer) request.getSession().getAttribute("newQuizKey"); 
		this.correctChoiceString = request.getParameter("correctChoices"); 
		this.question= request.getParameter("question"); 
		// set ivars 
		this.pKey = getNextQuestionpKey(request); 
		this.type = "multipleChoiceQuestion";
		this.data = compressData();
		// move this to servlet mysqlManager.addToDatabase(this);
		return 0;
	}
	

	@Override
	int parseData(String data) {
		this.type = "multipleChoiceQuestion";
		// TODO Auto-generated method stub
		this.data = data;
		return 1; 
	}
	public String displayQuestion() {
		// TODO Auto-generated method stub
		return this.data;
	}

	public int scoreAnswer() {
		// TODO Auto-generated method stub
		return 0;
	}
/* return just a form not any of the other html.*/

	public String displayQuestionForm() {
		String form = "<form action = \"CreateQuestionServlet\" method = \"post\">";
		form += "Enter the question:  <input type=\"text\" name=\"question\"><br>";
		form += "Enter choices, separated by commas:  <input type=\"text\" name=\"choices\"><br>";
		form += "Enter the correct choice numbers, separated by commas (e.g. 1,2,4)   <input type=\"text\" name=\"correctChoices\"><br>";
		form += "<input type=\"submit\" value=\"submit\">";
		form += "</form>";
		System.out.println(form);
		return form;
	}


	@Override
	int scoreAnswer(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	String className() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	String compressData() {
		return question +"__" + choicesString+ "__" + correctChoiceString;
	}


	@Override
	String getFeedback(HttpServletRequest request) {
		return "<br> Feedback: your score is" + scoreAnswer(request);
	}
}
