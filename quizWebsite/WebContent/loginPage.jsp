<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*,java.sql.Connection, quizWebsite.*, quizWebsite.Constants.*"%>

<html>
<head>

<style>
* {
 font-family: "Trebuchet MS", Helvetica, sans-serif;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>

<body>
<h1> Welcome to QuizMaster!</h1>
<form action=LoginServlet method="post">
<br>Username: <input type="text" name ="username">
<br>Password: <input type="text" name ="password">
<br><input type="checkbox" name="remember me" value="remember me">Remember me

<br><input type="submit" value="login"><br><br></form>

<form action=NewUserServlet method="post">
<br>username: <input type="text" name="username" >
<br>password: <input type="text" name="password">
<br><input type="checkbox" name="remember me" value="remember me">Remember me

<br><input type="submit" value="Create User"> </form>

<form action=LoginServlet method="post">
<input type = "submit" value="Continue as a nonregistered user">
</form>
</body>
</html>

