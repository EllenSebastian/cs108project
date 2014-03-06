<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,quizWebsite.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	int id = Integer.parseInt(request.getParameter("id"));
	User user = UserManager.getUser(id);
	String name = user.name();
	Announcement[] announcements = user.getAnnouncements();
	ArrayList<Message> messages = user.getUserMessages();
	ArrayList<Achievement> achievements = user.getUserAchievements();
	ArrayList<Activity> userActivity = user.getUserActivities();
	List<Activity> friendsActivity = user.getFriendsActivity();
	out.println("<title>Welcome " + name + "!</title>");

	out.println("</head>");
	out.println("<body>");
	out.println("<a href=userPage.jsp>Home</a>");
	out.println("<h1>" + name + "'s page</h1>");
	out.println("<h2>Announcements</h2>");
	for (Announcement a : announcements) {
		out.println("<h3>" + a.time + " " + a.subject + ":</h3>");
		out.println("<p>" + a.body + "</p>");
	}

	out.println("<h2>Recently Taken Quizzes:</h2>");
	for (Activity a : userActivity) {
		int i = 0;
		if (i >= 5)
			break;
		if (a.type == 2) {
			out.println("<h3>" + a.time + ":</h3>");
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
			out.println("<p>" + a.description + "</p>");
			i++;
		}
	}

	out.println("<h2>Recently Achievements:</h2>");
	for (Achievement a : achievements) {
		int i = 0;
		if (i >= 5)
			break;
		out.println("<h3>" + a.time + " " + a.title + ":</h3>");
		out.println("<p>" + a.description + "</p>");
		i++;
	}

	out.println("<h2>Recently Received Messages:</h2>");
	for (Message m : messages) {
		int i = 0;
		if (i >= 5)
			break;
		if (!m.checked) {
			out.println("<h3>From " + m.fromUser + " at " + m.time
					+ ":</h3>");
			out.println("<p>" + m.alert + "</p>");
			i++;
		}
	}

	out.println("<h2>Recent Friend Activity:</h2>");
	for (Activity a : friendsActivity) {
		int i = 0;
		if (i >= 5)
			break;
		out.println("<h3>" + a.time + ":</h3>");
		out.println("<p>" + a.description + "</p>");
		if (a.type == 2) {
			out.println("<p>Score: " + a.score + "</p>");
		}
		i++;
	}
%>

</body>
</html>