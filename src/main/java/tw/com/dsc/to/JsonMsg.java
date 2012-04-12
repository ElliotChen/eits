package tw.com.dsc.to;

public class JsonMsg {
	private String message;

	public JsonMsg() {
		this("");
	}
	
	public JsonMsg(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
