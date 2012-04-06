package tw.com.dsc.domain.support;

public class InCondition implements Condition {
	private String fieldName;
	private Object[] values;
	
	public InCondition() {
		super();
	}
	
	public InCondition(String fieldName, Object[] values) {
		super();
		this.fieldName = fieldName;
		this.values = values.clone();
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object[] getValues() {
		return values.clone();
	}

	public void setValues(Object[] values) {
		this.values = values.clone();
	}

	@Override
	public String toSqlString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConditionEnum getConditionEnum() {
		return ConditionEnum.In;
	}

}
