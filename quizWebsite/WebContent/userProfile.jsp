<!--  this is for other users to view someone's profile.  -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ page import="java.util.*,quizWebsite.*, java.sql.Connection,quizWebsite.Constants.*"%>
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
	String uid = request.getParameter("uid");
	User user = UserManager.getUser(Integer.parseInt(uid));
	ArrayList<Achievement> achievements = user.getUserAchievements();
	ArrayList<Activity> userActivity = user.getUserActivities();
	User currUser = (User)session.getAttribute(quizWebsite.Constants.session_currentUser);
	out.println("<title>" + user.name() + "</title>");

	out.println("</head>");
	out.println("<body>");
	out.println("<a href=userPage.jsp>Home</a>");
	if(currUser.isAdmin()){
		if(!user.isAdmin())
			out.println("<p><a href=userPromoted.jsp?id="+user.user_id+">Promote User</a></p>");
		out.println("<p><a href=userRemoved.jsp?id="+user.user_id+">Delete User</a></p>");
	}
	out.println("<h1>" + user.name() + "</h1>");


	out.println("<a href=messageSend.jsp?name="+ user.name() + ">Send Message</a>");
	
	out.println("<h2>" + user.name() + "'s Recently Taken Quizzes:</h2>");
	for (Activity a : userActivity) {
		int i = 0;
		if (i >= 5)
			break;
		if (a.type == 2) {
			out.println("<h3>" + a.time + ":</h3>");
			Quiz q = mysqlManager.retreiveQuiz(a.quizId,con);
			out.println("<a href=quizIntro?id=" + q.pKey + ">" + q.name + "</a>");

			out.println("<p>Score: " + a.score + "</p>");
			i++;
		}
	}

	out.println("<h2>" + user.name() + "'s Recently Created Quizzes:</h2>");
	for (Activity a : userActivity) {
		int i = 0;
		if (i >= 5)
			break;
		if (a.type == 1) {
			out.println("<h3>" + a.time + ":</h3>");
			out.println("<p>" + a.description + ": </p>");
			Quiz q = mysqlManager.retreiveQuiz(a.quizId,con);
			out.println("<a href=quizIntro?id=" + q.pKey + ">" + q.name + "</a>");
			i++;
		}
	}

	out.println("<h2>Achievements:</h2>");
	for (Achievement a : achievements) {
		int i = 0;
		if (i >= 5)
			break;
		out.println("<h3>" + a.time + " " + a.title + ":</h3>");
		out.println("<p>" + a.description + "</p>");
		i++;
	}
	out.println("<a href=achievementsList.jsp>see all achievements...</a>");


%>

</body>
</html>