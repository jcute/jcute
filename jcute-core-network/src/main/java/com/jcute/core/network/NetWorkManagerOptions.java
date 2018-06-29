package com.jcute.core.network;


public interface NetWorkManagerOptions{
	
	public int getBossPoolSize();
	
	public int getWorkPoolSize();
	
	public long getThreadCheckInterval();
	
	public long getMaxBossExecuteTime();
	
	public long getMaxWorkExecuteTime();
	
	public long getWarningExceptionTime();
	
	public String getWorkThreadPrefix();
	
	public String getBossThreadPrefix();
	
	public void setWorkPoolSize(int size);
	
	public void setBossPoolSize(int size);
	
	public void setThreadCheckInterval(long interval);
	
	public void setMaxBossExecuteTime(long maxExecuteTime);
	
	public void setMaxWorkExecuteTime(long maxExecuteTime);
	
	public void setWarningExceptionTime(long warningExceptionTime);
	
	public void setBossThreadPrefix(String prefix);
	
	public void setWorkThreadPrefix(String prefix);
	
}