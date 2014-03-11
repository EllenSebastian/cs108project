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
	public Integer creator; 
	public boolean immediateFeedback;
	public boolean multiplePages; 
	public boolean practiceMode;
	public boolean randomOrder;
	public String whenCreated; 
	public String description; 
	public String immediateFeedbackString, multiplePagesString, practiceModeString, randomOrderString; 
	public Quiz() {}
	public Quiz(String name, String url, String description, Integer creator, boolean immediateFeedback,boolean multiplePages,
			boolean practiceMode,boolean randomOrder, String whenCreated, ServletContext context){
		// insert into db
		pKey = mysqlManager.getNextQuizKey((Connection)context.getAttribute(Constants.context_Connection));
		this.name = name;
		this.url = url;
		this.creator = creator;
		this.immediateFeedback = immediateFeedback;
		this.multiplePages = multiplePages;
		this.practiceMode = practiceMode;
		this.randomOrder = randomOrder;
		this.whenCreated = whenCreated;
		this.description = description;
		immediateFeedbackString = "FALSE";
		multiplePagesString = "FALSE";
		practiceModeString = "FALSE";
		randomOrderString = "FALSE";
		if (immediateFeedback) immediateFeedbackString = "TRUE";
		if (multiplePages) multiplePagesString = "TRUE";
		if (practiceMode) practiceModeString = "TRUE";
		if (randomOrder) randomOrderString = "TRUE";
		
	}
	
}

