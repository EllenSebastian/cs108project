package quizWebsite;

public class Message{
	boolean read;
	String type;
	String body;
	String quizId;
	String fromUser;
	String toUser;
	
	public Message(Boolean read, String type, String body, 
			String quizId, String fromUser, String toSser){
		this.read = read;
		this.type = type;
		this.body = body;
		this.quizId = quizId;
		this.fromUser = fromUser;
		this.toUser = toUser;
	}
}
