package quizWebsite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FillInTheBlankQuestion extends Question {
	private String questionP1;
	private String answer;
	private String questionP2;
	
	@Override
	public String className() {
		return "FillInTheBlankQuestion";
	}

	@Override
	public String displayQuestion() {
		String result = "<p>" + questionP1 + " ";
		result += "<input type=\"text\" name=\"" + pKey + "answer\" >";
		result += " " + questionP2 + "</p>";
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
		if(Question.match(userAnswer,questionAnswer)){
			return "For question: <br> " + questionP1 + "___" + questionP2 + ", your answer of \"" + 
					userAnswer + "\"was correct. You earned 1 point.";
		}
		return "For question: <br> " + questionP1 + "___" + questionP2 + ", your answer was incorrect. The correct answer was \"" 
		+ answer + "\".   You earned 0 points.";

	}

	@Override
	public String compressData() {
		return questionP1 + "__" + answer + "__" + questionP2;
	}

	@Override
	public int parseData(String data) {
		int delim = data.indexOf("__");
		if (delim == -1 ) return 0; 
		questionP1 = data.substring(0,delim);
		System.out.println("Questionp1; " + questionP1);
		
		data = data.substring(delim+2);
		
		System.out.println("data now; " + data);
		
		delim = data.indexOf("__");
		if (delim == -1 ) return 0; 
		answer = data.substring(0,delim);
		System.out.println("answer; " + answer);
		
		data = data.substring(delim+2);
		if (data.length() == 0) return 0; 
		questionP2 = data;
		System.out.println("questionp2; " + questionP2);
		
		
		return 1;
	}

	@Override
	public String newQuestionForm() {
		String result = "<p>";
		result += "A Fill-in-the-Blank question displays a statement with an embedded text field and expects the user to provide an answer in the text-field such that the statement is true.";
		result += "</p>";
		result += "<p>";
		result += "Define the question using the fields below. ";
		result += "The PART 1 field should contain the portion of the statement that the user must complete that comes before the answer box. ";
		result += "The ANSWER field should contain the answer that the user must enter into the answer box. ";
		result += "The PART 2 field should contain the portion of the statement that comes after the answer box. ";
		result += "The answer is not case-sensitive. ";
		result += "Answers that are sufficiently and not necessarily exactly similar will be considered correct.";
		result += "</p>";
		result += "<p>No field should be empty or contain the underscore (_) character.</p>";
		result += "<p>PART 1: <input type=\"text\" name=\"part1\" /></p>";
		result += "<p>ANSWER: <input type=\"text\" name=\"answer\" /></p>";
		result += "<p>PART 2: <input type=\"text\" name=\"part2\" /></p>";
		result += "<input type=\"submit\" value = \"Submit\" /></p>";
		return result;
	}

	@Override
	public int parseNewQuestion(HttpServletRequest request) {
		questionP1 = request.getParameter("part1");
		answer = request.getParameter("answer");
		questionP2 = request.getParameter("part2");
		if(questionP1.contains("__") || answer.contains("__") || questionP2.contains("__"))
			return 1;
		if(questionP1.length() == 0 || answer.length() == 0 || questionP2.length() == 0)
			return 1;
		HttpSession session = request.getSession();
		quizKey =( (Quiz) session.getAttribute(Constants.session_newQuiz)).pKey;
		pKey = getNextQuestionpKey(request);
		return 0;
	}
}
