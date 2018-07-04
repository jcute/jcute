package com.jcute.core.plugin.support;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionRegistry;
import com.jcute.core.bean.BeanDefinitionResolver;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.plugin.Plugin;
import com.jcute.core.plugin.PluginManager;
import com.jcute.core.plugin.PluginManagerEvent;
import com.jcute.core.plugin.PluginManagerListener;
import com.jcute.core.toolkit.cycle.EventableCallBack;
import com.jcute.core.toolkit.cycle.support.AbstractStable;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public abstract class AbstractPluginManager extends AbstractStable<PluginManagerEvent,PluginManagerListener> implements PluginManager{

	private static final Logger logger = LoggerFactory.getLogger(AbstractPluginManager.class);

	protected static final String EVENT_NAME_PLUGIN_ADD_SUCCESS = "EVENT_NAME_PLUGIN_ADD_SUCCESS";
	protected static final String EVENT_NAME_PLUGIN_DEL_SUCCESS = "EVENT_NAME_PLUGIN_DEL_SUCCESS";

	protected ApplicationContext applicationContext;
	protected Set<Plugin> plugins;

	public AbstractPluginManager(ApplicationContext applicationContext){
		if(null == applicationContext){
			throw new IllegalArgumentException("application context must not be null");
		}
		this.applicationContext = applicationContext;
		this.plugins = new LinkedHashSet<Plugin>();
	}

	@Override
	public Set<Plugin> getAllPlugins(){
		return Collections.unmodifiableSet(this.plugins);
	}

	@Override
	protected PluginManagerEvent createEvent(){
		return new PluginManagerEvent() {
			@Override
			public PluginManager getPluginManager(){
				return AbstractPluginManager.this;
			}

			@Override
			public BeanDefinitionResolver getBeanDefinitionResolver(){
				return applicationContext.getBeanDefinitionResolver();
			}

			@Override
			public BeanDefinitionRegistry getBeanDefinitionRegistry(){
				return applicationContext.getBeanDefinitionRegistry();
			}

			@Override
			public BeanDefinitionFactory getBeanDefinitionFactory(){
				return applicationContext.getBeanDefinitionFactory();
			}

			@Override
			public ApplicationContext getApplicationContext(){
				return applicationContext;
			}

			@Override
			public Plugin getPlugin(){
				return null;
			}
		};
	}

	@Override
	public void attachPlugin(final Plugin plugin){
		if(null == plugin){
			return;
		}
		synchronized(this.plugins){
			if(this.plugins.contains(plugin)){
				return;
			}
			this.plugins.add(plugin);
			this.fireEvent(EVENT_NAME_PLUGIN_ADD_SUCCESS,new EventableCallBack<PluginManagerEvent>() {
				@Override
				public PluginManagerEvent callback(final PluginManagerEvent event){
					return new PluginManagerEvent() {
						@Override
						public PluginManager getPluginManager(){
							return event.getPluginManager();
						}

						@Override
						public Plugin getPlugin(){
							return plugin;
						}

						@Override
						public BeanDefinitionResolver getBeanDefinitionResolver(){
							return event.getBeanDefinitionResolver();
						}

						@Override
						public BeanDefinitionRegistry getBeanDefinitionRegistry(){
							return event.getBeanDefinitionRegistry();
						}

						@Override
						public BeanDefinitionFactory getBeanDefinitionFactory(){
							return event.getBeanDefinitionFactory();
						}

						@Override
						public ApplicationContext getApplicationContext(){
							return event.getApplicationContext();
						}
					};
				}
			});
		}
	}

	@Override
	public void detachPlugin(final Plugin plugin){
		if(null == plugin){
			return;
		}
		synchronized(this.plugins){
			if(!this.plugins.contains(plugin)){
				return;
			}
			this.plugins.remove(plugin);
			this.fireEvent(EVENT_NAME_PLUGIN_DEL_SUCCESS,new EventableCallBack<PluginManagerEvent>() {
				@Override
				public PluginManagerEvent callback(final PluginManagerEvent event){
					return new PluginManagerEvent() {
						@Override
						public PluginManager getPluginManager(){
							return event.getPluginManager();
						}

						@Override
						public Plugin getPlugin(){
							return plugin;
						}

						@Override
						public BeanDefinitionResolver getBeanDefinitionResolver(){
							return event.getBeanDefinitionResolver();
						}

						@Override
						public BeanDefinitionRegistry getBeanDefinitionRegistry(){
							return event.getBeanDefinitionRegistry();
						}

						@Override
						public BeanDefinitionFactory getBeanDefinitionFactory(){
							return event.getBeanDefinitionFactory();
						}

						@Override
						public ApplicationContext getApplicationContext(){
							return event.getApplicationContext();
						}
					};
				}
			});
		}
	}

	@Override
	public void attachPluginAddSuccessListener(PluginManagerListener listener){
		this.attachListener(EVENT_NAME_PLUGIN_ADD_SUCCESS,listener);
	}

	@Override
	public void detachPluginAddSuccessListener(PluginManagerListener listener){
		this.detachListener(EVENT_NAME_PLUGIN_ADD_SUCCESS,listener);
	}

	@Override
	public void attachPluginDelSuccessListener(PluginManagerListener listener){
		this.attachListener(EVENT_NAME_PLUGIN_DEL_SUCCESS,listener);
	}

	@Override
	public void detachPluginDelSuccessListener(PluginManagerListener listener){
		this.detachListener(EVENT_NAME_PLUGIN_DEL_SUCCESS,listener);
	}

	@Override
	protected void doStart() throws Exception{
		for(Plugin plugin : this.plugins){
			try{
				plugin.start();
				logger.debug("plugin start success {}",this.toString());
			}catch(Exception e){
				e.printStackTrace();
				logger.warn(e.getMessage(),e);
			}
		}
	}

	@Override
	protected void doClose() throws Exception{
		for(Plugin plugin : this.plugins){
			try{
				plugin.close();
				logger.debug("plugin close success {}",this.toString());
			}catch(Exception e){
				logger.warn(e.getMessage(),e);
			}
		}
	}

}