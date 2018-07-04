package com.jcute.core.config.source;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jcute.core.config.ConfigSource;
import com.jcute.core.config.support.AbstractConfigSource;
import com.jcute.core.util.StringUtils;

public class DefaultConfigSourceForXml extends AbstractConfigSource{

	private Element element;

	public DefaultConfigSourceForXml(String fileName,Element element){
		super(fileName,ConfigSource.EXTENSION_XML);
		if(null == element){
			throw new IllegalArgumentException("xml element must not be null");
		}
		this.element = element;
	}

	@Override
	public boolean hasConfigValue(String configName){
		return this.getNodeValue(configName) != null;
	}

	@Override
	public String getConfigValue(String configName,String defaultValue){
		String result = this.getNodeValue(configName);
		if(null == result){
			return defaultValue;
		}
		return result;
	}

	private String getNodeValue(String configName){
		String[] names = null;
		if(configName.indexOf(".") != -1){
			names = configName.split("\\.");
		}else{
			names = new String[]{configName};
		}
		if(null == names || names.length == 0){
			return null;
		}
		NodeList nodeList = this.element.getChildNodes();
		if(null == nodeList || nodeList.getLength() == 0){
			return null;
		}
		for(int i = 0;i < names.length;i++){
			String name = names[i];
			if(StringUtils.isEmpty(name)){
				return null;
			}
			name = name.trim();
			for(int j = 0;j < nodeList.getLength();j++){
				Node node = nodeList.item(j);
				if(!node.getNodeName().equals(name)){
					continue;
				}
				nodeList = node.getChildNodes();
				if(i == names.length - 1){
					return node.getTextContent();
				}
			}
		}
		return null;
	}

}