package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "ZSCODE_TODOS_CATEGORIES")
public class Category implements Serializable {

	private static final long serialVersionUID = -7115718855249661349L;

    @Id
    @GeneratedValue
    private Long id;
    
    private String uuid;

    private String name;

	public Category() {
		
	}

	public Category(String name) {

		this.uuid = UUID.randomUUID().toString();
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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
		return "Category [id=" + id + ", uuid=" + uuid + ", name=" + name + "]";
	}
}
