package quizWebsite;

public class Constants {
	// user stuff
	public static final String session_currentUser = "session.currentUser"; // Integer (id for user)

	// quiz taking stuff
	public static final String session_previousAnswer = "session.previousAnswer";
	public static final String session_questionHTML = "session.currentQuestionHTML";
	public static final String session_lastQuestionBool = "session.lastQuestion";
	public static final String session_currentScore = "session.currentScore";
	public static final String session_currentQuestion = "session.currentQuestion";
	public static final String session_currentQuiz = "session.currentQuiz";
	public static final String session_allFeedback = "session.allFeedback";
	public static final String session_previousFeedback = "session.previousFeedback";
	public static final String session_currentQuestionIndex= "session.currentQuestionIndex";
	public static final String session_quizQuestions= "session.quizQuestions";
	
	
	// quiz creation stuff	
	public static final String session_newQuizKey = "session.newQuizKey"; // Integer
	public static final String session_isNewQuizBool = "newQuiz"; // false or true
	public static final String session_newQuestionType = "session.newQuestionType"; // type: String
	public static final String session_newQuestion= "session.newQuestion";  // type: Question subclass


	
	public static final String dateFormat = "yyyy:MM:dd HH:mm:ss";
	public static final String context_Connection = "Connection";

}
