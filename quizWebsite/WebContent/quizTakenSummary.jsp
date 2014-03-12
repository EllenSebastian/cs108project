<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quizWebsite.*, java.util.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz completed</title>
</head>
<body>
<%

quizWebsite.Quiz currentQuiz = (quizWebsite.Quiz) session.getAttribute(quizWebsite.Constants.session_currentQuiz);
User currentUser = (User) session.getAttribute(quizWebsite.Constants.session_currentUser);
String feedback = (String) session.getAttribute(quizWebsite.Constants.session_allFeedback);
ArrayList<Feedback> feedbacks = (ArrayList<Feedback>) session.getAttribute(quizWebsite.Constants.session_allFeedbackObjs);

Integer score = (Integer) session.getAttribute(quizWebsite.Constants.session_currentScore);

if (!currentUser.equals(Constants.UNREGISTERED_USER)){
	java.util.Date date= new java.util.Date();
	java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());

	quizWebsite.Activity newActivity = new quizWebsite.Activity(currentUser.user_id, time, quizWebsite.Activity.quiz_Taken,score,currentQuiz.pKey,0);
	newActivity.addActivity();
}
	out.println("<h1>Thanks for taking the quiz \"" + currentQuiz.name + "\".</h1>");
out.println("<h2>Your score was: " + score);
for (Feedback f : feedbacks){
	out.println(f.toString());
}
	

%><a href=userPage.jsp>Home</a>
<br>

<form action=FlagQuizServlet method="post">
<input type="submit" value="Mark this quiz as inappropariate"></form>
<br>
</body>
</html>
