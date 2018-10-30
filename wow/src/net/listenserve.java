package net;

import java.io.IOException;
import java.net.ServerSocket;

public class listenserve {
	private ServerSocket ss;

	public listenserve() {
		Init();
		lisn();
	}

	public void Init() {
		try {
			ss = new ServerSocket(10015, 10);
		} catch (IOException ie) {
			System.out.println("无法在10015端口监听");
			ie.printStackTrace();
		}
	}

	public void lisn() {
		try {
				System.out.println("开始监听！");
//				for(int i=0;i<10;i++)
				new Thread(new dialogserve(ss.accept())).start();
				
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new listenserve();
	}
}
