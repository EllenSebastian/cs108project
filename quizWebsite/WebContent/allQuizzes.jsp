<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quizWebsite.*, java.util.*, java.sql.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quizzes</title>
</head>
<body>
<% 
java.sql.Connection con = (java.sql.Connection) application.getAttribute(Constants.context_Connection);
ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
PreparedStatement p = con.prepareStatement("SELECT * FROM Quizzes");

ResultSet result = p.executeQuery();
while(result.next()) {
	Quiz q = mysqlManager.retreiveQuiz(result.getInt("pKey"),con);
	//System.out.println(result.getInt("user_id"));
	quizzes.add(q);
}
for (Quiz q : quizzes)
	out.println("<a href=quizIntro.jsp?id=" + q.pKey + ">" + q.name + "</a><br>");
%>
<a href=userPage.jsp>Home</a>

</body>
</html>