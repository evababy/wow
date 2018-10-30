package wow.twow;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import wow.twow.support.WOW;

public class WOWDecompose {

	static WOWDecomposeThread thread = null;

	public static void main(String[] aa) {
		try {
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println(" 启 动 : Alt+E");
			System.out.println(" 暂 停 : Alt+Q");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("参数输入：键值,等待毫秒,次数;...最后没有分号");
			BufferedReader parameter = new BufferedReader(
					new InputStreamReader(System.in));

			String strp = parameter.readLine();
			if (!strp.equals("")) {
				String strArray[] = strp.split(";");
				WOW.decompose = new int[strArray.length][3];
				for (int j1 = 0; j1 < strArray.length; j1++) {
					String strtem[] = strArray[j1].split(",");
					WOW.decompose[j1][0] = Integer.parseInt(strtem[0]);
					WOW.decompose[j1][1] = Integer.parseInt(strtem[1]);
					WOW.decompose[j1][2] = Integer.parseInt(strtem[2]);
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i1 = 0; i1 < WOW.decompose.length; i1++) {
			if (WOW.decompose[i1][0] < 10) {
				System.out.println("按键："
						+ KeyEvent.getKeyText(WOW.decompose[i1][0] + 48)
						+ ",等待：" + WOW.decompose[i1][1] + ",次数："
						+ WOW.decompose[i1][2]);
			} else {
				System.out.println("按键："
						+ KeyEvent.getKeyText(WOW.decompose[i1][0]) + ",等待："
						+ WOW.decompose[i1][1] + ",次数：" + WOW.decompose[i1][2]);
			}
		}
		JIntellitype.getInstance().registerHotKey(1000, JIntellitype.MOD_ALT,
				(int) 'E');
		JIntellitype.getInstance().registerHotKey(2000, JIntellitype.MOD_ALT,
				(int) 'Q');
		HotkeyListener HL = new HotkeyListener() {

			@SuppressWarnings( { "static-access", "deprecation" })
			public void onHotKey(int arg0) {
				WOWMisc b = new WOWMisc();
				if (arg0 == 1000) {
					try {
						thread = new WOWDecomposeThread();
						thread.start();
						System.out.println("启动...");
						b.play(WOW.url_start, 2000, false);
					} catch (Exception e) {
						System.out.println("启动失败...");
						b.play(WOW.url_error, 2000, false);
					}
				} else if (arg0 == 2000) {
					try {
						thread.stop();
						System.out.println("停止...");
						b.play(WOW.url_start, 2000, false);
					} catch (Exception e) {
						System.out.println("停止失败...");
						b.play(WOW.url_error, 2000, false);
					}
				}
			}
		};
		JIntellitype.getInstance().addHotKeyListener(HL);
	}
}
