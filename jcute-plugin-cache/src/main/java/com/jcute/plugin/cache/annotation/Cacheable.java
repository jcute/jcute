package com.jcute.plugin.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Cacheable{

	// 默认的缓存对象名称
	public String value() default "";

	// 默认的cacheManager名称
	public String cacheManager() default "";

}