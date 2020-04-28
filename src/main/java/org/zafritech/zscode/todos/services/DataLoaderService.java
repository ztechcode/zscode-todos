package org.zafritech.zscode.todos.services;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public interface DataLoaderService {

	public void registerSystemUser(String dataKey) throws JsonProcessingException;
		
	public boolean isInitialised(String dataKey);

	public void initialiseCategories(String dataKey);
	
	public void initialiseTasks(String dataKey);
}
