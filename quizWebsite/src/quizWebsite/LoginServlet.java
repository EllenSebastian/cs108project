package quizWebsite;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		String password = request.getParameter("password");
		if (username == null && password == null){
			// they clicked the "nonreisted user" button
			session.setAttribute(Constants.session_currentUser,Constants.UNREGISTERED_USER);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userPage.jsp");
			dispatcher.forward(request, response);
		}
		else{
			User u = UserManager.checkUser(username,password);

		if (u != null){
			Cookie c; 
			String checked = request.getParameter("remember me");
			if(checked != null && checked.equals("remember me")){
				String key = u.setCookieKey();
				c = new Cookie("user_key",key);
				c.setMaxAge(365 * 86400);
			}
			else{
				c = new Cookie("user_key", "");
				c.setMaxAge(0);
			}
			c.setHttpOnly(true);
			c.setPath("/");
			response.addCookie(c);
			session.setAttribute(Constants.session_currentUser,u);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userPage.jsp");
			dispatcher.forward(request, response);
		}else{
			RequestDispatcher dispatcher = request.getRequestDispatcher("incorrectLogin.html");
			dispatcher.forward(request, response);
		}
		}
	}

}
