<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quizWebsite.Quiz.*,java.sql.Connection, java.util.Vector, java.util.Collections"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a new quiz
</title>
</head>
<body>
<a href=userPage.jsp>Home</a>

<%
session.setAttribute("newQuiz",true);
%>
<h1>Choose your quiz options </h1>
<form action ="ChooseQuestionTypeServlet" method = "post">
Quiz name: <input type="text" name="name"><br>
Quiz description: <input type="text" name="description"><br>
Tags (comma-separated): <input type="text" name="tags"><br>

Should the questions all be one one page or separate pages?<br>
<input type="radio" name="pages" value="multiplePages">One question per page<br>
<input type="radio" name="pages" value="onePage">All questions on one page<br>

<input type="checkbox" name="immediateFeedback" value="immediateFeedback">Provide immediate feedback after questions<br>
<input type="checkbox" name="practiceMode" value="practiceMode">Allow students to take this quiz in practice mode<br>
<input type="checkbox" name="randomOrder" value="randomOrder">Randomize the order of questions<br>
<input type="submit" value = "Create your first question"/> </form>
</body>
</html>