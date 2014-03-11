package quizWebsite;

public class Constants {
	// user stuff
	public static final String session_currentUser = "session.currentUser"; // User
	public static final String session_sendMessageTo = "session.sendMessageTo"; // User
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
	public static final String session_previousFeedbackObj = "session.previousFeedbackObj"; // Feedback object
	public static final String session_allFeedbackObjs = "session.allFeedbackObjs"; // ArraList<Feedback>

	
	// quiz creation stuff	
	public static final String session_newQuiz = "session.newQuiz"; // Quiz
	public static final String session_isNewQuizBool = "newQuiz"; // false or true
	public static final String session_newQuestionType = "session.newQuestionType"; // type: String
	public static final String session_newQuestion= "session.newQuestion";  // type: Question subclass


	// acheivements, activities
	public static final String dateFormat = "yyyy:MM:dd HH:mm:ss";
	public static final String context_Connection = "Connection";
}
