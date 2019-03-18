package com.chapter3;

public class ThisEscape {
	
	private String name;
	
	private String address;
	
	
	

	public ThisEscape(String name, String address) {
		this.name = name;
		new Thread(new Runnable() {			
			@Override
			public void run() {
				System.out.println(ThisEscape.this);				
			}
		}).start();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.address = address;
	}
	
	




	@Override
	public String toString() {
		return "ThisEscape [name=" + name + ", address=" + address + "]";
	}






	public static void main(String[] args) {
		
		new ThisEscape("james", "the 5th avenue");
	}

}
