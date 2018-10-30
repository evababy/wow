package D3;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

/**
 * @comment 自动按键，根据不同职业
 * @author Sylvanas
 * @date 2015-10-24 下午1:28:41
 * @project wow
 */

public class D3_Automatic_Kye {

	public static boolean start = false;

	public static long key_q = 30 * 1000 - 600;
	/**
	 * 野蛮人：技能的持续、冷却时间
	 */
	public static long ymr_key_34 = 120 * 1000 - 5000;
	public static long ymr_kye_1 = 30 * 1000 - 600;

	public static Robot robot = null;
	// 职业
	public static String profession = null;

	public static int kye_sleep = 50;

	/**
	 * @comment
	 * @author Sylvanas
	 * @date 2015-10-24 下午1:28:41
	 * @param args
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws AWTException, InterruptedException {
		if (args.length > 0) {
			profession = args[0];
		} else {
			// profession = "死灵法师";
			profession = "野蛮人";
		}
		robot = new Robot();

		JIntellitype.getInstance().registerHotKey(2000, JIntellitype.MOD_SHIFT, (int) 'W');
		Thread.sleep(100);
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {

			Robot robot = new Robot();

			@Override
			public void onHotKey(int arg0) {
				try {
					if (arg0 == 2000) {
						if (start) {
							start = false;
							System.err.println();
							System.err.println("暂停");
						} else {
							start = true;
							System.err.println();
							System.err.println("启动");
							System.err.println();
							Thread.sleep(1000);
							if (profession.equals("野蛮人")) {
								color_thread(robot, profession, "l", 100);
							} else if (profession.equals("死灵法师")) {
								color_thread(robot, profession, "l", 100);
								color_thread(robot, profession, "r", 10);
							}
						}
					}
				} catch (Exception e) {
				}
			}
		});
		System.err.println();
		System.err.println("*******暗黑3自动按键  【shift+W】启动/暂停 ");
		System.err.println();
		System.err.println("*******野蛮人	【3键|4键=>120秒、1键=>自动减伤、Q键=>自动加血】");
		System.err.println();
		System.err.println("*******死灵法师	【3毽=>80%+能量自动释放、Q=>自动加血】");
		System.err.println();

	}

	/**
	 * @comment 自动释放技能，1为减伤技，Q加血
	 * @author Sylvanas
	 * @date 2018年10月29日 上午8:32:05
	 * @param robot
	 */
	public static void color_thread(final Robot robot, final String profession, final String type, final int sleep) {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		cachedThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					long currtime = System.currentTimeMillis();
					long key_q_time = new Date().getTime() - key_q;
					long ymr_key_34_time = new Date().getTime() - ymr_key_34;
					long ymr_kye_1_time = new Date().getTime() - ymr_kye_1;
					double fen = 1.0;
					while (start) {
						Thread.sleep(sleep);
						if (profession.equals("野蛮人")) {
							key(robot, KeyEvent.VK_3, ymr_key_34_time, ymr_key_34);
							key(robot, KeyEvent.VK_4, ymr_key_34_time, ymr_key_34);
							ymr_key_34_time = System.currentTimeMillis();
							fen = D3_Image.getPercent(robot, type);
							if (fen <= 0.2) {
								key(robot, KeyEvent.VK_Q, key_q_time, key_q);
								key(robot, KeyEvent.VK_1, ymr_kye_1_time, ymr_kye_1);
							} else if (fen <= 0.35) {
								if (System.currentTimeMillis() - currtime > 2000) {
									long l = key(robot, KeyEvent.VK_Q, key_q_time, key_q);
									currtime = l > 0 ? l : currtime;
								}
								if (System.currentTimeMillis() - currtime > 2000) {
									long l = key(robot, KeyEvent.VK_1, ymr_kye_1_time, ymr_kye_1);
									currtime = l > 0 ? l : currtime;
								}
							} else if (fen <= 0.5) {
								if (System.currentTimeMillis() - currtime > 3000) {
									long l = key(robot, KeyEvent.VK_1, ymr_kye_1_time, ymr_kye_1);
									currtime = l > 0 ? l : currtime;
								} else if (System.currentTimeMillis() - currtime > 3000) {
									long l = key(robot, KeyEvent.VK_Q, key_q_time, key_q);
									currtime = l > 0 ? l : currtime;
								}
							} else if (fen <= 0.7) {
								long l = key(robot, KeyEvent.VK_1, ymr_kye_1_time, ymr_kye_1);
								currtime = l > 0 ? l : currtime;
							}
						} else if (profession.equals("死灵法师")) {
							fen = D3_Image.getPercent(robot, type);
							if (type.equals("l")) {
								if (fen <= 0.4) {
									if (System.currentTimeMillis() - currtime > 3000) {
										long l = key(robot, KeyEvent.VK_Q, key_q_time, key_q);
										currtime = l > 0 ? l : currtime;
										if (l > 0) {
											Thread.sleep(2000);
										}
									}
								}
							} else if (type.equals("r")) {
								if (fen >= 0.8) {
									key(robot, KeyEvent.VK_3, 0, 0);
									Thread.sleep(2000);
								}
							}
						}

					}
				} catch (Exception e) {
				}
			}
		});
		cachedThreadPool.shutdown();
	}

	public synchronized static long key(Robot robot, Integer key, long time, long max) {
		try {
			if (System.currentTimeMillis() - time >= max) {
				Thread.sleep(kye_sleep);
				robot.keyPress(key);
				Thread.sleep(kye_sleep);
				robot.keyRelease(key);
				Thread.sleep(kye_sleep * 2);
				System.err.print(KeyEvent.getKeyText(key));
				return System.currentTimeMillis();
			}
		} catch (InterruptedException e) {
		}
		return 0;
	}
}
