package quizWebsite;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class questionServlet
 */
@WebServlet("/questionServlet")
public class questionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public questionServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	/* 
	 * score the previous question and add its 
	 */
	void scorePreviousAnswer(HttpSession session, HttpServletRequest request){

		Integer score = (Integer) session.getAttribute(Constants.session_currentScore);
		Question prevQuestion = (Question) session.getAttribute(Constants.session_currentQuestion);
		if (prevQuestion != null) score += prevQuestion.scoreAnswer(request); 

		if (((Quiz) session.getAttribute(Constants.session_currentQuiz)).immediateFeedback){
			if (prevQuestion != null) session.setAttribute(Constants.session_previousFeedback, prevQuestion.getFeedback(request));
		}
		String allFeedback = (String) session.getAttribute(Constants.session_allFeedback);
		if (allFeedback != null) allFeedback += "<br><br> " + prevQuestion.getFeedback(request);
		else if (prevQuestion != null) allFeedback = "<br><br> " + prevQuestion.getFeedback(request);
		else allFeedback = "";
		session.setAttribute(Constants.session_allFeedback, allFeedback);
		session.setAttribute(Constants.session_currentScore,score);
	}

	Integer scoreOneAnswer(Integer questionpKey, HttpServletRequest request){
		Integer score = 0; 

		return score; 
	}
	/* build up a session attribute, currentQuestionHTML, containing the HTML for *ALL*
	 * the questions. 
	 * set session attribute lastQuestion to true so that only one page of questions is displayed. 
	 */
	void onePageQuiz(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();
		Boolean alreadyAnswered = (Boolean) session.getAttribute(Constants.session_lastQuestionBool);
		Vector<Integer> quizQuestions = (Vector<Integer>) session.getAttribute(Constants.session_quizQuestions); // need to initialize this

		if (alreadyAnswered){
			Integer score = 0; 
			String feedback = "";
			int i = 1; 
			for (Integer questionI : quizQuestions){
				Question question = mysqlManager.getQuestion(request,session,questionI,
						(java.sql.Connection)(request.getServletContext().getAttribute(Constants.context_Connection)));
				score += question.scoreAnswer(request);
				feedback += "<br>Question " + i + ":<br> " + question.getFeedback(request);
				i++;
			}

			session.setAttribute(Constants.session_allFeedback, feedback);
			session.setAttribute(Constants.session_currentScore,score);
			RequestDispatcher dispatcher = request.getRequestDispatcher("quizTakenSummary.jsp");
			dispatcher.forward(request, response);
		}else{
			String allHTML = "<form action = \"questionServlet\" method = \"post\">" ; 
			for (Integer questionI : quizQuestions){
				Question question = mysqlManager.getQuestion(request,session,questionI,
						(java.sql.Connection)(request.getServletContext().getAttribute(Constants.context_Connection)));
				allHTML += question.displayQuestion();
				allHTML += "<br><br>";
			}
			allHTML += "<input type=\"submit\" value=\"submit\">";
			allHTML += "</form>";
			session.setAttribute(Constants.session_lastQuestionBool, true);
			session.setAttribute(Constants.session_questionHTML,allHTML);
			RequestDispatcher dispatcher = request.getRequestDispatcher("showQuestion.jsp");
			dispatcher.forward(request, response);
		}
	}

	void multiplePageQuiz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();

		Object previousAnswer = session.getAttribute(Constants.session_previousAnswer);
		Integer previousQuestionIndex = (Integer) session.getAttribute(Constants.session_currentQuestionIndex);
		Integer currentQuestionIndex = previousQuestionIndex + 1; 

		Vector<Integer> quizQuestions = (Vector<Integer>) session.getAttribute(Constants.session_quizQuestions); // need to initialize this

		//if (previousAnswer != null && previousQuestionIndex >= 0)
		scorePreviousAnswer(session,request);
		String form = "";

		if (currentQuestionIndex == quizQuestions.size() ){
			form += "<form action=\"quizTakenSummary.jsp\" method=\"post\">";
			form += "<input type=\"submit\" value=\"Finish quiz\">";
			form += "</form>";
			session.setAttribute(Constants.session_lastQuestionBool, true);
		}
		else{
			form +="<form action=\"questionServlet\" method=\"post\">";

			session.setAttribute(Constants.session_currentQuestionIndex,currentQuestionIndex);
			System.out.println("getting from quizQuestions at index " + currentQuestionIndex + " with length" + quizQuestions.size());
			Integer currentQuestionpKey = quizQuestions.get(currentQuestionIndex);
			Question question = mysqlManager.getQuestion(request,session,currentQuestionpKey,
					(java.sql.Connection)(request.getServletContext().getAttribute(Constants.context_Connection)));
			session.setAttribute(Constants.session_currentQuestion, question);
			form += question.displayQuestion();

			form += "<input type=\"submit\" value=\"Next question\">";
			form += "</form>";
			session.setAttribute(Constants.session_lastQuestionBool, false);
		}
		session.setAttribute(Constants.session_questionHTML,form);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("questionservlet post called");
		Quiz currentQuiz = (Quiz) request.getSession().getAttribute(Constants.session_currentQuiz);
		if (currentQuiz.multiplePages){
			multiplePageQuiz(request,response);
			RequestDispatcher dispatcher = request.getRequestDispatcher("showQuestion.jsp");
			dispatcher.forward(request, response);
		}
		else{
			onePageQuiz(request,response);
		}
		// get rid of showQuestion.jsp completely if this works. 
		System.out.println("forwarding to showQuestion.jsp");

	}
}
