package org.zafritech.zscode.todos.services;

import java.text.ParseException;

import org.springframework.stereotype.Service;

@Service
public interface DataLoaderService {
	
	public boolean isInitialised(String dataKey);

	public void initialiseCategories(String dataFile, String dataKey);
	
	public void initialiseTasks(String dataFile, String dataKey) throws ParseException;
}
