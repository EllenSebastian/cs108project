<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quizWebsite.Question.*, java.util.Vector"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
String questionHTML =(String) session.getAttribute("currentQuestionHTML");
Integer questionIndex = (Integer) session.getAttribute("currentQuestionIndex");

Vector<Integer> quizQuestions = (Vector<Integer>) session.getAttribute("quizQuestions"); // need to initialize this
Boolean lastQuestion = (Boolean) session.getAttribute("lastQuestion");
String previousFeedback = (String) session.getAttribute("previousFeedback");


if (previousFeedback != null){
	out.println("previous question feedback: <br>" + previousFeedback + "<br><br>");
}

out.println(questionHTML);



if (lastQuestion){
	// forward to showQuestion.jsp
	out.println("<form action=\"QuizResultsServlet\" method=\"post\">"); // call doPost in questionServlet
	out.print("<input type=\"submit\" value = \"Finish Quiz\"/> </form>");
}
else{ // last question
	out.println("<form action=\"questionServlet\" method=\"post\">");// go to QuizResults
	out.print("<input type=\"submit\" value = \"Go to next question\"/> </form>");
}

%>
</body>
</html>