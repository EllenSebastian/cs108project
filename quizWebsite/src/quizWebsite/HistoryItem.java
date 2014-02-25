package quizWebsite;

public class HistoryItem {
	String date;
	String type;
	double score;
	String quizId;
	String user;
	
	public HistoryItem(String user,String date, String type, 
			double score, String quizId){
		this.date = date;
		this.type = type;
		this.score = score;
		this.quizId = quizId;
		this.user = user;
	}
}
