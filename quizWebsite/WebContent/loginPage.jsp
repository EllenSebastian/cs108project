<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*,java.sql.Connection, quizWebsite.*, quizWebsite.Constants.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>

<body>

<form action=LoginServlet method="post">
username: <input type="text" name ="username">
password: <input type="text" name ="password">
<input type="submit" value="login"><br><br></form>

<form action=NewUserServlet method="post">
username: <input type="text" name="username" >
password: <input type="text" name="password">
<input type="submit" value="Create User"> </form>

</body>
</html>

