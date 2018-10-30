package wow.twow;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import wow.twow.support.WOW;

public class WOWAWTFishingThread extends Thread {

	private static WOWAWTFishing wowAWTFishing;
	public WOWMisc wowMisc = null;

	static TargetDataLine line;
	static int mouseoldx = 0;
	static int mouseoldy = 0;
	static Date st_key_time = null;
	static int st_key_int = 0;
	static boolean fish = false;

	static int shui = 0;
	static int shui_0 = 0;
	static int shui_1 = 0;
	static int shui_5 = 0;

	static boolean first = true;

	public void init(WOWAWTFishing fishing) {
		wowAWTFishing = fishing;
	}

	public void run() {
		try {
			Robot robot = new Robot();
			sleep(2000);
			Point mousePoint = MouseInfo.getPointerInfo().getLocation();
			mouseoldx = mousePoint.x;
			mouseoldy = mousePoint.y;
			Date dateclose = new Date();

			while (true) {
				if (wowAWTFishing.wowJCheckBox_seep.isSelected()) {
					shui = (int) (Math.random() * 100);
					shui_1 = (int) (Math.random() * 1000);
					shui_5 = (int) (Math.random() * 5000);
					if ((int) (Math.random() * 100) > 95) {
						shui_0 = (int) (Math.random() * 400);
					} else if ((int) (Math.random() * 100) > 90) {
						shui_0 = (int) (Math.random() * 200);
					} else if ((int) (Math.random() * 100) > 70) {
						shui_0 = (int) (Math.random() * 50);
					}
				}

				// 定時
				if (wowAWTFishing.wowJCheckBox_times.isSelected()) {
					if (new Date().getTime() - dateclose.getTime() > Integer
							.parseInt(wowAWTFishing.wowJTextField_times
									.getText()) * 60 * 1000) {
						lushi();
						outLine();
						closewindow();
						return;
					}
				}
				// GM
				if (wowAWTFishing.wowJCheckBox_gm.isSelected()) {
					Color color = robot.getPixelColor(WOW.gmxy[0], WOW.gmxy[1]);
					if (Math.abs(color.getRed() - WOW.gmrgb[0]) <= WOW.gjRGB
							&& Math.abs(color.getGreen() - WOW.gmrgb[1]) <= WOW.gjRGB
							&& Math.abs(color.getBlue() - WOW.gmrgb[2]) <= WOW.gjRGB) {
						wowAWTFishing.println("GM警报");
						wowMisc = new WOWMisc();
						wowMisc.play(WOW.url_gm, 5000, true);
						if (wowAWTFishing.wowJCheckBox_gm_hf.isSelected()) {
							String str[] = wowAWTFishing.wowJTextField_gm
									.getText().split("#");
							mousemove(WOW.gmxy[0], WOW.gmxy[1]);
							sleep(3000);
							robot.mousePress(MouseEvent.BUTTON1_MASK);
							keySleep();
							robot.mouseRelease(MouseEvent.BUTTON1_MASK);
							sleep(2000);
							wowAWTFishing.println("GM警报---密語");
							for (int i = 0; i < str.length; i++) {
								MGM(str[i]);
							}
						}
						lushi();
						outLine();
						closewindow();
						return;
					}

				}
				// 判断是否掉线
				if (wowAWTFishing.wowJCheckBox_line.isSelected()) {
					Color dcolor = robot.getPixelColor(WOW.diaoxianX,
							WOW.diaoxianY);
					if ((WOW.diaoxianCX[0] - WOW.gjRGB <= dcolor.getRed() && dcolor
							.getRed() <= WOW.diaoxianCD[0] + WOW.gjRGB)
							&& (WOW.diaoxianCX[1] - WOW.gjRGB <= dcolor
									.getGreen() && dcolor.getGreen() <= WOW.diaoxianCD[1]
									+ WOW.gjRGB)
							&& (WOW.diaoxianCX[2] - WOW.gjRGB <= dcolor
									.getBlue() && dcolor.getBlue() <= WOW.diaoxianCD[2]
									+ WOW.gjRGB)) {
						wowMisc = new WOWMisc();
						wowMisc.play(WOW.url, 5000, false);
						sleep(5000);
						return;
					}
				}
				// 判断是否死亡
				if (wowAWTFishing.wowJCheckBox_dead.isSelected()) {
					Color kcolor = robot.getPixelColor(WOW.deadX, WOW.deadY);
					if ((WOW.deadCX[0] - WOW.gjRGB <= kcolor.getRed() && kcolor
							.getRed() <= WOW.deadCD[0] + WOW.gjRGB)
							&& (WOW.deadCX[1] - WOW.gjRGB <= kcolor.getGreen() && kcolor
									.getGreen() <= WOW.deadCD[1] + WOW.gjRGB)
							&& (WOW.deadCX[2] - WOW.gjRGB <= kcolor.getBlue() && kcolor
									.getBlue() <= WOW.deadCD[2] + WOW.gjRGB)) {
						wowMisc = new WOWMisc();
						wowMisc.play(WOW.url_resurrect, 20000, true);
						return;
					}
				}
				// 第一次启动释放2技能
				long dates_td = new java.util.Date().getTime();
				if (first) {
					first = false;
					if (wowAWTFishing.wowJCheckBox_start.isSelected()) {
						if (wowAWTFishing.wowJCheckBox_10.isSelected()) {
							if (wowAWTFishing.getDates() == wowAWTFishing
									.getDates_bf()) {
								wowAWTFishing.setDates_bf(0);
								wowAWTFishing.setDates(dates_td);
								robot.keyPress(KeyEvent.VK_2);
								keySleep();
								robot.keyRelease(KeyEvent.VK_2);
								sleep(2000 + shui_1);
								wowAWTFishing.println("使用10分鐘魚餌");
							}
						}
						// 第一次启动是风3技能
						if (wowAWTFishing.wowJCheckBox_mf.isSelected()) {
							if (wowAWTFishing.getDateh() == wowAWTFishing
									.getDateh_bf()) {
								wowAWTFishing.setDateh_bf(0);
								wowAWTFishing.setDateh(dates_td);
								robot.keyPress(KeyEvent.VK_3);
								keySleep();
								robot.keyRelease(KeyEvent.VK_3);
								sleep(2000 + shui_1);
								wowAWTFishing.println("使用木筏");
							}
						}
					}
				}
				// 10分钟释放2技能
				if (wowAWTFishing.wowJCheckBox_10.isSelected()) {
					if (dates_td - wowAWTFishing.getDates() > 602000) {
						sleep(2000);
						wowAWTFishing.setDates(dates_td);
						robot.keyPress(KeyEvent.VK_2);
						keySleep();
						robot.keyRelease(KeyEvent.VK_2);
						sleep(2000 + shui_1);
						wowAWTFishing.println("使用10分鐘魚餌");
					}
				}
				// 8分钟释放3技能
				if (wowAWTFishing.wowJCheckBox_mf.isSelected()) {
					if (dates_td - wowAWTFishing.getDateh() >= 500000) {
						sleep(2000);
						wowAWTFishing.setDateh(dates_td);
						robot.keyPress(KeyEvent.VK_3);
						keySleep();
						robot.keyRelease(KeyEvent.VK_3);
						sleep(2000 + shui_1);
						wowAWTFishing.println("使用木筏");
					}
				}
				// 开始钓鱼
				sleep(1000 + shui_0);
				if ((int) (Math.random() * 100) > 95) {
					sleep(2000 + shui_5);
				} else if ((int) (Math.random() * 100) > 90) {
					sleep(2000 + shui_1);
				}
				robot.keyPress(KeyEvent.VK_1);
				keySleep();
				robot.keyRelease(KeyEvent.VK_1);
				wowAWTFishing.wowJLabel_t_x_v.setText("0");
				if (wowAWTFishing.wowJCheckBox_next.isSelected()) {
					if ((int) (Math.random() * 1000) > 900) {
						sleep(1000 + shui);
						robot.keyPress(KeyEvent.VK_1);
						keySleep();
						robot.keyRelease(KeyEvent.VK_1);
					}
				}
				st_key_time = new java.util.Date();
				long datekey1 = new java.util.Date().getTime();
				int temp1 = new Date().getSeconds();
				wowAWTFishing.print("下杆：" + (temp1 < 10 ? "0" + temp1 : temp1));
				sleep(3000 + shui_1);
				// 巡店
				findFishing();
				// 监控声音软件变化
				wowAWTFishing.print(" ---- ");
				listenerMisc(datekey1);
				automisc();

			}
		} catch (Exception e) {

		}
	}

