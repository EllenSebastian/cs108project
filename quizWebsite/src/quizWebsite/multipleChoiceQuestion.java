package quizWebsite;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class multipleChoiceQuestion extends Question {
	private List<String> choices;
	private String question;
	private String answer;
	
	@Override
	public String className() {
		return "MultipleChoiceQuestion";
	}
	
	@Override
	public String displayQuestion() {
		String result = "<p>" + question + "<p>";
		for(String choice : choices) {
			result += "<p><input type=\"radio\" name=\"answer\" value=\"" + pKey + choice + "\">" + choice + "</p>";
		}
		return result;
	}
	
	@Override
	public int scoreAnswer(HttpServletRequest request) {
		String userAnswer = (String) request.getAttribute("" + pKey + "answer");
		if(Question.match(userAnswer,answer))
			return 1;
		return 0;
	}
	
	@Override
	public String getFeedback(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String compressData() {
		String result = question + "__" + answer;
		for(String choice : choices) {
			result += "__" + choice;
		}
		return result;
	}
	
	@Override
	public int parseData(String data) {
		choices = new ArrayList<String>();
		String remainingData = "";
		if(extractField(data,question,remainingData) != 0)
			return 1;
		if(extractField(remainingData,answer,remainingData) != 0)
			return 1;
		String choice = "";
		while(extractField(remainingData,choice,remainingData) == 0) {
			choices.add(choice);
		}
		return 0;
	}
	
	@Override
	public String newQuestionForm() {
		String result = "<p>";
		result += "A Multiple-choice question displays a question and a list of possible answers from which the user is required to choose the single correct one.";
		result += "</p>";
		result += "<p>";
		result += "Define the question using the fields below. ";
		result += "The QUESTION field should contain the question to be displayed that the user must answer. ";
		result += "Every CHOICE field should either contain a potential answer to the question or be blank. ";
		result += "A maximum of 10 choices are allowed. ";
		result += "If you want there to be less than 10 choices, simply fill in only as many answer fields as you need. ";
		result += "All blank choice fields will be ignored. ";
		result += "To signify which choice is the correct answer, click the selection box next to it. ";
		result += "The choices will be displayed in the order that you enter them.";
		result += "</p>";
		result += "<p>The question field should not be empty and none of the fields should contain the underscore (_) character.</p>";
		result += "<p>QUESTION: <input type=\"text\" name=\"question\" /></p>";
		for(int i = 0; i < 10; i++) {
			result += "<p>";
			result += "<input type=\"radio\" name=\"answer\" value=\"" + i + "\">";
			result += "CHOICE: <input type=\"text\" name=\"choice" + i + "\" />";
			result += "</p>";
		}
		result += "<input type=\"submit\" value = \"Submit\" /></p>";
		return result;
	}
	
	@Override
	public int parseNewQuestion(HttpServletRequest request) {
		choices = new ArrayList<String>();
		question = request.getParameter("question");
		if(question.contains("__") || question.length() == 0)
			return 1;
		String answerStr = request.getParameter("answer");
		int answerInt = 0;
		try {
			answerInt = Integer.parseInt(answerStr);
		} catch (NumberFormatException e) {
			return 1;
		}
		for(int i = 0; i < 10; i++) {
			String choice = request.getParameter("choice" + i);
			if(choice.contains("__"))
				return 1;
			if(i == answerInt) {
				 if(choice.length() == 0)
					 return 1;
				 answer = choice;
			}
			if(choice.length() != 0) {
				choices.add(choice);
			}
		}
		HttpSession session = request.getSession();
		quizKey = (Integer) session.getAttribute(Constants.session_newQuizKey);
		pKey = Question.getNextQuestionpKey(request);
		return 0;
	}
}
