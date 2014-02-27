package quizWebsite;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ChooseQuestionTypeServlet
 */
@WebServlet("/ChooseQuestionTypeServlet")
public class ChooseQuestionTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChooseQuestionTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void createNewQuiz(HttpServletRequest request){
//		public Quiz(String name, String url, String creator, boolean immediateFeedback,boolean multiplePages,
	//			boolean practiceMode,boolean randomOrder, String whenCreated, ServletContext context){
		String name = (String) request.getParameter("name");
		String creator = (String) request.getSession().getAttribute("newQuizCreator");
		String immediateFeedback = (String) request.getParameter("immediateFeedback");
		String practiceMode =  (String) request.getParameter("practiceMode");
		String randomOrder = (String)  request.getParameter("randomOrder");
		String multiplePages =  (String) request.getParameter("pages");
		DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss"); 
		Calendar cal = Calendar.getInstance();
		String datetime = dateFormat.format(cal.getTime());
		Quiz q = new Quiz(name,"",creator,immediateFeedback != null, multiplePages.equals("multiplePages"), practiceMode != null, randomOrder != null, datetime, request.getServletContext()); 
		String url = "quizIntro.jsp?id=" + q.pKey;
		q.url = url; 
		int success = mysqlManager.addToDatabase(q, (Connection) request.getServletContext().getAttribute("Connection"));
		request.getSession().setAttribute("newQuizKey",q.pKey);
		System.out.println(success);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		Boolean newQuiz = (Boolean) session.getAttribute("newQuiz"); 
		PrintWriter out = response.getWriter();
		if (newQuiz){
			createNewQuiz(request);
			session.setAttribute("newQuiz", false);
			RequestDispatcher dispatcher = request.getRequestDispatcher("askForQuestion.jsp");
			dispatcher.forward(request, response);
		}
		else{
			String questionType = request.getParameter("questionType");
			Class<?> clazz;
			try {
				clazz = Class.forName("quizWebsite." + questionType);
				session.setAttribute("newQuestionType", questionType);
				java.lang.reflect.Constructor<?> ctor = clazz.getConstructor();
				Question q = (Question) ctor.newInstance ();
				multipleChoiceQuestion question = new multipleChoiceQuestion(); 
				session.setAttribute("newQuestion", question);
				// TODO figure out how to subclass it
				RequestDispatcher dispatcher = request.getRequestDispatcher("CreateQuestion.jsp");
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
	}

}
