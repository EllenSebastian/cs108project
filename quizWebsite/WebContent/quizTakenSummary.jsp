<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quizWebsite.*, java.util.Vector"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz completed</title>
</head>
<body>
<%

out.println((Integer)session.getAttribute(quizWebsite.Constants.session_currentScore));
out.println("<br> Feedback: <br>");
out.println((String)session.getAttribute(quizWebsite.Constants.session_allFeedback));


quizWebsite.Quiz currentQuiz = (quizWebsite.Quiz) session.getAttribute(quizWebsite.Constants.session_currentQuiz);
User currentUser = (User) session.getAttribute(quizWebsite.Constants.session_currentUser);
String feedback = (String) session.getAttribute(quizWebsite.Constants.session_allFeedback);

Integer score = (Integer) session.getAttribute(quizWebsite.Constants.session_currentScore);
java.util.Date date= new java.util.Date();
java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
System.out.println("currentUser" + currentUser);
System.out.println("currentQuiz" + currentQuiz.pKey);
// TODO measure time 
quizWebsite.Activity newActivity = new quizWebsite.Activity(currentUser.user_id, time, quizWebsite.Activity.quiz_Taken,score,currentQuiz.pKey,0);
newActivity.addActivity();
out.println("Thanks for taking the quiz \"" + currentQuiz.name + "\".");
out.println("Your score was " + score + "\"");
out.println(feedback);
out.println("added activity for User " + currentUser + ", quizTaken, score:  " + score + " on quiz: " + currentQuiz.pKey);


%><a href=userPage.jsp>Home</a>

</body>
</html>
