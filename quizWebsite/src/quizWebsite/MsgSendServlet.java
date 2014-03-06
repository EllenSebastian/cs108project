package quizWebsite;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MsgSendServlet
 */
@WebServlet("/MsgSendServlet")
public class MsgSendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MsgSendServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = request.getSession().getAttribute(Constants.session_currentUser);
		int type = request.getParameterValues("Type");
		String body = request.getParameter("Body");
		int quizId = request.getParameterValues("quizID");
		String toUser = request.getParameterValues("toUser");
		Message msg = new Message(type, false, body, quizId, 
				user.name(), toUser, new Timestamp(System.currentTimeMillis()));
		
		msg.sendMessage();
		RequestDispatcher dispatcher = request.getRequestDispatcher("userPage.jsp");
		dispatcher.forward(request, response);
	}

}
