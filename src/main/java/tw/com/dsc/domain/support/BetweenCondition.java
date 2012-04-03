package tw.com.dsc.domain.support;

public class BetweenCondition implements Condition {
	private String fieldName;
	private Object minValue;
	private Object maxValue;

	public BetweenCondition() {
		super();
	}

	public BetweenCondition(String fieldName, Object minValue, Object maxValue) {
		super();
		this.fieldName = fieldName;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getMinValue() {
		return minValue;
	}

	public void setMinValue(Object minValue) {
		this.minValue = minValue;
	}

	public Object getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Object maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public String toSqlString() {
		return fieldName + " between " + minValue + " and " + maxValue;
	}

	@Override
	public ConditionEnum getConditionEnum() {
		return ConditionEnum.Between;
	}

}
