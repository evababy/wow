package wow.twow;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.color.ColorSpace;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import wow.twow.support.WOW;

public class WOWFishingThread extends Thread {

	static private int areaPosition[][];
	private WOWFishing wOWFishing;
	public WOWMisc b = null;

	static int mouseoldx = 0;
	static int mouseoldy = 0;

	static boolean fish = false;

	public void init(int[][] i, WOWFishing dy) {
		areaPosition = i;
		wOWFishing = dy;
	}

	public void run() {
		try {
			Robot robot = new Robot();
			Thread.currentThread().sleep(2000 + (int) (Math.random() * 2000));
			Point mousePoint = MouseInfo.getPointerInfo().getLocation();
			mouseoldx = mousePoint.x;
			mouseoldy = mousePoint.y;
			while (true) {
				if ((int) (Math.random() * 60000) > 58000) {
					Thread.currentThread().sleep((int) (Math.random() * 5000));
				} else if ((int) (Math.random() * 60000) > 55000) {
					Thread.currentThread().sleep((int) (Math.random() * 4000));
				} else if ((int) (Math.random() * 60000) > 30000) {
					Thread.currentThread().sleep((int) (Math.random() * 1000));
				} else if ((int) (Math.random() * 60000) < 2000) {
					Thread.currentThread().sleep((int) (Math.random() * 2000));
				} else if ((int) (Math.random() * 60000) < 5000) {
					Thread.currentThread().sleep((int) (Math.random() * 3000));
				} else if ((int) (Math.random() * 60000) > 59000) {
					Thread.currentThread().sleep(
							60000 + (int) (Math.random() * 6000));
				} else if ((int) (Math.random() * 60000) < 1000) {
					Thread.currentThread().sleep(
							30000 + (int) (Math.random() * 3000));

				}
				// 判断是否掉线
				Color dcolor = robot
						.getPixelColor(WOW.diaoxianX, WOW.diaoxianY);
				if ((WOW.diaoxianCX[0] - WOW.gjRGB <= dcolor.getRed() && dcolor
						.getRed() <= WOW.diaoxianCD[0] + WOW.gjRGB)
						&& (WOW.diaoxianCX[1] - WOW.gjRGB <= dcolor.getGreen() && dcolor
								.getGreen() <= WOW.diaoxianCD[1] + WOW.gjRGB)
						&& (WOW.diaoxianCX[2] - WOW.gjRGB <= dcolor.getBlue() && dcolor
								.getBlue() <= WOW.diaoxianCD[2] + WOW.gjRGB)) {
					b = new WOWMisc();
					b.play(WOW.url, 5000, false);
					Thread.currentThread().sleep(5000);
					continue;
				}
				// 判断是否死亡
				Color kcolor = robot.getPixelColor(WOW.deadX, WOW.deadY);
				if ((WOW.diaoxianCX[0] - WOW.gjRGB <= kcolor.getRed() && kcolor
						.getRed() <= WOW.diaoxianCD[0] + WOW.gjRGB)
						&& (WOW.diaoxianCX[1] - WOW.gjRGB <= kcolor.getGreen() && kcolor
								.getGreen() <= WOW.diaoxianCD[1] + WOW.gjRGB)
						&& (WOW.diaoxianCX[2] - WOW.gjRGB <= kcolor.getBlue() && kcolor
								.getBlue() <= WOW.diaoxianCD[2] + WOW.gjRGB)) {
					b = new WOWMisc();
					b.play(WOW.url_resurrect, 20000, true);
					return;
				}
				// 第一次启动释放2技能
				long dates_td = new java.util.Date().getTime();
				if (wOWFishing.getDates() == wOWFishing.getDates_bf()) {
					wOWFishing.setDates_bf(0);
					wOWFishing.setDates(dates_td);
					robot.keyPress(KeyEvent.VK_2);
					Thread.currentThread().sleep(
							10 + (int) (Math.random() * 20));
					robot.keyRelease(KeyEvent.VK_2);
					Thread.currentThread().sleep(
							2500 + (int) (Math.random() * 2000));
				}
				// 第一次启动是风3技能
				if (wOWFishing.getDateh() == wOWFishing.getDateh_bf()) {
					wOWFishing.setDateh_bf(0);
					wOWFishing.setDateh(dates_td);
					robot.keyPress(KeyEvent.VK_3);
					Thread.currentThread().sleep(
							10 + (int) (Math.random() * 20));
					robot.keyRelease(KeyEvent.VK_3);
					Thread.currentThread().sleep(
							2500 + (int) (Math.random() * 2000));
				}
				// 10分钟释放2技能
				if (dates_td - wOWFishing.getDates() >= 600000 - (int) (Math
						.random() * 30000)) {
					wOWFishing.setDates(dates_td);
					robot.keyPress(KeyEvent.VK_2);
					Thread.currentThread().sleep(
							10 + (int) (Math.random() * 20));
					robot.keyRelease(KeyEvent.VK_2);
					Thread.currentThread().sleep(
							2500 + (int) (Math.random() * 2000));
				}
				// 60分钟释放3技能
				if (dates_td - wOWFishing.getDateh() >= 3600000 - (int) (Math
						.random() * 60000)) {
					wOWFishing.setDateh(dates_td);
					robot.keyPress(KeyEvent.VK_3);
					Thread.currentThread().sleep(
							10 + (int) (Math.random() * 20));
					robot.keyRelease(KeyEvent.VK_3);
					Thread.currentThread().sleep(
							2500 + (int) (Math.random() * 2000));
				}
				// 开始钓鱼
				// Thread.currentThread().sleep((int) (Math.random() * 1000));
				// move((int) (Math.random() * 1000),
				// (int) (Math.random() * 600));
				Thread.currentThread().sleep(1000+(int) (Math.random() * 1000));
				robot.keyPress(KeyEvent.VK_1);
				Thread.currentThread().sleep(10 + (int) (Math.random() * 20));
				robot.keyRelease(KeyEvent.VK_1);
				if ((int) (Math.random() * 1000) > 850) {
					Thread.currentThread().sleep((int) (Math.random() * 1000));
					robot.keyPress(KeyEvent.VK_1);
					Thread.currentThread().sleep(
							10 + (int) (Math.random() * 20));
					robot.keyRelease(KeyEvent.VK_1);
				}
				if ((int) (Math.random() * 1000) > 850) {
					Thread.currentThread().sleep((int) (Math.random() * 1000));
					robot.keyPress(KeyEvent.VK_1);
					Thread.currentThread().sleep(
							10 + (int) (Math.random() * 20));
					robot.keyRelease(KeyEvent.VK_1);
				}
				long datekey1 = new java.util.Date().getTime();
				System.out.print("下杆:" + new Date().getSeconds() + "");
				Thread.currentThread().sleep(
						2000 + (int) (Math.random() * 1000));
				// 巡店
				findFishing();
				// 监控声音软件变化
				while (true) {
					long datenow = new java.util.Date().getTime();
					Color color1 = robot.getPixelColor(wOWFishing.exe[0],
							wOWFishing.exe[1]);
					// 符合钓鱼
					if (color1.getRed() == 255 && color1.getGreen() == 0
							&& color1.getBlue() == 0) {
						if (datenow - datekey1 >= 6000) {
							shiqu();
							if (datenow - datekey1 <= 10000) {
								Thread.currentThread().sleep(
										10000 + (int) (Math.random() * 2000));
							}
							break;
						}
					}
					// 超过17秒钓鱼
					if (datenow - datekey1 >= 17000 + (int) (Math.random() * 1000)) {
						System.out.println("...");
						break;
					}

				}
			}
		} catch (Exception e) {

		}
	}

