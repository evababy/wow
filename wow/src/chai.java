import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class chai {

	public static void main(String[] args) {

		try {
			Robot robot = null;
			robot = new Robot();

			String time = "";
			String type_ = "";
			String key = "";
			System.out.println("");
			System.out.println("������������������������");
			System.out.println("");
			System.out.print("�����밴1��ɵ���밴2��");
			BufferedReader zidong = new BufferedReader(new InputStreamReader(
					System.in));
			String zidongstr = zidong.readLine();
			if (zidongstr.equals("1")) {
				System.out.println("");
				System.out.print("��1,�ݣ�2,FM��3:");
				BufferedReader types = new BufferedReader(
						new InputStreamReader(System.in));
				String typestr = types.readLine();
				System.out.println("");
				System.out.print("����1��2��3:");
				BufferedReader keys = new BufferedReader(new InputStreamReader(
						System.in));
				key = keys.readLine();
				if (typestr.equals("1")) {
					time = "2000";
					type_ = "��";
				} else if (typestr.equals("2")) {
					time = "2000";
					type_ = "��";
				} else {
					time = "3000";
					type_ = "FM";
				}
				System.out.println();
				System.out.println("=============���л�����Ϸ����===============");
				Thread.currentThread().sleep(1000);
				System.out.println("===================3=====================");
				Thread.currentThread().sleep(1000);
				System.out.println("===================2=====================");
				Thread.currentThread().sleep(1000);
				System.out.println("===================1=====================");
				Thread.currentThread().sleep(1000);
				System.out.println("===================0=====================");
				Random random = new Random();

				int sui = (int) (random.nextDouble() * 10);
				sui = Integer.parseInt(time) + sui + 2000;
				for (int k = 0; k < 10000; k++) {
					for (int j = 0; j < 10; j++) {
						for (int i = 0; i < 10; i++) {
							if (key.equals("1")) {
								robot.keyPress(KeyEvent.VK_1);
								Thread.currentThread().sleep(100);
								robot.keyRelease(KeyEvent.VK_1);
							} else if (key.equals("2")) {
								robot.keyPress(KeyEvent.VK_2);
								Thread.currentThread().sleep(100);
								robot.keyRelease(KeyEvent.VK_2);
							} else {
								robot.keyPress(KeyEvent.VK_3);
								Thread.currentThread().sleep(100);
								robot.keyRelease(KeyEvent.VK_3);
							}

							Thread.currentThread().sleep(sui);
						}
					}
				}
			} else {
				System.out.println();
				System.out.println("0��أ�9�ֽ⣬8������7��ָ");
				System.out.println();
				System.out.println("==============���л�����Ϸ����==============");
				Thread.currentThread().sleep(1000);
				System.out.println("===================3=====================");
				Thread.currentThread().sleep(1000);
				System.out.println("===================2=====================");
				Thread.currentThread().sleep(1000);
				System.out.println("===================1=====================");
				Thread.currentThread().sleep(1000);
				System.out.println("===================0=====================");
				Random random = new Random();
				int sui = (int) (random.nextDouble() * 10);
				sui = sui + 1900;
				for (int f = 0; f < 1000000; f++) {
					Thread.currentThread().sleep(100);
					robot.keyPress(KeyEvent.VK_0);
					Thread.currentThread().sleep(100);
					robot.keyRelease(KeyEvent.VK_0);
					Thread.currentThread().sleep(2000);
					for (int i = 0; i < 5; i++) {
						robot.keyPress(KeyEvent.VK_7);
						Thread.currentThread().sleep(100);
						robot.keyRelease(KeyEvent.VK_7);
						Thread.currentThread().sleep(sui + 4500);
					}
					for (int j = 0; j < 5; j++) {
						robot.keyPress(KeyEvent.VK_9);
						Thread.currentThread().sleep(100);
						robot.keyRelease(KeyEvent.VK_9);
						Thread.currentThread().sleep(sui + 3000);
					}
					robot.keyPress(KeyEvent.VK_8);
					Thread.currentThread().sleep(100);
					robot.keyRelease(KeyEvent.VK_8);
					Thread.currentThread().sleep(1500);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
