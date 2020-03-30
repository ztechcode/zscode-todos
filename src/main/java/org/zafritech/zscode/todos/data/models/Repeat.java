package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.zafritech.zscode.todos.data.converters.StringListConverter;
import org.zafritech.zscode.todos.enums.RepeatType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity(name = "ZSCODE_TODOS_REPEATS")
public class Repeat implements Serializable {

	private static final long serialVersionUID = 8285268136943603670L;

	@Id
    @GeneratedValue
    @Setter(AccessLevel.PROTECTED)
    private Long id;
    
    private String uuid;

    @Enumerated(EnumType.STRING)
    private RepeatType type;
	
	private Integer count;
	
    @Temporal(TemporalType.TIMESTAMP)
	private Date start;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date last;

    @Convert(converter = StringListConverter.class)
    private List<String> days;

	public Repeat() {
		
	}

	public Repeat(RepeatType type, Integer count, Date start) {
		
		this.uuid = UUID.randomUUID().toString();
		this.type = type;
		this.count = count;
		this.start = start;
	}
}
