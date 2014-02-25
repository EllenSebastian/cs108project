package quizWebsite;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// if there was previous question, parse it 
		// if previous question returned 1, 
			//set session attr that there was an error
			// dispatch back to createQuestionServlet 
			// return
		// else
			// put previous question in database
		// "done with questions" button --> send to jsp if there have been questions added
		// get question type 
		// forward to CreateQuestionServlet
	}

}
