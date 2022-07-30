package Enitities;

public class Post {
	private int Id ; 
	private int userId ; 
	private String body; 
	private String title; 
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	
	
}
