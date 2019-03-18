package com.chapter3;

public class ConnMain {

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					MyConn conn = (MyConn) ConnUtil.getConn();
					String name = Thread.currentThread().getName();
					conn.setName(name);
					System.out.println(name+" set name:"+name);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
					System.out.println(name+":"+conn);
				}
			}).start();
		}

	}

}
