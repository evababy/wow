package wow.twow;

import java.awt.Robot;
import java.awt.event.MouseEvent;

public class PetThread extends Thread {

	int x1 = 0, y1 = 0;

	int xz = 114, yq = 105;

	public void run() {
		try {
			Robot robot = new Robot();
			while (true) {

				robot.mouseMove(x1, y1);
				Thread.currentThread().sleep(1000);
				robot.mouseMove(x1, y1 - 65);
				Thread.currentThread().sleep(1000);
				robot.mouseMove(x1, y1);
				Thread.currentThread().sleep(1000);
				mouse(x1, y1, robot);
				Thread.currentThread().sleep(1000);
				robot.mouseMove(x1 - xz, y1);
				Thread.currentThread().sleep(1000);
				mouse(x1 - xz, y1, robot);
				Thread.currentThread().sleep(4500);
				mouse(x1, y1 - yq + 40, robot);
				Thread.currentThread().sleep(500);
				for (int i = 0; i <= 20000; i++) {
					for (int mx = x1 + 45; mx <= x1 + 320; mx += 12) {
						mouse(mx, y1 - yq, robot);
					}
				}
				Thread.currentThread().sleep(8500);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * @Comments ：
	 * @param x
	 * @param y
	 * @param robot
	 * @throws InterruptedException
	 * @Author ：陈彦吉
	 * @Group : A组
	 * @Worker: 1416
	 * @Date ：2012-8-22 下午11:10:45
	 */
	private void mouse(int x, int y, Robot robot) throws InterruptedException {

		robot.mouseMove(x, y);
		Thread.currentThread().sleep(1);
		robot.mousePress(MouseEvent.BUTTON1_MASK);
		robot.mouseRelease(MouseEvent.BUTTON1_MASK);
	}
}
