package wow.twow;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import wow.twow.support.WOW;

/**
 * @comments :钓鱼
 * @project : wow
 * @author : chenyanji
 * @date : Dec 22, 2010 10:34:20 AM
 */
public class WOWFishing {

	/**
	 * @comments :
	 * @param args
	 * @author : chenyanji
	 * @date : Dec 6, 2010 11:54:44 AM
	 */
	static int areaPosition[][] = new int[2][2];

	static WOWFishingThread dyt = null;

	static long dateh = new java.util.Date().getTime();
	static long dateh_bf = dateh;
	static long dates = new java.util.Date().getTime();
	static long dates_bf = dates;
	static WOWFishing dy = null;
	static int stat = 0;
	static int start = 2;
	static int[] exe = new int[2];

	public static void main(String[] aa) {
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("	取    点 : Alt+W");
		System.out.println("	启    动 : Alt+E");
		System.out.println("	暂    停 : Alt+Q");
		System.out.println("	钓    鱼 ： 动作条1-1");
		System.out.println("	鱼饵10分 ： 动作条1-2");
		System.out.println("	鱼饵60分 ： 动作条1-3");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		JIntellitype.getInstance().registerHotKey(1000, JIntellitype.MOD_ALT, (int) 'W');
		JIntellitype.getInstance().registerHotKey(2000, JIntellitype.MOD_ALT, (int) 'E');
		JIntellitype.getInstance().registerHotKey(3000, JIntellitype.MOD_ALT, (int) 'Q');
		dy = new WOWFishing();
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {

			// 取值次数
			int i = 0;

			@SuppressWarnings({ "static-access", "deprecation" })
			public void onHotKey(int arg0) {
				try {
					@SuppressWarnings("unused")
					Robot robot = new Robot();
					WOWMisc b = new WOWMisc();
					if (arg0 == 1000) {
						if (stat == 0) {
							if (i == 0) {
								Point mousePoint = MouseInfo.getPointerInfo().getLocation();
								areaPosition[0][0] = mousePoint.x;
								areaPosition[0][1] = mousePoint.y;

								System.out.println("左上坐标：[x:" + areaPosition[0][0] + " y:" + areaPosition[0][1] + "]");
								i++;
								b.play(WOW.url_start, 2000, false);
							} else if (i == 1) {
								Point mousePoint = MouseInfo.getPointerInfo().getLocation();
								areaPosition[1][0] = mousePoint.x;
								areaPosition[1][1] = mousePoint.y;
								System.out.println("右下坐标：[x:" + areaPosition[1][0] + " y:" + areaPosition[1][1] + "]");
								i++;

								b.play(WOW.url_start, 2000, false);
							} else if (i == 2) {
								Point mousePoint = MouseInfo.getPointerInfo().getLocation();
								exe[0] = mousePoint.x;
								exe[1] = mousePoint.y;
								System.out.println("软件取点:[x:" + exe[0] + ",y:" + exe[1]);
								i++;
								stat = 2;
								b.play(WOW.url_start, 2000, false);
							} else {
								b.play(WOW.url_error, 2000, false);
							}
						} else {
							b.play(WOW.url_error, 2000, false);
						}
					} else if (arg0 == 2000) {
						if (stat == 2 || start == 0) {
							i = 0;
							System.out.println("启动...");
							dyt = new WOWFishingThread();
							try {
								dyt.init(areaPosition, dy);
								dyt.start();
							} catch (Exception gg) {
								System.out.println("启动失败");
							}
							stat = 3;
							start = 1;
							b.play(WOW.url_start, 2000, false);
						} else {
							b.play(WOW.url_error, 2000, false);
						}
					} else if (arg0 == 3000) {
						if (stat == 3 && start == 1) {
							System.out.println("停止...");
							try {
								try {
									dyt.b.stop();
								} catch (Exception stb) {

								}
								dyt.stop();
								dyt = null;
							} catch (Exception aa) {
								System.out.println("停止失败...");
							}

							stat = 0;
							start = 0;
							b.play(WOW.url_start, 2000, false);
						} else {
							b.play(WOW.url_error, 2000, false);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public long getDateh() {
		return this.dateh;
	}

	public void setDateh(long dateh) {
		this.dateh = dateh;
	}

	public long getDateh_bf() {
		return this.dateh_bf;
	}

	public void setDateh_bf(long dateh_bf) {
		this.dateh_bf = dateh_bf;
	}

	public long getDates() {
		return this.dates;
	}

	public void setDates(long dates) {
		this.dates = dates;
	}

	public long getDates_bf() {
		return this.dates_bf;
	}

	public void setDates_bf(long dates_bf) {
		this.dates_bf = dates_bf;
	}

}
