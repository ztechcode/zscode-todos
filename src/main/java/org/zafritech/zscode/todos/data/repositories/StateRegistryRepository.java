package org.zafritech.zscode.todos.data.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.zafritech.zscode.todos.data.models.StateRegistry;

public interface StateRegistryRepository extends PagingAndSortingRepository<StateRegistry, Long> {
	
	StateRegistry findByStateKey(String stateKey);
	   
	StateRegistry findByStateKeyAndActive(String stateKey, boolean activated);
}
