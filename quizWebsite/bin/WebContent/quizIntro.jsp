<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quizWebsite.Quiz.*,java.sql.Connection, java.util.Vector, java.util.Collections"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%

Connection con = (Connection) application.getAttribute("Connection"); 
String id = request.getParameter("id");

quizWebsite.Quiz quiz =  quizWebsite.mysqlManager.retreiveQuiz(Integer.parseInt(id),con);
session.setAttribute(quizWebsite.Constants.session_currentQuiz, quiz);

out.println("<title>"+  quiz.name + "	</title> </head>		<body>");
out.println("Welcome to \"" + quiz.name + "\".");
// set the current quiz
// set the list of questions
Vector<Integer> quizQuestions = (Vector<Integer>) quizWebsite.mysqlManager.getQuestions(Integer.parseInt(id),con);
if (quiz.randomOrder)
	Collections.shuffle(quizQuestions);
session.setAttribute(quizWebsite.Constants.session_quizQuestions , quizQuestions);
session.setAttribute(quizWebsite.Constants.session_previousAnswer,null);
session.setAttribute(quizWebsite.Constants.session_currentQuestionIndex,-1);
session.setAttribute(quizWebsite.Constants.session_currentScore,0);
session.setAttribute(quizWebsite.Constants.session_currentQuestion,null);
session.setAttribute(quizWebsite.Constants.session_previousFeedback,null);
session.setAttribute(quizWebsite.Constants.session_allFeedback,null);
session.setAttribute(quizWebsite.Constants.session_lastQuestionBool,false);
// link to the first question
out.println("<form action=\"questionServlet\" method=\"post\">"); // call doPost in questionServlet
out.print("<input type=\"submit\" value = \"Go to Question 0\"/> </form>");

%>
</body>
</html>