	// 鼠标移动
	public static void mousemove(int x, int y) {
		try {
			Robot robot = new Robot();
			robot.mouseMove(x, y);
			Thread.currentThread().sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 控制鼠标移动
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

	// 获取坐标直线长度
	public static int calcDistance(int x, int y) {

		int b = Math.abs((x - mouseoldx));
		int a = Math.abs((y - mouseoldy));
		return (int) Math.sqrt((a * a + b * b));

	}

	// 拾取
	public static void shiqu() {
		try {
			Robot robot = new Robot();
			Thread.currentThread().sleep((int) (50 + Math.random() * 200));
			robot.mousePress(MouseEvent.BUTTON3_MASK);
			Thread.currentThread().sleep(10 + (int) (Math.random() * 20));
			robot.mouseRelease(MouseEvent.BUTTON3_MASK);
			System.out.println("上鱼:" + new Date().getSeconds() + "");
		} catch (Exception e) {

		}

	}

	// 取白点
	public static void findFishing() {
		fish = false;
		BufferedImage screenshot = null;
		try {
			screenshot = (new Robot()).createScreenCapture(new Rectangle(
					areaPosition[0][0], areaPosition[0][1], areaPosition[1][0]
							- areaPosition[0][0], areaPosition[1][1]
							- areaPosition[0][1]));
			screenshot = grayImage(screenshot);
			int w = screenshot.getWidth();
			int h = screenshot.getHeight();
			// ImageIO.write(screenshot, "bmp",
			// new File("E:/外掛/java外挂/wow/aa.bmp"));
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
			int hei = Integer.parseInt(list.get(0).toString());
			int suix = (int) (Math.random() * 6);
			int suiy = (int) (Math.random() * 15);
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					color = new Color(screenshot.getRGB(i, j));
					if (WOW.start == 1) {
						if (color.getRed() == bai) {
							move(areaPosition[0][0] + i + suix,
									areaPosition[0][1] + j + suiy);
							fish = true;
							return;
						}
					} else {
						if (color.getRed() == hei) {
							move(areaPosition[0][0] + i + suix,
									areaPosition[0][1] + j + suiy);
							fish = true;
							return;
						}
					}
				}
			}

			System.out.println("未找到...");
		} catch (Exception e) {

		} finally {
		}
	}

	// 黑白
	public static BufferedImage grayImage(BufferedImage srcImg) {
		int iw = srcImg.getWidth();
		int ih = srcImg.getHeight();
		Graphics2D srcG = srcImg.createGraphics();
		RenderingHints rhs = srcG.getRenderingHints();

		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp theOp = new ColorConvertOp(cs, rhs);
		BufferedImage dstImg = new BufferedImage(iw, ih,
				BufferedImage.TYPE_3BYTE_BGR);

		theOp.filter(srcImg, dstImg);
		return dstImg;
	}

}
