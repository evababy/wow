import java.io.IOException;


public class cmdprint {

	/**
	 * @comments :
	 * @param args
	 * @author : chenyanji
	 * @date : Jan 11, 2011 11:44:42 AM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Runtime.getRuntime().exec("cmd.exe /C start acrord32 /P /h  D:/vst/pdf自动打印/rwservlet.pdf" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
