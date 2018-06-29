package com.jcute.test.plugin.transaction;

import com.jcute.core.annotation.Component;
import com.jcute.core.annotation.Configuration;
import com.jcute.core.boot.Application;
import com.jcute.plugin.transaction.EnableTransactionManager;
import com.jcute.plugin.transaction.TransactionManager;
import com.jcute.plugin.transaction.support.TransactionManagerForDataSource;

@Configuration
@EnableTransactionManager("dataSourceTransactionManager")
public class TestTransactionManager{

	public static void main(String[] args){
		Application.run(TestTransactionManager.class);
	}
	
	@Component
	public TransactionManager dataSourceTransactionManager(){
		return new TransactionManagerForDataSource();
	}
	
}