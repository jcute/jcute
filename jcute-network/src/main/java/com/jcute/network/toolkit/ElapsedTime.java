package com.jcute.network.toolkit;

public final class ElapsedTime{

	private static final TimeProvider TIME_PROVIDER;

	static{
		TimeProvider timeProvider = new MilliSecondProvider();

		boolean supportNanoTime = false;
		try{
			System.class.getMethod("nanoTime",new Class[0]);
			supportNanoTime = true;
		}catch(Exception e){}

		if(supportNanoTime){
			timeProvider = new NanoSecondProvider();
		}else{
			try{
				Class.forName("sun.misc.Perf");
				timeProvider = new PerfProvider();
			}catch(ClassNotFoundException e){}
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

		public long getCurrentTime();

		public long toMillisecond(long time);

	}

	private static class MilliSecondProvider implements TimeProvider{

		@Override
		public long getCurrentTime(){
			return System.currentTimeMillis();
		}

		@Override
		public long toMillisecond(long time){
			return time;
		}

	}

	private static class NanoSecondProvider implements TimeProvider{
		@Override
		public long getCurrentTime(){
			return System.nanoTime();
		}

		@Override
		public long toMillisecond(long time){
			return System.nanoTime() / 1000000;
		}
	}

	private static class PerfProvider implements TimeProvider{

		@SuppressWarnings("restriction")
		private final sun.misc.Perf perf = sun.misc.Perf.getPerf();
		@SuppressWarnings("restriction")
		private final long frequency = perf.highResFrequency();

		@SuppressWarnings("restriction")
		@Override
		public long getCurrentTime(){
			return perf.highResCounter();
		}

		@Override
		public long toMillisecond(long time){
			return (long)((double)time * 1000 / this.frequency);
		}

	}

}