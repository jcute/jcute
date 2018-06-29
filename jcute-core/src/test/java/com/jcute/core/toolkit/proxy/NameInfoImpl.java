package com.jcute.core.toolkit.proxy;

public class NameInfoImpl implements NameInfo{

	private String name;

	public NameInfoImpl(){
	}

	public NameInfoImpl(String name){
		this.name = name;
	}

	@Override
	public String getName(){
		if(null != this.name){
			return this.name;
		}
		return "hello";
	}

}