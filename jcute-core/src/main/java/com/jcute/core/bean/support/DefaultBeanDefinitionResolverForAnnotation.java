package com.jcute.core.bean.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jcute.core.annotation.Autowired;
import com.jcute.core.annotation.Component;
import com.jcute.core.annotation.Destory;
import com.jcute.core.annotation.Initial;
import com.jcute.core.annotation.Interceptor;
import com.jcute.core.annotation.Order;
import com.jcute.core.annotation.Property;
import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionRegistry;
import com.jcute.core.bean.exception.BeanDefinitionMultipleException;
import com.jcute.core.bean.exception.BeanDefinitionNotFoundException;
import com.jcute.core.bean.exception.BeanInstanceInjectException;
import com.jcute.core.config.ConfigValue;
import com.jcute.core.toolkit.graph.DirectedGraph;
import com.jcute.core.toolkit.graph.DirectedGraphUtil;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.toolkit.matcher.PathMatcher;
import com.jcute.core.toolkit.matcher.support.AntPathMatcher;
import com.jcute.core.toolkit.proxy.ProxyFactory;
import com.jcute.core.util.AnnotationUtils;
import com.jcute.core.util.ReflectionUtils;
import com.jcute.core.util.StringUtils;

public class DefaultBeanDefinitionResolverForAnnotation extends AbstractBeanDefinitionResolver{

	private static final Logger logger = LoggerFactory.getLogger(DefaultBeanDefinitionResolverForAnnotation.class);

	public DefaultBeanDefinitionResolverForAnnotation(BeanDefinitionFactory beanDefinitionFactory,BeanDefinitionRegistry beanDefinitionRegistry){
		super(beanDefinitionFactory,beanDefinitionRegistry);
	}

	@Override
	protected int sortMethod(Method methodA,Method methodB){
		int sortA = Integer.MAX_VALUE;
		int sortB = Integer.MAX_VALUE;
		Order orderA = AnnotationUtils.getAnnotation(methodA,Order.class);
		Order orderB = AnnotationUtils.getAnnotation(methodB,Order.class);
		if(null != orderA){
			sortA = orderA.value();
		}
		if(null != orderB){
			sortB = orderB.value();
		}
		return sortA - sortB;
	}

	@Override
	public String resolveBeanName(Class<?> beanType){
		String beanName = StringUtils.toCamelName(beanType.getSimpleName());
		if(AnnotationUtils.hasAnnotation(beanType,Component.class)){
			Component component = AnnotationUtils.getAnnotation(beanType,Component.class);
			if(StringUtils.hasText(component.value())){
				beanName = component.value();
			}
		}
		return beanName;
	}

	@Override
	public String resolveBeanName(Method method){
		String beanName = StringUtils.toCamelName(method.getName());
		if(AnnotationUtils.hasAnnotation(method,Component.class)){
			Component component = AnnotationUtils.getAnnotation(method,Component.class);
			if(StringUtils.hasText(component.value())){
				beanName = component.value();
			}
		}
		return beanName;
	}

	@Override
	public String resolveBeanScope(Class<?> beanType){
		String beanScope = BeanDefinition.BEAN_SCOPE_SINGLETON;
		if(AnnotationUtils.hasAnnotation(beanType,Component.class)){
			Component component = AnnotationUtils.getAnnotation(beanType,Component.class);
			if(StringUtils.hasText(component.scope())){
				beanScope = component.scope();
			}
		}
		return beanScope;
	}

	@Override
	public String resolveBeanScope(Method method){
		String beanScope = BeanDefinition.BEAN_SCOPE_SINGLETON;
		if(AnnotationUtils.hasAnnotation(method,Component.class)){
			Component component = AnnotationUtils.getAnnotation(method,Component.class);
			if(StringUtils.hasText(component.scope())){
				beanScope = component.scope();
			}
		}
		return beanScope;
	}

	@Override
	protected boolean isInitialMethod(Method method){
		return AnnotationUtils.hasAnnotation(method,Initial.class);
	}

