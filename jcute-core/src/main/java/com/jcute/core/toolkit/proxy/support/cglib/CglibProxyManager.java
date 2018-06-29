package com.jcute.core.toolkit.proxy.support.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

import com.jcute.core.toolkit.proxy.ProxyHandler;
import com.jcute.core.toolkit.proxy.ProxyMethod;
import com.jcute.core.toolkit.proxy.support.AbstractProxyManager;
import com.jcute.core.toolkit.proxy.support.ProxyChainForClass;
import com.jcute.core.toolkit.proxy.support.ProxyChainForInterface;
import com.jcute.core.toolkit.proxy.support.ProxyHandlerForInterface;
import com.jcute.core.util.GenericUtils;

public class CglibProxyManager extends AbstractProxyManager{

	@Override
	public <T>T createProxyByClass(final Class<T> targetClass,final Class<?>[] targetParameterTypes,final Object[] targetParameterDatas,final ProxyHandler... handlers){
		if(null == targetClass){
			throw new IllegalArgumentException("proxy class must not be null");
		}
		if(null == handlers || handlers.length == 0){
			throw new IllegalArgumentException("proxy handlers must not be null");
		}
		if(targetClass.isInterface()){
			throw new IllegalArgumentException("proxy class must not be interface");
		}
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object targetObject,Method targetMethod,Object[] targetParameterDatas,MethodProxy targetMethodProxy) throws Throwable{
				ProxyMethod targetProxyMethod = new CglibProxyMethodByClass(targetMethod,targetMethodProxy);
				return new ProxyChainForClass(targetClass,targetObject,targetParameterDatas,targetProxyMethod,handlers).doProxyChain();
			}
		});
		if(null == targetParameterTypes || targetParameterTypes.length == 0){
			return GenericUtils.parse(enhancer.create());
		}else{
			return GenericUtils.parse(enhancer.create(targetParameterTypes,targetParameterDatas));
		}
	}

	@Override
	public <T>T createProxyByInstance(final T instance,final ProxyHandler... handlers){
		if(null == instance){
			throw new IllegalArgumentException("proxy instance must not be null");
		}
		if(null == handlers || handlers.length == 0){
			throw new IllegalArgumentException("proxy handlers must not be null");
		}
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(instance.getClass());
		enhancer.setCallbackType(MethodInterceptor.class);
		final Class<?> clazz = enhancer.createClass();
		Objenesis objenesis = new ObjenesisStd();
		ObjectInstantiator<?> thingInstantiator = objenesis.getInstantiatorOf(clazz);
		Object proxyInstance = thingInstantiator.newInstance();
		Factory factory = (Factory)proxyInstance;
		factory.setCallback(0,new MethodInterceptor() {
			@Override
			public Object intercept(Object targetObject,Method targetMethod,Object[] targetParameterDatas,MethodProxy targetMethodProxy) throws Throwable{
				ProxyMethod targetProxyMethod = new CglibProxyMethodByInstance(targetMethod,targetMethodProxy);
				return new ProxyChainForClass(instance.getClass(),instance,targetParameterDatas,targetProxyMethod,handlers).doProxyChain();
			}
		});
		return GenericUtils.parse(factory);
	}

	@Override
	public <T>T createProxyByInterface(final Class<T> targetClass,final ProxyHandlerForInterface... handlers){
		if(null == targetClass){
			throw new IllegalArgumentException("proxy class must not be null");
		}
		if(null == handlers || handlers.length == 0){
			throw new IllegalArgumentException("proxy handlers must not be null");
		}
		if(!targetClass.isInterface()){
			throw new IllegalArgumentException("proxy class must not be interface");
		}
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object targetObject,Method targetMethod,Object[] targetParameterDatas,MethodProxy targetMethodProxy) throws Throwable{
				ProxyMethod targetProxyMethod = new CglibProxyMethodByInterface(targetMethod,targetMethodProxy);
				return new ProxyChainForInterface(targetClass,targetObject,targetParameterDatas,targetProxyMethod,handlers).doProxyChain();
			}
		});
		return GenericUtils.parse(enhancer.create());
	}

	@Override
	public boolean isProxy(Object targetInstance){
		return null != targetInstance && targetInstance instanceof Factory;
	}

}