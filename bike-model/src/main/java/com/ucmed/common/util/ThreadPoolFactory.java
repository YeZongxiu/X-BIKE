package com.ucmed.common.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFactory {
	private int corePoolSize = 2;
	private int maximumPoolSize = 10;
	private int maxQueueSize = 12;
	private long keepAliveTime = 5;
	private TimeUnit unit = TimeUnit.MINUTES;
	private BlockingQueue<Runnable> blockingQueue = null;
	private ThreadPoolExecutor pool = null;

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public int getMaxQueueSize() {
		return maxQueueSize;
	}

	public void setMaxQueueSize(int maxQueueSize) {
		this.maxQueueSize = maxQueueSize;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

	public BlockingQueue<Runnable> getBlockingQueue() {
		return blockingQueue;
	}

	public void setBlockingQueue(BlockingQueue<Runnable> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	/**
	 * 默认为ArrayBlockingQueue
	 * 
	 * @return
	 */
	public ThreadPoolExecutor getPool() {
		if (pool == null) {
			pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
					keepAliveTime, unit,
					blockingQueue == null ? new LinkedBlockingQueue<Runnable>(
							maxQueueSize) : blockingQueue,
					new ThreadPoolExecutor.CallerRunsPolicy());
		}
		return pool;
	}
}
