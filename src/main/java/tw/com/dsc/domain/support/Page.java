package tw.com.dsc.domain.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7227111290162020181L;

	/**
	 * 預設單頁顯示資料筆數, 10
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * 預設顯示頁面數, 10
	 */
	public static final int DEFAULT_PAGENO_SIZE = 10;
	
	/**
	 * 頁面顯示資料筆數
	 */
	private Integer pageSize = DEFAULT_PAGE_SIZE;
	
	/**
	 * 當前頁碼
	 */
	private Integer pageNo = 1;
	
	/**
	 * 資料總筆數
	 */
	private Integer totalCount;

	/**
	 * 查詢結果
	 */
	private List<T> result;
	
	/**
	 * 查詢用Example
	 */
	private T example;
	
	/**
	 * 升冪排序欄位
	 */
	private String[] ascOrders = null;
	
	/**
	 * 降冪排序欄位
	 */
	private String[] descOrders = null;
	
	private Condition[] conditions = null;
	/**
	 * 是否使用Like，NONE代表不使用，ANYWHERE代表前後
	 */
	private LikeMode likeMode = LikeMode.ANYWHERE;
	
	public Page() {
		this(null);
	}
	
	public Page(T example) {
		this(example, new Integer(1), DEFAULT_PAGE_SIZE, new ArrayList<T>());
	}
	
	public Page(T example, Integer pageNo, Integer pageSize, List<T> result) {
		this.example = example;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.result = result;
	}
	
	/*-----------------------------------------------------------------------*/
	/*                         Properties Accessors                          */
	/*-----------------------------------------------------------------------*/
	/**
	 * @param result setter of result
	 */
	public void setResult(List<T> result) {
		this.result = result;
	}

	/**
	 * @return the result
	 */
	public List<T> getResult() {
		return result;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	public String[] getAscOrders() {
		return ascOrders;
	}

	public void setAscOrders(String[] ascOrders) {
		this.ascOrders = ascOrders;
	}

	public String[] getDescOrders() {
		return descOrders;
	}

	public void setDescOrders(String[] descOrders) {
		this.descOrders = descOrders;
	}
	
	public T getExample() {
		return example;
	}

	public void setExample(T example) {
		this.example = example;
	}

	public Condition[] getConditions() {
		return conditions;
	}

	public void setConditions(Condition[] conditions) {
		this.conditions = conditions;
	}
	
	public LikeMode getLikeMode() {
		return likeMode;
	}

	public void setLikeMode(LikeMode likeMode) {
		this.likeMode = likeMode;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	/*-----------------------------------------------------------------------*/
	/*                         Page Information                              */
	/*-----------------------------------------------------------------------*/
	/**
	 * 取得總頁數
	 * 
	 * @return Integer 總頁數
	 */
	public Integer getLastPageNo() {
		if (totalCount % pageSize == 0) {
			return new Long(totalCount / pageSize).intValue();
		} else {
			return new Long(totalCount / pageSize).intValue() + 1;
		}
	}
	
	/**
	 * 取得目前頁面第一筆資料的序號
	 * @return
	 */
	public Long getCurrentIndex() {
		return new Long(pageNo - 1) * pageSize;
	}
	
	/**
	 * 當前頁碼, 起始值為1
	 * @return 當前頁碼
	 */
	public Integer getCurrentPageNo() {
		return this.pageNo;
	}
	
	public Integer getCurrentLoopNo() {
		return this.getLoopNo(this.getCurrentPageNo());
	}
	/**
	 * 取得前一頁的頁碼, 若無前一頁時回傳目前頁碼
	 * @return
	 */
	public Integer getPreviousPageNo() {
		Integer currentPageNo = getCurrentPageNo();
		if (isHasPreviousPage()) {
			return currentPageNo - 1;
		}
		return currentPageNo;
	}
	
	/**
	 * 取得下一頁的頁碼, 若無下一頁時回傳目前頁碼
	 * @return
	 */
	public Integer getNextPageNo() {
		Integer currentPageNo = getCurrentPageNo();
		if (isHasNextPage()) {
			return currentPageNo + 1;
		}
		return currentPageNo;
	}
	/**
	 * 是否有前一頁
	 * @return true為有前一頁, false則無前一頁
	 */
	public boolean isHasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}
	
	/**
	 * 是否有下一頁
	 * @return true為有下一頁, false則無下一頁
	 */
	public boolean isHasNextPage() {
		return this.getCurrentPageNo() < this.getLastPageNo();
	}
	
	/*
	private static Integer getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}
	*/
	
	/**
	 * 該頁碼第1筆資料的序號
	 */
	public static Integer getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 是否要顯示第1頁連結
	 */
	public boolean isDisplayFirstPageNo() {
//		return this.getLoopNo(this.getCurrentPageNo()) > 1;
		return this.pageNo - (DEFAULT_PAGENO_SIZE/2) > 0;
	}
	
	public boolean isDisplayLastPageNo() {
//		return this.getLoopNo(this.getCurrentPageNo()) < this.getLoopNo(this.getLastPageNo());
		return this.pageNo + (DEFAULT_PAGENO_SIZE/2) < this.getLastPageNo();
	}
	
	public List<Integer> getLoopPageNos() {
		ArrayList<Integer> nos = new ArrayList<Integer>();
		int from = isDisplayFirstPageNo()? this.pageNo - (DEFAULT_PAGENO_SIZE/2) + 1: 1;
		int end = isDisplayLastPageNo() ? this.pageNo + (DEFAULT_PAGENO_SIZE/2) : this.getLastPageNo();
		
		if ((end - from + 1) < DEFAULT_PAGENO_SIZE) {
			if (isDisplayFirstPageNo()) {
				from = this.pageNo - DEFAULT_PAGENO_SIZE > 0 ? end - DEFAULT_PAGENO_SIZE + 1 : 1;
			} else if (isDisplayLastPageNo()) {
				end = DEFAULT_PAGENO_SIZE;
			}
		}
		
		for (int i = from; i <= end; i++) {
			nos.add(new Integer(i));
		}

		return nos;
	}
	
	private int getLoopNo(Integer pageNo) {
		if (pageNo % DEFAULT_PAGENO_SIZE == 0) {
			return pageNo/DEFAULT_PAGENO_SIZE;
		} else {
			return (pageNo/DEFAULT_PAGENO_SIZE) + 1;
		}
	}
}
