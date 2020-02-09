package org.zafritech.zscode.todos.data.daos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicTaskDao {

    private Long id;

    private String task;

	public Long getId() {
		return id;
	}

	public String getTask() {
		return task;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTask(String task) {
		this.task = task;
	}
    
}
