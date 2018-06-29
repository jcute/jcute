package com.jcute.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jcute.core.bean.BeanDefinition;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface Component{
	
	public String value() default "";
	
	public String scope() default BeanDefinition.BEAN_SCOPE_SINGLETON;
	
}