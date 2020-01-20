package org.zafritech.zscode.todos.enums;

import java.util.HashMap;
import java.util.Map;

public enum Priority {

	TRIVIAL(1, "Very Low"),
	LOW(2, "Low"),
	MEDIUM(3, "Medium"),
	HIGH(4, "High"),
	CRITICAL(5, "Very High");
	
	private final Integer value;
	private final String text;
	private static Map<Integer, Priority> mapping;
	
	Priority(Integer value, String text) {
		
        this.value = value;
        this.text = text;
    }
	
	public static Priority getPriority(Integer i){
		
        if(mapping == null){
            initMapping();
        }
        
        return mapping.get(i);
    }
	
	private static void initMapping(){
		
		mapping = new HashMap<>();
		for(Priority p : values()){
			
			mapping.put(p.value, p);
		}
	}

	public Integer getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
	
	@Override
    public String toString(){
		
        final StringBuilder sb = new StringBuilder();
        
        sb.append("Priority : ");
        sb.append("{value=").append(value).append(", ");
        sb.append("text=").append(text).append("}");
        
		return sb.toString();
	}
}
