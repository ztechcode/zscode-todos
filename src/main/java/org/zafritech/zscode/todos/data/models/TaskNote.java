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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity(name = "ZSCODE_TODOS_TASK_NOTES")
public class TaskNote implements Serializable {

	private static final long serialVersionUID = -7305060289812610534L;

	@Id
    @GeneratedValue
    @Setter(AccessLevel.PROTECTED)
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
}