	@Override
	protected boolean isDestoryMethod(Method method){
		return AnnotationUtils.hasAnnotation(method,Destory.class);
	}

	@Override
	protected Constructor<?> doResolveBeanConstructor(Constructor<?>[] constructors){
		Constructor<?> noArgumentsConstructor = null;
		List<Constructor<?>> resolveConstructors = new ArrayList<Constructor<?>>();
		for(int i = 0;i < constructors.length;i++){// 搜索无参构造函数
			Constructor<?> tempConstructor = constructors[i];
			if(tempConstructor.getParameterTypes() == null || tempConstructor.getParameterTypes().length == 0){
				noArgumentsConstructor = tempConstructor;
				break;
			}
		}
		for(int i = 0;i < constructors.length;i++){// 搜索含有@Initial注解的构造函数
			Constructor<?> tempConstructor = constructors[i];
			if(AnnotationUtils.hasAnnotation(tempConstructor,Initial.class)){
				resolveConstructors.add(tempConstructor);
			}
		}
		if(resolveConstructors.size() == 0){// 如果没有@Initial注解的构造函数
			if(null == noArgumentsConstructor){// 且没有无参构造函数,返回构造函数数组中的第一个
				return constructors[0];
			}else{
				return noArgumentsConstructor;// 返回无参构造函数
			}
		}else if(resolveConstructors.size() == 1){// 如果只搜索到一个含有@Initial注解的构造函数,则使用含有@Initial注解的构造函数
			return resolveConstructors.get(0);
		}else{// 如果搜索到多个@Initial注解的构造函数,默认返回参数中含有@Autowired注解的第一个构造函数
			for(Constructor<?> tempConstructor : resolveConstructors){
				if(AnnotationUtils.hasAnnotation(tempConstructor,Autowired.class)){
					return tempConstructor;
				}
			}
			return resolveConstructors.get(0);// 如果参数都没有含有@Autowired注解的,则返回第一个含有@Initial注解的构造函数
		}
	}

