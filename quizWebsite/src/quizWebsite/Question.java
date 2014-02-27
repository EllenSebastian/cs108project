package quizWebsite;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public abstract class Question {
		// subclass constructors must set these private global vars: 
		
		public int pKey; 
		public String type;
		public int quizKey; 
		public String data; 
		// subclasses must implement these abstract methods:
		abstract String displayQuestion();
		abstract int scoreAnswer (HttpServletRequest request);
		abstract int parseData(String data);
		abstract String className(); 
		abstract String compressData(); 
		abstract  String newQuestionForm();
		abstract String getFeedback(HttpServletRequest request);
		abstract int parseNewQuestion(HttpServletRequest request);
		static int getNextQuestionpKey(HttpServletRequest request){
			
			Integer pKey = (Integer) request.getServletContext().getAttribute("maxQuestionKey");
			request.getServletContext().setAttribute("maxQuestionKey",pKey+1);
			return pKey; 
		}
		
	
		
		// this is for retreiving questions that are ALREADY in the db. 
		// TODO change column numbers to names 
		
		// this method is common to all subclasses.
		
		// I don't know where you're going to get this request from, but you need it
		// retrieve the RAW string of data from mysql corresponding to this Question's pKey.
		
		
		public String getType(){ return type; }
		
}
