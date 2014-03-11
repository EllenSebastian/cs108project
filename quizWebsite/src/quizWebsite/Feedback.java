package quizWebsite;

public class Feedback {

	public String questionHTML; 
	public Integer score; 
	public String feedback; 
	public Integer questionIndex; 
	public Feedback(String questionHTML, String feedback, Integer score, Integer questionIndex){
		this.questionHTML = questionHTML;
		this.feedback = feedback; 
		this.score = score; 
		this.questionIndex = questionIndex; 
	}
	public String toString(){
		String toReturn = "<h3> Question " + (questionIndex+1) + "</h3>";
		toReturn += "<h4>You received " + score + " point";
		if (score != 1) toReturn += "s";
		toReturn += "</h4>";
		toReturn += feedback;
		toReturn += questionHTML;
		return toReturn; 
	}
}
