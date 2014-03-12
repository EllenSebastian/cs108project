package quizWebsite;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.jmx.snmp.Timestamp;

/**
 * Servlet implementation class ReviewQuizServlet
 */
@WebServlet("/ReviewQuizServlet")
public class ReviewQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewQuizServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String r = request.getParameter("review");
		String quiz = request.getParameter("quizID");
		
		Integer quizID= Integer.parseInt(quiz);
		int rating = Integer.parseInt(request.getParameter("rating"));
		java.sql.Timestamp time = new java.sql.Timestamp(System.currentTimeMillis());
		User u = (User) session.getAttribute(Constants.session_currentUser);
		
		Review review = new Review(u.user_id, quizID, rating, r,time);
		Integer added = review.addReview();
		RequestDispatcher dispatcher = request.getRequestDispatcher("reviewAdded.jsp");
		dispatcher.forward(request, response);
	}

}
