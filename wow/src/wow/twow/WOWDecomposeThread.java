package wow.twow;

import java.awt.Robot;

import wow.twow.support.WOW;

public class WOWDecomposeThread extends Thread {

	public void init() {
	}

	public void run() {
		try {
			Thread.currentThread().sleep(2000);
			Robot robot = new Robot();
			while (true) {
				for (int i1 = 0; i1 < WOW.decompose.length; i1++) {
					for (int i2 = 0; i2 < WOW.decompose[i1][2]; i2++) {
						if (WOW.decompose[i1][0] < 10) {
							robot.keyPress(WOW.decompose[i1][0] + 48);
							robot.keyRelease(WOW.decompose[i1][0] + 48);
						} else {
							robot.keyPress(WOW.decompose[i1][0]);
							robot.keyRelease(WOW.decompose[i1][0]);
						}
						Thread.currentThread().sleep(
								WOW.decompose[i1][1] + WOW.Deviation
										+ (int) (Math.random() * 1000));
						if ((int) (Math.random() * 1000) > 900) {
							Thread.currentThread().sleep(
									(int) (Math.random() * 5000));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
