package com.jcute.core.bean.support;

import java.lang.reflect.Method;

import com.jcute.core.annotation.Component;
import com.jcute.core.annotation.Configuration;
import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionRegistryEvent;
import com.jcute.core.bean.BeanDefinitionRegistryListener;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.util.AnnotationUtils;
import com.jcute.core.util.ReflectionUtils;
import com.jcute.core.util.StringUtils;

public class DefaultBeanDefinitionRegistry extends AbstractBeanDefinitionRegistry{

	private static final Logger logger = LoggerFactory.getLogger(DefaultBeanDefinitionRegistry.class);

	public DefaultBeanDefinitionRegistry(BeanDefinitionFactory beanDefinitionFactory){
		super(beanDefinitionFactory);
		this.attachBeanDefinitionAddSuccessListener(new BeanDefinitionRegistryListener() {
			@Override
			public void execute(BeanDefinitionRegistryEvent event) throws Exception{
				logger.debug("attach bean definition success {}",event.getBeanDefinition());
			}
		});
		this.attachBeanDefinitionAddSuccessListener(new BeanDefinitionRegistryListener() {
			@Override
			public void execute(BeanDefinitionRegistryEvent event) throws Exception{
				BeanDefinition parentBeanDefinition = event.getBeanDefinition();
				Class<?> parentBeanType = parentBeanDefinition.getBeanType();
				if(!AnnotationUtils.hasAnnotation(parentBeanType,Configuration.class)){
					return;
				}
				Method[] methods = ReflectionUtils.getDeclaredMethods(parentBeanType);
				if(null == methods || methods.length == 0){
					return;
				}
				for(Method method : methods){
					if(!AnnotationUtils.hasAnnotation(method,Component.class)){
						continue;
					}
					Component component = AnnotationUtils.getAnnotation(method,Component.class);
					String beanName = component.value();
					String beanScope = component.scope();
					if(StringUtils.isEmpty(beanName)){
						beanName = method.getName();
					}
					BeanDefinition childBeanDefinition = event.getBeanDefinitionFactory().createBeanDefinition(parentBeanDefinition,method,beanName,beanScope);
					event.getBeanDefinitionRegistry().attachBeanDefinition(childBeanDefinition);
				}
			}
		});
		this.attachBeanDefinitionDelSuccessListener(new BeanDefinitionRegistryListener() {
			@Override
			public void execute(BeanDefinitionRegistryEvent event) throws Exception{
				logger.debug("detach bean definition success {}",event.getBeanDefinition());
			}
		});
	}

}