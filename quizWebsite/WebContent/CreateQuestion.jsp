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

quizWebsite.multipleChoiceQuestion newQuestion = (quizWebsite.multipleChoiceQuestion) session.getAttribute("newQuestion");
String questionType = (String) session.getAttribute("newQuestionType");
//Class clazz = Class.forName("quizWebsite." + questionType);
//java.lang.reflect.Constructor<?> ctor = clazz.getConstructor();
//quizWebsite.multipleChoiceQuestion q = (quizWebsite.multipleChoiceQuestion) ctor.newInstance ();
 out.println(newQuestion.displayQuestionForm()); // TODO this doesn't work if not cast as multipleChoiceQuestion THIS DOESN'T WORK TODO 

%>


</body>
</html>