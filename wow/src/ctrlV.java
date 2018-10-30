import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class ctrlV {

	/**
	 * @comments :
	 * @param args
	 * @author : chenyanji
	 * @date : Dec 20, 2010 1:04:15 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Robot robot;
		try {
			robot = new Robot();
			Thread.currentThread().sleep(5000);
			System.out.println("1");
			copy("aaaaaaaaaaaaaaa");
			Thread.currentThread().sleep(5000);
			System.out.println(2);
			paste("aaaaaaaaaaaaaaa");
//			robot.keyPress(KeyEvent.CTRL_MASK);
//			Thread.currentThread().sleep(100);
//			robot.keyPress(KeyEvent.VK_V);
//			Thread.currentThread().sleep(100);
//			robot.keyRelease(KeyEvent.VK_V);
//			Thread.currentThread().sleep(100);
//			robot.keyRelease(KeyEvent.CTRL_MASK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static public void copy(String str) {
		StringSelection stsel = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel, stsel);
	}
	static public void paste(String str) {
		StringSelection stsel = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().getContents(stsel);
	}

}
