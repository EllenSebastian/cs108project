package quizWebsite;

import java.io.IOException;
import java.sql.Connection;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateQuestionServlet
 */
@WebServlet("/CreateQuestionServlet")
public class CreateQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	// from QuestionSubclasses.newQuestionForm
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Enumeration<String> attrs =  request.getAttributeNames();
		while(attrs.hasMoreElements()) {
		    System.out.println(attrs.nextElement());
		}
		Enumeration<String> params =  request.getParameterNames();
		while(params.hasMoreElements()) {
			String elem = params.nextElement();
			String a = (String) request.getParameter(elem);
			System.out.println(elem + " "  + a);
		}
		Connection con = (Connection) request.getServletContext().getAttribute(Constants.context_Connection);
	//String questionType = (String)  session.getAttribute(Constants.session_newQuestionType);
		String questionType = (String) session.getAttribute(quizWebsite.Constants.session_newQuestionType);

		System.out.println("got question type " + questionType);
		Class<?> clazz;
		try {
			clazz = Class.forName("quizWebsite." + questionType);
			java.lang.reflect.Constructor<?> ctor = clazz.getConstructor();
			Question q = (Question) ctor.newInstance ();
			q.parseNewQuestion(request);
			mysqlManager.addToDatabase(q,con);
		} catch (Exception e){
			e.printStackTrace();
		}		
	
		RequestDispatcher dispatcher = request.getRequestDispatcher("askForQuestion.jsp");
		dispatcher.forward(request, response);
	}
	}
