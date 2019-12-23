package org.zafritech.zscode.todos.data.init;

import org.springframework.stereotype.Service;

@Service
public interface DataLoaderService {
	
	public boolean isInitialised(String dataKey);

	public void initialiseTodos(String dataKey);
}
