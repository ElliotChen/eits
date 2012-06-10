package tw.com.dsc.domain.support;

public class OrCondition implements Condition {
	private Condition[] conditions;
	public OrCondition() {
		super();
	}

	public OrCondition(Condition... conditions) {
		super();
		this.conditions = conditions;
	}


	public Condition[] getConditions() {
		return conditions;
	}

	public void setConditions(Condition[] conditions) {
		this.conditions = conditions;
	}

	@Override
	public String toSqlString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConditionEnum getConditionEnum() {
		return ConditionEnum.Or;
	}

}
