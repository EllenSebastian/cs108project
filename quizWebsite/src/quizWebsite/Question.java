package quizWebsite;

public abstract class Question {
		abstract String displayQuestionForm();
		abstract String displayQuestion();
		abstract int scoreAnswer (/*HTTPRequest request*/);
		abstract String buildData(); 
		abstract void parseData(String data);
		void storeData(){
			
		}
		String getData(){
			return ""; 
		}
}
