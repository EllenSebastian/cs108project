<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ page import="java.sql.*, java.util.*,quizWebsite.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz search results</title>
</head>
<body>
<%

	System.out.println("reached showQuizzes.jsp");
	Connection con = (Connection) application.getAttribute("Connection");
//	ArrayList<Quiz> quizzes = quizWebsite.mysqlManager.searchForQuiz(searchName,(Connection) application.getAttribute("Connection"));
	ArrayList<Quiz> quizzes = (ArrayList<Quiz>) session.getAttribute("quizSearchResults");
	
	System.out.println("found " + quizzes.size() + " quizzes matching your query: <br>");
	for (Quiz q : quizzes) {
		out.println("<h4>"+q.name+"</h4>");
		out.println("<a href=quizIntro.jsp?id="+q.pKey+">" + q.name + "</a>");
	}
%>
<a href=userPage.jsp>Home</a>

</body>
</html>