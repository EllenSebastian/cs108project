package quizWebsite;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class multipleChoiceQuestion extends Question {
	private List<String> choices;
	private String question;
	private String answer;
	
	@Override
	public String className() {
		return "multipleChoiceQuestion";
	}
	
	@Override
	public String displayQuestion() {
		String result = "<p>" + question + "<p>";
		for(String choice : choices) {
			result += "<p><input type=\"radio\" name=\"" + pKey + "answer\" value=\"" + pKey +"_"+  choice + "\">" + choice + "</p>";
		}
		return result;
	}
	
	@Override
	public int scoreAnswer(HttpServletRequest request) {
		/*Enumeration<String> attrs =  request.getAttributeNames();
		while(attrs.hasMoreElements()) {
		    System.out.println(attrs.nextElement());
		}
		Enumeration<String> params =  request.getParameterNames();
		while(params.hasMoreElements()) {
			String elem = params.nextElement();
			String a = (String) request.getParameter(elem);
			System.out.println(elem + " "  + a);
		}*/	
		String userAnswer = (String) request.getParameter("" + pKey + "answer");
		System.out.println("User answer : " + userAnswer);
		userAnswer = userAnswer.substring(userAnswer.indexOf("_")+1);
		if (userAnswer.equals(answer))
			return 1;
	
		return 0;
	}
	
	@Override
	public String getFeedback(HttpServletRequest request) {
		String userAnswer = (String) request.getParameter("" + pKey + "answer");
		userAnswer = userAnswer.substring(userAnswer.indexOf("_")+1);

		if (scoreAnswer(request) == 1){
			return "Your answer of " + userAnswer + " was correct. You received 1 point.";
		}
		return "Your answer of " + userAnswer + " was not correct. The correct answer was "
			+ answer + ". You received 0 points";
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
	// return 1 for ok, 0 for error. 
	public int parseData(String data) {
		choices = new ArrayList<String>();
		System.out.println("data : "  + data);
		
		int delim = data.indexOf("__");
		if (delim == -1 || delim >=  data.length() -2) return 0; 
		question = data.substring(0,delim);
		System.out.println("Question; " + question);
		
		data = data.substring(delim+2);
		System.out.println("data now; " + data);
		
		delim = data.indexOf("__");
		if (delim == -1 || delim >= data.length() -2) return 0; 
		
		answer = data.substring(0,delim);
		System.out.println("answer : " + answer);
		
		data = data.substring(delim+2);
		System.out.println("data now; " + data);

		String choice; 
		while(data.contains("__")){
			delim = data.indexOf("__");
			choice = data.substring(0,delim);
			choices.add(choice);
			System.out.println("Choice : " + choice );
			data = data.substring(delim+2);
			System.out.println("data now; " + data);
		}
		System.out.println("last choice : " + data);
		choices.add(data);
		if (!choices.contains(answer)){
			return 0; 
		}
		return 1;
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
		quizKey =( (Quiz) session.getAttribute(Constants.session_newQuiz)).pKey;
		pKey = Question.getNextQuestionpKey(request);
		return 0;
	}
}
