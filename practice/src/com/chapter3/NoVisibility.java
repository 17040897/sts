package com.chapter3;

public class NoVisibility {

	private static boolean ready;

	private static int number;

	private static class ReaderThread extends Thread {
		@Override
		public void run() {
			if (!ready) {
				Thread.yield();
			}
			System.out.println(number);
		}
	}

	public static void main(String[] args) {
		new ReaderThread().start();
		ready = true;
		number = 42;				
	}

}
