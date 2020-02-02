package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "ZSCODE_TODOS_TASKS")
public class Task implements Serializable {

	private static final long serialVersionUID = -5715371767694860756L;

    @Id
    @GeneratedValue
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

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "ZSCODE_TODOS_TASKTAGS",
            joinColumns = {
                @JoinColumn(name = "taskId", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "tagId", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<Tag> tags = new HashSet<Tag>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "ZSCODE_TODOS_TASKNOTES",
            joinColumns = {
                @JoinColumn(name = "taskId", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "noteId", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<Note> notes = new HashSet<Note>();

    private LocalDate target;
    
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
		this.created = new Timestamp(System.currentTimeMillis());
	}

	public Task(String details, LocalDate target) {
	
		this.uuid = UUID.randomUUID().toString();
		this.parent = null;
		this.details = details;
		this.priority = Priority.MEDIUM;
		this.category = null;
		this.created = new Timestamp(System.currentTimeMillis());
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

	public Task getParent() {
		return parent;
	}

	public String getDetails() {
		return details;
	}

	public Repeat getRepeat() {
		return repeat;
	}

	public Priority getPriority() {
		return priority;
	}

	public Category getCategory() {
		return category;
	}

	public Project getProject() {
		return project;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public Set<Note> getNotes() {
		return notes;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setParent(Task parent) {
		this.parent = parent;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setRepeat(Repeat repeat) {
		this.repeat = repeat;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	public LocalDate getTarget() {
		return target;
	}

	public void setTarget(LocalDate target) {
		this.target = target;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", uuid=" + uuid + ", owner=" + owner + ", parent=" + parent + ", details=" + details
				+ ", repeat=" + repeat + ", priority=" + priority + ", category=" + category + ", project=" + project
				+ ", tags=" + tags + ", notes=" + notes + ", created=" + created + "]";
	}
}
