package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity(name = "ZSCODE_TODOS_TASK_LOGS")
public class TaskLog implements Serializable {

	private static final long serialVersionUID = -3177895980900620157L;

	@Id
    @GeneratedValue
    @Setter(AccessLevel.PROTECTED)
    private Long id;
    
    private String uuid;

    private String owner;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completed;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

	public TaskLog() {
		
	}

	public TaskLog(Task task) {

		this.uuid = UUID.randomUUID().toString();
		this.task = task;
		this.completed = new Timestamp(System.currentTimeMillis());
		this.created = new Timestamp(System.currentTimeMillis());
	}

	public TaskLog(Task task, Date completed) {

		this.uuid = UUID.randomUUID().toString();
		this.task = task;
		this.completed = completed;
		this.created = new Timestamp(System.currentTimeMillis());
	}

	public TaskLog(String owner, Task task, Date completed) {

		this.uuid = UUID.randomUUID().toString();
		this.owner = owner;
		this.task = task;
		this.completed = completed;
		this.created = new Timestamp(System.currentTimeMillis());
	}
}
