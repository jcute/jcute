package com.jcute.plugin.cache.support;

import java.util.Map;
import java.util.Map.Entry;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.jcute.core.util.StringUtils;

public class DefaultCacheKeyPolicy extends AbstractCacheKeyPolicy{

	private static ScriptEngineManager manager = new ScriptEngineManager();
	private static ScriptEngine engine = manager.getEngineByName("js");

	@Override
	protected Object doGetCacheKey(Map<String,Object> context,String expression){
		try{
			if(StringUtils.isEmpty(expression)){
				expression = "this";
			}
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("var getCacheKey = function(");
			int index = 0;
			Object[] datas = new Object[context.size()];
			for(Entry<String,Object> entry : context.entrySet()){
				stringBuffer.append(entry.getKey());
				datas[index] = entry.getValue();
				if(++index != context.size()){
					stringBuffer.append(",");
				}
			}
			stringBuffer.append("){");
			stringBuffer.append("return").append(" ").append(expression).append(";");
			stringBuffer.append("}");
			engine.eval(stringBuffer.toString());
			Invocable invocable = (Invocable)engine;
			return invocable.invokeFunction("getCacheKey",datas);
		}catch(Exception e){
			return null;
		}
	}

}