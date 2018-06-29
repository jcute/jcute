package com.jcute.plugin.transaction;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jcute.core.annotation.Pluggable;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Pluggable(EnableTransactionManagerPlugin.class)
public @interface EnableTransactionManager{

	public String value() default "";

}