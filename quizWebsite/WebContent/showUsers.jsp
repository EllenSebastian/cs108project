<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*,quizWebsite.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friends List</title>
</head>
<body>
<h1>All Friends</h1>
<%
	String searchName = session.getAttribute("userSearch");
	ArrayList<User> users = UserManager.search(searchName);
	for (User u : users) {
		String userName = u.name();
		out.println("<h4>"+userName+"</h4>");
		out.println("<a href=messageSend.jsp?id="+userName+">Add Friend</a>");
	}
%>
</body>
</html>