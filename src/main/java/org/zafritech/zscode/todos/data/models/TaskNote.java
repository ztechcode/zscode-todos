package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "ZSCODE_TODOS_TASK_NOTES")
public class TaskNote implements Serializable {

	private static final long serialVersionUID = -7305060289812610534L;

	@Id
    @GeneratedValue
    private Long id;
    
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "taskId")
    @JsonBackReference
    private Task task;
    
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String note;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public TaskNote() {
        
    }

	public TaskNote(Task task, @NotNull String note) {

		this.uuid = UUID.randomUUID().toString();
		this.task = task;
		this.note = note;
		this.created = new Timestamp(System.currentTimeMillis());
	}

	public Long getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}

	public Task getTask() {
		return task;
	}

	public String getNote() {
		return note;
	}

	public Date getCreated() {
		return created;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "TaskNote [id=" + id + ", uuid=" + uuid + ", task=" + task + ", note=" + note + ", created=" + created
				+ "]";
	}
}
