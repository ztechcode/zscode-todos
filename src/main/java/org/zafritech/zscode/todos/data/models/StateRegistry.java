package org.zafritech.zscode.todos.data.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "ZSCODE_TODOS_STATE_REGISTRIES")
public class StateRegistry implements Serializable {

	private static final long serialVersionUID = 3123301447886815328L;

    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    private String stateKey;
    
    private boolean active;

    private Timestamp creationDateTime;

    private Timestamp activationDateTime;

    public StateRegistry() {
        
    }

    public StateRegistry(String stateKey) {
        
        this.uuId = UUID.randomUUID().toString();
        this.stateKey = stateKey;
        this.active = false;
        this.creationDateTime = new Timestamp(System.currentTimeMillis());
    }

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getStateKey() {
		return stateKey;
	}

	public void setStateKey(String stateKey) {
		this.stateKey = stateKey;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Timestamp getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(Timestamp creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public Timestamp getActivationDateTime() {
		return activationDateTime;
	}

	public void setActivationDateTime(Timestamp activationDateTime) {
		this.activationDateTime = activationDateTime;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "StateRegistry [id=" + id + ", uuId=" + uuId + ", stateKey=" + stateKey + ", active=" + active
				+ ", creationDateTime=" + creationDateTime + ", activationDateTime=" + activationDateTime + "]";
	}
}
