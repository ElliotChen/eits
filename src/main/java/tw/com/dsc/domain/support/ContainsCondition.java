package tw.com.dsc.domain.support;

public class ContainsCondition implements Condition {
	private String fieldName;
	private String value;
	private Integer index;
	public ContainsCondition(String fieldName, String value, Integer index) {
		super();
		this.fieldName = fieldName;
		this.value = value;
		this.index = index;
	}

	@Override
	public String toSqlString() {
		return null;
	}

	@Override
	public ConditionEnum getConditionEnum() {
		return ConditionEnum.Contains;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
}
