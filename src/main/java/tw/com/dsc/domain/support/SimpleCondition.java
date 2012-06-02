package tw.com.dsc.domain.support;

public class SimpleCondition implements Condition {

	private String fieldName;
	private Object value;
	private OperationEnum operation;
	private boolean ignoreCase = false;
	public SimpleCondition() {
		super();
	}
	
	public SimpleCondition(String fieldName, Object value, OperationEnum operation) {
		this(fieldName, value, operation, false);
	}
	
	public SimpleCondition(String fieldName, Object value, OperationEnum operation, boolean ignoreCase) {
		super();
		this.fieldName = fieldName;
		this.value = value;
		this.operation = operation;
		this.ignoreCase = ignoreCase;
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

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
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
