package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity(name = "ZSCODE_TODOS_NOTES")
public class Note implements Serializable {

	private static final long serialVersionUID = 4478446913579602825L;

	@Id
    @GeneratedValue
    @Setter(AccessLevel.PROTECTED)
    private Long id;
    
    private String owner;
    
    private String uuid;

    @Column(columnDefinition = "TEXT")
    private String text;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant timestamp;

	public Note() {

	}

	public Note(String text) {

        this.uuid = UUID.randomUUID().toString();
		this.text = text;
		this.setTimestamp(Instant.now());
	}

	public Note(String owner, String text) {

		this.uuid = UUID.randomUUID().toString();
		this.owner = owner;
		this.text = text;
		this.setTimestamp(Instant.now());
	}
}
