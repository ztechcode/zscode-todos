package org.zafritech.zscode.todos.data.daos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepeatDao {

    private Long id;
   
    private String type;
	
	private Integer count;
	
	private String start;

	public Long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public Integer getCount() {
		return count;
	}

	public String getStart() {
		return start;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setStart(String start) {
		this.start = start;
	}
}
