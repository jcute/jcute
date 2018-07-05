package com.jcute.core.toolkit.scanner.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.toolkit.matcher.PathMatcher;
import com.jcute.core.toolkit.matcher.support.AntPathMatcher;
import com.jcute.core.toolkit.scanner.Scanner;
import com.jcute.core.toolkit.scanner.ScannerClassProcessor;
import com.jcute.core.toolkit.scanner.ScannerClassResult;
import com.jcute.core.toolkit.scanner.ScannerFilter;
import com.jcute.core.toolkit.scanner.ScannerResourceProcessor;
import com.jcute.core.toolkit.scanner.ScannerResourceResult;
import com.jcute.core.toolkit.scanner.ScannerResult;
import com.jcute.core.toolkit.scanner.ScannerResultType;

public abstract class AbstractScanner implements Scanner{

	private static final Logger logger = LoggerFactory.getLogger(AbstractScanner.class);
	private static final Map<String,Class<?>> classCaches = new ConcurrentHashMap<String,Class<?>>();
	
	public static final String SEPARATOR = ".";
	public static final String PROTOCOL_FILE = "file";
	public static final String PROTOCOL_JARF = "jar";
	public static final String CLASS_SUFFIX = ".class";
	
	private boolean verbos = false;
	private ClassLoader classLoader;
	private PathMatcher pathMatcher;
	private Set<String> classPatterns;
	private Set<String> resourcePatterns;
	private Set<ScannerFilter> scannerFilters;
	private Set<ScannerClassProcessor> classProcessors;
	private Set<ScannerResourceProcessor> resourceProcessors;

	public AbstractScanner(ClassLoader classLoader){
		this.classLoader = null == classLoader ? AbstractScanner.class.getClassLoader() : classLoader;
		this.pathMatcher = new AntPathMatcher(SEPARATOR);
		this.classPatterns = new LinkedHashSet<String>();
		this.resourcePatterns = new LinkedHashSet<String>();
		this.scannerFilters = new LinkedHashSet<ScannerFilter>();
		this.classProcessors = new LinkedHashSet<ScannerClassProcessor>();
		this.resourceProcessors = new LinkedHashSet<ScannerResourceProcessor>();
	}
	
	@Override
	public void setVerbos(boolean verbos){
		this.verbos = verbos;
	}

	@Override
	public boolean isVerbos(){
		return this.verbos;
	}
	
	@Override
	public void setPathMatcher(PathMatcher pathMatcher){
		if(null == pathMatcher){
			logger.warn("ignore path matcher , path matcher is null");
			return;
		}
		this.pathMatcher = pathMatcher;
	}

	@Override
	public PathMatcher getPathMatcher(){
		return this.pathMatcher;
	}

	@Override
	public void setClassLoader(ClassLoader classLoader){
		if(null == classLoader){
			return;
		}
		this.classLoader = classLoader;
	}

	@Override
	public ClassLoader getClassLoader(){
		return this.classLoader;
	}

	@Override
	public void attachClassPattern(String classPattern){
		if(null == classPattern){
			logger.warn("ignore attach class pattern , class pattern is null");
			return;
		}
		this.classPatterns.add(this.formatClassPattern(classPattern));
	}

	@Override
	public void detachClassPattern(String classPattern){
		if(null == classPattern){
			logger.warn("ignore detach class pattern , class pattern is null");
			return;
		}
		this.classPatterns.remove(this.formatClassPattern(classPattern));
	}

	@Override
	public void attachResourcePattern(String resourcePattern){
		if(null == resourcePattern){
			logger.warn("ignore attach resource pattern , resource pattern is null");
			return;
		}
		this.resourcePatterns.add(this.formatResourcePattern(resourcePattern));
	}

	@Override
	public void detachResourcePattern(String resourcePattern){
		if(null == resourcePattern){
			logger.warn("ignore detach resource pattern , resource pattern is null");
			return;
		}
		this.resourcePatterns.remove(this.formatResourcePattern(resourcePattern));
	}

	@Override
	public void attachScannerFilter(ScannerFilter scannerFilter){
		if(null == scannerFilter){
			logger.warn("ignore attach scanner filter , scanner filter is null");
			return;
		}
		this.scannerFilters.add(scannerFilter);
	}

	@Override
	public void detachScannerFilter(ScannerFilter scannerFilter){
		if(null == scannerFilter){
			logger.warn("ignore detach scanner filter , scanner filter is null");
			return;
		}
		this.scannerFilters.remove(scannerFilter);
	}

	@Override
	public void attachScannerClassProcessor(ScannerClassProcessor classProcessor){
		if(null == classProcessor){
			logger.warn("ignore attach class processor , class processor is null");
			return;
		}
		this.classProcessors.add(classProcessor);
	}

	@Override
	public void detachScannerClassProcessor(ScannerClassProcessor classProcessor){
		if(null == classProcessor){
			logger.warn("ignore detach class processor , class processor is null");
			return;
		}
		this.classProcessors.remove(classProcessor);
	}

	@Override
	public void attachScannerResourceProcessor(ScannerResourceProcessor resourceProcessor){
		if(null == resourceProcessor){
			logger.warn("ignore attach resource processor , resource processor is null");
			return;
		}
		this.resourceProcessors.add(resourceProcessor);
	}

