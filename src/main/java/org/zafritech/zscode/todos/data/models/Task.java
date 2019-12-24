package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.zafritech.zscode.todos.enums.Priority;
import org.zafritech.zscode.todos.enums.RepeatType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "ZSCODE_TODOS_TASKS")
public class Task implements Serializable {

	private static final long serialVersionUID = -5715371767694860756L;

    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String details;

    @Enumerated(EnumType.STRING)
    private RepeatType repeatType;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    
    private boolean complete;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "ZSCODE_TODOS_TASKSTAGS",
            joinColumns = {
                @JoinColumn(name = "taskId", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "tagId", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<Tag> tags = new HashSet<Tag>();
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date due;

	public Task() {

	}

	public Task(String details) {
	
		this.uuId = UUID.randomUUID().toString();
		this.details = details;
		this.repeatType = RepeatType.ONCE_OFF;
		this.priority = Priority.MEDIUM;
		this.category = null;
		this.complete = false;
		this.created = new Timestamp(System.currentTimeMillis());
		this.due = new Timestamp(System.currentTimeMillis());;
	}

	public Task(String details, Date due) {
	
		this.uuId = UUID.randomUUID().toString();
		this.details = details;
		this.repeatType = RepeatType.ONCE_OFF;
		this.priority = Priority.MEDIUM;
		this.category = null;
		this.complete = false;
		this.created = new Timestamp(System.currentTimeMillis());
		this.due = due;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public RepeatType getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(RepeatType repeatType) {
		this.repeatType = repeatType;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Category getCategory() {
		return category;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getDue() {
		return due;
	}

	public void setDue(Date due) {
		this.due = due;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", uuId=" + uuId + ", details=" + details + ", repeatType=" + repeatType
				+ ", priority=" + priority + ", category=" + category + ", complete=" + complete + ", project="
				+ project + ", tags=" + tags + ", created=" + created + ", due=" + due + "]";
	}
}
