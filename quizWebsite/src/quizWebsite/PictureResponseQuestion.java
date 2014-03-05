package quizWebsite;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PictureResponseQuestion extends Question {
	private String imageURL;
	private String question;
	private String answer;
	
	@Override
	public String className() {
		return "PictureResponseQuestion";
	}

	@Override
	public String displayQuestion() {
		String result = "<img src=\"" + imageURL + "\">";
		result += "<p>" + question + "</p><br><br>";
		result += "<input type=\"text\" name=\"" + pKey + "answer\" >";
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
		return imageURL + "__" + question + "__" + answer;
	}

	@Override
	// return 1 if ok, 0 if not ok
	public int parseData(String data) {
		System.out.println("data : " + data);
		int delim = data.indexOf("__");
		if (delim == -1) return 0 ; 
		imageURL = data.substring(0,delim);
		System.out.println("image: " + imageURL);
		data = data.substring(delim+2);
		delim = data.indexOf("__");
		if (delim == -1) return 0 ; 
		question = data.substring(0,delim);
		data = data.substring(delim+2);
		answer = data;
		System.out.println(" Question:" + question);
		System.out.println("answer : " + answer);
		
		return 1;
	}

	@Override
	public String newQuestionForm() {
		String result = "<p>";
		result += "A Picture-Response question displays a picture and a question relevant to the picture and requires an answer in text format.";
		result += "</p>";
		result += "<p>";
		result += "Define the question using the fields below. ";
		result += "The IMAGE URL field should contain a valid URL to the image to be displayed. ";
		result += "The QUESTION field should contain the question to be displayed that the user must answer. ";
		result += "The ANSWER field should contain the answer to the question. ";
		result += "The answer is not case-sensitive. ";
		result += "Answers that are sufficiently and not necessarily exactly similar will be considered correct.";
		result += "</p>";
		result += "<p>No field should be empty or contain two underscore (_) characters in sequence.</p>";
		result += "<p>IMAGE URL: <input type=\"text\" name=\"imageURL\" /></p>";
		result += "<p>QUESTION: <input type=\"text\" name=\"question\" /></p>";
		result += "<p>ANSWER: <input type=\"text\" name=\"answer\" /></p>";
		result += "<input type=\"submit\" value = \"Submit\" /></p>";
		return result;
	}

	@Override
	public int parseNewQuestion(HttpServletRequest request) {
		imageURL = request.getParameter("imageURL");
		question = request.getParameter("question");
		answer = request.getParameter("answer");
		if(imageURL.contains("__") || imageURL.length() == 0 || question.contains("__") || question.length() == 0 || answer.contains("__") || answer.length() == 0)
			return 1;
		HttpSession session = request.getSession();
		quizKey =( (Quiz) session.getAttribute(Constants.session_newQuiz)).pKey;
		pKey = Question.getNextQuestionpKey(request);
		return 0;
	}

}
