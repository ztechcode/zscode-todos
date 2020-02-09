package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "ZSCODE_TODOS_SCHEDULES")
public class Schedule implements Serializable {

	private static final long serialVersionUID = -4361643559250891137L;

    @Id
    @GeneratedValue
    private Long id;
    
    private String uuid;

    private String owner;
    
    private Date deadline;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;
    
    private boolean done;
    
    public Schedule() {
    	
    }

	public Schedule(Task task, Date deadline) {

		this.uuid = UUID.randomUUID().toString();
		this.deadline = deadline;
		this.task = task;
		this.done = false;
	}

	public Schedule(Task task, Date deadline, String owner) {

		this.uuid = UUID.randomUUID().toString();
		this.owner = owner;
		this.deadline = deadline;
		this.task = task;
		this.done = false;
	}

	public Long getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}

	public String getOwner() {
		return owner;
	}

	public Task getTask() {
		return task;
	}

	public boolean isDone() {
		return done;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", uuid=" + uuid + ", owner=" + owner + ", deadline=" + deadline + ", task="
				+ task + ", done=" + done + "]";
	}
}
