package wow.twow;

import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class WOWMisc extends Thread {

	boolean boo = false;
	String url = "";
	int sleep = 0;

	public void run() {
		InputStream in = null;
		AudioStream as = null;
		try {

			if (boo) {
				while (boo) {
					in = new FileInputStream(url);
					as = new AudioStream(in);
					AudioPlayer.player.start(as);
					Thread.currentThread().sleep(sleep);
					as.close();
					in.close();
				}
			} else {
				in = new FileInputStream(url);
				as = new AudioStream(in);
				AudioPlayer.player.start(as);
				Thread.currentThread().sleep(sleep);
				as.close();
				in.close();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				as.close();
				in.close();
			} catch (Exception e) {
			}

		}
	}

	public void play(String url, int sleep, boolean boo) {
		// TODO Auto-generated method stub
		this.url = url;
		this.sleep = sleep;
		this.boo = boo;
		this.start();
	}

	/**
	 * @comments :
	 * @param args
	 * @author : chenyanji
	 * @date : Dec 20, 2010 3:16:43 PM
	 */
	public static void main(String[] args) {

		WOWMisc b = new WOWMisc();
		b.play("E:/java1.5/wow/music/offline.wav", 5000, true);
		System.out.println("aaaa");
	}

}
