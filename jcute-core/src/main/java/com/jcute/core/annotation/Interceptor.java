package com.jcute.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Interceptor{

	public String[] value() default {};
	
	public Class<?>[] classes() default {};

	public Class<? extends Annotation>[] annotations() default {};

}