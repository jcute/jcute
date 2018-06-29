package com.jcute.test.plugin.cachemanager;

import com.jcute.core.annotation.Autowired;
import com.jcute.core.annotation.Component;
import com.jcute.core.annotation.Configuration;
import com.jcute.core.annotation.Initial;
import com.jcute.core.boot.Application;
import com.jcute.plugin.cache.CacheManager;
import com.jcute.plugin.cache.CacheManagerEvent;
import com.jcute.plugin.cache.CacheManagerListener;
import com.jcute.plugin.cache.EnableCacheManager;
import com.jcute.plugin.cache.support.DefaultCacheManager;

@Configuration
//自定义缓存管理器
@EnableCacheManager("cacheManager")
public class TestCustomCacheManager{

	public static void main(String[] args){
		Application.run(TestCustomCacheManager.class);
	}

	@Component
	public CacheManager cacheManager() throws Exception{
		System.out.println("创建缓存管理器");
		CacheManager cacheManager = new DefaultCacheManager();
		cacheManager.attachCacheCreateListener(new CacheManagerListener() {
			@Override
			public void execute(CacheManagerEvent event) throws Exception{
				System.out.println("缓存创建成功:"+event.getCache().getCacheName());
			}
		});
		return cacheManager;
	}
	
	@Autowired
	private TestCustomService testService;
	
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