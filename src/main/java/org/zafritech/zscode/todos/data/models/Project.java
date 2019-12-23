package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "ZSCODE_TODOS_PROJECTS")
public class Project implements Serializable {

	private static final long serialVersionUID = -596086726927714355L;

    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;

    private String name;

	public Project() {

	}

	public Project(String name) {

		this.uuId = UUID.randomUUID().toString();
		this.name = name;
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

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", uuId=" + uuId + ", name=" + name + "]";
	}
}
