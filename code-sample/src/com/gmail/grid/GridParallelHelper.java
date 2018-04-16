/**
 * 
 */
package com.gmail.grid;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.runners.Parameterized;
import org.junit.runners.model.RunnerScheduler;

import com.gmail.util.AutomationHelper;

/**
 * Gives us more fine-tuned control over parallel/concurrent execution.  We're able to extend JUnit system 
 * threads with the help of Java Executors
 * @author IRiley
 *
 */
public class GridParallelHelper extends Parameterized{

	public GridParallelHelper(Class<?> klass) throws Throwable {
		super(klass);
		setScheduler(new ThreadPoolScheduler());
		
	}
	/*
	 * Manages Executors: really manages Futures (asynchronous tasks); due to the asynchronous nature of each WebDriver
	 *  instance, we wouldn't want this to run in the main/UI thread
	 */
	private static class ThreadPoolScheduler implements RunnerScheduler{

		String threadType = "junit.parallel.threads";
		// how many nodes are you targeting?
		String numThreads = "2";
		private final ExecutorService executor;
		
		public ThreadPoolScheduler() {
			String threads = System.getProperty(threadType, numThreads);
			int numThreadsToUse = Integer.parseInt(threads);
			executor = Executors.newFixedThreadPool(numThreadsToUse);
		}
		
		@Override
		public void finished() {
			executor.shutdown();
			try {
				//existing tasks have 5 minutes to wrap up (maybe too long but...)
				executor.awaitTermination(5, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				AutomationHelper.logStackTrace("Thread Termination Failure: "+e.getMessage());
				e.printStackTrace();
				throw new RuntimeException(e);
			}//catch			
		}//finished

		@Override
		public void schedule(Runnable child) {
			executor.submit(child);
		}
		
	}//ThreadPoolScheulder
}
