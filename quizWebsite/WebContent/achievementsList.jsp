<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,quizWebsite.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
* {
 font-family: "Trebuchet MS", Helvetica, sans-serif;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Achievements</title>
</head>
<body>
<a href=userPage.jsp>Home</a>
<h1>All Achievements</h1>
<%
	User user = (User)session.getAttribute(Constants.session_currentUser);
	String name = user.name();
	ArrayList<Achievement> Achievments = user.getUserAchievements();
	for (Achievement a : Achievments) {
		out.println("<h3>" + a.time + " " + a.title + ":</h3>");
		out.println("<p>" + a.description + "</p>");
	}
%>

</body>
</html>