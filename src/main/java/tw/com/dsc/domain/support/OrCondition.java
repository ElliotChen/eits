package tw.com.dsc.domain.support;

public class OrCondition implements Condition {
	private Condition condition1;
	private Condition condition2;

	public OrCondition() {
		super();
	}

	public OrCondition(Condition condition1, Condition condition2) {
		super();
		this.condition1 = condition1;
		this.condition2 = condition2;
	}

	public Condition getCondition1() {
		return condition1;
	}

	public void setCondition1(Condition condition1) {
		this.condition1 = condition1;
	}

	public Condition getCondition2() {
		return condition2;
	}

	public void setCondition2(Condition condition2) {
		this.condition2 = condition2;
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
