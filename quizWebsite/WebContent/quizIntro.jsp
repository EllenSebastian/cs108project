<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quizWebsite.*,java.sql.Connection, java.util.Vector, java.util.Collections"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%

Connection con = (Connection) application.getAttribute("Connection"); 
String id = request.getParameter("id");
Integer qid = Integer.parseInt(id);
quizWebsite.User currentUser = (quizWebsite.User) session.getAttribute(quizWebsite.Constants.session_currentUser);
quizWebsite.Quiz quiz =  quizWebsite.mysqlManager.retreiveQuiz(Integer.parseInt(id),con);
session.setAttribute(quizWebsite.Constants.session_currentQuiz, quiz);

System.out.println("Quiz : " + quiz);
User user = (User)session.getAttribute(quizWebsite.Constants.session_currentUser);

if(user.isAdmin()){
	out.println("<p><a href=quizClear.jsp?id="+qid+">Clear History</a></p>");
	out.println("<p><a href=quizDelete.jsp?id="+qid+">Delete Quiz</a></p>");
}

out.println("<title>"+  quiz.name + "	</title> </head>		<body>");
out.println("<h1>Welcome to \"" + quiz.name + "\".</h1>");
out.println("<h2>" + quiz.description + "</h2>");
quizWebsite.User creator = quizWebsite.mysqlManager.retreiveUser(quiz.creator,con);
out.println("Quiz created by <a href=userProfile.jsp?uid=" + quiz.creator + ">" + creator.name() + "</a>");


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
session.setAttribute(quizWebsite.Constants.session_previousFeedbackObj,null);
session.setAttribute(quizWebsite.Constants.session_allFeedbackObjs,null);

session.setAttribute(quizWebsite.Constants.session_allFeedback,null);
session.setAttribute(quizWebsite.Constants.session_lastQuestionBool,false);
// link to the first question

out.println(" Your past performance");
String userQuizHistory = quizWebsite.mysqlManager.quizHistory(currentUser.user_id,
		qid,null,null,con);
String allTopPerformers = quizWebsite.mysqlManager.quizHistory(null,qid,null,5,con);
String pastDayPerformers = quizWebsite.mysqlManager.quizHistory(null,qid,24,null,con);

out.println("<br><h3>your history on this quiz: </h3><br>" + userQuizHistory + "<br><br>");

out.println("<h3>All-time top performers:</h3> <br>" + allTopPerformers + "<br><br>");
out.println("<h3>your history on this quiz: </h3><br>" + pastDayPerformers + "<br><br>");
out.println("<h3> Summary statistics for this quiz: <h3>");
out.println("<br>Number of times taken: " + quizWebsite.mysqlManager.timesQuizTaken(qid,con));
Float qa = quizWebsite.mysqlManager.quizAverage(qid,con);
out.println("<br>Average score: " + qa);
out.println("<br>High score: " + quizWebsite.mysqlManager.quizMin(qid,con));
out.println("<br>Lowest score: " + quizWebsite.mysqlManager.quizMax(qid,con));

// add "summary statistics on how well all users " 
out.println("<form action=\"questionServlet\" method=\"post\">"); // call doPost in questionServlet
out.print("<input type=\"submit\" value = \"Go to Question 1\"/> </form>");

%>
<a href=userPage.jsp>Home</a>

</body>
</html>