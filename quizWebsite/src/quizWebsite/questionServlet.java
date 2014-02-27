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

import com.sun.tools.internal.xjc.model.Constructor;

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

		Integer score = (Integer) session.getAttribute("currentScore");
		Question prevQuestion = (Question) session.getAttribute("currentQuestion");
		if (prevQuestion != null) score += prevQuestion.scoreAnswer(request); 

		if (((Quiz) session.getAttribute("currentQuiz")).immediateFeedback){
			if (prevQuestion != null) session.setAttribute("previousFeedback", prevQuestion.getFeedback(request));
		}
		String allFeedback = (String) session.getAttribute("allFeedback");
		if (allFeedback != null) allFeedback += "<br><br> " + prevQuestion.getFeedback(request);
		else if (prevQuestion != null) allFeedback = "<br><br> " + prevQuestion.getFeedback(request);
		else allFeedback = "";
		session.setAttribute("allFeedback", allFeedback);


		session.setAttribute("currentScore",score);
	}


	/* build up a session attribute, currentQuestionHTML, containing the HTML for *ALL*
	 * the questions. 
	 * set session attribute lastQuestion to true so that only one page of questions is displayed. 
	 */
	void onePageQuiz(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Vector<Integer> quizQuestions = (Vector<Integer>) session.getAttribute("quizQuestions"); // need to initialize this
		String allHTML = "" ; 
		for (Integer quizI : quizQuestions){
			Question question = mysqlManager.getQuestion(request,session,quizI,
					(java.sql.Connection)(request.getServletContext().getAttribute("Connection")));
			allHTML += question.displayQuestion();
			allHTML += "<br><br>";
		}
		session.setAttribute("lastQuestion", true);
		session.setAttribute("currentQuestionHTML",allHTML);
	}

	void multiplePageQuiz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();

		Object previousAnswer = session.getAttribute("previousAnswer");
		Integer previousQuestionIndex = (Integer) session.getAttribute("currentQuestionIndex");
		Integer currentQuestionIndex = previousQuestionIndex + 1; 

		Vector<Integer> quizQuestions = (Vector<Integer>) session.getAttribute("quizQuestions"); // need to initialize this

		//if (previousAnswer != null && previousQuestionIndex >= 0)
			scorePreviousAnswer(session,request);

		if (currentQuestionIndex == quizQuestions.size() - 1)
			session.setAttribute("lastQuestion", true);

		else
			session.setAttribute("lastQuestion", false);

		session.setAttribute("currentQuestionIndex",currentQuestionIndex);
		Integer currentQuestionpKey = quizQuestions.get(currentQuestionIndex);
		Question question = mysqlManager.getQuestion(request,session,currentQuestionpKey,
				(java.sql.Connection)(request.getServletContext().getAttribute("Connection")));
		session.setAttribute("currentQuestion", question);
		session.setAttribute("currentQuestionHTML", question.displayQuestion());			
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Quiz currentQuiz = (Quiz) request.getSession().getAttribute("currentQuiz");
		if (currentQuiz.multiplePages){
			multiplePageQuiz(request,response);
		}
		else{
			onePageQuiz(request,response);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("showQuestion.jsp");
		dispatcher.forward(request, response);
	}

}
