package com.jcute.core.toolkit.scanner;

import java.util.Set;

import com.jcute.core.toolkit.matcher.PathMatcher;

/**
 * 资源扫描器
 */
public interface Scanner{
	
	public void setVerbos(boolean verbos);
	
	public boolean isVerbos();
	
	/**
	 * 设置路径匹配器
	 * @param pathMatcher
	 */
	public void setPathMatcher(PathMatcher pathMatcher);
	
	/**
	 * 获取路径匹配器
	 * @return
	 */
	public PathMatcher getPathMatcher();
	
	/**
	 * 设置classLoader
	 * 
	 * @param classLoader
	 */
	public void setClassLoader(ClassLoader classLoader);

	/**
	 * 获取classLoader
	 * 
	 * @return
	 */
	public ClassLoader getClassLoader();
	
	/**
	 * 添加类扫描匹配符
	 * @param classPattern
	 */
	public void attachClassPattern(String classPattern);

	/**
	 * 移除类扫描匹配符
	 * @param classPattern
	 */
	public void detachClassPattern(String classPattern);
	
	/**
	 * 添加资源扫描匹配符
	 * @param resourcePattern
	 */
	public void attachResourcePattern(String resourcePattern);

	/**
	 * 移除资源扫描匹配符
	 * @param resourcePattern
	 */
	public void detachResourcePattern(String resourcePattern);
	
	/**
	 * 添加路径过滤器
	 * @param scannerFilter
	 */
	public void attachScannerFilter(ScannerFilter scannerFilter);
	
	/**
	 * 移除路径过滤器
	 * @param scannerFilter
	 */
	public void detachScannerFilter(ScannerFilter scannerFilter);
	
	/**
	 * 添加类扫描后置处理器
	 * @param classProcessor
	 */
	public void attachScannerClassProcessor(ScannerClassProcessor classProcessor);
	
	/**
	 * 移除类扫描后置处理器
	 * @param classProcessor
	 */
	public void detachScannerClassProcessor(ScannerClassProcessor classProcessor);

	/**
	 * 添加资源扫描后置处理器
	 * @param resourceProcessor
	 */
	public void attachScannerResourceProcessor(ScannerResourceProcessor resourceProcessor);

	/**
	 * 移除资源扫描后置处理器
	 * @param resourceProcessor
	 */
	public void detachScannerResourceProcessor(ScannerResourceProcessor resourceProcessor);
	
	/**
	 * 执行扫描操作
	 * @return
	 */
	public Set<ScannerResult> scan();
	
}