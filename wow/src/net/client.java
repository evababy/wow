package net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class client {
	private Socket con;// 客户端连接socket
	private OutputStream out;
	private String sen;
	private byte b[];

	public client() {
		clientInit();
	}

	public void clientInit() {
		try {
			System.out.println("开始发送");
			con = new Socket("localhost", 10015);
			con.setSoTimeout(3000);
			b = new byte[1024];
			OutputStream out = con.getOutputStream();
			sen = "hello serve,以TCP方式发送数据！";
			
			b = sen.getBytes();
			out.write(b);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			out.write(b);//服务器关闭，如果再次写，客户端报：java.net.SocketException: socket write error: Connection reset by peer.
//			System.exit(0);//服务器报：java.net.SocketException: Connection reset
			System.out.println("out.flush();");
			out.flush();
//			System.exit(0);//客户端报：java.net.ConnectException: Connection refused: connect
			System.out.println("out.close();");
			out.close();
//			out.write(b);//java.net.SocketException: Socket closed
//			System.exit(0);//无错误
			System.out.println("con.close();");
			con.close();
			
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public static void main(String args[]) {
			new client();
	}
}
