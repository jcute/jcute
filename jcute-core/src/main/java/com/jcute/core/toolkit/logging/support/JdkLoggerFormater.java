package com.jcute.core.toolkit.logging.support;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class JdkLoggerFormater extends Formatter{

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public synchronized String format(LogRecord record){
		StringBuffer sb = new StringBuffer();
		String level = toLevel(record.getLevel());
		sb.append(dateFormat.format(new Date(record.getMillis()))).append(" ");
		sb.append(level).append(" ");
		if(level.equals("WARN") || level.equals("INFO")){
			sb.append(" ");
		}
		sb.append("[").append(Thread.currentThread().getName()).append("]").append(" ");
		sb.append(record.getLoggerName()).append(".").append(record.getSourceMethodName()).append(" ");
		sb.append("- ").append(record.getMessage()).append("\n");
		return sb.toString();
	}

	private static String toLevel(Level level){
		if(null == level){
			return "UNKNOW";
		}
		if(level == Level.FINEST){
			return "TRACE";
		}else if(level == Level.FINE){
			return "DEBUG";
		}else if(level == Level.INFO){
			return "INFO";
		}else if(level == Level.WARNING){
			return "WARN";
		}else if(level == Level.SEVERE){
			return "ERROR";
		}
		return "UNKNOW";
	}

}