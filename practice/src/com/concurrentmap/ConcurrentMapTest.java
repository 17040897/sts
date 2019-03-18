package com.concurrentmap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapTest {
	
	private static final int MAXIMUM_CAPACITY = 1 << 30;

	public static void main(String[] args) {
		ConcurrentMap<String, String> cm = new ConcurrentHashMap<String, String>();
		cm.put("A", "001");
		cm.put("B", "002");
		cm.put("C", "003");
		cm.forEach((k, v) -> {
			System.out.println(k + "-" + v);
		});
		String res = cm.putIfAbsent("C", "fuck");
		System.out.println(res);
		String putres = cm.putIfAbsent("D", "cunt");
		System.out.println("flag:" + putres + " " + cm.get("D"));
		System.out.println(cm.remove("D", "cunt"));
		System.out.println("after remove:" + cm.get("D"));
		String res2 = cm.computeIfAbsent("C", (k) -> {
			return (k + "000");
		});
		System.out.println(res2);
		System.out.println(spread(100));
		System.out.println(tableSizeFor(17));
		Long l = new Long(100L);
		System.out.println(comparableClassFor(l));
		
	
	}
	
	static final int spread(int h) {
        return (h ^ (h >>> 16)) & 0x7fffffff;
    }
	
	private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
	
	private static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c; Type[] ts, as; Type t; ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                        ((p = (ParameterizedType)t).getRawType() ==
                         Comparable.class) &&
                        (as = p.getActualTypeArguments()) != null &&
                        as.length == 1 && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }
}
