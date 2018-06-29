package com.jcute.core.bean;

import org.junit.Test;

import com.jcute.core.bean.support.DefaultBeanDefinitionFactory;

public class TestBeanDefinitionFactory{

	@Test
	public void testCreate() throws Exception{

		BeanDefinitionFactory beanDefinitionFactory = new DefaultBeanDefinitionFactory();
		beanDefinitionFactory.start();

		Thread.sleep(1000);

		beanDefinitionFactory.close();

	}

	@Test
	public void testFind() throws Exception{
		BeanDefinitionFactory beanDefinitionFactory = new DefaultBeanDefinitionFactory();
		beanDefinitionFactory.start();

		System.out.println(beanDefinitionFactory.getBeanDefinition(BeanDefinitionFactory.class).getBeanInstance());
		System.out.println(beanDefinitionFactory.getBeanDefinition(BeanDefinitionFactory.class).getBeanInstance());
		System.out.println(beanDefinitionFactory.getBeanDefinition(BeanDefinitionFactory.class).getBeanInstance());

		beanDefinitionFactory.close();
	}

	@Test
	public void testClass() throws Exception{

		BeanDefinitionFactory beanDefinitionFactory = new DefaultBeanDefinitionFactory();
		BeanDefinition beanDefinition = beanDefinitionFactory.createBeanDefinition(TestService.class);
		beanDefinitionFactory.getBeanDefinitionRegistry().attachBeanDefinition(beanDefinition);
		beanDefinitionFactory.start();

		TestService testService = (TestService)beanDefinitionFactory.getBeanDefinition(TestService.class).getBeanInstance();
		System.out.println(testService.getName());

		beanDefinitionFactory.close();

	}

	@Test
	public void testMethod() throws Exception{

		BeanDefinitionFactory beanDefinitionFactory = new DefaultBeanDefinitionFactory();
		BeanDefinition beanDefinition = beanDefinitionFactory.createBeanDefinition(TestService.class);
		BeanDefinition childBeanDefinition = beanDefinitionFactory.createBeanDefinition(beanDefinition,TestService.class.getMethod("childService"));

		beanDefinitionFactory.getBeanDefinitionRegistry().attachBeanDefinition(beanDefinition);
		beanDefinitionFactory.getBeanDefinitionRegistry().attachBeanDefinition(childBeanDefinition);
		beanDefinitionFactory.start();

		TestChildService testService = (TestChildService)beanDefinitionFactory.getBeanDefinition(TestChildService.class).getBeanInstance();
		System.out.println(testService.getAge());

		beanDefinitionFactory.close();

	}

}