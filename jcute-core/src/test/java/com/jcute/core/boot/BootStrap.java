package com.jcute.core.boot;

import com.jcute.core.annotation.Configuration;
import com.jcute.core.annotation.ImportResource;
import com.jcute.core.annotation.Initial;
import com.jcute.core.annotation.Property;
import com.jcute.core.config.ConfigSourceManager;

@Configuration
@ImportResource("config.json")
public class BootStrap{
	
	@Property("os.name")
	private String name;
	
	@Property("server.name")
	private String serverName;
	
	@Initial
	public void init(){
		System.out.println(this.name);
		System.out.println(this.serverName);
	}
	
	public static void main(String[] args){
		Application application = Application.run(BootStrap.class);
		ConfigSourceManager configSourceManager = application.getApplicationContext().getConfigSourceManager();
		System.out.println(configSourceManager.getStringValue("name").getValue());
		System.out.println(configSourceManager.getStringValue("server.config.port").getValue());
	}
	
}