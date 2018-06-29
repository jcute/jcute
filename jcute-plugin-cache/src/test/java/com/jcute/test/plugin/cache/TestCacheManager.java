package com.jcute.test.plugin.cache;

import com.jcute.core.annotation.Autowired;
import com.jcute.core.annotation.Configuration;
import com.jcute.core.annotation.Initial;
import com.jcute.core.boot.Application;
import com.jcute.plugin.cache.EnableCacheManager;

@Configuration
@EnableCacheManager
public class TestCacheManager{
	
	public static void main(String[] args) throws InterruptedException{
		
		Application.run(TestCacheManager.class);
		
//		CacheManager cacheManager = application.getApplicationContext().getBean(CacheManager.class);
//		
//		Cache cache = cacheManager.getCache("demo");
//		
//		cache.putValue("hello","123",100);
//		System.out.println(cache.getValue("hello"));
//		System.out.println(cache.getCacheNative());
//		Thread.sleep(110);
//		System.out.println(cache.getValue("hello"));
//		System.out.println(cache.getCacheNative());
	}
	
	@Autowired
	private TestService testService;
	
	@Initial
	public void init() throws InterruptedException{
		
		this.testService.setName("张三");
		System.out.println(this.testService.getName());
		System.out.println(this.testService.getName());
		this.testService.clearName();
		System.out.println(this.testService.getName());
		
		System.out.println("------------------------------");
		
		System.out.println(this.testService.getSex());
		System.out.println(this.testService.getSex());
		System.out.println(this.testService.getSex());
		Thread.sleep(100);
		System.out.println(this.testService.getSex());
		System.out.println(this.testService.getSex());
		Thread.sleep(100);
		System.out.println(this.testService.getSex());
		System.out.println(this.testService.getSex());
		
	}
	
}