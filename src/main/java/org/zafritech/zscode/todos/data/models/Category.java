package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity(name = "ZSCODE_TODOS_CATEGORIES")
public class Category implements Serializable {

	private static final long serialVersionUID = -7115718855249661349L;

	@Id
    @GeneratedValue
    @Setter(AccessLevel.PROTECTED)
    private Long id;
    
    private String uuid;

    private String name;

	public Category() {
		
	}

	public Category(String name) {

		this.uuid = UUID.randomUUID().toString();
		this.name = name;
	}
}
