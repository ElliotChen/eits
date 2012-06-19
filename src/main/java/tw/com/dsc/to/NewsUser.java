package tw.com.dsc.to;

public class NewsUser {
	private String id;
	private String name;
	private String teamId;
	
	
	public NewsUser() {
		super();
	}

	public NewsUser(String id, String name, String teamId) {
		super();
		this.id = id;
		this.name = name;
		this.teamId = teamId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
}
