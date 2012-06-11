package tw.com.dsc.domain.support;

public class LikeCondition implements Condition {

	private String fieldName;
	private Object value;
	private LikeMode likeMode;
	private boolean ignoreCase = false;
	public LikeCondition() {
		super();
	}
	
	public LikeCondition(String fieldName, Object value) {
		this(fieldName, value, false);
	}
	
	public LikeCondition(String fieldName, Object value, boolean ignoreCase) {
		super();
		this.fieldName = fieldName;
		this.value = value;
		this.likeMode = likeMode;
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

	public LikeMode getLikeMode() {
		return likeMode;
	}

	public void setLikeMode(LikeMode likeMode) {
		this.likeMode = likeMode;
	}

	@Override
	public String toSqlString() {
		return fieldName + " " + likeMode + " " + value;
	}

	@Override
	public ConditionEnum getConditionEnum() {
		return ConditionEnum.Like;
	}

	@Override
	public String toString() {
		return "SimpleCondition [fieldName=" + fieldName + ", value=" + value + ", likeMode=" + likeMode + "]";
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	
}
