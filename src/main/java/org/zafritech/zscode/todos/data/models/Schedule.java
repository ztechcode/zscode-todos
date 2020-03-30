package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity(name = "ZSCODE_TODOS_SCHEDULES")
public class Schedule implements Serializable {

	private static final long serialVersionUID = -4361643559250891137L;

	@Id
    @GeneratedValue
    @Setter(AccessLevel.PROTECTED)
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
}
