package com.jcute.core.network.support;

import com.jcute.core.network.NetWorkManagerOptions;

public abstract class AbstractNetWorkManagerOptions implements NetWorkManagerOptions{

	protected int bossPoolSize;
	protected int workPoolSize;
	protected long threadCheckInterval;
	protected long maxBossExecuteTime;
	protected long maxWorkExecuteTime;
	protected long warningExceptionTime;
	protected String bossThreadPrefix;
	protected String workThreadPrefix;
	
	public AbstractNetWorkManagerOptions(){
		this.bossPoolSize = 1;
		this.workPoolSize = Runtime.getRuntime().availableProcessors() * 2;
		this.threadCheckInterval = 1000;//1000ms
		this.maxBossExecuteTime = 2L * 1000 * 1000000;//2 seconds
		this.maxWorkExecuteTime = 60L * 1000 * 1000000;//60 seconds
		this.warningExceptionTime = 5L * 1000 * 1000000;//5 seconds
		this.bossThreadPrefix = "boss";
		this.workThreadPrefix = "work";
	}

	@Override
	public int getBossPoolSize(){
		return this.bossPoolSize;
	}

	@Override
	public int getWorkPoolSize(){
		return this.workPoolSize;
	}

	@Override
	public long getThreadCheckInterval(){
		return this.threadCheckInterval;
	}

	@Override
	public long getMaxBossExecuteTime(){
		return this.maxBossExecuteTime;
	}

	@Override
	public long getMaxWorkExecuteTime(){
		return this.maxWorkExecuteTime;
	}

	@Override
	public long getWarningExceptionTime(){
		return this.warningExceptionTime;
	}

	@Override
	public String getWorkThreadPrefix(){
		return this.workThreadPrefix;
	}

	@Override
	public String getBossThreadPrefix(){
		return this.bossThreadPrefix;
	}

	@Override
	public void setWorkPoolSize(int size){
		this.workPoolSize = size;
	}

	@Override
	public void setBossPoolSize(int size){
		this.bossPoolSize = size;
	}

	@Override
	public void setThreadCheckInterval(long interval){
		this.threadCheckInterval = interval;
	}

	@Override
	public void setMaxBossExecuteTime(long maxExecuteTime){
		this.maxBossExecuteTime = maxExecuteTime;
	}

	@Override
	public void setMaxWorkExecuteTime(long maxExecuteTime){
		this.maxWorkExecuteTime = maxExecuteTime;
	}

	@Override
	public void setWarningExceptionTime(long warningExceptionTime){
		this.warningExceptionTime = warningExceptionTime;
	}

	@Override
	public void setBossThreadPrefix(String prefix){
		this.bossThreadPrefix = prefix;
	}

	@Override
	public void setWorkThreadPrefix(String prefix){
		this.workThreadPrefix = prefix;
	}

}