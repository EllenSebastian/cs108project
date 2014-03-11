<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*,quizWebsite.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Send Message</title>
</head>
<body>

<form action='MsgSendServlet' method='post'>
<p>To User: <input type="text" name="toUser"/>
<p>Type: <select name = "Type">
	<option value = 1>Friend Request</option>
	<option value = 2>Challenge</option>
	<option value = 3>Note</option>
</select>

<p>QuizID(if applicable): <select name = "quizID">
	<%
		//get Quiz Id
		//for(quizId q:quizIDs){
			//<option value = quizID>quizID</option>
		//}
	%>
</select>

<p>Body: <input type="text" name="body" />
<input type="submit" value = "send"/></p>
</form>
</body>
</html>