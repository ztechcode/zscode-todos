package org.zafritech.zscode.todos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "zafritech")
public class TodosConfigProperties {
	 
    private Paths paths;

    public static class Paths {

        private String staticResources;

        private String uploadDir;

        private String dataDir;
        
		public String getStaticResources() {
			return staticResources;
		}

		public void setStaticResources(String staticResources) {
			this.staticResources = staticResources;
		}

		public String getUploadDir() {
			return uploadDir;
		}

		public void setUploadDir(String uploadDir) {
			this.uploadDir = uploadDir;
		}

		public String getDataDir() {
			return dataDir;
		}

		public void setDataDir(String dataDir) {
			this.dataDir = dataDir;
		}
    }

	public Paths getPaths() {
		return paths;
	}

	public void setPaths(Paths paths) {
		this.paths = paths;
	}
}
