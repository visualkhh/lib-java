package khh.httpclient.twitter;

public class Twitt {
	String id;
	String content;
	public Twitt() {
		// TODO Auto-generated constructor stub
	}
	public Twitt(String id,String content){
		this.id=id;
		this.content=content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
