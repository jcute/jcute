package com.jcute.core.net.util;

import sun.misc.Perf;

@SuppressWarnings("restriction")
public final class ElapsedTime{

	private static final TimeProvider TIME_PROVIDER;

	static{
		TimeProvider timeProvider = new MillisecondTimeProvider();
		boolean supportNanoTime = false;
		try{
			System.class.getMethod("nanoTime",new Class[0]);
			supportNanoTime = true;
		}catch(Exception e){}
		if(supportNanoTime){
			timeProvider = new NanoTimeProvider();
		}else{
			try{
				Class.forName("sun.misc.Perf");
				timeProvider = new PerfProvider();
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}
		}
		TIME_PROVIDER = timeProvider;
	}

	private long startTime = TIME_PROVIDER.getCurrentTime();

	public long getElapsedTime(){
		return TIME_PROVIDER.toMillisecond(Math.max(0,TIME_PROVIDER.getCurrentTime() - startTime));
	}

	public long reset(){
		long currentTime = TIME_PROVIDER.getCurrentTime();
		long elapsedTime = TIME_PROVIDER.toMillisecond(Math.max(0,currentTime - startTime));
		startTime = currentTime;
		return elapsedTime;
	}

	private static interface TimeProvider{
		long getCurrentTime();

		long toMillisecond(long time);
	}

	private static class MillisecondTimeProvider implements TimeProvider{
		@Override
		public long getCurrentTime(){
			return System.currentTimeMillis();
		}

		@Override
		public long toMillisecond(long time){
			return time;
		}
	}

	private static class NanoTimeProvider implements TimeProvider{
		@Override
		public long getCurrentTime(){
			return System.nanoTime();
		}

		@Override
		public long toMillisecond(long time){
			return time / 1000000;
		}
	}

	private static class PerfProvider implements TimeProvider{
		private final Perf perf = Perf.getPerf();
		private final long frequency = perf.highResFrequency();

		@Override
		public long getCurrentTime(){
			return perf.highResCounter();
		}

		@Override
		public long toMillisecond(long time){
			return (long)((double)time * 1000 / frequency);
		}
	}

}