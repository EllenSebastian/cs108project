<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.Connection,quizWebsite.*, quizWebsite.Constants.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	Connection con = (Connection) application.getAttribute("Connection"); 

	User user = (User)session.getAttribute(quizWebsite.Constants.session_currentUser);
	String name = user.name();
	ArrayList<Announcement> announcements = user.getAnnouncements();
	ArrayList<Message> messages = user.getUserMessages();
	ArrayList<Achievement> achievements = user.getUserAchievements();
	ArrayList<Activity> userActivity = user.getUserActivities();
	List<Activity> friendsActivity = user.getFriendsActivity();
	if (user.equals(Constants.UNREGISTERED_USER)) 
		out.println("<title>Welcome!</title></head><body><h1>Welcome!</h1>");
	
	else 
		out.println("<title>Welcome " + name + "!</title></head><body><h1>Welcome " + name + "!</h1>");
	

	out.println("<form action='SearchUsersServlet' method='post'>");
	out.println("<p>User Search: <input type='text' name='userSearch' />");
	out.println("<input type='submit' value = 'Search'/></p>");
	out.println("</form>");
	if (!user.equals(Constants.UNREGISTERED_USER)){		
		out.println("<br><a href=messageSend.jsp?id=>Send Message</a>");
		out.println("<br><a href=friendsList.jsp?id=>Friends List</a>");
		out.println("<br><a href=newQuiz.jsp>Create a quiz</a>");
		out.println("<br><a href=logOut.jsp>Log Out</a>");
		if(user.isAdmin()){
			out.println("<a href = createAnnouncement.html>Create Announcement</a>");
			out.println("<a href = statistics.jsp>Check Statistics</a>");
		}
	}
	out.println("<br><a href=searchQuiz.jsp>Take a quiz</a>");

	out.println("<h2>Announcements</h2>");
	for (Announcement a : announcements) {
		out.println("<h3>" + a.time + " " + a.subject + ":</h3>");
		out.println("<p>" + a.body + "</p>");
	}
	

	out.println("<h2>Global Recently Taken Quizzes:</h2>");
	ArrayList<Activity> takenQuizzes = UserManager.getActivityType(2);
	for (Activity a : takenQuizzes) {
		int i = 0;
		if (i >= 5)
			break;
		User temp = UserManager.getUser(a.user_id);
		out.println("<h3>"+temp.name()+" at " + a.time + ":</h3>");
		out.println("<p>" + a.description + "</p>");
		out.println("<p>Score: " + a.score + "</p>");
		Quiz q = mysqlManager.retreiveQuiz(a.quizId,con);
		out.println("<a href=quizIntro?id=" + q.pKey + ">" + q.name + "</a>");
		i++;
	}
	out.println("<h2>Global Recently Created Quizzes:</h2>");
	ArrayList<Activity> createdQuizzes = UserManager.getActivityType(1);
	for (Activity a : createdQuizzes) {
		int i = 0;
		if (i >= 5)
			break;
		User temp = UserManager.getUser(a.user_id);
		out.println("<h3>"+temp.name()+" at " + a.time + ":</h3>");
		out.println("<p>" + a.description + "</p>");
		Quiz q = mysqlManager.retreiveQuiz(a.quizId,con);
		out.println("<a href=quizIntro?id=" + q.pKey + ">" + q.name + "</a>");

		i++;
	}
	if (!user.equals(Constants.UNREGISTERED_USER)){
		out.println("<h2>Your Recently Taken Quizzes:</h2>");
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
	
		out.println("<h2>Your Recently Created Quizzes:</h2>");
		for (Activity a : userActivity) {
			int i = 0;
			if (i >= 5)
				break;
			if (a.type == 1) {
				out.println("<h3>" + a.time + ":</h3>");
				out.println("<p>" + a.description + "</p>");
				Quiz q = mysqlManager.retreiveQuiz(a.quizId,con);
				out.println("<a href=quizIntro?id=" + q.pKey + ">" + q.name + "</a>");

				Integer qKey = a.quizId;
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

		out.println("<h2>Recently Received Messages:</h2>");
		for (Message m : messages) {
			int i = 0;
			if (i >= 5)
				break;
			User temp = UserManager.getUser(m.fromUser);
			if (!m.checked) {
				out.println("<h3>From " + temp.name() + " at " + m.time
						+ ":</h3>");
				out.println("<p>" + m.alert + "</p>");
				i++;
			}
		}
		out.println("<a href=messageList.jsp>see all messages...</a>");

		out.println("<h2>Recent Friend Activity:</h2>");
		for (Activity a : friendsActivity) {
			int i = 0;
			if (i >= 5)
				break;
				User temp = UserManager.getUser(a.user_id);
			String nm = temp.name();
			out.println("<p><a href=friendPage.jsp?id=" + a.user_id + ">"
					+ nm + ":</a></p>");
			out.println("<p><a href=friendPage.jsp?id=" + a.user_id + ">"
					+ a.quizId + ":</a></p>");
			out.println("<h3>" + a.time + ":</h3>");
			out.println("<p>" + a.description + "</p>");
			if (a.type == 2) {
					out.println("<p>Score: " + a.score + "</p>");
			}
			i++;
		}
	}
%>

</body>
</html>