package tw.com.dsc.domain.support;

import java.util.Arrays;

public class NotInCondition implements Condition {
	private String fieldName;
	private Object[] values;
	
	public NotInCondition() {
		super();
	}
	
	public NotInCondition(String fieldName, Object[] values) {
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

	@Override
	public String toString() {
		return "InCondition [fieldName=" + fieldName + ", values=" + Arrays.toString(values) + "]";
	}
	
}
