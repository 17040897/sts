package com.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Reactor implements Runnable {

	private ServerSocketChannel serverSocketChannel;

	private Selector selector;

	public Reactor(int port) {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1",port));
			SelectionKey selectionKey = serverSocketChannel.register(selector,
					SelectionKey.OP_ACCEPT);
			selectionKey.attach(new Acceptor());
			System.out.println("服务器启动，监听端口 ：" + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				selector.select();
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey selectionKey = it.next();
					dispatch((Runnable) selectionKey.attachment());
					it.remove();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Thread(new Reactor(7777)).start();
	}
	
	private void dispatch(Runnable runnable) {
		System.out.println(runnable);
		if (runnable != null) {
			runnable.run();
		}

	}

	class Acceptor implements Runnable {

		@SuppressWarnings("static-access")
		@Override
		public void run() {
			try {
				System.out.println("Acceptor.run()");
				SocketChannel socketChannel = serverSocketChannel.accept();
				if (socketChannel != null) {
					System.out.println("服务器收到客户端:"
							+ socketChannel.socket().getInetAddress()
									.getLocalHost() + "的连接");
					new Handler(selector, socketChannel);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}

class Handler implements Runnable {
	private static final int READ_STATUS = 1;

	private static final int WRITE_STATUS = 2;

	private SocketChannel socketChannel;

	private SelectionKey selectionKey;

	private int status = READ_STATUS;

	public Handler(Selector selector, SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
		try {
			socketChannel.configureBlocking(false);
			selectionKey = socketChannel.register(selector, 0);
			selectionKey.interestOps(SelectionKey.OP_READ);
			selectionKey.attach(this);
			selector.wakeup();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			if (status == READ_STATUS) {
				read();
				selectionKey.interestOps(SelectionKey.OP_WRITE);
				status = WRITE_STATUS;
			} else if (status == WRITE_STATUS) {
				process();
				selectionKey.cancel();
				System.out.println("服务器发送信息成功");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void process() throws IOException {
		String content = "Hello world!";
		ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes());
		socketChannel.write(byteBuffer);
		
	}

	@SuppressWarnings("static-access")
	private void read() throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		socketChannel.read(byteBuffer);
		System.out.println("服务器收到客户端:"
				+ socketChannel.socket().getInetAddress()
						.getLocalHost() +":"+ new String(byteBuffer.array()));
	}

}
