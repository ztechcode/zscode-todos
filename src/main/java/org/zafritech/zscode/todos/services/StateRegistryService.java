package org.zafritech.zscode.todos.services;

import org.springframework.stereotype.Service;
import org.zafritech.zscode.commons.data.models.StateRegistry;

@Service
public interface StateRegistryService {

	public void loadData(String dataKey);
	
	public void loadData(String fileName, String dataKey);
	
	public StateRegistry activateState(String state);
}
