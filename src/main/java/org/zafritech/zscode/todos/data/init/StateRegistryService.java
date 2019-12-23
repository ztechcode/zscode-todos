package org.zafritech.zscode.todos.data.init;

import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.models.StateRegistry;

@Service
public interface StateRegistryService {

	void loadData(String dataKey);
	
	void loadData(String fileName, String dataKey);
	
	StateRegistry activateState(String state);
}
