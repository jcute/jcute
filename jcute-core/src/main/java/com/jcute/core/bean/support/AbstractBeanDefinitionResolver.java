package com.jcute.core.bean.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionRegistry;
import com.jcute.core.bean.BeanDefinitionResolver;
import com.jcute.core.util.ReflectionUtils;

public abstract class AbstractBeanDefinitionResolver implements BeanDefinitionResolver{

	protected BeanDefinitionFactory beanDefinitionFactory;
	protected BeanDefinitionRegistry beanDefinitionRegistry;

	public AbstractBeanDefinitionResolver(BeanDefinitionFactory beanDefinitionFactory,BeanDefinitionRegistry beanDefinitionRegistry){
		if(null == beanDefinitionFactory){
			throw new IllegalArgumentException("bean definition factory must not be null");
		}
		if(null == beanDefinitionRegistry){
			throw new IllegalArgumentException("bean definition registry must not be null");
		}
		this.beanDefinitionFactory = beanDefinitionFactory;
		this.beanDefinitionRegistry = beanDefinitionRegistry;
	}

	@Override
	public Object[] getArguments(Method method){
		return this.doGetArguments(method.getDeclaringClass(),method,method.getParameterTypes(),method.getParameterAnnotations());
	}

	@Override
	public Object[] getArguments(Constructor<?> constructor){
		return this.doGetArguments(constructor.getDeclaringClass(),constructor,constructor.getParameterTypes(),constructor.getParameterAnnotations());
	}

	@Override
	public Method[] getInitialMethod(Class<?> beanType){
		if(null == beanType){
			return new Method[0];
		}
		List<Method> result = new ArrayList<Method>();
		Method[] methods = ReflectionUtils.getDeclaredMethods(beanType);
		if(null != methods && methods.length > 0){
			for(int i = 0;i < methods.length;i++){
				Method method = methods[i];
				if(this.isInitialMethod(method)){
					result.add(method);
				}
			}
		}
		Collections.sort(result,new Comparator<Method>() {
			@Override
			public int compare(Method o1,Method o2){
				return sortMethod(o1,o2);
			}
		});
		return result.toArray(new Method[result.size()]);
	}

	@Override
	public Method[] getDestoryMethod(Class<?> beanType){
		if(null == beanType){
			return new Method[0];
		}
		List<Method> result = new ArrayList<Method>();
		Method[] methods = ReflectionUtils.getDeclaredMethods(beanType);
		if(null != methods && methods.length > 0){
			for(int i = 0;i < methods.length;i++){
				Method method = methods[i];
				if(this.isDestoryMethod(method)){
					result.add(method);
				}
			}
		}
		Collections.sort(result,new Comparator<Method>() {
			@Override
			public int compare(Method o1,Method o2){
				return sortMethod(o1,o2);
			}
		});
		return result.toArray(new Method[result.size()]);
	}

	@Override
	public Constructor<?> resolveBeanConstructor(Class<?> beanType){
		Constructor<?>[] constructors = beanType.getConstructors();
		if(null == constructors || constructors.length == 0){
			return null;
		}
		if(constructors.length == 1){
			return constructors[0];
		}
		return this.doResolveBeanConstructor(constructors);
	}

	@Override
	public BeanDefinitionFactory getBeanDefinitionFactory(){
		return this.beanDefinitionFactory;
	}

	@Override
	public BeanDefinitionRegistry getBeanDefinitionRegistry(){
		return this.beanDefinitionRegistry;
	}

	protected abstract int sortMethod(Method methodA,Method methodB);

	protected abstract boolean isInitialMethod(Method method);

	protected abstract boolean isDestoryMethod(Method method);

	protected abstract boolean isAutowiredBean(Field field);

	protected abstract boolean isAutowiredBean(Method method);

	protected abstract boolean isAutowiredProperty(Field field);

	protected abstract Object[] doGetArguments(Class<?> declaringClass,AccessibleObject accessibleObject,Class<?>[] parameterTypes,Annotation[][] annotations);
	
	protected abstract Constructor<?> doResolveBeanConstructor(Constructor<?>[] constructors);

}