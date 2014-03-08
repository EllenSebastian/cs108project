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
	String searchName = (String)session.getAttribute("quizSearch");
	Connection con = (Connection) application.getAttribute("Connection");
//	ArrayList<Quiz> quizzes = quizWebsite.mysqlManager.searchForQuiz(searchName,(Connection) application.getAttribute("Connection"));
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
	
	try {
		PreparedStatement p = con.prepareStatement("SELECT * FROM Quizzes WHERE name LIKE ? ORDER BY whenCreated");
		p.setString(1, "%" + searchName + "%");
		System.out.println("Connected to db");
		ResultSet result = p.executeQuery();
		while(result.next()) {
			Quiz q = mysqlManager.retreiveQuiz(result.getInt("pKey"),con);
			//System.out.println(result.getInt("user_id"));
			quizzes.add(q);
		}
	}catch (SQLException e) {
		e.printStackTrace(); 	
	}
	System.out.println("found " + quizzes.size() + " quizzes matching your query: <br>");
	for (Quiz q : quizzes) {
		out.println("<h4>"+q.name+"</h4>");
		out.println("<a href=quizIntro.jsp?id="+q.pKey+">" + q.name + "</a>");
	}
%>
<a href=userPage.jsp>Home</a>

</body>
</html>