package wow.twow;

import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import wow.twow.support.WOW;

public class WOWOnline {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JIntellitype.getInstance().registerHotKey(1000, JIntellitype.MOD_ALT,
				(int) 'W');
		JIntellitype.getInstance().registerHotKey(3000, JIntellitype.MOD_ALT,
				(int) 'E');
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("	数    量 : Alt+W");
		System.out.println("	启    动 : Alt+E");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("挂机数量:1");
		HotkeyListener HL = new HotkeyListener() {
			// 取值次数
			@SuppressWarnings( { "static-access", "deprecation" })
			public void onHotKey(int arg0) {
				WOWMisc b = new WOWMisc();
				try {
					Robot robot = new Robot();
					if (arg0 == 1000) {
						if (WOW.shu == 1) {
							++WOW.shu;
						} else {
							--WOW.shu;
						}
						System.out.println("挂机数量:" + WOW.shu);
						b.play(WOW.url_start, 2000, false);
					} else if (arg0 == 3000) {
						System.out.println("启动...");
						b.play(WOW.url_start, 2000, false);
						Thread.currentThread().sleep(10000);
						int diao1i = 0;
						int diao2i = 0;
						while (true) {
							Random random = new Random();
							Thread.currentThread().sleep(2000);
							if (WOW.shu == 1) {

							} else if (WOW.shu == 2) {
								robot.keyPress(KeyEvent.VK_ALT);
								Thread.currentThread().sleep(100);
								robot.keyPress(KeyEvent.VK_TAB);
								Thread.currentThread().sleep(100);
								robot.keyRelease(KeyEvent.VK_TAB);
								Thread.currentThread().sleep(100);
								robot.keyRelease(KeyEvent.VK_ALT);
								if (WOW.diangqian == 1) {
									WOW.diangqian = 2;
								} else {
									WOW.diangqian = 1;
								}
							}
							Thread.currentThread().sleep(
									5000 + (int) (Math.random() * 5000));
							Color color = robot.getPixelColor(WOW.dengluX,
									WOW.dengluY);
							Color dcolor = robot.getPixelColor(WOW.diaoxianX,
									WOW.diaoxianY);
							// 判断掉线，防服务器重启及网络掉线
							if ((WOW.diaoxianCX[0] - WOW.gjRGB <= dcolor
									.getRed() && dcolor.getRed() <= WOW.diaoxianCD[0]
									+ WOW.gjRGB)
									&& (WOW.diaoxianCX[1] - WOW.gjRGB <= dcolor
											.getGreen() && dcolor.getGreen() <= WOW.diaoxianCD[1]
											+ WOW.gjRGB)
									&& (WOW.diaoxianCX[2] - WOW.gjRGB <= dcolor
											.getBlue() && dcolor.getBlue() <= WOW.diaoxianCD[2]
											+ WOW.gjRGB)) {
								if (WOW.shu == 1) {
									diao1i = 2;
								} else {
									diao2i = 2;
								}
								b.play(WOW.url, 5000, false);
								robot.mouseMove(WOW.diaoxianX, WOW.diaoxianY);
								Thread.currentThread().sleep(100);
								robot.mousePress(MouseEvent.BUTTON1_MASK);
								Thread.currentThread().sleep(100);
								robot.mouseRelease(MouseEvent.BUTTON1_MASK);
								Thread.currentThread().sleep(1000);
								copy(WOW.user[WOW.diangqian - 1]);
								Thread.currentThread().sleep(100);
								robot.mouseMove(WOW.userX, WOW.userY);
								Thread.currentThread().sleep(100);
								robot.mousePress(MouseEvent.BUTTON1_MASK);
								Thread.currentThread().sleep(100);
								robot.mouseRelease(MouseEvent.BUTTON1_MASK);
								Thread.currentThread().sleep(100);
								for (int o = 0; o < WOW.user[WOW.diangqian - 1]
										.length(); o++) {
									Thread.currentThread().sleep(100);
									robot.keyPress(8);
									Thread.currentThread().sleep(100);
									robot.keyRelease(8);
								}
								Thread.currentThread().sleep(100);
								robot.keyPress(17);
								Thread.currentThread().sleep(100);
								robot.keyPress(KeyEvent.VK_V);
								Thread.currentThread().sleep(100);
								robot.keyRelease(KeyEvent.VK_V);
								Thread.currentThread().sleep(100);
								robot.keyRelease(17);
								copy(WOW.password[WOW.diangqian - 1]);
								Thread.currentThread().sleep(100);
								robot.mouseMove(WOW.passwordX, WOW.passwordY);
								robot.mousePress(MouseEvent.BUTTON1_MASK);
								Thread.currentThread().sleep(100);
								robot.mouseRelease(MouseEvent.BUTTON1_MASK);
								Thread.currentThread().sleep(100);
								for (int o = 0; o < WOW.password[WOW.diangqian - 1]
										.length(); o++) {
									Thread.currentThread().sleep(100);
									robot.keyPress(8);
									Thread.currentThread().sleep(100);
									robot.keyRelease(8);
								}
								Thread.currentThread().sleep(100);
								robot.keyPress(17);
								Thread.currentThread().sleep(100);
								robot.keyPress(KeyEvent.VK_V);
								Thread.currentThread().sleep(100);
								robot.keyRelease(KeyEvent.VK_V);
								Thread.currentThread().sleep(100);
								robot.keyRelease(17);
								Thread.currentThread().sleep(100);
								robot.mouseMove(WOW.zhanghaoX, WOW.zhanghaoY);
								Thread.currentThread().sleep(100);
								robot.mousePress(MouseEvent.BUTTON1_MASK);
								Thread.currentThread().sleep(100);
								robot.mouseRelease(MouseEvent.BUTTON1_MASK);
								System.out.println(WOW.diangqian + "掉线登录");
							}
							// 判断人物登录界面，防止副本重启
							else if ((WOW.dengluCX[0] - WOW.gjRGB <= color
									.getRed() && color.getRed() <= WOW.dengluCD[0]
									+ WOW.gjRGB)
									&& (WOW.dengluCX[1] - WOW.gjRGB <= color
											.getGreen() && color.getGreen() <= WOW.dengluCD[1]
											+ WOW.gjRGB)
									&& (WOW.dengluCX[2] - WOW.gjRGB <= color
											.getBlue() && color.getBlue() <= WOW.dengluCD[2]
											+ WOW.gjRGB)) {
								if (WOW.shu == 1) {
									diao1i = 1;
								} else {
									diao2i = 1;
								}
								Thread.currentThread().sleep(100);
								robot.mouseMove(WOW.dengluX, WOW.dengluY);
								Thread.currentThread().sleep(100);
								robot.mousePress(MouseEvent.BUTTON1_MASK);
								Thread.currentThread().sleep(100);
								robot.mouseRelease(MouseEvent.BUTTON1_MASK);
								System.out.println(WOW.diangqian + "人物界面登录");
							} else {
								if (WOW.shu == 1) {
									if (diao1i == 2) {
										b.play(WOW.url, 20000, true);
										return;
									}
								} else {
									if (diao2i == 2) {
										b.play(WOW.url, 20000, true);
										return;
									}
								}
								Thread.currentThread().sleep(
										5000 + (int) (Math.random() * 5000));
								robot.keyPress(KeyEvent.VK_SPACE);
								robot.keyRelease(KeyEvent.VK_SPACE);
								if (WOW.shu == 1) {
									Thread.currentThread()
											.sleep(
													30000 + (int) (Math
															.random() * 30000));
								} else {
									Thread.currentThread()
											.sleep(
													15000 + (int) (Math
															.random() * 15000));
								}

							}
						}
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}

		};
		JIntellitype.getInstance().addHotKeyListener(HL);
	}

	static public void copy(String str) {
		StringSelection stsel = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel,
				stsel);
	}

}
