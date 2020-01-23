package org.zafritech.zscode.todos.data.daos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonNoteDao {

    private Long id;

    private String text;
    
    private String timestamp;

	public JsonNoteDao() {

	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "JsonNoteDao [id=" + id + ", text=" + text + ", timestamp=" + timestamp + "]";
	}
}
