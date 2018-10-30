package D3;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

/**
 * @comment 截图判断特定颜色位置，耗时约15毫秒
 * @author Sylvanas
 * @date 2018年10月30日 下午2:17:35
 */
public class D3_Image {

	public static int x[] = { 0, 0 };
	public static int y[] = { 0, 0 };
	// 满血、满蓝 误差值
	static final int gap = 85;

	/**
	 * 血条上部位置
	 */
	public final static int xue_x = 535;
	public final static int xue_y = 925;

	/**
	 * 蓝条上部位置
	 */
	public final static int lan_x = 1378;
	public final static int lan_y = 925;

	// X轴扩展像素
	static final int xlen = 3;
	// Y轴扩展像素
	static final int ylen = 118;

	/**
	 * @comments :
	 * @param args
	 * @author : chenyanji
	 * @date : Jan 25, 2011 5:25:28 PM
	 */
	public static void main(String[] args) {
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("	取    点 : SHIFT+W 默认x+" + xlen + " y+" + ylen + "像素");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		JIntellitype.getInstance().registerHotKey(1000, JIntellitype.MOD_SHIFT, (int) 'W');

		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {

			public void onHotKey(int arg0) {

				try {
					Robot robot = new Robot();
					if (arg0 == 1000) {
						Point mousePoint = MouseInfo.getPointerInfo().getLocation();
						Color color = robot.getPixelColor(x[0], y[0]);
						System.out.println(mousePoint.x + "," + mousePoint.y + " => " + color.getRed() + " "
								+ color.getGreen() + " " + color.getBlue());
						System.err.println(getPercent(new Robot(), "r", mousePoint.x, mousePoint.y));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static double getPercent(Robot robot, String type) {
		int x = type.equals("l") ? xue_x : lan_x;
		int y = type.equals("l") ? xue_y : lan_y;
		return getPercent(robot, type, x, y);
	}

	/**
	 * @comment 支持血条和死灵法师蓝条
	 * @author Sylvanas
	 * @date 2018年10月30日 下午12:00:44
	 * @param robot
	 * @param type
	 *            l(left)/r(right)
	 * @param x
	 * @param y
	 * @return
	 */
	public static double getPercent(Robot robot, String type, int xx, int yy) {
		x[0] = xx;
		y[0] = yy;
		x[1] = x[0] + xlen;
		y[1] = y[0] + ylen;
		try {
			// 最耗时的操作，无法改善
			BufferedImage screenshot = robot.createScreenCapture(new Rectangle(x[0], y[0], x[1] - x[0], y[1] - y[0]));
			int w = screenshot.getWidth();
			int h = screenshot.getHeight();
			// ImageIO.write(screenshot, "bmp", new File("F:/外挂/aaa.bmp"));
			Color color = null;
			List<Double> listold = new LinkedList<Double>();
			List<Double> list = new ArrayList<Double>();
			for (int i = 0; i < h; i++) {
				int count = 0;
				for (int j = 0; j < w; j++) {
					color = new Color(screenshot.getRGB(j, i));
					count += type.equals("l") ? color.getRed() : (color.getGreen() + color.getBlue()) / 2;
				}
				list.add(count / xlen + 0.0);
				listold.add(count / xlen + 0.0);
			}

			Collections.sort(list);

			double tmp_min = list.get(0);
			double tmp_max = list.get(list.size() - 1);

			// System.err.println(tmp_min + " " + tmp_max + " => " + Math.abs(tmp_min - tmp_max));

			double l = 0;
			if (Math.abs(tmp_min - tmp_max) > (type.equals("l") ? gap : gap * 1.8)) {
				for (Double c : listold) {
					if (c == tmp_max) {
						return 1 - (l / ylen);
					}
					l++;
				}
			} else {
				if (tmp_max > 120) {
					return 1.0;
				} else {
					return 0.0;
				}
			}
		} catch (Exception e) {

		}
		return 1.0;
	}

}
