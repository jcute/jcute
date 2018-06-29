package com.jcute.core.toolkit.proxy;

import java.lang.reflect.Method;

import org.junit.Test;

import com.jcute.core.toolkit.proxy.support.ProxyFactoryForInterface;
import com.jcute.core.toolkit.proxy.support.ProxyHandlerForInterface;
import com.jcute.core.toolkit.proxy.support.cglib.CglibProxyManager;

public class TestProxy{

	@Test
	public void testClassProxy(){

		ProxyManager proxyManager = new CglibProxyManager();
		NameInfo nameInfoImpl = proxyManager.createProxyByClass(NameInfoImpl.class,new ProxyHandler() {
			@Override
			public ProxyFactory getFactory(){
				return new ProxyFactory() {
					@Override
					public Object execute(ProxyChain proxyChain) throws Throwable{
						System.out.println("--> before");
						Object result = proxyChain.doProxyChain();
						System.out.println("--> after");
						return result;
					}
				};
			}
		});

		System.out.println(nameInfoImpl.getName());

	}

	@Test
	public void testClassProxyWithArguments(){

		ProxyManager proxyManager = new CglibProxyManager();
		NameInfoImpl nameInfoImpl = proxyManager.createProxyByClass(NameInfoImpl.class,new Class<?>[]{String.class},new Object[]{"中文测试"},new ProxyHandler() {
			@Override
			public ProxyFactory getFactory(){
				return new ProxyFactory() {
					@Override
					public Object execute(ProxyChain proxyChain) throws Throwable{
						System.out.println("--> before");
						Object result = proxyChain.doProxyChain();
						System.out.println("--> after");
						return result;
					}
				};
			}
		});

		System.out.println(nameInfoImpl.getName());

	}

	@Test
	public void testInstanceProxy(){

		ProxyManager proxyManager = new CglibProxyManager();

		NameInfoImpl nameInfoImpl = new NameInfoImpl("中文测试");
		NameInfoImpl newNameInfoImpl = proxyManager.createProxyByInstance(nameInfoImpl,new ProxyHandler() {
			@Override
			public ProxyFactory getFactory(){
				return new ProxyFactory() {
					@Override
					public Object execute(ProxyChain proxyChain) throws Throwable{
						System.out.println("--> before");
						Object result = proxyChain.doProxyChain();
						System.out.println("--> after");
						return result;
					}
				};
			}
		});
		
		System.out.println(newNameInfoImpl.getName());
		
	}
	
	@Test
	public void testInterfaceProxy(){
		ProxyManager proxyManager = new CglibProxyManager();
		NameInfo nameInfo = proxyManager.createProxyByInterface(NameInfo.class,new ProxyHandlerForInterface(){
			@Override
			public ProxyFactoryForInterface getFactory(){
				return new ProxyFactoryForInterface() {
					@Override
					protected Object execute(Object targetInstance,Method targetMethod,Object[] targetParameterDatas){
						return "123";
					}
				};
			}
		});
		System.out.println(nameInfo.getName());
	}
	
}