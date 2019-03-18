package com.chapter4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import com.annotation.ThreadSafe;

@ThreadSafe
public class ListHelper2<E> {

	public List<E> list = Collections.synchronizedList(new ArrayList<E>());

	public boolean putIfAbsent(E e) {
		synchronized (list) {
			boolean absent = !list.contains(e);
			if (absent) {
				list.add(e);
				System.out.println(Thread.currentThread().getName()+" add "+e);
			}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return absent;
		}


	}

	public static void main(String[] args) {
		final ListHelper2<Character> lp = new ListHelper2<Character>();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (char c = 'A'; c < 'Z'; c++) {
					lp.list.add(c);
					System.out.println(Thread.currentThread().getName()+" add "+c);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lp.putIfAbsent('X');
			}
		}).start();

	}
}
