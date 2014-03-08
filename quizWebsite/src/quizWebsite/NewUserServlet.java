package quizWebsite;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class NewUserServlet
 */
@WebServlet("/NewUserServlet")
public class NewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUserServlet() {
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

		String username = request.getParameter("username");
		System.out.println("username : " + username);
		
		User u = UserManager.addUser(request.getParameter("username"),request.getParameter("password"),false);
		if (u == null){
			RequestDispatcher dispatcher = request.getRequestDispatcher("nameAlreadyTaken.jsp");
			dispatcher.forward(request, response);
		}
		else{
			session.setAttribute(Constants.session_currentUser,u);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userPage.jsp");
			dispatcher.forward(request, response);
			}
	}

}
