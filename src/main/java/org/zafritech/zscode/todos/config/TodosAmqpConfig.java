package org.zafritech.zscode.todos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zafritech.zscode.commons.amqp.config.AmqpConfig;

@Configuration
public class TodosAmqpConfig {
	
    @Bean
    @ConfigurationProperties(prefix = "rabbitmq")
    public AmqpConfig amqpConfi() {
		
		return new AmqpConfig();
	}
}
