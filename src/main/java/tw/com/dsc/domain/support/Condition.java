package tw.com.dsc.domain.support;

public interface Condition {
	public String toSqlString();
	public ConditionEnum getConditionEnum();
}
