package com.jcute.core.junit;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.jcute.core.annotation.Configuration;
import com.jcute.core.bean.exception.BeanDefinitionExistsException;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.context.support.ApplicationContextForAnnotation;
import com.jcute.core.util.AnnotationUtils;

public class JCuteRunner extends BlockJUnit4ClassRunner{

	private ApplicationContext applicationContext;

	public JCuteRunner(Class<?> klass) throws InitializationError{
		super(klass);
		if(AnnotationUtils.hasAnnotation(klass,RunWithBoot.class)){
			RunWithBoot boot = AnnotationUtils.getAnnotation(klass,RunWithBoot.class);
			if(null == boot.value()){
				throw new InitializationError(new Throwable(String.format("@RunWithBoot Missing runner class %s",klass.getName())));
			}
			try{
				this.applicationContext = new ApplicationContextForAnnotation(boot.value());
				this.applicationContext.getBeanDefinitionRegistry().attachBeanDefinition(this.applicationContext.getBeanDefinitionFactory().createBeanDefinition(klass));
			}catch(BeanDefinitionExistsException e){
				throw new InitializationError(e);
			}
		}else{
			if(!AnnotationUtils.hasAnnotation(klass,Configuration.class)){
				throw new InitializationError(new Throwable(String.format("Missing @Configuration annotation %s",klass.getName())));
			}
			this.applicationContext = new ApplicationContextForAnnotation(klass);
		}
	}

	@Override
	protected Object createTest() throws Exception{
		try{
			this.applicationContext.start();
			return this.applicationContext.getBean(this.getTestClass().getJavaClass());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	/**
	 * 测试结束后,释放applicationContext
	 */
	@Override
	protected Statement withAfterClasses(final Statement statement){
		return new Statement() {
			@Override
			public void evaluate() throws Throwable{
				if(null != statement){
					statement.evaluate();
				}
				if(null != applicationContext){
					applicationContext.close();
				}
			}
		};
	}

}