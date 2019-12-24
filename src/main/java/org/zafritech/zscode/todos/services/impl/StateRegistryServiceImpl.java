package org.zafritech.zscode.todos.services.impl;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.zscode.todos.data.models.StateRegistry;
import org.zafritech.zscode.todos.data.repositories.StateRegistryRepository;
import org.zafritech.zscode.todos.services.StateRegistryService;

@Service
public class StateRegistryServiceImpl implements StateRegistryService {

	@Autowired
	private StateRegistryRepository stateRepository;

    private static final Logger logger = LoggerFactory.getLogger(StateRegistryServiceImpl.class);
    
	@Override
	public void loadData(String dataKey) {

		activateState(dataKey);
	}

	@Override
	public void loadData(String fileName, String dataKey) {

		activateState(dataKey);
	}

	@Override
	public StateRegistry activateState(String dataKey) {

		StateRegistry state;
		state = stateRepository.findByStateKey(dataKey);

		if (state == null) {

			state = new StateRegistry(dataKey);
		}

		state.setActive(true);
		state.setCreationDateTime(new Timestamp(System.currentTimeMillis()));
		state.setActivationDateTime(new Timestamp(System.currentTimeMillis()));

		logger.info("Registry State " + dataKey + " completed....");

		return stateRepository.save(state);
	}

}
