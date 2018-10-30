package net;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;

public class dialogserve implements Runnable {
	private Socket s;
	private InputStream in;
	private String rev, temp;
	private byte b[];
	private int len;

	public dialogserve(Socket ss) {
		s = ss;
		b = new byte[1024];
		try {
			in = s.getInputStream();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public void run() {
		try {
			while (s.isConnected() == true) {
				if ((len = in.read(b)) != -1) {
					temp = new String(b, 0, len);
					System.out.println("接到请求："+temp);
					System.out.println("服务器强制退出");
					System.exit(0);//
					temp = null;
				}
			}
			System.out.println("in.close();");
			in.close();
			System.out.println("s.close();");
			s.close();
			System.out.println("正常断开！");
		} catch (SocketException se) {
			se.printStackTrace();
		} catch (Exception io) {
			io.printStackTrace();
		} 
	}
}
