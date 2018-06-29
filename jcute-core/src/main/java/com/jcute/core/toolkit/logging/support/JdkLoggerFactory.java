package com.jcute.core.toolkit.logging.support;

import java.io.ByteArrayInputStream;
import java.util.logging.LogManager;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public class JdkLoggerFactory extends LoggerFactory{

	public JdkLoggerFactory(){
		try{
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(".level").append("=").append("FINEST").append("\n");
			stringBuffer.append("java.level").append("=").append("INFO").append("\n");
			stringBuffer.append("javax.level").append("=").append("INFO").append("\n");
			stringBuffer.append("com.sun.level").append("=").append("INFO").append("\n");
			stringBuffer.append("sun.level").append("=").append("INFO").append("\n");
			stringBuffer.append("jdk.level").append("=").append("INFO").append("\n");

			stringBuffer.append("handlers").append("=").append("java.util.logging.ConsoleHandler").append("\n");
			stringBuffer.append("java.util.logging.ConsoleHandler.level").append("=").append("FINEST").append("\n");
			stringBuffer.append("java.util.logging.ConsoleHandler.formatter").append("=").append("com.jcute.core.toolkit.logging.support.JdkLoggerFormater").append("\n");

			ByteArrayInputStream inputStream = new ByteArrayInputStream(stringBuffer.toString().getBytes("UTF-8"));

			LogManager logManager = LogManager.getLogManager();
			logManager.readConfiguration(inputStream);
			inputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	protected Logger createLogger(String loggerName){
		return new JdkLogger(java.util.logging.Logger.getLogger(loggerName));
	}

}