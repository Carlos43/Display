package com.dinfo.bdms.extract;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	
	private static ExecutorService threadPool = null;

	public ThreadPool() {
		threadPool = Executors.newCachedThreadPool();
	}
	
	public synchronized static void addTask(ExtractTask task) {
		threadPool.execute(task);
	}
	
	public static void shutdown() {
		threadPool.shutdown();
	}
}
