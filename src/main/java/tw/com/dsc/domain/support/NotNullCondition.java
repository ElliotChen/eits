package tw.com.dsc.domain.support;

public class NotNullCondition implements Condition {
	private String fieldName;
	
	public NotNullCondition() {
		super();
	}
	public NotNullCondition(String fieldName) {
		super();
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	@Override
	public String toSqlString() {
		return null;
	}
	@Override
	public ConditionEnum getConditionEnum() {
		return ConditionEnum.NotNull;
	}

}
