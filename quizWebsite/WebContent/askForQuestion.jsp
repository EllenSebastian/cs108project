<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
* {
 font-family: "Trebuchet MS", Helvetica, sans-serif;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Choose a question type</title>
</head>
<body>
<form action="quizCreatedSummary.jsp" method="post">
<input type="submit" value = "Done"/> </form>
<form action="ChooseQuestionTypeServlet" method="post">
<input type="radio" name="questionType" value="multipleChoiceQuestion">Multiple Choice<br>
<input type="radio" name="questionType" value="PictureResponseQuestion">Picture response <br>
<input type="radio" name="questionType" value="FillInTheBlankQuestion">Fill-In-The-Blank<br>
<input type="radio" name="questionType" value="QuestionResponseQuestion">Single response<br>
<input type="submit" value = "Create a new question"/> </form>



</body>
</html>
<a href=userPage.jsp>Home</a>
