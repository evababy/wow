import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

public class MouseMove {

	static int mouseoldx = 0;
	static int mouseoldy = 0;

	/**
	 * @comments :
	 * @param args
	 * @author : chenyanji
	 * @date : Jan 27, 2011 11:50:14 AM
	 */
	public static void main(String[] args) {
		try {
			Thread.currentThread().sleep(1000);
			System.out.println("开始");
			// TODO Auto-generated method stub
			Point mousePoint = MouseInfo.getPointerInfo().getLocation();
			mouseoldx = 0;
			mouseoldy = 0;
			while (true) {
				Thread.currentThread().sleep(2000);
				move(100 + (int) (Math.random() * 800), 100 + (int) (Math
						.random() * 600));
			}

			// while (true) {
			// Thread.currentThread().sleep(5000);
			//
			// System.out.println(":"
			// + calcDistance((int) Math.random() * 1000, (int) Math
			// .random() * 1000));
			//
			// mousemove((int) (Math.random() * 1000),
			// (int) (Math.random() * 600));
			// }
		} catch (Exception e) {

		}
	}

	public static void mousemove(int x, int y) {
		try {
			Robot robot = new Robot();
			robot.mouseMove(x, y);
			Thread.currentThread().sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void move(int x, int y) {

		int size = calcDistance(x, y);
		if (size > 15) {
			size = size / 15;
		}
		double zx;
		double zy;
		if (Math.abs(x - mouseoldx) == 0) {
			zx = 0;
		} else {
			zx = ((double) Math.abs(x - mouseoldx)) / size;
		}
		if (Math.abs(y - mouseoldy) == 0) {
			zy = 0;
		} else {
			zy = ((double) Math.abs(y - mouseoldy)) / size;
		}
		for (int i = 1; i <= size; i++) {
			if (x > mouseoldx && y > mouseoldy) {
				mousemove(mouseoldx + (int) (zx * i), mouseoldy
						+ (int) (zy * i));
			} else if (x > mouseoldx && y < mouseoldy) {
				mousemove(mouseoldx + (int) (zx * i), mouseoldx
						- (int) (zy * i));
			} else if (x < mouseoldx && y > mouseoldy) {
				mousemove(mouseoldx - (int) (zx * i), mouseoldy
						+ (int) (zy * i));
			} else if (x < mouseoldx && y < mouseoldy) {
				mousemove(mouseoldx - (int) (zx * i), mouseoldy
						- (int) (zy * i));
			} else if (x == mouseoldx && y == mouseoldy) {
				mousemove(mouseoldx, mouseoldy);
			} else if (x == mouseoldx && y < mouseoldy) {
				mousemove(mouseoldx, mouseoldy - (int) (zy * i));
			} else if (x == mouseoldx && y > mouseoldy) {
				mousemove(mouseoldx, mouseoldy + (int) (zy * i));
			} else if (x > mouseoldx && y == mouseoldy) {
				mousemove(mouseoldx + (int) (zx * i), mouseoldy);
			} else if (x < mouseoldx && y == mouseoldy) {
				mousemove(mouseoldx - (int) (zx * i), mouseoldy);
			}
		}
		mousemove(x, y);
		mouseoldx = x;
		mouseoldy = y;
	}

	public static int calcDistance(int x, int y) {

		int b = Math.abs((x - mouseoldx));
		int a = Math.abs((y - mouseoldy));
		return (int) Math.sqrt((a * a + b * b));

	}

}
