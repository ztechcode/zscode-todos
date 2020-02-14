package org.zafritech.zscode.todos.data.daos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepeatDao {

    private Long id;
   
    private String type;
	
	private Integer count;
	
	private String start;
	
	private String last;
	
	private List<String> days = new ArrayList<>();

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

	public String getLast() {
		return last;
	}

	public List<String> getDays() {
		return days;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public void setDays(List<String> days) {
		this.days = days;
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

	@Override
	public String toString() {
		return "RepeatDao [id=" + id + ", type=" + type + ", count=" + count + ", start=" + start + ", last=" + last
				+ ", days=" + days + "]";
	}
}
