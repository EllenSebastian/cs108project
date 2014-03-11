<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quizWebsite.*, java.util.Vector"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
String questionHTML =(String) session.getAttribute(quizWebsite.Constants.session_questionHTML);
Integer questionIndex = (Integer) session.getAttribute(quizWebsite.Constants.session_currentQuestionIndex);

Vector<Integer> quizQuestions = (Vector<Integer>)session.getAttribute(quizWebsite.Constants.session_quizQuestions); // need to initialize this
Boolean lastQuestion = (Boolean) session.getAttribute(quizWebsite.Constants.session_lastQuestionBool);
Feedback previousFeedback = (Feedback) session.getAttribute(quizWebsite.Constants.session_previousFeedbackObj);


if (previousFeedback != null){
	out.println("previous question feedback: <br>" + previousFeedback.toString() + "<br><br>");
}

out.println(questionHTML);
System.out.println(questionHTML);
/*if (lastQuestion){
	// forward to showQuestion.jsp
	out.println("<form action=\"QuizResultsServlet\" method=\"post\">"); // call doPost in questionServlet
	out.print("<input type=\"submit\" value = \"Finish Quiz\"/> </form>");
}
else{ // last question
	out.println("<form action=\"questionServlet\" method=\"post\">");// go to QuizResults
	out.print("<input type=\"submit\" value = \"Go to next question\"/> </form>");
}*/

%><a href=userPage.jsp>Home</a>

</body>
</html>