	@Override
	public void detachScannerResourceProcessor(ScannerResourceProcessor resourceProcessor){
		if(null == resourceProcessor){
			logger.warn("ignore detach resource processor , resource processor is null");
			return;
		}
		this.resourceProcessors.remove(resourceProcessor);
	}

	@Override
	public Set<ScannerResult> scan(){
		Set<ScannerResult> result = new LinkedHashSet<ScannerResult>();
		Set<String> scannerClassPackages = this.resolveScannerClassPackages();
		Set<String> scannerResourcePackages = this.resolveScannerResourcePackages();
		Set<ScannerResult> target = this.doScan(scannerClassPackages,scannerResourcePackages);
		if(null != target){
			result.addAll(target);
		}
		return result;
	}
	
	protected String formatResourcePattern(String resourcePattern){
		if(resourcePattern.startsWith("/")){
			resourcePattern = resourcePattern.substring(1);
		}
		return resourcePattern;
	}
	
	protected String formatClassPattern(String classPattern){
		if(this.pathMatcher.isPattern(classPattern)){
			return classPattern;
		}
		if(classPattern.endsWith(SEPARATOR)){
			return classPattern + "**";
		}else{
			return classPattern + SEPARATOR + "**";
		}
	}

	protected boolean isFilterPass(String path,ScannerResultType type){
		if(null == this.scannerFilters || this.scannerFilters.size() == 0){
			return true;
		}
		Set<String> scannerPatterns = type == ScannerResultType.Class ? this.classPatterns : this.resourcePatterns;
		for(ScannerFilter filter : this.scannerFilters){
			if(filter.execute(path,type,scannerPatterns)){
				return true;
			}
		}
		return false;
	}
	
	protected boolean isProcessorClassPass(ScannerClassResult scannerClassResult){
		if(null == this.classProcessors || this.classProcessors.size() == 0){
			return true;
		}
		for(ScannerClassProcessor processor : this.classProcessors){
			if(processor.execute(scannerClassResult)){
				return true;
			}
		}
		return false;
	}

	protected boolean isProcessorResourcePass(ScannerResourceResult scannerResourceResult){
		if(null == this.resourceProcessors || this.resourceProcessors.size() == 0){
			return true;
		}
		for(ScannerResourceProcessor processor : this.resourceProcessors){
			if(processor.execute(scannerResourceResult)){
				return true;
			}
		}
		return false;
	}
	
	protected Set<String> resolveScannerClassPackages(){
		Set<String> result = new LinkedHashSet<String>();
		List<String> list = new ArrayList<String>();
		if(null != this.classPatterns && this.classPatterns.size() > 0){
			for(String pattern : this.classPatterns){
				String newPattern = pattern;
				if(!this.pathMatcher.isPattern(newPattern)){
					if(newPattern.endsWith(SEPARATOR)){
						newPattern += "**";
					}else{
						newPattern += SEPARATOR + "**";
					}
				}
				int index = -1;
				if((index = newPattern.indexOf("*")) != -1){
					newPattern = newPattern.substring(0,index);
				}
				if((index = newPattern.indexOf("?")) != -1){
					newPattern = newPattern.substring(0,index);
				}
				if((index = newPattern.indexOf("{")) != -1){
					newPattern = newPattern.substring(0,index);
				}
				if((index = newPattern.indexOf("}")) != -1){
					newPattern = newPattern.substring(0,index);
				}
				if(newPattern.indexOf(SEPARATOR) != -1){
					newPattern = newPattern.substring(0,newPattern.lastIndexOf(SEPARATOR));
				}
				if(!list.contains(newPattern)){
					list.add(newPattern);
					if(this.isVerbos()){
						logger.debug("found class pattern {}",newPattern);
					}
				}
			}
		}
		Collections.sort(list);
		result.addAll(list);
		return result;
	}

	protected Set<String> resolveScannerResourcePackages(){
		Set<String> result = new LinkedHashSet<String>();
		List<String> list = new ArrayList<String>();
		if(null != this.resourcePatterns && this.resourcePatterns.size() > 0){
			for(String pattern : this.resourcePatterns){
				String newPattern = pattern;
				if(newPattern.indexOf("/") != -1){
					newPattern = newPattern.substring(0,newPattern.lastIndexOf("/"));
				}else{
					newPattern = "";
				}
				if(!list.contains(newPattern)){
					list.add(newPattern);
					if(this.isVerbos()){
						logger.debug("found resource pattern {}",newPattern);
					}
				}
			}
		}
		Collections.sort(list);
		result.addAll(list);
		return result;
	}

	protected Class<?> classForName(String className){
		try{
			Class<?> targetClass = classCaches.get(className);
			if(null == targetClass){
				classCaches.put(className,Class.forName(className));
				targetClass = classCaches.get(className);
			}
			return targetClass;
		}catch(ClassNotFoundException e){
			return null;
		}catch(Throwable t){
			logger.warn("{} rely on {}",className,t.getMessage(),t);
			return null;
		}
	}

	protected abstract Set<ScannerResult> doScan(Set<String> scannerClassPackages,Set<String> scannerResourcePackages);

}