package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "ZSCODE_TODOS_TASK_TAGS")
public class TaskTag implements Serializable  {

	private static final long serialVersionUID = 1422040630098840299L;

	@Id
    @GeneratedValue
    private Long id;
 
    @ManyToOne
    @JoinColumn(name = "taskId")
    @JsonBackReference
    private Task task;
    
    @NotNull
    private String tag;

    public TaskTag() {
        
    }

	public TaskTag(Task task, @NotNull String tag) {

		this.task = task;
		this.tag = tag;
	}

	public Long getId() {
		return id;
	}

	public Task getTask() {
		return task;
	}

	public String getTag() {
		return tag;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "TaskTag [id=" + id + ", task=" + task + ", tag=" + tag + "]";
	}
}
