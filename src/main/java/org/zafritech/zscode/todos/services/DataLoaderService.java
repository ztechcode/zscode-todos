package org.zafritech.zscode.todos.services;

import org.springframework.stereotype.Service;

@Service
public interface DataLoaderService {
	
	public boolean isInitialised(String dataKey);

	public void initialiseTasks(String dataKey);
}
