package com.jcute.core.plugin;

import java.lang.annotation.Annotation;

import com.jcute.core.context.ApplicationContext;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public class LoggerPlugin extends Plugin{

	private static final Logger logger = LoggerFactory.getLogger(LoggerPlugin.class);

	public LoggerPlugin(ApplicationContext applicationContext,Annotation annotation){
		super(applicationContext,annotation);
	}

	@Override
	public void onStart() throws Exception{
		EnableLoggerPlugin plugin = this.getAnnotation();
		logger.debug("测试 -> 启动日志[{}]",plugin.level());
	}

	@Override
	protected void onClose() throws Exception{
		EnableLoggerPlugin plugin = this.getAnnotation();
		logger.debug("测试 -> 关闭日志[{}]",plugin.level());
	}

}