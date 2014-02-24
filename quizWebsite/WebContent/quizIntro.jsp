<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quizWebsite.Quiz.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to quiz!
</title>
</head>
<body>
hello
<%
 String id = request.getParameter("id");
out.println("Welcome to quiz "  + id);
// set the current quiz
session.setAttribute("currentQuiz", new quizWebsite.Quiz(Integer.parseInt(id),application));
// set the list of questions
session.setAttribute("quizQuestions",((quizWebsite.Quiz) session.getAttribute("currentQuiz")).getQuestions());
session.setAttribute("previousAnswer",null);
session.setAttribute("previousQuestionIndex",-1);
session.setAttribute("currentScore",0);
// link to the first question
out.println("<form action=\"questionServlet\" method=\"post\">"); // call doPost in questionServlet
out.print("<input type=\"submit\" value = \"Go to Question 0\"/> </form>");

//out.println("<input name=\"productID\" type=\"hidden\" value=\"" + pageItem.getID() + "\"/>"); 
%>
</body>
</html>