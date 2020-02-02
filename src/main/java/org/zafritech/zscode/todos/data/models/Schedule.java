package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "ZSCODE_TODOS_SCHEDULES")
public class Schedule implements Serializable {

	private static final long serialVersionUID = -4361643559250891137L;

    @Id
    @GeneratedValue
    private Long id;
    
    private String uuid;

    private String owner;
    
    private LocalDate date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;
    
    private boolean done;
    
    public Schedule() {
    	
    }

	public Schedule(Task task, Date time) {

		this.uuid = UUID.randomUUID().toString();
		this.date = Instant.ofEpochMilli(time.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		this.time = time;
		this.task = task;
		this.done = false;
	}

	public Schedule(Task task, Date time, String owner) {

		this.uuid = UUID.randomUUID().toString();
		this.owner = owner;
		this.date = Instant.ofEpochMilli(time.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		this.time = time;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Date getTime() {
		return time;
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

	public void setTime(Date time) {
		this.time = time;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", uuid=" + uuid + ", owner=" + owner + ", date=" + date + ", time=" + time
				+ ", task=" + task + ", done=" + done + "]";
	}
}
