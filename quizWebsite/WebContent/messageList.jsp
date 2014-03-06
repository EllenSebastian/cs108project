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
<a href=userPage.jsp>Home</a>
<h1>All Messages</h1>
<%
	User user = (User) session
			.getAttribute(Constants.session_currentUser);
	String name = user.name();
	ArrayList<Message> messages = user.getUserMessages();
	out.println("<a href=messageSend.jsp?id=>Send Message</a>");
	for (Message m : messages) {
		User temp = UserManager.getUser(m.fromUser);
		out.println("<h3>From " + temp.name() + " at " + m.time
				+ ":</h3>");
		out.println("<p>" + m.alert + "</p>");
		String read = "unread";
		if (m.checked)
			read = "read";
		out.println("<p>Status: " + read + "</p>");
		out.println("<p>" + m.body + "</p>");
		if (m.type == 1) {
			out.println("<p><a href=addFriend.jsp?id="+m.fromUser+">Add Friend</a></p>");
		}
	}
%>

</body>
</html>