<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a quiz </title>
</head>
<body>
<% 
	out.println("<form action='SearchQuizzesServlet' method='get'>");
	out.println("<p>Quiz Search: <input type='text' name='quizSearch' />");
	out.println("<input type='submit' value = 'Search'/></p>");
	out.println("</form>");
	out.println("<a href=allQuizzes.jsp>see all quizzes</a>");
	%>
	<a href=userPage.jsp>Home</a>
	
</body>
</html>