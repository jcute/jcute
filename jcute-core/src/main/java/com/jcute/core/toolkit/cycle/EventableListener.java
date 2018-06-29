package com.jcute.core.toolkit.cycle;

public interface EventableListener<E extends EventableEvent>{
	
	public void execute(E event)throws Exception;
	
}