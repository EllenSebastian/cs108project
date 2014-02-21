package quizWebsite;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class multipleChoiceQuestion extends Question {
	public multipleChoiceQuestion(HttpServletRequest request, ArrayList<String> choices, String question, int correctChoice) {
		context=request.getServletContext();
		pKey = (Integer) context.getAttribute("maxQuestionKey");
		context.setAttribute("maxQuestionKey",pKey+1);
		// TODO Auto-generated method stub
	}

	public String displayQuestion() {
		// TODO Auto-generated method stub
		return null;
	}

	public int scoreAnswer() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String displayQuestionForm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void parseData(String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	int scoreAnswer(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	void storeData() {
		// TODO Auto-generated method stub
		
	}

}
