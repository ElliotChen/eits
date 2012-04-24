package tw.com.dsc.domain;
/**
 * 00. 1 Month
 * 01. 2 Months
 * 02. 1 Quarter
 * 03. Half Year
 * 04. 1 Year
 * 05. 2 Years
 * @author elliot
 *
 */
public enum ExpireType {
	M1(1), M2(2), M3(3), M6(6), M12(12), M24(24);
	
	private int month;
	
	ExpireType(int month) {
		this.month = month;
	}
	
	public int getMonth() {
		return this.month;
	}
}
