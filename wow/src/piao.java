import java.awt.Robot;
import java.awt.event.MouseEvent;

/**@Comments ：
 * @Author ：Chen Yanji
 * @Date ：2012-1-9 上午09:26:26
 * @Project ：wow 
 * @Company ：Vstsoft
 */
public class piao {

	/**@Comments ：
	 * @param args
	 * @throws Exception 
	 * @Author ：Chen Yanji
	 * @Date ：2012-1-9 上午09:26:26
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		while(true){
		Robot robot = new Robot();
		Thread.currentThread().sleep(10000);
		robot.mousePress(MouseEvent.BUTTON1_MASK);
		Thread.currentThread().sleep(100);
		robot.mouseRelease(MouseEvent.BUTTON1_MASK);
		}
	}

}
