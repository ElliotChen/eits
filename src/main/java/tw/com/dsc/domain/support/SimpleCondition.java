package tw.com.dsc.domain.support;

public class SimpleCondition implements Condition {

	private String fieldName;
	private Object value;
	private OperationEnum operation;
	public SimpleCondition() {
		super();
	}
	
	public SimpleCondition(String fieldName, Object value, OperationEnum operation) {
		super();
		this.fieldName = fieldName;
		this.value = value;
		this.operation = operation;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public OperationEnum getOperation() {
		return operation;
	}

	public void setOperation(OperationEnum operation) {
		this.operation = operation;
	}

	@Override
	public String toSqlString() {
		return fieldName + " " + operation + " " + value;
	}

	@Override
	public ConditionEnum getConditionEnum() {
		return ConditionEnum.Simple;
	}

	@Override
	public String toString() {
		return "SimpleCondition [fieldName=" + fieldName + ", value=" + value + ", operation=" + operation + "]";
	}

	
}
