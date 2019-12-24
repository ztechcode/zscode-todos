package org.zafritech.zscode.todos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "zafritech")
public class TodosConfigProperties {
	
	private Organisation organisation;
	 
    private Paths paths;
    
    public static class Organisation {
    	
    	private String name;

        private String domain;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDomain() {
			return domain;
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}
    }

    public static class Paths {

        private String staticResources;

        private String uploadDir;

        private String dataDir;
        
        private String imagesDir;
        
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

		public String getImagesDir() {
			return imagesDir;
		}

		public void setImagesDir(String imagesDir) {
			this.imagesDir = imagesDir;
		}
		
    }

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}

	public Paths getPaths() {
		return paths;
	}

	public void setPaths(Paths paths) {
		this.paths = paths;
	}
}
