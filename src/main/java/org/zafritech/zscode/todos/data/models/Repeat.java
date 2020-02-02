package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private RepeatType type;
	
	private Integer count;

    @Temporal(TemporalType.TIMESTAMP)
	private Date start;
    
    private LocalDate lastRepeat;

	public Repeat() {
		
	}

	public Repeat(RepeatType type, Integer count, Date start) {
		
		this.uuid = UUID.randomUUID().toString();
		this.type = type;
		this.count = count;
		this.start = start;
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

	public Date getStart() {
		return start;
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

	public void setStart(Date start) {
		this.start = start;
	}
	
	public LocalDate getLastRepeat() {
		return lastRepeat;
	}

	public void setLastRepeat(LocalDate lastRepeat) {
		this.lastRepeat = lastRepeat;
	}

	@Override
	public String toString() {
		return "Repeat [id=" + id + ", uuid=" + uuid + ", type=" + type + ", count=" + count + ", start=" + start
				+ ", lastRepeat=" + lastRepeat + "]";
	}

}
