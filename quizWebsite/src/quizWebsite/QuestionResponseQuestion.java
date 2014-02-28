package quizWebsite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class QuestionResponseQuestion extends Question {
	private String question;
	private String answer;
	
	@Override
	public String className() {
		return "QuestionResponseQuestion";
	}
	
	@Override
	public String displayQuestion() {
		String result = "<p>" + question + "<p>";
		result += "<p><input type=\"text\" name=\"" + pKey + "answer\" /></p>";
		return result;
	}
	
	@Override
	public int scoreAnswer(HttpServletRequest request) {
		String userAnswer = (String) request.getAttribute("" + pKey + "answer");
		userAnswer = userAnswer.toLowerCase();
		String questionAnswer = answer.toLowerCase();
		if(Question.match(userAnswer,questionAnswer))
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
		return question + "__" + answer;
	}
	
	@Override
	public int parseData(String data) {
		return extractField(data,question,answer);
	}
	
	@Override
	public String newQuestionForm() {
		String result = "<p>";
		result += "A Question-Response question displays a question and requires an answer in text format.";
		result += "</p>";
		result += "<p>";
		result += "Define the question using the fields below. ";
		result += "The QUESTION field should contain the question to be displayed that the user must answer. ";
		result += "The ANSWER field should contain the answer to the question. ";
		result += "The answer is not case-sensitive. ";
		result += "Answers that are sufficiently and not necessarily exactly similar will be considered correct.";
		result += "</p>";
		result += "<p>Neither field should be empty or contain the underscore (_) character.</p>";
		result += "<p>QUESTION: <input type=\"text\" name=\"question\" /></p>";
		result += "<p>ANSWER: <input type=\"text\" name=\"answer\" /></p>";
		result += "<input type=\"submit\" value = \"Submit\" /></p>";
		return result;
	}
	
	@Override
	public int parseNewQuestion(HttpServletRequest request) {
		question = request.getParameter("question");
		answer = request.getParameter("answer");
		if(question.contains("__") || question.length() == 0 || answer.contains("__") || answer.length() == 0)
			return 1;
		HttpSession session = request.getSession();
		quizKey = (Integer) session.getAttribute(Constants.session_newQuizKey);
		pKey = getNextQuestionpKey(request);
		return 0;
	}
}
