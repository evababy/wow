package wow.twow;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import wow.twow.support.WOW;

public class WOWMouseColor {

	static int x[] = new int[100];
	static int y[] = new int[100];
	static int r[] = new int[100];
	static int g[] = new int[100];
	static int b[] = new int[100];

	static int item = 0;

	/**
	 * @comments :
	 * @param args
	 * @author : chenyanji
	 * @date : Dec 21, 2010 12:54:13 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JIntellitype.getInstance().registerHotKey(2000, JIntellitype.MOD_ALT, (int) 'W');
		JIntellitype.getInstance().registerHotKey(3000, JIntellitype.MOD_ALT, (int) 'E');
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("	取    点 : Alt+W");
		System.out.println("	启    动 : Alt+E");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {

			@SuppressWarnings({ "static-access", "deprecation" })
			public void onHotKey(int key) {
				WOWMisc wowb = new WOWMisc();
				try {
					Robot robot = new Robot();
					if (key == 1000) {

					} else if (key == 2000) {
						if (item >= 100) {
							System.out.println("鼠标点达到上限");
						} else {
							Point mousePoint = MouseInfo.getPointerInfo().getLocation();
							x[item] = mousePoint.x;
							y[item] = mousePoint.y;
							item++;
							System.out.println("x:	" + mousePoint.x + "	y:	" + mousePoint.y);
							wowb.play(WOW.url_start, 2000, false);
						}

					} else if (key == 3000) {
						for (int i = 0; i < item; i++) {
							Color color = robot.getPixelColor(x[i], y[i]);
							r[i] = color.getRed();
							g[i] = color.getGreen();
							b[i] = color.getBlue();
						}
						initrgb();
						item = 0;
						wowb.play(WOW.url_start, 2000, false);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void initrgb() {
		List lr = new ArrayList();
		List lg = new ArrayList();
		List lb = new ArrayList();
		for (int i = 0; i < item; i++) {
			lr.add(r[i]);
			lg.add(g[i]);
			lb.add(b[i]);
		}
		Collections.sort(lr);
		Collections.sort(lg);
		Collections.sort(lb);
		System.out.println("=============================");
		System.out.println("rgb:	" + lr.get(0) + "," + lg.get(0) + "," + lb.get(0));
		System.out
				.println("rgb:	" + lr.get(lr.size() - 1) + "," + lg.get(lg.size() - 1) + "," + lb.get(lb.size() - 1));
		System.out.println("=============================");
		System.out.println("");
	}
}
