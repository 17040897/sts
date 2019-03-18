package com.chapter7;

import java.math.BigInteger;
import java.util.*;

public class PrimeGenerator implements Runnable {
	private List<BigInteger> primes = new ArrayList<BigInteger>();
	private volatile boolean cancelled = false;
	private final Random random = new Random();

	@Override
	public void run() {
		BigInteger p = BigInteger.ONE;
		while (!cancelled) {
			p = BigInteger.probablePrime(10, random);
			synchronized (this) {
				primes.add(p);
			}
		}
	}

	public void cancel() {
		cancelled = true;
	}

	public synchronized List<BigInteger> get() {
		return new ArrayList<BigInteger>(primes);
	}

	public static void main(String[] args) {
		PrimeGenerator pg = new PrimeGenerator();
		new Thread(pg).start();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pg.cancel();
		}
		List<BigInteger> res = pg.get();
		System.out.println(res);
	}
}
