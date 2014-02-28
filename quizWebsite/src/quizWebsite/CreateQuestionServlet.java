package quizWebsite;

import java.io.IOException;
import java.sql.Connection;

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
		Connection con = (Connection) request.getServletContext().getAttribute("Connection");
		String questionType = (String)  session.getAttribute("newQuestionType");
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
