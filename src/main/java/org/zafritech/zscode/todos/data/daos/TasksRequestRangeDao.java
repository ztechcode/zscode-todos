package org.zafritech.zscode.todos.data.daos;

public class TasksRequestRangeDao {
	
	private String filter;

	private String startDate;
	
	private String endDate;

	public String getFilter() {
		return filter;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
