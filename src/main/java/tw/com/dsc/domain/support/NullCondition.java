package tw.com.dsc.domain.support;

public class NullCondition implements Condition {
	private String fieldName;
	
	public NullCondition() {
		super();
	}
	public NullCondition(String fieldName) {
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
		return ConditionEnum.Null;
	}

}
