package com.jcute.core.context.impl;

import com.jcute.core.annotation.Autowired;
import com.jcute.core.annotation.Component;
import com.jcute.core.annotation.Initial;
import com.jcute.core.annotation.Order;
import com.jcute.core.annotation.Property;
import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.context.TestService;

@Component(value = "testService",scope = BeanDefinition.BEAN_SCOPE_PROTOTYPE)
public class TestServiceImpl implements TestService{
	
	@Autowired
	private TestMember testMember;
	
	@Property("config.server.port")
	private int serverPort;
	
	@Override
	public String getName(){
		return "TestServiceImpl";
	}
	
	@Initial
	@Order(2)
	public void initA(){
		System.out.println("2");
		System.out.println("-----------------> "+this.serverPort);
		System.out.println(this.testMember.getName());
	}

	@Initial
	@Order(1)
	public void initB(@Property("os.name") String osName){
		System.out.println("1");
		System.out.println("osname -> " + osName);
	}
	
}