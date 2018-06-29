package com.jcute.test.plugin.cache;

import com.jcute.core.annotation.Component;
import com.jcute.plugin.cache.annotation.CacheClear;
import com.jcute.plugin.cache.annotation.CachePut;
import com.jcute.plugin.cache.annotation.Cacheable;

@Component
@Cacheable(value="test")
public class TestService{
	
	private String name;
	
	@CachePut(cacheKey="name")
	public String getName(){
		System.out.println("来自服务,并非缓存");
		return this.name;
	}
	
	@CachePut(cacheKey="sex",cacheExpiry=200)
	public String getSex(){
		System.out.println("来自服务,并非缓存");
		return "男";
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	@CacheClear
	public void clearName(){
		this.name = null;
	}
	
}