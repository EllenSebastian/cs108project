
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>temp intro</title>

</head>
<body>
<ul> 
<!--  scriplet -->

<%
	out.println("<ul><li><a href=\"newQuiz.jsp?creator=Molly\"> create a quiz</a></li><ul>");
session.setAttribute(quizWebsite.Constants.session_currentUser, 1); // TODO get loginServlet to set current user 
Integer currentUser = (Integer) session.getAttribute(quizWebsite.Constants.session_currentUser);
out.println("current user " + currentUser);
%>  
</ul>
</body>
</html>