<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,quizWebsite.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Send Message</title>
</head>
<body>
<a href=userPage.jsp>Home</a>

<h1>Send a Message</h1>
<form action='MsgSendServlet' method='post'>
<%
	// if we got here from a user profile, set session attr for who to send message to. 
	// else allow user to choose. 
	String name = request.getParameter("name");
	if (name == null)
		out.println("<p>To User: <input type=\"text\" name=\"toUser\"/>");
	else
		// got here form a user profile
		session.setAttribute(Constants.session_sendMessageTo, name);
	java.sql.Connection con = (java.sql.Connection) application.getAttribute(Constants.context_Connection);
	ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
	PreparedStatement p = con.prepareStatement("SELECT * FROM Quizzes");

	ResultSet result = p.executeQuery();
	while(result.next()) {
		Quiz q = mysqlManager.retreiveQuiz(result.getInt("pKey"),con);
		//System.out.println(result.getInt("user_id"));
		quizzes.add(q);
	}
%>
<p>Select Message Type: <select name="Type">
	<option value=1>Friend Request</option>
	<option value=2>Challenge</option>
	<option value=3>Note</option>
</select>
<p>QuizID(if applicable): <select name="quizID">
	<option value="-1">None</option>
	<%
	for (Quiz q : quizzes)
		out.println("<option value ="+ q.pKey + ">" + q.name + "</option>");
	%>
</select>
<p>Body: <input type="text" name="body" /> <input type="submit"
	value="send" /></p>
</form>
</body>
</html>