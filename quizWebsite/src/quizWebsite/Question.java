package quizWebsite;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public abstract class Question {
		// subclass constructors must set these private global vars: 
		
		protected int pKey; 
		protected ServletContext context;
		protected String type;
		
		// subclasses must implement these abstract methods:
		abstract String displayQuestionForm();
		abstract String displayQuestion();
		abstract int scoreAnswer (HttpServletRequest request);
		abstract void parseData(String data);
		abstract String className(); 
		abstract String compressData(); 
		
		
		
		void storeData(String data, int quizKey, int pKey, String className){
			  java.sql.Connection connection= (Connection) this.context.getAttribute("Connection");
				try {
					java.sql.Statement st = connection.createStatement();
					int rs= st.executeUpdate("insert into Questions values(" + pKey + "," + quizKey + ",\"" + className + "\",\"" + data + "\"");
					} catch (SQLException e) {
					e.printStackTrace();
				} 
		}
		
		// this is for retreiving questions that are ALREADY in the db. 

		
		// this method is common to all subclasses.
		
		// I don't know where you're going to get this request from, but you need it
		// retrieve the RAW string of data from mysql corresponding to this Question's pKey.
		String retreiveData(){
			  java.sql.Connection connection= (Connection) context.getAttribute("Connection");
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
		
		public String getType(){ return type; }
}
