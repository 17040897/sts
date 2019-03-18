package com.chapter4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UnmodifiableMapTest {

	public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("A", "james");
		map.put("B", "durant");
		map.put("C", "harden");		
		Map<String,String> res = Collections.unmodifiableMap(map);
		System.out.println(res.getClass());
		for(Map.Entry<String, String> entry : res.entrySet()){
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		res.put("D", "curry");

	}

}
