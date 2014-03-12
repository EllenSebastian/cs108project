package quizWebsite;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Set;

/**
 * Servlet implementation class SearchQuizzesServlet
 */
@WebServlet("/SearchQuizzesServlet")
public class SearchQuizzesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchQuizzesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("quizSearch");
		Connection con = (Connection) request.getServletContext().getAttribute("Connection");
		String tag = request.getParameter("tagSearch");

		HashSet<Integer> quizzes = new HashSet<Integer>();
			if (!name.equals("")){
				try {
				PreparedStatement p = con.prepareStatement("SELECT * FROM Quizzes WHERE name LIKE ? ORDER BY whenCreated");
				p.setString(1, "%" + name + "%");
				System.out.println("Connected to db");
				ResultSet result = p.executeQuery();
				while(result.next()) {
					//System.out.println(result.getInt("user_id"));
					quizzes.add(result.getInt("pKey"));
				}
				}catch (SQLException e) {
					e.printStackTrace(); 	
				}
			}
		if (tag != null && !tag.equals("")){
			tag = tag.replaceAll("\\s+",""); // remove whitespace
			String[] tags = tag.split(",");
			for (String t : tags){
				ArrayList<Integer> quizKeys = Tag.getQuizByTag(t);
				for (Integer q : quizKeys){	
						quizzes.add(q);
				}
			}	
		}
		ArrayList<Quiz> Quizzes = new ArrayList<Quiz>();
		for (Integer q : quizzes){
			Quizzes.add( mysqlManager.retreiveQuiz(q,con));

		}
		request.getSession().setAttribute("quizSearchResults", Quizzes);
		RequestDispatcher dispatch = 
			 request.getRequestDispatcher("showQuizzes.jsp"); 
			 dispatch.forward(request, response); 	}

	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
