package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.zafritech.zscode.todos.enums.RepeatType;

@Entity(name = "ZSCODE_TODOS_REPEATS")
public class Repeat implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    
    private String uuid;
    
    private RepeatType type;
	
	private Integer count;

    @Temporal(TemporalType.TIMESTAMP)
	private Date current;

    @Temporal(TemporalType.TIMESTAMP)
	private Date last;

    @Temporal(TemporalType.TIMESTAMP)
	private Date started;

	public Repeat() {
		
	}

	public Repeat(RepeatType type, Integer count, Date current, Date started) {
		
		this.uuid = UUID.randomUUID().toString();
		this.type = type;
		this.count = count;
		this.current = current;
		this.started = started;
	}

	public Long getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}

	public RepeatType getType() {
		return type;
	}

	public Integer getCount() {
		return count;
	}

	public Date getCurrent() {
		return current;
	}

	public Date getLast() {
		return last;
	}

	public Date getStarted() {
		return started;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setType(RepeatType type) {
		this.type = type;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setCurrent(Date current) {
		this.current = current;
	}

	public void setLast(Date last) {
		this.last = last;
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	@Override
	public String toString() {
		return "Repeat [id=" + id + ", uuid=" + uuid + ", type=" + type + ", count=" + count + ", current=" + current
				+ ", last=" + last + ", started=" + started + "]";
	}
}
