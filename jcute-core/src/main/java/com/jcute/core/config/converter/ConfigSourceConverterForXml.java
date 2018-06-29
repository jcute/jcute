package com.jcute.core.config.converter;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jcute.core.config.ConfigSource;
import com.jcute.core.config.source.DefaultConfigSourceForXml;
import com.jcute.core.config.support.AbstractConfigSourceConverter;

public class ConfigSourceConverterForXml extends AbstractConfigSourceConverter{
	
	@Override
	public String getExtension(){
		return ConfigSource.EXTENSION_XML;
	}
	
	@Override
	protected ConfigSource doConvert(InputStream inputStream,String fileName)throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		Element element = document.getDocumentElement();
		return new DefaultConfigSourceForXml(fileName,element);
	}
	
}