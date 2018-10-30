
package wow.twow;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class WOWStaticDPSThread extends Thread {

	public void run() {
		try {
			while (true) {
				Robot robot = new Robot();
				Thread.currentThread().sleep(400);
					robot.keyPress(KeyEvent.VK_7);
					Thread.currentThread().sleep(
							10 + (int) (Math.random() * 20));
					robot.keyRelease(KeyEvent.VK_7);
			}
		} catch (Exception e) {

		}
	}
}

