package com.concurrentmap;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

@SuppressWarnings("restriction")
public class UnsafeTest {

	public static void main(String[] args) throws Exception {
		String[] strings = new String[] { "1", "2", "3" };
		Field f = Unsafe.class.getDeclaredField("theUnsafe");
		f.setAccessible(true);
		Unsafe unsafe = (Unsafe) f.get(null);
		long i = unsafe.arrayBaseOffset(String[].class);
		System.out.println("strings[] base offset is :" + i);

		long scale = unsafe.arrayIndexScale(String[].class);
		System.out.println("strings[] index scale is " + scale);

		System.out.println("first element is :"
				+ unsafe.getObject(strings, i + 2 * scale));

		Class<?> k = User.class;
		User u = new User(100, "james", 200.99, "南京路");
		long userNameOffset = unsafe.objectFieldOffset(k
				.getDeclaredField("name"));
		System.out.println(userNameOffset);
		long userAgeOffset = unsafe
				.objectFieldOffset(k.getDeclaredField("age"));
		System.out.println(userAgeOffset);
		long salaryOffset = unsafe.objectFieldOffset(k
				.getDeclaredField("salary"));
		System.out.println(salaryOffset);
		long addressOffset = unsafe.objectFieldOffset(k
				.getDeclaredField("address"));
		System.out.println(addressOffset);

		System.out.println(unsafe.getObject(u, addressOffset));
		unsafe.putObject(u, addressOffset, "北京路");
		System.out.println("修改地址后：" + unsafe.getObject(u, addressOffset));

		// 分配一个8byte的内存
		long address = unsafe.allocateMemory(8L);
		unsafe.setMemory(address, 8L, (byte) (1));
		System.out.println("add byte to memory:" + unsafe.getInt(address));
		// 设置0-3 4个byte为0x7fffffff
		unsafe.putInt(address, 0x7fffffff);
		unsafe.putInt(address + 4, 0x00000000);
		System.out.println("add byte to memory:" + unsafe.getInt(address));
		System.out.println("add byte to memory:" + unsafe.getInt(address + 4));

		User u2 = new User(200, "jack", 199.99, "纽约");
		Field ff = u2.getClass().getDeclaredField("age");
		long ffset = unsafe.objectFieldOffset(ff);
		ff.setAccessible(true);
		unsafe.compareAndSwapInt(u2, ffset, 200, 300);
		System.out.println("afeter cas:" + u2.getAge());

	}

}
