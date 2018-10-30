import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.util.Vector;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.format.VideoFormat;
import javax.swing.JFrame;

public class CaptureAndPlay implements ControllerListener {

	Vector audioCapInfo = null;
	Vector videoCapDevList = null;
	CaptureDeviceInfo audioCapDevInfo = null;
	CaptureDeviceInfo videoCapDevInfo = null;
	MediaLocator audioCapDevLoc = null;
	MediaLocator videoCapDevLoc = null;

	Player audioPlayer;
	Player videoPlayer;

	public void initAudioCapDevLoc() {
		// 这里可以填写其它的音频编码格式，具体请看AudioFormat类

		// 或许有几个CaptureDevice,这里取第一个
		audioCapDevInfo = CaptureDeviceManager
				.getDevice("UIU32a.exe");
		audioCapDevLoc = audioCapDevInfo.getLocator();
		System.out.println("找不到音频采集设备");
		System.exit(0);

	}

	public void initVideoCapDevLoc() {
		// 这里可以填写其它的编码视频格式，具体请看VideoFormat类
		videoCapDevList = CaptureDeviceManager.getDeviceList(new VideoFormat(
				VideoFormat.YUV));

		if ((videoCapDevList.size() > 0)) {
			// 或许有几个CaptureDevice,这里取第一个
			videoCapDevInfo = (CaptureDeviceInfo) videoCapDevList.elementAt(0);
			videoCapDevLoc = videoCapDevInfo.getLocator();
		} else {
			System.out.println("找不到视频采集设备");
			System.exit(0);
		}

	}

	public void initAudioPlayer() {

		try {
			audioPlayer = Manager.createPlayer(audioCapDevLoc);
		} catch (NoPlayerException ex) {
		} catch (IOException ex) {
		}
		// 增加一个侦听器，侦听player状态的改变
		audioPlayer.addControllerListener(this);
		audioPlayer.realize();
	}

	public void initVideoPlayer() {
		try {
			videoPlayer = Manager.createPlayer(videoCapDevLoc);
		} catch (NoPlayerException ex) {
		} catch (IOException ex) {
		}
		// 增加一个侦听器，侦听player状态的改变
		videoPlayer.addControllerListener(this);
		videoPlayer.realize();
	}

	public void stopAndClosePlayer() {
		audioPlayer.stop();
		audioPlayer.close();
		videoPlayer.stop();
		videoPlayer.close();
	}

	public synchronized void controllerUpdate(ControllerEvent ce) {
		Player p = (Player) ce.getSourceController();

		JFrame jFrame = new JFrame();
		Component com;

		if (p == null) {
			return;
		}

		// 如果player的状态变为Realized
		if (ce instanceof RealizeCompleteEvent) {

			if ((com = p.getControlPanelComponent()) != null) {
				jFrame.add(com, BorderLayout.SOUTH);
			}

			if ((com = p.getVisualComponent()) != null) {
				jFrame.add(com, BorderLayout.NORTH);
			}

			jFrame.setVisible(true);
			jFrame.pack();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			p.start();
		}
	}

	public static void main(String args[]) {
		CaptureAndPlay cap = new CaptureAndPlay();
		cap.initAudioCapDevLoc();
		cap.initVideoCapDevLoc();
		cap.initAudioPlayer();
		cap.initVideoPlayer();

		try {
			Thread.currentThread().sleep(20000);// 20秒后停止并关闭播放器
		} catch (InterruptedException ex) {
		}
		cap.stopAndClosePlayer();
	}
}
