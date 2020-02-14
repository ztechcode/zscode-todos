package org.zafritech.zscode.todos.data.daos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDao {

    private Long id;
    
    private String uuid;

    private String details;

    private RepeatDao repeat;

    private String priority;

    private String category;
    
    private boolean complete;

    private String project;
    
    private String deadline;
    
    private List<TagDao> tags = new ArrayList<>();
    
    private List<TaskNoteDao> notes = new ArrayList<>();

	public TaskDao() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public RepeatDao getRepeat() {
		return repeat;
	}

	public void setRepeat(RepeatDao repeat) {
		this.repeat = repeat;
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

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public List<TagDao> getTags() {
		return tags;
	}

	public List<TaskNoteDao> getNotes() {
		return notes;
	}

	public void setTags(List<TagDao> tags) {
		this.tags = tags;
	}

	public void setNotes(List<TaskNoteDao> notes) {
		this.notes = notes;
	}
}
