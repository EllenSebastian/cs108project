<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
* {
 font-family: "Trebuchet MS", Helvetica, sans-serif;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz created</title>
</head>
<body>
	<%
	quizWebsite.Quiz newQuiz= (quizWebsite.Quiz) request.getSession().getAttribute(quizWebsite.Constants.session_newQuiz);
	out.println("Thanks for creating the quiz \"" + newQuiz.name + "\".<br><br>");
	out.println("<a href=\"" + newQuiz.url + "\"> Take your new quiz.</a><br>");

	java.util.Date date= new java.util.Date();
	java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
	
	quizWebsite.User currentUser = (quizWebsite.User) session.getAttribute(quizWebsite.Constants.session_currentUser);

	System.out.println("currentUser" + currentUser.user_id);
	System.out.println("currentQuiz" + newQuiz.pKey);
	quizWebsite.Activity newActivity = new quizWebsite.Activity(currentUser.user_id, time, 
									quizWebsite.Activity.quiz_Created,0,newQuiz.pKey,0 );
	newActivity.addActivity();
	out.println("added activity for User " + currentUser + ", quizCreated: " + newQuiz.pKey);


%>
<a href=userPage.jsp>Home</a>

</body>
</html>