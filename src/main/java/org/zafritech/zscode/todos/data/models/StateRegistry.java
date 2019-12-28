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

    private Timestamp created;

    private Timestamp activated;

    public StateRegistry() {
        
    }

    public StateRegistry(String stateKey) {
        
        this.uuId = UUID.randomUUID().toString();
        this.stateKey = stateKey;
        this.active = false;
        this.created = new Timestamp(System.currentTimeMillis());
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

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getActivated() {
		return activated;
	}

	public void setActivated(Timestamp activated) {
		this.activated = activated;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "StateRegistry [id=" + id + ", uuId=" + uuId + ", stateKey=" + stateKey + ", active=" + active
				+ ", created=" + created + ", activated=" + activated + "]";
	}
}