	public static void automisc() {
		if (wowAWTFishing.wowJCheckBox_automisc.isSelected()) {
			wowAWTFishing.wowJCheckBox_automisc.setSelected(false);
			int size = Integer
					.parseInt(wowAWTFishing.wowJLabel_t_x_v.getText());
			size = size / 10 * 7;
			wowAWTFishing.miscSize = size;
			wowAWTFishing.wowJTextField_misc.setText(size + "");
			wowAWTFishing.println("自动初始化声音值");
		}
	}

	// 鼠标移动
	public static void mousemove(int x, int y) {
		try {
			Robot robot = new Robot();
			robot.mouseMove(x, y);
			sleep(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 控制鼠标移动
	public static void move(int x, int y) {
		if (wowAWTFishing.wowJCheckBox_mouse.isSelected()) {
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
					mousemove(mouseoldx + (int) (zx * i), mouseoldy
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
			mouseoldx = x;
			mouseoldy = y;
			mousemove(x, y);
		} else {
			mousemove(x, y);
		}
		wowAWTFishing.wowJTextField_y_x.setText(x + "");
		wowAWTFishing.wowJTextField_y_y.setText(y + "");

	}

	// 获取坐标直线长度
	public static int calcDistance(int x, int y) {

		int b = Math.abs((x - mouseoldx));
		int a = Math.abs((y - mouseoldy));
		return (int) Math.sqrt((a * a + b * b));

	}

	// 取白点
	public static void findFishing() {
		fish = false;
		BufferedImage screenshot = null;
		try {
			screenshot = (new Robot()).createScreenCapture(new Rectangle(
					wowAWTFishing.areaPosition[0][0],
					wowAWTFishing.areaPosition[0][1],
					wowAWTFishing.areaPosition[1][0]
							- wowAWTFishing.areaPosition[0][0],
					wowAWTFishing.areaPosition[1][1]
							- wowAWTFishing.areaPosition[0][1]));
			screenshot = grayImage(screenshot);
			int w = screenshot.getWidth();
			int h = screenshot.getHeight();
			// ImageIO.write(screenshot, "bmp",
			// new File("E:/外掛/java外挂/wow/aa.bmp"));
			Color color = null;
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
			list = null;
			int suix = (int) (Math.random() * 5);
			int suiy = (int) (Math.random() * 5);
			boolean s255 = wowAWTFishing.wowJRadioButton_255.isSelected();
			boolean s0 = wowAWTFishing.wowJRadioButton_0.isSelected();
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					color = new Color(screenshot.getRGB(i, j));
					if (s255) {
						if (color.getRed() == bai) {
							wowAWTFishing.wowJTextField_y_r.setText(color
									.getRed()
									+ "");
							wowAWTFishing.wowJTextField_y_g.setText(color
									.getGreen()
									+ "");
							wowAWTFishing.wowJTextField_y_b.setText(color
									.getBlue()
									+ "");
							move(wowAWTFishing.areaPosition[0][0] + i + suix,
									wowAWTFishing.areaPosition[0][1] + j + suiy);
							fish = true;
							return;
						}
					} else if (s0) {
						if (color.getRed() == hei) {
							wowAWTFishing.wowJTextField_y_r.setText(color
									.getRed()
									+ "");
							wowAWTFishing.wowJTextField_y_g.setText(color
									.getGreen()
									+ "");
							wowAWTFishing.wowJTextField_y_b.setText(color
									.getBlue()
									+ "");
							move(wowAWTFishing.areaPosition[0][0] + i + suix,
									wowAWTFishing.areaPosition[0][1] + j + suiy);
							fish = true;
							return;
						}
					}
				}
			}

			wowAWTFishing.println("未找到");
			wowAWTFishing.wowJTextField_no.setText(Integer
					.parseInt(wowAWTFishing.wowJTextField_no.getText())
					+ 1 + "");
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

	public void shutDown() {
		line.close();
	}

	// 监听麦克声音大小
	public void listenerMisc(long datekey1) {
		AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
		float rate = 96000;
		int sampleSize = 8;
		boolean bigEndian = true;
		int channels = 2;
		AudioFormat format = new AudioFormat(encoding, rate, sampleSize,
				channels, (sampleSize / sampleSize) * channels, rate, bigEndian);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if (!AudioSystem.isLineSupported(info)) {
			shutDown();
			return;
		}
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format, line.getBufferSize());
		} catch (Exception ex) {
			shutDown();
			return;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int frameSizeInBytes = format.getFrameSize();
		int bufferLengthInFrames = line.getBufferSize() / sampleSize;
		int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;

		byte[] data = new byte[bufferLengthInBytes];
		int numBytesRead;
		line.start();
		while (true) {
			Date date = new Date();
			if (date.getTime() - datekey1 >= 21500) {
				int temp1 = new Date().getSeconds();
				wowAWTFishing.println("超时："
						+ (temp1 < 10 ? "0" + temp1 : temp1));
				wowAWTFishing.wowJTextField_no.setText(Integer
						.parseInt(wowAWTFishing.wowJTextField_no.getText())
						+ 1 + "");
				try {
					line.stop();
					line.close();
					line = null;
					out.close();
				} catch (IOException ex) {
				}
				return;
			}
			while (true) {
				if ((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1) {
					break;
				}
				out.write(data, 0, numBytesRead);
				if (new Date().getTime() - date.getTime() > 10) {
					break;
				}
			}
			byte b[] = out.toByteArray();
			for (int i = 0; i < b.length; i++) {
				if (Math.abs(b[i] * 10) > Integer
						.parseInt(wowAWTFishing.wowJLabel_t_x_v.getText())) {
					wowAWTFishing.wowJLabel_t_x_v.setText(Math.abs(b[i] * 10)
							+ "");
				}

			}
			for (int i = 0; i < b.length; i++) {
				if (Math.abs(b[i] * 10) >= wowAWTFishing.miscSize) {
					shiqu();
					try {
						line.stop();
						line.close();
						line = null;
						out.close();
					} catch (IOException ex) {
						line = null;
						out = null;
					}
					return;
				}

			}
			out = new ByteArrayOutputStream();
		}
	}

	// 拾取

	public static void shiqu() {
		try {

			Robot robot = new Robot();
			// 小於7秒進行記錄
			if (new Date().getTime() - st_key_time.getTime() <= 7000) {
				st_key_int++;
			} else {
				st_key_int = 0;
			}
			// 超過2此小於7秒 進行展廳（10-30秒）
			if (st_key_int >= 2) {
				wowAWTFishing.println("急速休停");
				st_key_int = 0;
				sleep(10000 + shui_5);
			} else {
				sleep(shui_0);
				robot.mousePress(MouseEvent.BUTTON3_MASK);
				keySleep();
				robot.mouseRelease(MouseEvent.BUTTON3_MASK);
				int temp1 = new Date().getSeconds();
				wowAWTFishing.println("上鱼："
						+ (temp1 < 10 ? "0" + temp1 : temp1));
				wowAWTFishing.wowJTextField_ok.setText(Integer
						.parseInt(wowAWTFishing.wowJTextField_ok.getText())
						+ 1 + "");
			}
		} catch (Exception e) {

		}

	}

	// 拷贝
	public static void copy(String str) {
		StringSelection stsel = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel,
				stsel);
	}

	public static void MGM(String str) {
		try {
			Robot robot = new Robot();
			copy(str);
			for (int i = WOW.mgm[1]; i < WOW.mgm[2]; i += 10) {
				sleep(300);
				move(WOW.mgm[0], i);
				sleep(300);
				robot.mousePress(MouseEvent.BUTTON1_MASK);
				keySleep();
				robot.mouseRelease(MouseEvent.BUTTON1_MASK);
				sleep(300);
			}
			robot.keyPress(17);
			sleep(100);
			robot.keyPress(KeyEvent.VK_V);
			keySleep();
			robot.keyRelease(KeyEvent.VK_V);
			sleep(100);
			robot.keyRelease(17);
			sleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			keySleep();
			robot.keyRelease(KeyEvent.VK_ENTER);
			sleep(5000 + shui_5);
		} catch (Exception e) {

		}
	}

	// 下線
	public static void outLine() {
		try {
			if (wowAWTFishing.wowJCheckBox_out_line.isSelected()) {
				wowAWTFishing.println("下线");
				Robot robot = new Robot();
				sleep(1000);
				mousemove(WOW.outline[0], WOW.outline[1]);
				sleep(1000);
				robot.mousePress(MouseEvent.BUTTON1_MASK);
				keySleep();
				robot.mouseRelease(MouseEvent.BUTTON1_MASK);
				sleep(1000);
				mousemove(WOW.outline[2], WOW.outline[3]);
				sleep(1000);
				robot.mousePress(MouseEvent.BUTTON1_MASK);
				keySleep();
				robot.mouseRelease(MouseEvent.BUTTON1_MASK);
				sleep(60000);
			}
		} catch (Exception e) {
		}
	}

	// 爐石
	public static void lushi() {
		try {
			if (wowAWTFishing.wowJCheckBox_gm_ls.isSelected()) {
				Robot robot = new Robot();
				sleep(2000);
				robot.keyPress(KeyEvent.VK_4);
				keySleep();
				robot.keyRelease(KeyEvent.VK_4);
				wowAWTFishing.println("炉石");
				sleep(20000);

			}
		} catch (Exception e) {
		}
	}

	// 關機
	public static void closewindow() {

		try {
			if (wowAWTFishing.wowJCheckBox_closewindow.isSelected()) {
				wowAWTFishing.println("關機");
				Robot robot = new Robot();
				sleep(2000);
				robot.keyPress(KeyEvent.VK_WINDOWS);
				keySleep();
				robot.keyRelease(KeyEvent.VK_WINDOWS);
				sleep(5000);
				mousemove(WOW.closew[0], WOW.closew[1]);
				robot.mousePress(MouseEvent.BUTTON1_MASK);
				keySleep();
				robot.mouseRelease(MouseEvent.BUTTON1_MASK);
				sleep(5000);
				mousemove(WOW.closew[2], WOW.closew[3]);
				robot.mousePress(MouseEvent.BUTTON1_MASK);
				keySleep();
				robot.mouseRelease(MouseEvent.BUTTON1_MASK);
			}
		} catch (Exception e) {
		}
	}

	public static void keySleep() {
		try {
			Thread.currentThread().sleep(20);
		} catch (InterruptedException e) {
		}
	}

	public static void sleep(int l) {
		try {
			Thread.currentThread().sleep(l);
		} catch (InterruptedException e) {
		}
	}

}
