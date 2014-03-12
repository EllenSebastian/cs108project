<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
* {
 font-family: "Trebuchet MS", Helvetica, sans-serif;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rate quiz</title>
</head>
<body>

<%
String name = request.getParameter("quizID");
out.println("<h1> You are rating Quiz \"" + name + "\".</h1>");
%>
<form action="ReviewQuizServlet" method="post">
<p>Select a rating: <select name="rating">
<option value=1>1</option>
<option value=2>2</option>
<option value=3>3</option>
<option value=4>4</option>
<option value=5>5</option>
</select>

<p>Review: <input type="text" name="review" />
<% 
out.println("<input type=\"hidden\" name=\"quizID\" value=\"" + name +"\" />");
%>

<br>
<input type="submit"	value="Submit review" /></p>
</form>

</body>
</html>