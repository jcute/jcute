package com.jcute.core.toolkit.scanner.support;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.toolkit.matcher.PathMatcher;
import com.jcute.core.toolkit.scanner.ScannerClassResult;
import com.jcute.core.toolkit.scanner.ScannerFilter;
import com.jcute.core.toolkit.scanner.ScannerResourceResult;
import com.jcute.core.toolkit.scanner.ScannerResult;
import com.jcute.core.toolkit.scanner.ScannerResultType;
import com.jcute.core.util.StringUtils;

public class DefaultScanner extends AbstractScanner implements ScannerFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultScanner.class);
	
	public DefaultScanner(){
		super(DefaultScanner.class.getClassLoader());
		this.attachScannerFilter(this);
	}

	@Override
	protected Set<ScannerResult> doScan(Set<String> scannerClassPackages,Set<String> scannerResourcePackages){
		Set<ScannerResult> result = new LinkedHashSet<ScannerResult>();
		this.doScanClassPackages(scannerClassPackages,result);
		this.doScanResourcePackages(scannerResourcePackages,result);
		return result;
	}
	
	@Override
	public boolean execute(String path,ScannerResultType type,Set<String> scannerPatterns){
		PathMatcher pathMatcher = this.getPathMatcher();
		for(String pattern : scannerPatterns){
			if(type == ScannerResultType.Class){
				if(pathMatcher.match(pattern,path)){
					return true;
				}
			}else if(type == ScannerResultType.Resource){
				if(pattern.equals(path) || pathMatcher.match(pattern,path)){
					return true;
				}
			}
		}
		return false;
	}
	
	protected void doScanClassPackages(Set<String> scannerClassPackages,Set<ScannerResult> sets){
		if(null == scannerClassPackages || scannerClassPackages.size() == 0){
			return;
		}
		this.doScannerPackages(scannerClassPackages,ScannerResultType.Class,sets);
	}

	protected void doScanResourcePackages(Set<String> scannerResourcePackages,Set<ScannerResult> sets){
		if(null == scannerResourcePackages || scannerResourcePackages.size() == 0){
			return;
		}
		this.doScannerPackages(scannerResourcePackages,ScannerResultType.Resource,sets);
	}
	
	protected void doScannerPackages(Set<String> scannerPackages,ScannerResultType resultType,Set<ScannerResult> sets){
		ClassLoader classLoader = this.getClassLoader();
		for(String packageName : scannerPackages){
			try{
				Enumeration<URL> urls = classLoader.getResources(packageName.replace(".","/"));
				while(urls.hasMoreElements()){
					URL url = urls.nextElement();
					String protocol = url.getProtocol();
					if(PROTOCOL_FILE.equals(protocol)){
						if(resultType == ScannerResultType.Class){
							this.findClassByDirs(packageName,url.getPath(),sets);
						}else if(resultType == ScannerResultType.Resource){
							this.findResourceByDirs(packageName,url.getPath(),sets);
						}
					}else if(PROTOCOL_JARF.equals(protocol)){
						JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
						if(resultType == ScannerResultType.Class){
							this.findClassByJars(packageName,jarURLConnection.getJarFile(),sets);
						}else if(resultType == ScannerResultType.Resource){
							this.findResourceByJars(packageName,jarURLConnection.getJarFile(),sets);
						}
					}
				}
			}catch(IOException e){
				logger.warn(e.getMessage(),e);
			}
		}
	}
	
	protected void findResourceByDirs(String packageName,String parentPath,Set<ScannerResult> sets){
		File parent = new File(parentPath);
		if(!parent.exists() || !parent.isDirectory()){
			return;
		}
		File[] files = parent.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname){
				return pathname.isDirectory() || (!pathname.getName().endsWith(CLASS_SUFFIX));
			}
		});
		if(null == files || files.length == 0){
			return;
		}
		String currentPackageName = StringUtils.isEmpty(packageName) ? "" : packageName + "/";
		for(int i=0;i<files.length;i++){
			File file = files[i];
			if(file.isDirectory()){
				this.findResourceByDirs(String.format("%s%s",currentPackageName,file.getName()),file.getPath(),sets);
			}else{
				String currentResourceName = String.format("%s%s",currentPackageName,file.getName());
				if(!this.isFilterPass(currentResourceName,ScannerResultType.Resource)){
					continue;
				}
				try{
					URL url = file.toURI().toURL();
					String name = file.getName();
					ScannerResourceResult scannerResourceResult = this.createScannerResourceResult(url,name);
					if(!this.isProcessorResourcePass(scannerResourceResult)){
						continue;
					}
					sets.add(scannerResourceResult);
					if(this.isVerbos()){
						logger.debug("found {}",scannerResourceResult);
					}
				}catch(MalformedURLException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	protected void findClassByDirs(String packageName,String parentPath,Set<ScannerResult> sets){
		File parent = new File(parentPath);
		if(!parent.exists() || !parent.isDirectory()){
			return;
		}
		File[] files = parent.listFiles(new FileFilter(){
			@Override
			public boolean accept(File pathname){
				return pathname.isDirectory() || pathname.getName().endsWith(CLASS_SUFFIX);
			}
		});
		if(null == files || files.length == 0){
			return;
		}
		String currentPackageName = StringUtils.isEmpty(packageName) ? "" : packageName + ".";
		for(int i=0;i<files.length;i++){
			File file = files[i];
			if(file.isDirectory()){
				this.findClassByDirs(String.format("%s%s",currentPackageName,file.getName()),file.getPath(),sets);
			}else{
				String currentClassName = file.getName();
				currentClassName = currentClassName.substring(0,currentClassName.length() - CLASS_SUFFIX.length());
				currentClassName = String.format("%s%s",currentPackageName,currentClassName);
				if(!this.isFilterPass(currentClassName,ScannerResultType.Class)){
					continue;
				}
				Class<?> currentClass = this.classForName(currentClassName);
				if(null == currentClass){
					continue;
				}
				try{
					ScannerClassResult scannerClassResult = this.createScannerClassResult(file.toURI().toURL(),currentClass);
					if(!this.isProcessorClassPass(scannerClassResult)){
						continue;
					}
					sets.add(scannerClassResult);
					if(this.isVerbos()){
						logger.debug("found {}",scannerClassResult);
					}
				}catch(MalformedURLException e){
					logger.warn(e.getMessage(),e);
				}
			}
		}
	}
	
	protected void findResourceByJars(String packageName,JarFile jarFile,Set<ScannerResult> sets){
		Enumeration<JarEntry> entries = jarFile.entries();
		while(entries.hasMoreElements()){
			JarEntry entry = entries.nextElement();
			String entryName = entry.getName();
			if(entryName.endsWith(CLASS_SUFFIX)){
				continue;
			}
			if(!this.isFilterPass(entryName,ScannerResultType.Resource)){
				continue;
			}
			try{
				String urlPath = String.format("jar:file:/%s!/%s",jarFile.getName().replace("\\","/"),entryName);
				URL url = new URL(urlPath);
				String name = entryName.substring(entryName.lastIndexOf("/")+1);
				ScannerResourceResult scannerResourceResult = this.createScannerResourceResult(url,name);
				if(!this.isProcessorResourcePass(scannerResourceResult)){
					continue;
				}
				sets.add(scannerResourceResult);
				if(this.isVerbos()){
					logger.debug("found {}",scannerResourceResult);
				}
			}catch(MalformedURLException e){
				logger.warn(e.getMessage(),e);
			}
		}
	}
	
	protected void findClassByJars(String packageName,JarFile jarFile,Set<ScannerResult> sets){
		Enumeration<JarEntry> entries = jarFile.entries();
		while(entries.hasMoreElements()){
			JarEntry entry = entries.nextElement();
			String entryName = entry.getName();
			if(!entryName.endsWith(CLASS_SUFFIX)){
				continue;
			}
			String currentClassName = entryName.replace('/','.');
			currentClassName = currentClassName.substring(0,currentClassName.length() - CLASS_SUFFIX.length());
			if(!this.isFilterPass(currentClassName,ScannerResultType.Class)){
				continue;
			}
			Class<?> currentClass = this.classForName(currentClassName);
			if(null == currentClass){
				continue;
			}
			String urlPath = String.format("jar:file:/%s!/%s.class",jarFile.getName().replace("\\","/"),currentClass.getName().replace(".","/"));
			try{
				URL url = new URL(urlPath);
				ScannerClassResult scannerClassResult = this.createScannerClassResult(url,currentClass);
				if(!this.isProcessorClassPass(scannerClassResult)){
					continue;
				}
				sets.add(scannerClassResult);
				if(this.isVerbos()){
					logger.debug("found {}",scannerClassResult);
				}
			}catch(MalformedURLException e){
				logger.warn(e.getMessage(),e);
			}
		}
	}
	
	protected ScannerResourceResult createScannerResourceResult(URL url,String name){
		return new DefaultScannerResourceResult(url,name);
	}
	
	protected ScannerClassResult createScannerClassResult(URL url,Class<?> currentClass){
		return new DefaultScannerClassResult(url,currentClass);
	}
	
}