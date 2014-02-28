<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quizWebsite.Question.*, java.util.Vector"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
// get the new question html form. 

String questionType = (String) session.getAttribute("newQuestionType");
Class<?> clazz = Class.forName("quizWebsite." + questionType);
java.lang.reflect.Constructor<?> ctor = clazz.getConstructor();
quizWebsite.Question q = (quizWebsite.Question) ctor.newInstance ();
q.newQuestionForm(); 

out.println("<form action = \"CreateQuestionServlet\" method = \"post\">");
out.println(q.newQuestionForm()); // TODO this doesn't work if not cast as multipleChoiceQuestion THIS DOESN'T WORK TODO 
out.println("</form>");
%>


</body>
</html>