<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,quizWebsite.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Message List</title>
</head>
<body>
<h1>All Messages</h1>
<%
	User user = session.getAttribute(Constants.session_currentUser);
	String name = user.name();
	ArrayList<Message> messages = user.getUserMessages();
	for (Message m : messages) {
		out.println("<h3>From " + m.fromUser + " at " + m.time
				+ ":</h3>");
		out.println("<p>" + m.alert + "</p>");
		String read = "unread";
		if(m.checked) read = "read";
		out.println("<p>Status: "+read+"</p>");
		out.println("<p>"+m.body+"</p>");
	}
%>

</body>
</html>