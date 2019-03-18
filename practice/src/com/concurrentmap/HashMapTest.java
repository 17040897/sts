package com.concurrentmap;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

	
	
	public static void main(String[] args) {
		HashMap<User,Integer> map = new HashMap<User,Integer>();
		User u1 = new User(18,"jack");
		User u2 = new User(19,"tom");
		User u3 = new User(20,"jerry");
		User u4 = new User(21,"wade");
		User u5 = new User(22,"durant");
		User u6 = new User(23,"james");
		User u7 = new User(24,"kobe");
		User u8 = new User(25,"willams");
		User u9 = new User(26,"tomas");
		map.put(u1, 1);
		map.put(u2, 2);
		map.put(u3, 3);
		map.put(u4, 4);
		map.put(u5, 5);
		map.put(u6, 6);
		map.put(u7, 7);
		map.put(u8, 8);
		map.put(u9, 9);

	}

}
