package com.jcute.core.context.support;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jcute.core.annotation.Component;
import com.jcute.core.annotation.ComponentScan;
import com.jcute.core.annotation.Configuration;
import com.jcute.core.annotation.ImportResource;
import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.exception.BeanDefinitionExistsException;
import com.jcute.core.bean.support.DefaultBeanDefinitionFactory;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.plugin.Plugin;
import com.jcute.core.plugin.PluginManager;
import com.jcute.core.plugin.support.DefaultPluginManagerForAnnotation;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.toolkit.scanner.Scanner;
import com.jcute.core.toolkit.scanner.ScannerClassProcessor;
import com.jcute.core.toolkit.scanner.ScannerClassResult;
import com.jcute.core.toolkit.scanner.ScannerResourceResult;
import com.jcute.core.toolkit.scanner.ScannerResult;
import com.jcute.core.toolkit.scanner.support.DefaultScanner;
import com.jcute.core.util.AnnotationUtils;
import com.jcute.core.util.ReflectionUtils;
import com.jcute.core.util.StringUtils;

public class ApplicationContextForAnnotation extends AbstractApplicationContext{

	private static final Logger logger = LoggerFactory.getLogger(ApplicationContextForAnnotation.class);

	private Class<?> runner;
	private PluginManager pluginManager;

	public ApplicationContextForAnnotation(Class<?> runner){
		super();
		if(null == runner){
			throw new IllegalArgumentException("runner class must not be null");
		}
		this.runner = runner;
		this.pluginManager = new DefaultPluginManagerForAnnotation(this);
	}

	@Override
	protected BeanDefinitionFactory createBeanDefinitionFactory(){
		return new DefaultBeanDefinitionFactory();
	}

	@Override
	protected void beforeDoStart(BeanDefinitionFactory beanDefinitionFactory){
		String packageName = this.runner.getPackage() == null ? "" : this.runner.getPackage().getName();
		Set<String> packageNames = new LinkedHashSet<String>();
		packageNames.add(packageName);
		this.getTargetScannerPackages(packageName,packageNames);
		try{
			this.pluginManager.start();
		}catch(Exception e){
			logger.warn("start plugin manager failed {}",e.getMessage(),e);
		}
		Scanner scanner = new DefaultScanner();
		for(String pattern : packageNames){
			scanner.attachClassPattern(pattern);
		}
		scanner.attachScannerClassProcessor(new ScannerClassProcessor() {
			@Override
			public boolean execute(ScannerClassResult target){
				return AnnotationUtils.hasAnnotation(target.getTargetClass(),Component.class) || AnnotationUtils.hasAnnotation(target.getTargetClass(),Configuration.class);
			}
		});
		Set<ScannerResult> sets = scanner.scan();
		if(null != sets && sets.size() > 0){
			for(ScannerResult result : sets){
				if(result.isTargetClass()){
					try{
						ScannerClassResult scannerClassResult = (ScannerClassResult)result;
						BeanDefinition beanDefinition = beanDefinitionFactory.createBeanDefinition(scannerClassResult.getTargetClass());
						beanDefinitionFactory.getBeanDefinitionRegistry().attachBeanDefinition(beanDefinition);
					}catch(BeanDefinitionExistsException e){
						logger.warn(e.getMessage());
					}
				}
			}
		}
	}

	@Override
	protected void beforeDoClose(BeanDefinitionFactory beanDefinitionFactory){
		try{
			this.pluginManager.close();
		}catch(Exception e){
			logger.warn("close plugin manager failed {}",e.getMessage(),e);
		}
	}

	protected void getTargetScannerPackages(String parent,Set<String> packageNames){
		if(null == parent){
			return;
		}
		Scanner scanner = new DefaultScanner();
		scanner.attachClassPattern(parent);
		scanner.attachScannerClassProcessor(new ScannerClassProcessor() {
			@Override
			public boolean execute(ScannerClassResult target){
				return AnnotationUtils.hasAnnotation(target.getTargetClass(),Configuration.class);
			}
		});
		for(ScannerResult scannerResult : scanner.scan()){
			if(scannerResult.isTargetClass()){
				ScannerClassResult scannerClassResult = (ScannerClassResult)scannerResult;
				this.initPlugin(scannerClassResult);
				this.initComponentScan(scannerClassResult,packageNames);
				this.initConfigSource(scannerClassResult);
			}
		}
	}

	protected void initConfigSource(ScannerClassResult scannerClassResult){
		if(AnnotationUtils.hasAnnotation(scannerClassResult.getTargetClass(),ImportResource.class)){
			ImportResource importResource = AnnotationUtils.getAnnotation(scannerClassResult.getTargetClass(),ImportResource.class);
			String[] locations = importResource.value();
			if(null != locations && locations.length > 0){
				Scanner scanner = new DefaultScanner();
				for(int i = 0;i < locations.length;i++){
					scanner.attachResourcePattern(locations[i]);
				}
				Set<ScannerResult> results = scanner.scan();
				if(null != results && results.size() > 0){
					for(ScannerResult result : results){
						if(result.isTargetResource()){
							try{
								ScannerResourceResult scannerResourceResult = (ScannerResourceResult)result;
								String extension = scannerResourceResult.getTargetName();
								if(extension.indexOf(".") != -1){
									extension = extension.substring(extension.lastIndexOf(".") + 1);
								}
								this.getConfigSourceManager().attachConfigSource(scannerResourceResult.getTargetInputStream(),scannerResourceResult.getTargetName(),extension);
							}catch(IOException e){
								logger.warn("attach config source failed {}",e.getMessage(),e);
							}
						}
					}
				}
			}
		}
	}

	protected void initComponentScan(ScannerClassResult scannerClassResult,Set<String> packageNames){
		if(AnnotationUtils.hasAnnotation(scannerClassResult.getTargetClass(),ComponentScan.class)){
			ComponentScan componentScan = AnnotationUtils.getAnnotation(scannerClassResult.getTargetClass(),ComponentScan.class);
			String[] packages = componentScan.value();
			if(null != packages && packages.length > 0){
				for(String pck : packages){
					if(StringUtils.hasText(pck) && (!packageNames.contains(pck))){
						packageNames.add(pck);
						this.getTargetScannerPackages(pck,packageNames);
					}
				}
			}
		}
	}

	protected void initPlugin(ScannerClassResult scannerClassResult){
		Map<Class<? extends Plugin>,Annotation> plugins = this.pluginManager.resolvePlugins(scannerClassResult.getTargetClass());
		if(null != plugins && plugins.size() > 0){
			for(Entry<Class<? extends Plugin>,Annotation> entry : plugins.entrySet()){
				try{
					Constructor<?> constructor = entry.getKey().getConstructor(new Class<?>[]{ApplicationContext.class,Annotation.class});
					if(null != constructor){
						Plugin p = (Plugin)ReflectionUtils.invokeConstructor(constructor,new Object[]{this,entry.getValue()});
						this.pluginManager.attachPlugin(p);
					}
				}catch(Exception e){
					logger.warn("create plugin failed {}",e.getMessage(),e);
				}
			}
		}
	}

}