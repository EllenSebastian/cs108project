<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,quizWebsite.*, java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
* {
 font-family: "Trebuchet MS", Helvetica, sans-serif;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	Connection con = (Connection) application.getAttribute("Connection"); 
	int id = Integer.parseInt(request.getParameter("id"));
	User user = UserManager.getUser(id);
	String name = user.name();
	ArrayList<Message> messages = user.getUserMessages();
	ArrayList<Achievement> achievements = user.getUserAchievements();
	ArrayList<Activity> userActivity = user.getUserActivities();
	List<Activity> friendsActivity = user.getFriendsActivity();
	out.println("<title>" + name + "'s page</title>");

	out.println("</head>");
	out.println("<body>");
	out.println("<a href=userPage.jsp>Home</a>");
	out.println("<p><a href=removeFriend.jsp?id="+id+">Remove Friend</a></p>");
	out.println("<h1>" + name + "'s page</h1>");

	out.println("<h2>Recently Taken Quizzes:</h2>");
	for (Activity a : userActivity) {
		int i = 0;
		if (i >= 5)
			break;
		if (a.type == 2) {
			out.println("<h3>" + a.time + ":</h3>");
			Quiz q = mysqlManager.retreiveQuiz(a.quizId,con);
			out.println("<a href=quizIntro?id=" + q.pKey + ">" + q.name + "</a>");
			out.println("<p>" + a.description + "</p>");
			out.println("<p>Score: " + a.score + "</p>");
			i++;
		}
	}

	out.println("<h2>Recently Created Quizzes:</h2>");
	for (Activity a : userActivity) {
		int i = 0;
		if (i >= 5)
			break;
		if (a.type == 1) {
			out.println("<h3>" + a.time + ":</h3>");
			Quiz q = mysqlManager.retreiveQuiz(a.quizId,con);
			out.println("<a href=quizIntro?id=" + q.pKey + ">" + q.name + "</a>");
			out.println("<p>" + a.description + "</p>");
			i++;
		}
	}

	out.println("<h2>Recent Achievements:</h2>");
	for (Achievement a : achievements) {
		int i = 0;
		if (i >= 5)
			break;
		out.println("<h3>" + a.time + " " + a.title + ":</h3>");
		out.println("<p>" + a.description + "</p>");
		i++;
	}

%>

</body>
</html>