package tw.com.dsc.domain;

public enum Role {
	L3Admin("L3_Admin"), L3Leader("L3_Leader"), L3Agent("L3_Agent"), L2Admin("L2_Admin"), L2Leader("L2_Leader"), L2Agent("L2_Agent"), Partner("Partner"), Guest("Guest");
	String value;
	Role(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
