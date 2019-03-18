package com.chapter3;

public class ConnUtil {
	
	private static ThreadLocal<Conn> connHolder = new ThreadLocal<Conn>(){
		protected Conn initialValue() {
			return new MyConn();
		};
	};
	
	public static Conn getConn(){
		return connHolder.get();
	}
	
	
	
	
	
}
