<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.*,java.sql.Connection,quizWebsite.*, quizWebsite.Constants.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
* {
 font-family: "Trebuchet MS", Helvetica, sans-serif;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


</head>
<body>
<%
// if they have a cookie, continue to userPage.
// if they don't have a cookie, continue to loginPage. 
Cookie[] cookies = request.getCookies();
String user_key = null; 
System.out.println("Cookies: "+ cookies);
for (int i = 0; i < cookies.length; i++){
	if (cookies[i].getName().equals("user_key")){
		user_key = cookies[i].getValue(); 			
	}
}

if (user_key == null){
	request.getRequestDispatcher("loginPage.jsp").forward(request, response);

}

else{
	User u = UserManager.getUserByCookie(user_key);
	session.setAttribute(Constants.session_currentUser,u);
	request.getRequestDispatcher("userPage.jsp").forward(request, response);
}

%>
</body>
</html>