package image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

/**
 * @comment
 * @author Sylvanas
 * @date 2018年10月30日 上午8:32:15
 */
public class Image {

	static int x[] = { 0, 0 };
	static int y[] = { 0, 0 };
	static boolean fish = false;

	/**
	 * @comments :
	 * @param args
	 * @author : chenyanji
	 * @date : Jan 25, 2011 5:25:28 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("	取    点 : SHIFT+W 选择两次，找出一个区域，");
		System.out.println("	启    动 : SHIFT+E 计算本区域RGB R定的最大值位置");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		JIntellitype.getInstance().registerHotKey(1000, JIntellitype.MOD_SHIFT, (int) 'W');
		JIntellitype.getInstance().registerHotKey(2000, JIntellitype.MOD_SHIFT, (int) 'E');

		HotkeyListener HL = new HotkeyListener() {

			int i = 0;

			@SuppressWarnings({ "static-access", "deprecation" })
			public void onHotKey(int arg0) {

				try {
					Robot robot = new Robot();
					if (arg0 == 1000) {
						if (i == 0) {
							Point mousePoint = MouseInfo.getPointerInfo().getLocation();
							x[0] = mousePoint.x;
							y[0] = mousePoint.y;
							i++;
							System.out.println(x[0] + "," + y[0]);
							Color color = robot.getPixelColor(x[i], y[i]);
							System.out.println(color.getRed() + " " + color.getGreen() + " " + color.getBlue());
						} else if (i == 1) {
							Point mousePoint = MouseInfo.getPointerInfo().getLocation();
							x[1] = mousePoint.x;
							y[1] = mousePoint.y;
							i++;
							System.out.println(x[1] + "," + y[1]);
							i = 0;
							Color color = robot.getPixelColor(x[i], y[i]);
							System.out.println(color.getRed() + " " + color.getGreen() + " " + color.getBlue());
						} else {
							i = 0;
						}
					} else if (arg0 == 2000) {
						System.out.println("开始处理");
						findFishing();
						System.out.println("处理完成");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		JIntellitype.getInstance().addHotKeyListener(HL);

	}

	// 黑白，找最亮
	public static BufferedImage grayImage(BufferedImage srcImg) {
		int iw = srcImg.getWidth();
		int ih = srcImg.getHeight();
		Graphics2D srcG = srcImg.createGraphics();
		RenderingHints rhs = srcG.getRenderingHints();

		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp theOp = new ColorConvertOp(cs, rhs);
		BufferedImage dstImg = new BufferedImage(iw, ih, BufferedImage.TYPE_3BYTE_BGR);

		theOp.filter(srcImg, dstImg);
		return dstImg;
	}

	public static void findFishing() {
		fish = false;
		BufferedImage screenshot = null;
		try {
			screenshot = new Robot().createScreenCapture(new Rectangle(x[0], y[0], x[1] - x[0], y[1] - y[0]));
			// screenshot = grayImage(screenshot);
			int w = screenshot.getWidth();
			int h = screenshot.getHeight();
			// ImageIO.write(screenshot, "bmp", new File("F:/外挂/" + System.currentTimeMillis()));
			Color color = null;
			Robot robot = new Robot();
			List list = new ArrayList();
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					color = new Color(screenshot.getRGB(i, j));
					list.add(color.getRed());
				}
			}
			Collections.sort(list);
			int bai = Integer.parseInt(list.get(list.size() - 1).toString());
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					color = new Color(screenshot.getRGB(i, j));
					if (color.getRed() == bai) {
						robot.mouseMove(x[0] + i, y[0] + j);
						fish = true;
						return;
					}
				}
			}

			System.out.println("未找到...");
		} catch (Exception e) {

		} finally {
		}
	}

}
