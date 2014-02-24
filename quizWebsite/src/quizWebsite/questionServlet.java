package quizWebsite;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	private Question getQuestion(HttpServletRequest request, HttpSession session, Integer currentQuestionIndex){
		
		@SuppressWarnings("unchecked")
		Vector<Integer> quizQuestions = (Vector<Integer>) session.getAttribute("quizQuestions"); // need to initialize this
		Integer currentQuestionpKey = quizQuestions.get(currentQuestionIndex);

		java.sql.Connection connection= (Connection) request.getServletContext().getAttribute("Connection");
		try {
			java.sql.Statement st = connection.createStatement();
			String query = "select type from Questions where pKey = " + currentQuestionpKey + ";";
			System.out.println("questionServlet.getQuestion query: " + "select type from Questions where pKey = " + currentQuestionpKey + ";");
			ResultSet rs= st.executeQuery(query);
			rs.next();
			String 	questionClassName = "quizWebsite." + rs.getString(1); 
			Class<?> clazz = Class.forName(questionClassName);
			java.lang.reflect.Constructor<?> ctor = clazz.getConstructor(Integer.class,ServletContext.class);
			System.out.println(" returning question with pkey " + currentQuestionpKey);
			return (Question) ctor.newInstance(new Object[] {currentQuestionpKey,request.getServletContext()});
		} catch (Exception e){
			e.printStackTrace();
		}
		return null; 
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object previousAnswer = session.getAttribute("previousAnswer");
		Integer previousQuestionIndex = (Integer) session.getAttribute("previousQuestionIndex");
		if (previousAnswer != null && previousQuestionIndex >= 0){
			Integer score = (Integer) session.getAttribute("score");
			Question prevQuestion = (Question) session.getAttribute("previousQuestion");
			String prevQuestionClassName = prevQuestion.getType();
			Class<?> clazz;
			try {
				clazz = Class.forName(prevQuestionClassName);
				java.lang.reflect.Constructor<?> ctor = clazz.getConstructor(String.class);
				prevQuestion = (Question) ctor.newInstance(new Object[] {0,request.getServletContext()});
			} catch (Exception e) {
				e.printStackTrace();
			}
			// TODO figure out how to get score to work, since I don't know exactly which subclass it is. 
			//score += prevQuestion.score(request);
		}
		Integer currentQuestionIndex = previousQuestionIndex + 1; 
		session.setAttribute("previousQuestionIndex",currentQuestionIndex);
		Question question = getQuestion(request,session,currentQuestionIndex);
		PrintWriter out = response.getWriter();

		out.println(question.displayQuestionForm());
		
		// grade the previous question (session attribute previousQuestion, previousAnswer)
		// score += answerScore(request)
		// TODO get question key from session data
		// if no question forward to results page
		// build up question
		// display question 
		// set this as response 
		// set previousQuestion
	}

}
