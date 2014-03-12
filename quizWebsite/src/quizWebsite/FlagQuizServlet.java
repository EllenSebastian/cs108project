package quizWebsite;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FlagQuizServlet
 */
@WebServlet("/FlagQuizServlet")
public class FlagQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlagQuizServlet() {
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
		Connection con = (Connection) request.getServletContext().getAttribute(Constants.context_Connection);
		Quiz q = (Quiz) session.getAttribute(Constants.session_currentQuiz);
		User u = ((User) session.getAttribute(Constants.session_currentUser));
		String userName = "An unregistered user";
		if (u != null) userName = u.name(); 
		java.util.Date date= new java.util.Date();
		java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
		String msg = "User " + userName + " marked quiz " + q.name + " as inappropriate. Please review the quiz and delete it if necessary.";
	
		ArrayList<User> admins = mysqlManager.retreiveAllUsers(con);
		for (User admin : admins){
			if (admin.isAdmin()){
				Message message = new Message(Message.GeneralNote, false,msg,q.pKey, u.user_id, admin.user_id, time);
				message.sendMessage(); 
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("flaggedQuiz.jsp");
		dispatcher.forward(request, response);
	}

}
