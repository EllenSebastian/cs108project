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
<ul>
<%
	User user = session.getAttribute(Constants.session_currentUser);
	String name = user.name();
	ArrayList<Integer> friends = user.getUserFriends();
	for (Integer f : friends) {
		User temp = UserManager.getUser(f);
		String friendName = temp.name();
		out.println("<li><a href=friendPage.jsp?id="+f+">"+friendName+"</a></li>");
	}
%>
</ul>
</body>
</html>