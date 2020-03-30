package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.zafritech.zscode.todos.enums.Priority;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(exclude = {"category", "notes", "project", "tags"})
@Entity(name = "ZSCODE_TODOS_TASKS")
public class Task implements Serializable {

	private static final long serialVersionUID = -5715371767694860756L;

	@Id
    @GeneratedValue
    @Setter(AccessLevel.PROTECTED)
    private Long id;
    
    private String uuid;

    private String owner;
    
    private Task parent;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String details;

    @ManyToOne
    @JoinColumn(name = "repeatId")
    private Repeat repeat;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "taskId")
    @JsonManagedReference
    @OrderBy("created DESC")
    private List<TaskNote> notes = new ArrayList<>();

    @ElementCollection
    @Column(name="tags")
    @CollectionTable(name="ZSCODE_TODOS_TASK_TAGS", joinColumns= {@JoinColumn(name="taskId")})
    private List<String> tags = new ArrayList<>();

    private Date deadline;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

	public Task() {

	}

	public Task(String details) {
	
		this.uuid = UUID.randomUUID().toString();
		this.parent = null;
		this.details = details;
		this.priority = Priority.MEDIUM;
		this.category = null;
		this.repeat = null;
		this.deadline = new Timestamp(System.currentTimeMillis());
		this.created = new Timestamp(System.currentTimeMillis());
	}

	public Task(String details, Date deadline) {
	
		this.uuid = UUID.randomUUID().toString();
		this.parent = null;
		this.details = details;
		this.priority = Priority.MEDIUM;
		this.category = null;
		this.repeat = null;
		this.deadline = deadline;
		this.created = new Timestamp(System.currentTimeMillis());
	}
}
