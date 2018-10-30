package wow.twow;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class WOWMoveDPSThread extends Thread {

	public void run() {
		try {
			while (true) {
				Robot robot = new Robot();
				Thread.currentThread().sleep(400);
				if ((int) (Math.random() * 20) >= 14) {
					robot.keyPress(KeyEvent.VK_Q);
					Thread.currentThread().sleep(
							10 + (int) (Math.random() * 20));
					robot.keyRelease(KeyEvent.VK_Q);
				}else if((int) (Math.random() * 20) >= 16){
					robot.keyPress(KeyEvent.VK_F12);
					Thread.currentThread().sleep(
							10 + (int) (Math.random() * 20));
					robot.keyRelease(KeyEvent.VK_F12);
				}else  {
					robot.keyPress(192);
					Thread.currentThread().sleep(
							10 + (int) (Math.random() * 20));
					robot.keyRelease(192);
				}
			}
		} catch (Exception e) {

		}
	}
}
