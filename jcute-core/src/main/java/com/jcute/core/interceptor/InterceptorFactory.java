package com.jcute.core.interceptor;

import java.lang.reflect.Method;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.toolkit.proxy.ProxyChain;
import com.jcute.core.toolkit.proxy.ProxyFactory;

public abstract class InterceptorFactory implements ProxyFactory{

	private static final Logger logger = LoggerFactory.getLogger(InterceptorFactory.class);

	@Override
	public Object execute(ProxyChain proxyChain) throws Throwable{
		Object result = null;
		Class<?> targetClass = proxyChain.getTargetClass();
		Method targetMethod = proxyChain.getTargetMethod();
		Object[] arguments = proxyChain.getTargetParameterDatas();
		try{
			if(this.onIntercept(targetClass,targetMethod,arguments)){
				this.onBefore(targetClass,targetMethod,arguments);
				result = proxyChain.doProxyChain();
				this.onAfter(targetClass,targetMethod,arguments,result);
			}else{
				result = proxyChain.doProxyChain();
			}
		}catch(Throwable t){
			logger.warn(t.getMessage(),t);
			this.onException(targetClass,targetMethod,arguments,t);
		}
		return result;
	}

	/**
	 * 判断当前方法是否需要拦截，默认返回true，自定义可重写此方法
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param arguments
	 * @return true为拦截
	 * @throws Throwable
	 */
	protected boolean onIntercept(Class<?> targetClass,Method targetMethod,Object[] arguments) throws Throwable{
		return true;
	}

	/**
	 * 方法调用前执行
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param arguments
	 * @throws Throwable
	 */
	protected void onBefore(Class<?> targetClass,Method targetMethod,Object[] arguments) throws Throwable{
	}

	/**
	 * 方法调用后执行
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param arguments
	 * @param result
	 * @throws Throwable
	 */
	protected void onAfter(Class<?> targetClass,Method targetMethod,Object[] arguments,Object result) throws Throwable{
	}

	/**
	 * 方法调用出现异常执行
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param arguments
	 * @param exception
	 */
	protected void onException(Class<?> targetClass,Method targetMethod,Object[] arguments,Throwable exception){
	}

}