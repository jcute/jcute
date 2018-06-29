package com.jcute.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtils{

	public static boolean isEmpty(Object str){
		return (str == null || "".equals(str));
	}

	public static boolean hasText(CharSequence str){
		return (str != null && str.length() > 0 && containsText(str));
	}

	public static boolean hasText(String str){
		return (str != null && !str.isEmpty() && containsText(str));
	}

	public static boolean hasLength(CharSequence str){
		return (str != null && str.length() > 0);
	}

	public static boolean hasLength(String str){
		return (str != null && !str.isEmpty());
	}

	public static String[] toStringArray(Collection<String> collection){
		return collection.toArray(new String[0]);
	}
	
	public static String toCamelName(String value){
		if(isEmpty(value)){
			return value;
		}
		return value.substring(0,1).toLowerCase() + value.substring(1);
	}

	public static boolean containsText(CharSequence str){
		int strLen = str.length();
		for(int i = 0;i < strLen;i++){
			if(!Character.isWhitespace(str.charAt(i))){
				return true;
			}
		}
		return false;
	}

	public static String[] tokenizeToStringArray(String str,String delimiters,boolean trimTokens,boolean ignoreEmptyTokens){
		if(str == null){
			return new String[0];
		}
		StringTokenizer st = new StringTokenizer(str,delimiters);
		List<String> tokens = new ArrayList<String>();
		while(st.hasMoreTokens()){
			String token = st.nextToken();
			if(trimTokens){
				token = token.trim();
			}
			if(!ignoreEmptyTokens || token.length() > 0){
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}
}