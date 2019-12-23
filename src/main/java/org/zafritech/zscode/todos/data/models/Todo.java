package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.zafritech.zscode.todos.enums.Frequency;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "ZSCODE_TODOS_TODOS")
public class Todo implements Serializable {

	private static final long serialVersionUID = -5715371767694860756L;

    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;

    private String name;

    private String details;

    private boolean recurring;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;
    
    private Category category;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "ZSCODE_TODOS_TODOPROJECTS",
            joinColumns = {
                @JoinColumn(name = "todoId", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "projectId", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<Project> projects = new HashSet<Project>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "ZSCODE_TODOS_TODOTAGS",
            joinColumns = {
                @JoinColumn(name = "todoId", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "tagId", referencedColumnName = "id")}
    )
    @JsonBackReference
    private Set<Tag> tags = new HashSet<Tag>();
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

	public Todo() {

	}

	public Todo(String name, String details) {
	
		this.uuId = UUID.randomUUID().toString();
		this.name = name;
		this.details = details;
		this.recurring = false;
		this.frequency = null;
		this.category = null;
		this.creationDate = new Timestamp(System.currentTimeMillis());
	}

	public Todo(String name, String details, Date due) {
	
		this.uuId = UUID.randomUUID().toString();
		this.name = name;
		this.details = details;
		this.recurring = false;
		this.frequency = null;
		this.category = null;
		this.creationDate = new Timestamp(System.currentTimeMillis());
		this.dueDate = due;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public boolean isRecurring() {
		return recurring;
	}

	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", uuId=" + uuId + ", name=" + name + ", details=" + details + ", recurring="
				+ recurring + ", frequency=" + frequency + ", category=" + category + ", projects=" + projects
				+ ", tags=" + tags + ", creationDate=" + creationDate + ", dueDate=" + dueDate + "]";
	}
}
