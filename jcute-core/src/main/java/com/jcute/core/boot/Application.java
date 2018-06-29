package com.jcute.core.boot;

import com.jcute.core.context.ApplicationContext;
import com.jcute.core.context.ApplicationContextEvent;
import com.jcute.core.context.ApplicationContextListener;
import com.jcute.core.context.support.ApplicationContextForAnnotation;

public final class Application implements ApplicationContextListener{

	private ApplicationContext applicationContext;

	private Application(Class<?> runner) throws Exception{
		if(null == runner){
			throw new IllegalArgumentException("runner class must not be null");
		}
		this.applicationContext = new ApplicationContextForAnnotation(runner);
		this.applicationContext.attachStartSuccessListener(this);
		this.applicationContext.start();
	}

	@Override
	public void execute(final ApplicationContextEvent event) throws Exception{
		Runtime.getRuntime().addShutdownHook(new Thread("jcute-shutdown-hook") {
			@Override
			public void run(){
				try{
					event.getApplicationContext().close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	public ApplicationContext getApplicationContext(){
		return this.applicationContext;
	}

	public static Application run(Class<?> runner){
		try{
			return new Application(runner);
		}catch(Exception e){
			System.exit(1);
			return null;
		}
	}

}