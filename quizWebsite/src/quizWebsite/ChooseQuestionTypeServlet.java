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
		System.out.println("new line");
		String[] names =  request.getParameterValues("name");
		User creator = ((User) request.getSession().getAttribute(Constants.session_currentUser));
		String immediateFeedback = (String) request.getParameter("immediateFeedback");
		String practiceMode =  (String) request.getParameter("practiceMode");
		String randomOrder = (String)  request.getParameter("randomOrder");
		String multiplePages =  (String) request.getParameter("pages");
		String[] description = request.getParameterValues("description");
		DateFormat dateFormat = new SimpleDateFormat(Constants.dateFormat); 
		Calendar cal = Calendar.getInstance();
		String datetime = dateFormat.format(cal.getTime());
		
		Quiz q = new Quiz(names[0],description[0],"",creator.user_id,immediateFeedback != null, 
				multiplePages.equals("multiplePages"), practiceMode != null, randomOrder != null, datetime, request.getServletContext()); 
		String url = "quizIntro.jsp?id=" + q.pKey;
		q.url = url; 
		int success = mysqlManager.addToDatabase(q, (Connection) request.getServletContext().getAttribute(Constants.context_Connection));
		request.getSession().setAttribute(Constants.session_newQuiz,q);
		System.out.println(success);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		Boolean newQuiz = (Boolean) session.getAttribute(Constants.session_isNewQuizBool); 
		PrintWriter out = response.getWriter();
		if (newQuiz){
			createNewQuiz(request);
			session.setAttribute(Constants.session_isNewQuizBool, false);
			RequestDispatcher dispatcher = request.getRequestDispatcher("askForQuestion.jsp");
			dispatcher.forward(request, response);
		}
		else{
			String questionType = request.getParameter("questionType");
			Class<?> clazz;
			try {
				clazz = Class.forName("quizWebsite." + questionType);
				session.setAttribute(Constants.session_newQuestionType, questionType);
				java.lang.reflect.Constructor<?> ctor = clazz.getConstructor();
				Question q = (Question) ctor.newInstance ();
				session.setAttribute(Constants.session_newQuestion, q);
				// TODO figure out how to subclass it
				RequestDispatcher dispatcher = request.getRequestDispatcher("CreateQuestion.jsp");
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
	}

}
