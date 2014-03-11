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
		result += "<p><input type=\"text\" name=\"" + pKey + "answer\" ></p>";
		return result;
	}
	
	@Override
	public int scoreAnswer(HttpServletRequest request) {
		String userAnswer = (String) request.getParameter("" + pKey + "answer");
		userAnswer = userAnswer.toLowerCase();
		String questionAnswer = answer.toLowerCase();
		if(Question.match(userAnswer,questionAnswer))
			return 1;
		return 0;
	}
	
	@Override
	public String getFeedback(HttpServletRequest request) {
		String userAnswer = (String) request.getParameter("" + pKey + "answer");
		userAnswer = userAnswer.toLowerCase();
		String questionAnswer = answer.toLowerCase();
		if(Question.match(userAnswer,questionAnswer))
			return "For question \"" + question + "\", your answer \"" + userAnswer + "\" was correct. You earned 1 point.";
		return "For question \"" + question + "\", your answer \"" + userAnswer + 
				"\" was incorrect. The correct answer is \"" + answer + "\". You earned 0 points.";

	}
	
	@Override
	public String compressData() {
		return question + "__" + answer;
	}
	
	@Override
	public int parseData(String data) {
		System.out.println(data);
		int delim = data.indexOf("__");
		if (delim == -1 ) return 0; 

		question = data.substring(0,delim);

		data = data.substring(delim+2);
		if (data.length() == 0) return 0; 
		answer = data;
		System.out.println("question: " + question + "  answer: " + answer);
		return 1; 
//		return extractField(data,question,answer);
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
		quizKey =( (Quiz) session.getAttribute(Constants.session_newQuiz)).pKey;
		pKey = getNextQuestionpKey(request);
		return 0;
	}
}
