package com.jcute.core.toolkit.cycle;

import org.junit.Test;

public class TestStable{
	
	@Test
	public void testNormal() throws Exception{
		
		Application application = new Application();
		application.attachCloseFailureListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭失败");
			}
		});
		application.attachCloseSuccessListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭成功");
			}
		});
		application.attachClosingListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭中");
			}
		});
		application.attachStartFailureListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动失败");
			}
		});
		application.attachStartSuccessListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动成功");
			}
		});
		application.attachStartingListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动中");
			}
		});
		application.start();
		Thread.sleep(1000);
		application.close();
		
	}
	
	@Test
	public void testStartError() throws Exception{
		
		Application application = new Application(){
			@Override
			protected void doStart() throws Exception{
				super.doStart();
				throw new RuntimeException("custome error");
			}
		};
		application.attachCloseFailureListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭失败");
			}
		});
		application.attachCloseSuccessListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭成功");
			}
		});
		application.attachClosingListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭中");
			}
		});
		application.attachStartFailureListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动失败");
			}
		});
		application.attachStartSuccessListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动成功");
			}
		});
		application.attachStartingListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动中");
			}
		});
		application.start();
		Thread.sleep(1000);
		application.close();
		
	}
	
	@Test
	public void testCloseError() throws Exception{
		
		Application application = new Application(){
			@Override
			protected void doClose() throws Exception{
				super.doClose();
				throw new RuntimeException("custome error");
			}
		};
		application.attachCloseFailureListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭失败");
			}
		});
		application.attachCloseSuccessListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭成功");
			}
		});
		application.attachClosingListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭中");
			}
		});
		application.attachStartFailureListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动失败");
			}
		});
		application.attachStartSuccessListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动成功");
			}
		});
		application.attachStartingListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动中");
			}
		});
		application.start();
		Thread.sleep(1000);
		application.close();
		
	}
	
	@Test
	public void testMuliListener() throws Exception{
		
		StableListener<StableEvent> closeListener = new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭中 -> custome");
			}
		};
		
		Application application = new Application();
		application.attachCloseFailureListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭失败");
			}
		});
		application.attachCloseSuccessListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭成功");
			}
		});
		application.attachClosingListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("关闭中");
			}
		});
		application.attachClosingListener(closeListener);
		application.attachStartFailureListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动失败");
			}
		});
		application.attachStartSuccessListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动成功 -> 1");
			}
		});
		application.attachStartSuccessListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动成功 -> 2");
			}
		});
		application.attachStartingListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动中 -> 1");
			}
		});
		application.attachStartingListener(new StableListener<StableEvent>() {
			@Override
			public void execute(StableEvent event) throws Exception{
				System.out.println("启动中 -> 2");
			}
		});
		System.out.println(application.getType());
		application.start();
		System.out.println(application.getType());
		application.detachClosingListener(closeListener);
		Thread.sleep(1000);
		application.close();
		
	}
	
}