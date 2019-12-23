package org.zafritech.zscode.todos.data.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.models.Category;
import org.zafritech.zscode.todos.data.models.StateRegistry;
import org.zafritech.zscode.todos.data.repositories.CategoryRepository;
import org.zafritech.zscode.todos.data.repositories.StateRegistryRepository;

@Service
public class DataLoaderServiceImpl implements DataLoaderService {

	@Autowired
    private CategoryRepository categoryRepository;
	
    @Autowired
    private StateRegistryRepository stateRepository;
    
    @Autowired
    private StateRegistryService stateService;

    private static final Logger logger = LoggerFactory.getLogger(DataLoaderServiceImpl.class);
    
	@Override
	public boolean isInitialised(String dataKey) {
		 
        StateRegistry task = stateRepository.findByStateKeyAndActive(dataKey, true);
        
        return task != null;
	}

	@Override
	public void initialiseTodos(String dataKey) {

		Category category = categoryRepository.save(new Category("Uncategorised"));
		logger.info("Initialised category: " + category.getName()); 

        stateService.activateState(dataKey);
	}
}
