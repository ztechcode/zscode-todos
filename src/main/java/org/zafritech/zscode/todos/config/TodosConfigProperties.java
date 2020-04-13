package org.zafritech.zscode.todos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter @Setter
@ConfigurationProperties(prefix = "zafritech")
public class TodosConfigProperties {

    private Organisation Organisation;
    
    private Paths Paths;
    
    private Urls Urls;
    
    private App App;

    @Getter @Setter
    public static class Organisation {

        private String name;
        
        private String domain; 
    }

    @Getter @Setter    
    public static class Paths {

        private String dataDir;

        private String imagesDir;

        private String uploadDir;
    }

    @Getter @Setter       
    public static class Urls {
    	
    	private String authApi;
    	
    	private String resourcesApi;
    	
    	private String resourcesUrl;
    }
    
    @Getter @Setter       
    public static class App {
    	
    	private String email;
    	
    	private String name;
    	
    	private String suffix;
    	
    	private String password;
    	
    	private Integer initDelayMillis;
    }
}
