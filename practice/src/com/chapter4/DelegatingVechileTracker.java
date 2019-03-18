package com.chapter4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DelegatingVechileTracker {
	private final ConcurrentHashMap<String, Point> locations;
	private final Map<String,Point> unmodifiableMap;
	
	public DelegatingVechileTracker(Map<String, Point> points){
		locations = new ConcurrentHashMap<String, Point>(points);
		unmodifiableMap = Collections.unmodifiableMap(locations);
	}
	
	public  Map<String,Point> getLocations(){
		return unmodifiableMap;
	}
	
	public Point getLocation(String id){
		return locations.get(id);
	}
	
	public void setLocations(String id,int x,int y){
		if(locations.replace(id, new Point(x,y)) == null){
			throw new RuntimeException("no such id!");
		}
	}
	
	public static void main(String[] args) {
		Map<String,Point> map = new HashMap<String, Point>();
		map.put("A", new Point(1,3));
		map.put("B", new Point(22,-10));
		map.put("C", new Point(-100,210));
		map.put("D", new Point(0,0));
		final DelegatingVechileTracker tracker = new DelegatingVechileTracker(map);
		for(int i=0;i<3;i++){
			new Thread(new Runnable() {				
				@Override
				public void run() {
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					String name = Thread.currentThread().getName();
					Point pa = tracker.getLocation("A");
					System.out.println(name+":"+pa);
				}
			}).start();
		}
		
		for(int i=0;i<10;i++){
			final int k = i;
			new Thread(new Runnable() {				
				@Override
				public void run() {
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					tracker.setLocations("A", 100+k, 200+k);
				}
			}).start();
		}

	}
}
