package com.chapter7;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BrokenPrimeGenerator extends Thread {
	private final BlockingQueue<BigInteger> queue;
	private volatile boolean cancelled = false;
	private final Random random = new Random();

	public BrokenPrimeGenerator(BlockingQueue<BigInteger> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			BigInteger p = BigInteger.ONE;
			while (!Thread.currentThread().isInterrupted()) {
				p = BigInteger.probablePrime(5, random);
				queue.put(p);
			}
		} catch (InterruptedException e) {
			System.out.println("********consumer cancell********");
		}
	}

	public void cancell() {
		this.interrupt();
	}

	public synchronized Object get() {
		return queue.toString();
	}

	public static void main(String[] args) {
		BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<BigInteger>(10);
		BrokenPrimeGenerator bpg = new BrokenPrimeGenerator(queue);
		bpg.start();
		try {
			Thread.sleep(3000);
			System.out.println(bpg.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bpg.cancell();
		}

	}

}
