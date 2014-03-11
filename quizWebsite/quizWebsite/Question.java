package quizWebsite;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public abstract class Question {
	// pKey: A unique identifier for every question.
	// NOTE WHEN IMPLEMENTING: 
	protected int pKey;
	// type: A description of the subclass type.
	// NOTE WHEN IMPLEMENTING: Should be set by the subclass constructor.
	protected String type;
	// quizKey: A unique identifier for the quiz that owns this question.
	// NOTE WHEN IMPLEMENTING: 
	protected int quizKey;
	/* Returns the exact class name.
	 */
	public abstract String className();
	/* Displays HTML code for displaying the question, to be placed in a form.
	 * Does NOT print HTML code for the form header, footer, or submit button.
	 * NOTE WHEN IMPLEMENTING: All attributes should be preceded by the question's pKey. E.G.: "[pKey]answer".
	 */
	public abstract String displayQuestion();
	/* Scores the answer to the question.
	 * Request should be from the post function of a servlet posted to by the servlet that displayed the question.
	 * NOTE WHEN IMPLEMENTING: Names of attributes pulled from request should match those defined in displayQuestion().
	 */
	public abstract int scoreAnswer (HttpServletRequest request);
	public abstract String getFeedback(HttpServletRequest request);
	
	/* Builds the data from the question (for entry into the MYSQL database).
	 * NOTE WHEN IMPLEMENTING: Use "__" as separators.
	 */
	public abstract String compressData(); 
	/* Builds the question from the string data.
	 * NOTE WHEN IMPLEMENTING: Must set the following attributes:
	 * NOTE WHEN IMPLEMENTING: Expect "__" as separators.
	 */
	public abstract int parseData(String data);
	/* Returns HTML code form from which the new question will be built.
	 * Includes the submit button but NOT the form header or footer.
	 */
	public abstract  String newQuestionForm();
	/* Builds the new question from the request if it can.
	 * Returns 0 on success, nonzero on failure.
	 * NOTE WHEN IMPLEMENTING: Must set pKey & quizKey. Rely on constructor to set type.
	 * NOTE WHEN IMPLEMENTING: Use getNextQuestionpKey() to get the pKey to set.
	 * NOTE WHEN IMPLEMENTING: Use "Constants.session_quizKey" to find the quizKey. Session attribute.
	 */
	public abstract int parseNewQuestion(HttpServletRequest request);
	/* Gets the next question pKey. To be used in parseNewQuestion() in subclasses.
	 */
	static int getNextQuestionpKey(HttpServletRequest request){
		return mysqlManager.getNextQuestionKey((Connection) request.getServletContext().getAttribute(Constants.context_Connection));
	}
	// this is for retreiving questions that are ALREADY in the db. 
	// TODO change column numbers to names 

	// this method is common to all subclasses.

	// I don't know where you're going to get this request from, but you need it
	// retrieve the RAW string of data from mysql corresponding to this Question's pKey.
	public String getType() {
		return type;
	}
	// remainingData is the string that has everything left now. remaining will be filled with what is left after this iteration. 
	static int extractField(StringBuilder remainingData, StringBuilder field) {
		int dLength = remainingData.length();
		int foundAt = -1;
		for(int i = 0; i < dLength-1; i++) {
			if(remainingData.substring(i, i+2).equals( "__")) {
				foundAt = i;
				break;
			}
		}
		if(foundAt == -1)
			return 1;
	
		field.append(remainingData.substring(0,foundAt));
		String temp = remainingData.substring(foundAt+2); 
		remainingData.setLength(0);
		remainingData.append(temp);
		if(field.length() == 0 || remainingData.length() == 0)
			return 1;
		return 0;
	}
	
	/* Returns a boolean indicating if the two strings match or not, based on their Jaro-Winkler distance.
	 * Strings are considered to match if their distance is less than 0.2.
	 */
	static boolean match (String s1, String s2) {
		double matchThreshold = 0.2;
	    double wThreshold = 0.7;
	    int prefix = 4;
	    // CALCULATE THE PROXIMITY:
	    double proximity = 0.0;
	    int s1Len = s1.length();
        int s2Len = s2.length();
        if (s1Len == 0) {
        	if(s2Len == 0)
        		proximity = 1.0;
        	else
        		proximity = 0.0;
        } else {
	        int  searchRange = Math.max(0,Math.max(s1Len,s2Len)/2 - 1);
	        boolean[] matched1 = new boolean[s1Len];
	        for (int i = 0; i < s1Len; i++) {
	            matched1[i] = false;
	        }
	        boolean[] matched2 = new boolean[s2Len];
	        for (int i = 0; i < s2Len; i++) {
	            matched2[i] = false;
	        }
	        int common = 0;
	        for (int i = 0; i < s1Len; ++i) {
	            int start = Math.max(0,i-searchRange);
	            int end = Math.min(i+searchRange+1,s2Len);
	            for (int j = start; j < end; ++j) {
	                if (matched2[j]) continue;
	                if (s1.charAt(i) != s2.charAt(j))
	                    continue;
	                matched1[i] = true;
	                matched2[j] = true;
	                ++common;
	                break;
	            }
	        }
	        if (common != 0) {
		        int halfTransposed = 0;
		        int k = 0;
		        for (int i = 0; i < s1Len; ++i) {
		            if (!matched1[i])
		            	continue;
		            while (!matched2[k])
		            	++k;
		            if (s1.charAt(i) != s2.charAt(k))
		                ++halfTransposed;
		            ++k;
		        }
		        int transposed = halfTransposed/2;
		        double commonD = common;
		        double weight = (commonD / s1Len + commonD / s2Len + (common - transposed) / commonD) / 3.0;
		        if (weight <= wThreshold) {
		        	proximity = weight;
		        } else {
			        int maxLen = Math.min(prefix,Math.min(s1.length(),s2.length()));
			        int pos = 0;
			        while (pos < maxLen && s1.charAt(pos) == s2.charAt(pos))
			            ++pos;
			        if (pos == 0) {
			        	proximity = weight;
			        } else {
			        	proximity = weight + 0.1 * pos * (1.0 - weight);
			        }
		        }
	        }
        }
        // DONE CALCULATING PROXIMITY. CALCULATE DISTANCE:
        double distance = 1.0 - proximity;
        if(distance <= matchThreshold)
        	return true;
        return false;
	}
}