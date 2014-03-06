package quizWebsite;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		User user = (User) request.getSession().getAttribute(
				Constants.session_currentUser);
		int type = Integer.parseInt(request.getParameter("Type"));
		String body = request.getParameter("body");
		int quizId = Integer.parseInt(request.getParameter("quizID"));
		String toUser = request.getParameter("toUser");
		User temp = UserManager.searchExact(toUser);
		System.out.println(toUser);
		if (temp != null) {
			System.out.println("found user");
			int toId = temp.user_id;
			Message msg = new Message(type, false, body, quizId, user.user_id,
					toId, new Timestamp(System.currentTimeMillis()));
			msg.sendMessage();
		}
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("userPage.jsp");
		dispatcher.forward(request, response);
	}

}
