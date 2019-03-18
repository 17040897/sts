package com.chapter5;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@SuppressWarnings("rawtypes")
public class WorkStealling {
	private final int QUEUE_SIZE = 3;
	public final BlockingDeque[] bds = new BlockingDeque[QUEUE_SIZE];

	public WorkStealling() {
		for (int i = 0; i < QUEUE_SIZE; i++) {
			bds[i] = new LinkedBlockingDeque();
		}
	}

	class Producer implements Runnable {

		private final BlockingDeque[] lbqs;

		private final Random random;

		public Producer(BlockingDeque[] lbqs) {
			this.lbqs = lbqs;
			random = new Random();
		}

		@Override
		public void run() {
			while (true) {
				String name = Thread.currentThread().getName();
				BlockingDeque pq = lbqs[Integer.valueOf(name)];
				Product p = new Product(random.nextInt(1000) + "");
				pq.add(p);
				System.out.println(name + " 生产 " + p + " to "
						+ Integer.valueOf(name));
			}
		}
	}

	class Consumer implements Runnable {

		private final BlockingDeque[] lbqs;

		public Consumer(BlockingDeque[] lbqs) {
			this.lbqs = lbqs;
		}

		@Override
		public void run() {
			while (true) {
				String name = Thread.currentThread().getName();
				int index = Integer.valueOf(name) % 3;
				BlockingDeque pq = lbqs[index];
				Object obj = pq.pollFirst();
				if (obj == null) {
					int stealIndex = (index + 1) % 3;
					Product p = (Product) lbqs[stealIndex].pollLast();
					System.out.println(name + " steal" + p + "from:"
							+ stealIndex);
				} else {
					Product p = (Product) obj;
					System.out.println(name + " 消费  " + p + " from:" + index);
				}
			}
		}

	}

	public static void main(String[] args) {
		WorkStealling ws = new WorkStealling();
		for (int i = 0; i < 3; i++) {
			new Thread(ws.new Producer(ws.bds), i + "").start();
		}
		for (int i = 3; i < 6; i++) {
			new Thread(ws.new Consumer(ws.bds), i + "").start();
		}
	}
}
