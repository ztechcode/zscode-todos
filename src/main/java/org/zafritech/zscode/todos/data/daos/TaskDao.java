package org.zafritech.zscode.todos.data.daos;

public class TaskDao {

    private Long id;
    
    private String uuId;

    private String details;

    private String repeatType;

    private String priority;

    private String category;
    
    private boolean complete;

    private String project;

    private String due;

	public TaskDao() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(String repeatType) {
		this.repeatType = repeatType;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getDue() {
		return due;
	}

	public void setDue(String due) {
		this.due = due;
	}

	@Override
	public String toString() {
		return "TaskDao [id=" + id + ", uuId=" + uuId + ", details=" + details + ", repeatType=" + repeatType
				+ ", priority=" + priority + ", category=" + category + ", complete=" + complete + ", project="
				+ project + ", due=" + due + "]";
	}
}
