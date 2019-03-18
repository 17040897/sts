package com.chapter5;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class TestDeque {

	public static void main(String[] args) {
		BlockingDeque<String> bd = new LinkedBlockingDeque<String>();
		bd.add("123");
		bd.add("456");
		bd.add("789");
		while(bd.size()>0){
			System.out.println(bd.pollLast());
		}
	}

}