	@Override
	public Set<BeanDefinition> getSortBeanDefinitions(Map<String,BeanDefinition> beanDefinitions) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		DirectedGraph<BeanDefinition> directedGraph = new DirectedGraph<BeanDefinition>();
		DirectedGraphUtil<BeanDefinition> directedGraphUtil = new DirectedGraphUtil<BeanDefinition>(directedGraph);
		for(Entry<String,BeanDefinition> entry : beanDefinitions.entrySet()){
			this.resolveBeanReference(entry.getValue(),directedGraph);
		}
		return directedGraphUtil.getSort();
	}

	@Override
	protected boolean isAutowiredBean(Field field){
		if(null != field && AnnotationUtils.hasAnnotation(field,Autowired.class)){// 必须包含@Autowired注解
			if(!field.getType().isPrimitive()){// field的类型不为基本数据类型
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean isAutowiredBean(Method method){
		if(null != method && AnnotationUtils.hasAnnotation(method,Autowired.class)){// 必须包含@Autowired注解
			Class<?>[] parameterTypes = method.getParameterTypes();
			if(null != parameterTypes && parameterTypes.length > 0){// 参数必须大于0
				if(method.getReturnType() != Void.class && (!method.getReturnType().isPrimitive())){// 返回值不为void 且 返回类型不为基本数据类型
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected boolean isAutowiredProperty(Field field){
		if(null != field && AnnotationUtils.hasAnnotation(field,Property.class)){// 必须包含@Property注解
			if(field.getType().isPrimitive()){// field的类型为基本数据类型
				return true;
			}
		}
		return false;
	}

	@Override
	public void injectField(Object beanInstance) throws BeanInstanceInjectException{
		Field[] fields = ReflectionUtils.getDeclaredFields(beanInstance.getClass());
		for(Field field : fields){
			if(AnnotationUtils.hasAnnotation(field,Autowired.class)){
				Autowired autowired = AnnotationUtils.getAnnotation(field,Autowired.class);
				String beanName = autowired.value();
				try{
					BeanDefinition beanDefinition = this.beanDefinitionFactory.getBeanDefinition(field.getType(),beanName);
					Object injectInstance = beanDefinition.getBeanInstance();
					ReflectionUtils.makeAccessible(field);
					field.set(beanInstance,injectInstance);
				}catch(BeanDefinitionNotFoundException e){
					String msg = String.format("bean definition not found %s#%s",field.getType().getName(),beanName);
					logger.error(msg,e);
					throw new BeanInstanceInjectException(msg,e);
				}catch(BeanDefinitionMultipleException ee){
					String msg = String.format("bean definition multiple %s , please specify the bean name",field.getType().getName());
					logger.error(msg,ee);
					throw new BeanInstanceInjectException(msg,ee);
				}catch(Exception e){
					String msg = String.format("inject bean field error %s.%s",field.getType().getName(),field.getName());
					logger.error(msg,e);
					throw new BeanInstanceInjectException(msg,e);
				}
			}
			if(AnnotationUtils.hasAnnotation(field,Property.class)){
				Property property = AnnotationUtils.getAnnotation(field,Property.class);
				if(!StringUtils.isEmpty(property.value())){
					String prefix = "";
					if(AnnotationUtils.hasAnnotation(beanInstance.getClass(),Property.class)){
						Property classProperty = AnnotationUtils.getAnnotation(beanInstance.getClass(),Property.class);
						if(!StringUtils.isEmpty(classProperty.prefix())){
							prefix = classProperty.prefix();
						}
					}
					if(!StringUtils.isEmpty(property.prefix())){
						if(StringUtils.isEmpty(prefix)){
							prefix = property.prefix();
						}else{
							if(prefix.endsWith(".")){
								prefix += property.prefix();
							}else{
								prefix += "." + property.prefix();
							}
						}
					}
					try{
						String configName = String.format("%s(%s)",property.value(),prefix);
						ConfigValue<?> configValue = this.beanDefinitionFactory.getConfigSourceManager().getValue(field.getType(),configName);
						ReflectionUtils.makeAccessible(field);
						field.set(beanInstance,configValue.getValue());
					}catch(Exception e){
						String msg = String.format("inject bean field error %s.%s",field.getType().getName(),field.getName());
						logger.error(msg,e);
						throw new BeanInstanceInjectException(msg,e);
					}
				}
			}
		}
	}

	@Override
	public void inejctMethod(Object beanInstance) throws BeanInstanceInjectException{
		Method[] methods = ReflectionUtils.getDeclaredMethods(beanInstance.getClass());
		for(Method method : methods){

			if(AnnotationUtils.hasAnnotation(method,Autowired.class) || AnnotationUtils.hasAnnotation(method.getParameterAnnotations(),Autowired.class) || AnnotationUtils.hasAnnotation(method,Property.class)
					|| AnnotationUtils.hasAnnotation(method.getParameterAnnotations(),Property.class)){
				Object[] parameterDatas = this.getArguments(method);
				ReflectionUtils.makeAccessible(method);
				ReflectionUtils.invokeMethod(method,beanInstance,parameterDatas);
			}

		}
	}
	
	@Override
	protected Object[] doGetArguments(Class<?> declaringClass,AccessibleObject accessibleObject,Class<?>[] parameterTypes,Annotation[][] annotations){
		if(parameterTypes.length == 0){
			return new Object[0];
		}
		if(AnnotationUtils.hasAnnotation(accessibleObject,Autowired.class) || AnnotationUtils.hasAnnotation(annotations,Autowired.class)){
			Autowired parentAutowired = AnnotationUtils.getAnnotation(accessibleObject,Autowired.class);
			Annotation[] autowireds = AnnotationUtils.getAnnotation(parameterTypes,annotations,Autowired.class);
			if(parameterTypes.length == 1){
				try{
					String beanName = null;
					if(null != parentAutowired){
						beanName = parentAutowired.value();
					}
					if(null != autowireds[0]){
						Autowired autowired = (Autowired)autowireds[0];
						if(StringUtils.isEmpty(autowired.value())){
							beanName = autowired.value();
						}
					}
					BeanDefinition beanDefinition = this.beanDefinitionFactory.getBeanDefinition(parameterTypes[0],beanName);
					return new Object[]{beanDefinition.getBeanInstance()};
				}catch(Exception e){
					logger.warn(e.getMessage(),e);
				}
			}else{
				Object[] result = new Object[parameterTypes.length];
				for(int i = 0;i < autowireds.length;i++){
					if(null == autowireds[i]){
						result[i] = null;
						continue;
					}
					try{
						Autowired autowired = (Autowired)autowireds[i];
						String beanName = autowired.value();
						BeanDefinition beanDefinition = this.beanDefinitionFactory.getBeanDefinition(parameterTypes[0],beanName);
						result[i] = beanDefinition.getBeanInstance();
					}catch(Exception e){
						logger.warn(e.getMessage(),e);
					}
				}
				return result;
			}
		}
		if(AnnotationUtils.hasAnnotation(accessibleObject,Property.class) || AnnotationUtils.hasAnnotation(annotations,Property.class)){
			String prefix = "";
			Annotation[] properties = AnnotationUtils.getAnnotation(parameterTypes,annotations,Property.class);
			if(AnnotationUtils.hasAnnotation(declaringClass,Property.class)){
				Property property = AnnotationUtils.getAnnotation(declaringClass,Property.class);
				if(!StringUtils.isEmpty(property.prefix())){
					prefix += property.prefix();
				}
			}
			if(AnnotationUtils.hasAnnotation(accessibleObject,Property.class)){
				Property property = AnnotationUtils.getAnnotation(accessibleObject,Property.class);
				if(!StringUtils.isEmpty(property.prefix())){
					if(prefix.endsWith(".")){
						prefix += property.prefix();
					}else{
						prefix += "." + property.prefix();
					}
				}
			}
			if(parameterTypes.length == 1){
				Property property = (Property)properties[0];
				if(null == property){
					return new Object[]{null};
				}
				if(StringUtils.isEmpty(property.value())){
					return new Object[]{null};
				}
				String configName = String.format("%s(%s)",property.value(),prefix);
				ConfigValue<?> configValue = this.beanDefinitionFactory.getConfigSourceManager().getValue(parameterTypes[0],configName);
				return new Object[]{configValue.getValue()};
			}else{
				Object[] result = new Object[parameterTypes.length];
				for(int i = 0;i < properties.length;i++){
					if(null == properties[i]){
						result[i] = null;
						continue;
					}
					Property property = (Property)properties[i];
					if(StringUtils.isEmpty(property.value())){
						result[i] = null;
						continue;
					}
					String configName = String.format("%s(%s)",property.value(),prefix);
					ConfigValue<?> configValue = this.beanDefinitionFactory.getConfigSourceManager().getValue(parameterTypes[0],configName);
					result[i] = configValue.getValue();
				}
				return result;
			}
		}
		return new Object[parameterTypes.length];
	}

	@Override
	public Set<BeanDefinition> getBeanDefinitionProxy(Class<?> beanType){
		Set<BeanDefinition> beanDefinitions = new LinkedHashSet<BeanDefinition>();
		for(Entry<String,BeanDefinition> entry : this.beanDefinitionRegistry.getAllBeanDefinitions().entrySet()){
			BeanDefinition beanDefinition = entry.getValue();
			if(this.isTargetInterceptor(beanType,beanDefinition)){
				beanDefinitions.add(beanDefinition);
			}
		}
		return beanDefinitions;
	}

	private boolean isTargetInterceptor(Class<?> targetClass,BeanDefinition sourceBeanDefinition){
		Class<?> beanType = sourceBeanDefinition.getBeanType();
		if(beanType.isAssignableFrom(targetClass)){
			return false;
		}
		if(!AnnotationUtils.hasAnnotation(beanType,Interceptor.class)){
			return false;
		}
		if(!sourceBeanDefinition.isAssignable(ProxyFactory.class)){
			return false;
		}
		Interceptor interceptor = AnnotationUtils.getAnnotation(beanType,Interceptor.class);
		if(null != interceptor.annotations() && interceptor.annotations().length > 0){
			for(Class<? extends Annotation> annotation : interceptor.annotations()){
				if(AnnotationUtils.hasAnnotation(targetClass,annotation)){
					return true;
				}
			}
		}
		if(null != interceptor.classes() && interceptor.classes().length > 0){
			for(Class<?> clazz : interceptor.classes()){
				if(clazz.isAssignableFrom(targetClass)){
					return true;
				}
			}
		}
		PathMatcher pathMatcher = new AntPathMatcher(".");
		if(null != interceptor.value() && interceptor.value().length > 0){
			for(String pattern : interceptor.value()){
				if(StringUtils.isEmpty(pattern)){
					continue;
				}
				if(pathMatcher.isPattern(pattern)){
					if(pathMatcher.match(pattern,targetClass.getName())){
						return true;
					}
				}else{
					if(pattern.equals(targetClass.getName())){
						return true;
					}
				}
			}
		}

		return false;
	}

	private void resolveBeanReference(BeanDefinition beanDefinition,DirectedGraph<BeanDefinition> directedGraph) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		directedGraph.addNode(beanDefinition);
		if(beanDefinition instanceof DefaultBeanDefinitionForClass){
			Class<?> beanType = beanDefinition.getBeanType();
			Constructor<?> beanConstructor = this.resolveBeanConstructor(beanType);
			Class<?>[] parameterTypes = beanConstructor.getParameterTypes();
			if(null != parameterTypes && parameterTypes.length > 0){
				Annotation[] annotations = AnnotationUtils.getAnnotation(parameterTypes,beanConstructor.getParameterAnnotations(),Autowired.class);
				for(int i = 0;i < annotations.length;i++){
					if(null == annotations[i]){
						continue;
					}
					Class<?> parameterType = parameterTypes[i];
					Autowired autowired = (Autowired)annotations[i];
					String beanName = autowired.value();
					BeanDefinition dependencyBeanDefinition = this.beanDefinitionFactory.getBeanDefinition(parameterType,beanName);
					directedGraph.addEdge(beanDefinition,dependencyBeanDefinition);// 分析出构造函数需要的用的Bean依赖
				}
			}
		}else if(beanDefinition instanceof DefaultBeanDefinitionForMethod){
			DefaultBeanDefinitionForMethod beanDefinitionForMethod = (DefaultBeanDefinitionForMethod)beanDefinition;
			BeanDefinition parentBeanDefinition = beanDefinitionForMethod.getParentBeanDefinition();
			Method createBeanMethod = beanDefinitionForMethod.getCreateBeanMethod();
			directedGraph.addEdge(beanDefinition,parentBeanDefinition);
			Class<?>[] parameterTypes = createBeanMethod.getParameterTypes();
			if(null != parameterTypes && parameterTypes.length > 0){
				Annotation[] annotations = AnnotationUtils.getAnnotation(parameterTypes,createBeanMethod.getParameterAnnotations(),Autowired.class);
				for(int i = 0;i < annotations.length;i++){
					if(null == annotations[i]){
						continue;
					}
					Class<?> parameterType = parameterTypes[i];
					Autowired autowired = (Autowired)annotations[i];
					String beanName = autowired.value();
					BeanDefinition dependencyBeanDefinition = this.beanDefinitionFactory.getBeanDefinition(parameterType,beanName);
					directedGraph.addEdge(beanDefinition,dependencyBeanDefinition);// 分析出方法需要的用的Bean依赖
				}
			}
		}else if(beanDefinition instanceof DefaultBeanDefinitionForObject){
			// bean instance 由外部创建,无需分析依赖关系
		}
	}

}