package com.concurrentmap;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {

	public static void main(String[] args) {
		final Lock lock = new ReentrantLock();
		final Object obj = new Object();
		try {
			// lock.lock();
			System.out.println(Thread.currentThread().getName()
					+ " is sleeping!");
			Thread.sleep(3000);

		} catch (Exception e) {
			e.printStackTrace();
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					// lock.lock();
					System.out.println(Thread.currentThread().getName()
							+ " is sleeping!");
					Thread.sleep(3000);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

}
