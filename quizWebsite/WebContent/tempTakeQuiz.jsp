
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
<%
	out.println("<ul><li><a href=\"quizIntro.jsp?id=0\"> take quiz 0: no immediatefeedback, one page pages, no practicemode,no random</a></li>");
	out.println("<li>\n\n<a href=\"quizIntro.jsp?id=1\"> take quiz 1: immediatefeedback, multiple page, practice mode, random</a></li>");
	out.println("<li>\n\n<a href=\"quizIntro.jsp?id=29\"> take quiz 29: all question types. THIS DOES NOT WORK YET</a></li></ul>");
	session.setAttribute(quizWebsite.Constants.session_currentUser, 1); // TODO get loginServlet to set current user 
%>  
</ul>
</body>
</html>