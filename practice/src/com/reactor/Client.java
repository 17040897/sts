package com.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Client {

	ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
	ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	
	public void start() throws IOException {
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);
		sc.connect(new InetSocketAddress("127.0.0.1", 7777));
		Selector selector = Selector.open();
		sc.register(selector, SelectionKey.OP_CONNECT);
		Scanner scanner = new Scanner(System.in);
		while(true){
			selector.select();
			Set<SelectionKey> keys = selector.selectedKeys();
			System.out.println("keys:"+keys.size());
			Iterator<SelectionKey> it = keys.iterator();
			while(it.hasNext()){
				SelectionKey key = it.next();
				it.remove();
				if(key.isConnectable()){
					sc.finishConnect();
					sc.register(selector, SelectionKey.OP_WRITE);
					System.out.println("server connected...");
					break;
				}else if(key.isWritable()){
					System.out.println("Please input message...");
					String msg = scanner.nextLine();
					writeBuffer.clear();
					writeBuffer.put(msg.getBytes());
					writeBuffer.flip();
					sc.write(writeBuffer);
					sc.register(selector, SelectionKey.OP_READ);
					sc.register(selector, SelectionKey.OP_WRITE);
					sc.register(selector, SelectionKey.OP_READ);
				}else if(key.isReadable()){
					System.out.print("receive message:");
					SocketChannel client = (SocketChannel) key.channel();
					readBuffer.clear();
					int num = client.read(readBuffer);
					System.out.println(new String(readBuffer.array(), 0, num));
					sc.register(selector, SelectionKey.OP_WRITE);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		try {
			new Client().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
